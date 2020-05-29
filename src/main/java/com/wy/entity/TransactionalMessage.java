package com.wy.entity;

import lombok.Data;
import java.util.Date;

@Data
public class TransactionalMessage {

    private Long id;
    private String createTime;
    private String editTime;
    private String creator;
    private String editor;
    private Integer deleted;
    private Integer currentRetryTimes;
    private Integer maxRetryTimes;
    private String queueName;
    private String exchangeName;
    private String exchangeType;
    private String routingKey;
    private String businessModule;
    private String businessKey;
    private String nextScheduleTime;
    private Integer messageStatus;
    private Long initBackoff;
    private Integer backoffFactor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getCurrentRetryTimes() {
        return currentRetryTimes;
    }

    public void setCurrentRetryTimes(Integer currentRetryTimes) {
        this.currentRetryTimes = currentRetryTimes;
    }

    public Integer getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(Integer maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getBusinessModule() {
        return businessModule;
    }

    public void setBusinessModule(String businessModule) {
        this.businessModule = businessModule;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getNextScheduleTime() {
        return nextScheduleTime;
    }

    public void setNextScheduleTime(String nextScheduleTime) {
        this.nextScheduleTime = nextScheduleTime;
    }

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Long getInitBackoff() {
        return initBackoff;
    }

    public void setInitBackoff(Long initBackoff) {
        this.initBackoff = initBackoff;
    }

    public Integer getBackoffFactor() {
        return backoffFactor;
    }

    public void setBackoffFactor(Integer backoffFactor) {
        this.backoffFactor = backoffFactor;
    }
}
