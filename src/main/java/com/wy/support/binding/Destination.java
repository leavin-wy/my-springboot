package com.wy.support.binding;

public interface Destination {
    ExchangeType exchangeType();

    String queueName();

    String exchangeName();

    String routingKey();
}
