package com.stockapp.broker.repository;

import com.stockapp.broker.models.GTTOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface GTTRepository extends JpaRepository<GTTOrder, Long> {

    @Query("SELECT g from GTTOrder g WHERE g.clientId=?1")
    List<GTTOrder> getGttOrdersByClientId(String clientId);

    @Query("SELECT g from GTTOrder g WHERE g.ticker=?1 and g.triggerType=?2")
    List<GTTOrder> fetchSingleGttOrdersByTicker(String ticker, String triggerType);
    @Query("SELECT g from GTTOrder g")
    List<GTTOrder> fetchAllGttOrders();
    @Query("SELECT g from GTTOrder g " +
            "WHERE g.ticker=?1 and g.triggerPrice<=?2 " +
            "and g.triggerType=?3 and g.status='active'")
    List<GTTOrder> fetchActiveGttByTickerAndTrigger(String ticker, float lastTradedPrice, String triggerType);
    @Transactional
    @Modifying
    @Query("UPDATE GTTOrder g SET g.status='triggered' WHERE g.id=?1")
    void updateStatusToTriggered(long id);
}
