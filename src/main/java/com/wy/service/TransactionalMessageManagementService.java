package com.wy.service;

import com.wy.dao.TransactionalMessageContentMapper;
import com.wy.dao.TransactionalMessageMapper;
import com.wy.entity.TransactionalMessage;
import com.wy.entity.TransactionalMessageContent;
import com.wy.support.DateUtil;
import com.wy.support.messeage.TxMessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionalMessageManagementService {

    @Autowired
    private TransactionalMessageMapper messageDao;
    @Autowired
    private TransactionalMessageContentMapper contentDao;

    private final RabbitTemplate rabbitTemplate;

    private static final long DEFAULT_INIT_BACKOFF = 10L;
    private static final int DEFAULT_BACKOFF_FACTOR = 2;
    private static final int DEFAULT_MAX_RETRY_TIMES = 5;
    private static final int LIMIT = 100;

    public void saveTransactionalMessageRecord(TransactionalMessage record, String content) {
        record.setMessageStatus(TxMessageStatus.PENDING.getStatus());
        record.setNextScheduleTime(calculateNextScheduleTime(DateUtil.getTime(), DEFAULT_INIT_BACKOFF,
                DEFAULT_BACKOFF_FACTOR, 0));
        record.setCurrentRetryTimes(0);
        record.setInitBackoff(DEFAULT_INIT_BACKOFF);
        record.setBackoffFactor(DEFAULT_BACKOFF_FACTOR);
        record.setMaxRetryTimes(DEFAULT_MAX_RETRY_TIMES);
        messageDao.insertSelective(record);
        TransactionalMessageContent messageContent = new TransactionalMessageContent();
        messageContent.setContent(content);
        messageContent.setMessageId(record.getId());
        messageContent.setId(System.currentTimeMillis());
        contentDao.insert(messageContent);
    }

    public void sendMessageSync(TransactionalMessage record, String content) {
        try {
            rabbitTemplate.convertAndSend(record.getExchangeName(), record.getRoutingKey(), content);
            if (log.isDebugEnabled()) {
                log.debug("发送消息成功,目标队列:{},消息内容:{}", record.getQueueName(), content);
            }
            // 标记成功
            markSuccess(record);
        } catch (Exception e) {
            // 标记失败
            markFail(record, e);
        }
    }

    private void markSuccess(TransactionalMessage record) {
        // 标记下一次执行时间为最大值
        record.setNextScheduleTime("9999-12-31 23:59:59");
        record.setCurrentRetryTimes(record.getCurrentRetryTimes().compareTo(record.getMaxRetryTimes()) >= 0 ?
                record.getMaxRetryTimes() : record.getCurrentRetryTimes() + 1);
        record.setMessageStatus(TxMessageStatus.SUCCESS.getStatus());
        record.setEditTime(DateUtil.getTime());
        messageDao.updateStatusSelective(record);
    }

    private void markFail(TransactionalMessage record, Exception e) {
        log.error("发送消息失败,目标队列:{}", record.getQueueName(), e);
        record.setCurrentRetryTimes(record.getCurrentRetryTimes().compareTo(record.getMaxRetryTimes()) >= 0 ?
                record.getMaxRetryTimes() : record.getCurrentRetryTimes() + 1);
        // 计算下一次的执行时间
        String nextScheduleTime = calculateNextScheduleTime(
                record.getNextScheduleTime(),
                record.getInitBackoff(),
                record.getBackoffFactor(),
                record.getCurrentRetryTimes()
        );
        //Date nextTime = Date.from(nextScheduleTime.atZone( ZoneId.systemDefault()).toInstant());
        record.setNextScheduleTime(nextScheduleTime);
        record.setMessageStatus(TxMessageStatus.FAIL.getStatus());
        record.setEditTime(DateUtil.getTime());
        messageDao.updateStatusSelective(record);
    }

    /**
     * 计算下一次执行时间
     *
     * @param dateStr          基础时间
     * @param initBackoff   退避基准值
     * @param backoffFactor 退避指数
     * @param round         轮数
     * @return String
     */
    private String calculateNextScheduleTime(String dateStr,
                                                    long initBackoff,
                                                    long backoffFactor,
                                                    long round) {
        LocalDateTime localDateTime = DateUtil.parse(dateStr,"yyyy-MM-dd HH:mm:ss").toInstant()
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();
        double delta = initBackoff * Math.pow(backoffFactor, round);
        return localDateTime.plusSeconds((long) delta).toString();
    }

    /**
     * 推送补偿 - 里面的参数应该根据实际场景定制
     */
    public void processPendingCompensationRecords() {
        // 时间的右值为当前时间减去退避初始值，这里预防把刚保存的消息也推送了
        LocalDateTime max = LocalDateTime.now().plusSeconds(-DEFAULT_INIT_BACKOFF);
        // 时间的左值为右值减去1小时
        LocalDateTime min = max.plusHours(-1);
        Map<Long, TransactionalMessage> collect = messageDao.queryPendingCompensationRecords(min, max, LIMIT)
                .stream()
                .collect(Collectors.toMap(TransactionalMessage::getId, x -> x));
        if (!collect.isEmpty()) {
            StringJoiner joiner = new StringJoiner(",", "(", ")");
            collect.keySet().forEach(x -> joiner.add(x.toString()));
            contentDao.queryByMessageIds(joiner.toString())
                    .forEach(item -> {
                        TransactionalMessage message = collect.get(item.getMessageId());
                        sendMessageSync(message, item.getContent());
                    });
        }else{
            log.info("未查询到需要补偿失推送的数据...");
        }
    }
}
