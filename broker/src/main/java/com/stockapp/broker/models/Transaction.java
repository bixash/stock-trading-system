package com.stockapp.broker.models;


import jakarta.persistence.*;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_id_sequence", sequenceName = "transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_generator")
    private long id;
    private long tradeId;
    private String clientId;
    private String ticker;
    private long creditQuantity;
    private long debitQuantity;
    private long balanceQuantity;
    private String description;
    private float tradedPrice;
    private String tradeDate;

    public Transaction() {
    }

    public Transaction(long tradeId, String clientId, String ticker, long creditQuantity, long debitQuantity,
                       long balanceQuantity, String description, float tradedPrice, String tradeDate) {
        this.tradeId = tradeId;
        this.clientId = clientId;
        this.ticker = ticker;
        this.creditQuantity = creditQuantity;
        this.debitQuantity = debitQuantity;
        this.balanceQuantity = balanceQuantity;
        this.description = description;
        this.tradedPrice = tradedPrice;
        this.tradeDate = tradeDate;
    }

    public Transaction(String clientId, String ticker, long creditQuantity, long debitQuantity, long balanceQuantity,
                       String description, float tradedPrice, String tradeDate) {
        this.clientId = clientId;
        this.ticker = ticker;
        this.creditQuantity = creditQuantity;
        this.debitQuantity = debitQuantity;
        this.balanceQuantity = balanceQuantity;
        this.description = description;
        this.tradedPrice = tradedPrice;
        this.tradeDate = tradeDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public long getCreditQuantity() {
        return creditQuantity;
    }

    public void setCreditQuantity(long creditQuantity) {
        this.creditQuantity = creditQuantity;
    }

    public long getDebitQuantity() {
        return debitQuantity;
    }

    public void setDebitQuantity(long debitQuantity) {
        this.debitQuantity = debitQuantity;
    }

    public long getBalanceQuantity() {
        return balanceQuantity;
    }

    public void setBalanceQuantity(long balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getTradedPrice() {
        return tradedPrice;
    }

    public void setTradedPrice(float tradedPrice) {
        this.tradedPrice = tradedPrice;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }
}
