package com.wy.support.messeage;

/**
 * 事务消息
 */
public interface TxMessage {
    String businessModule();

    String businessKey();

    String content();
}
