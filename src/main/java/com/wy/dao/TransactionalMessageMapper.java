package com.wy.dao;

import com.wy.entity.TransactionalMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TransactionalMessageMapper {
    void insertSelective(TransactionalMessage record);

    void updateStatusSelective(TransactionalMessage record);

    List<TransactionalMessage> queryPendingCompensationRecords(LocalDateTime minScheduleTime,
                                                               LocalDateTime maxScheduleTime,
                                                               int limit);
}
