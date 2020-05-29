package com.wy.service;

import com.wy.support.binding.Destination;
import com.wy.support.messeage.TxMessage;

public interface TransactionalMessageService {
    void sendTransactionalMessage(Destination destination, TxMessage message);
}
