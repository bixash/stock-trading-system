# Stock Trading System with Automated Order Trigger

This project simulates a real broker and exchange system in seperate containarized enviornment, simulating as real-time stock market. The Automated Order Trigger (AOT) uses this simulated market data and algorithmic strategies to execute trades based on predefined criteria. The system leverages WebSocket for live data streaming, REST APIs for secure communication, and message queues for sending and receiving orders and trade information between brokers and exchanges.

## High-level Design of System

![system-architecture](/assets/system-architecture.png)

## Working mechanism

![working-mechanism](https://github.com/user-attachments/assets/6225876e-39c7-477a-87a1-6ca85c73a1a7)

## Broker DB Schema

![Broker database schema](/assets/broker-schema.png)

## Exchange DB Schema

![Exchange database Schema](/assets/exchange-schema-drawsql.png)
