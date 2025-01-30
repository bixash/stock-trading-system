# Stock Trading System implementing AOT

## Introduction
Stock Trading System with the implementation of Automated Order Trigger (AOT) Algorithm, a feature which allows to set a trigger price, if trigger price is hit in a future date, an order will be placed. It simulates a real broker and exchange system, integrating real-time market data and algorithmic strategies to execute trades based on predefined criteria. This enhances efficiency and reduces human error. The system leverages WebSocket for live data streaming, REST APIs for secure communication, and message queues for sending and receiving orders and trade information between brokers and exchanges.

## Working mechanism

![working-mechanism](/assets/working-mechanism.png)

## High-level System Design

![system-architecture](/assets/system-architecture.png)

## Broker DB Schema

![Broker database schema](/assets/broker-schema.png)

## Exchange DB Schema

![Exchange database Schema](/assets/exchange-schema-drawsql.png)
