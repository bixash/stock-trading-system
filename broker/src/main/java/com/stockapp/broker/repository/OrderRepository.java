package com.stockapp.broker.repository;


import com.stockapp.broker.models.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.ticker =?1")
    List<Order> findOrderByTicker(String ticker);

    @Query("SELECT o FROM Order o WHERE o.orderTime =?1")
    List<Order> findOrderByDate(Date today);

    @Query("select o from Order o where o.status = 'active' and o.orderType ='limit'")
    List<Order> findActiveLimitOrders();

    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'active' and " +
            "o.orderType ='limit' and " +
            "o.ticker=?1 and " +
            "o.price=?2 and " +
            "o.transactionType=?3 ")
   List<Order> findActiveLimitOrder(String ticker, float order_price,
                                    String transactionType, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update Order o " +
            "SET o.tradedQuantity = ?2, " +
            "o.remainingQuantity = ?3, " +
            "o.status = ?4 " +
            "Where o.orderId =?1")
    void updateOrder(long orderId,
                     long tradedQuantity,
                     long remainingQuantity,
                     String status);

    @Query("SELECT o from Order o WHERE o.clientId=?1 " +
            "AND o.orderDate=?2 AND o.status IN ('pending', 'open')")
    List<Order> getOpenOrders(String clientId, String date);

    @Query("SELECT o from Order o " +
            "WHERE o.clientId=?1 AND o.orderDate=?2 AND " +
            "o.status IN ('rejected','cancelled', 'complete')")
    List<Order> getExecutedOrders(String clientId, String date);

    @Query("SELECT o from Order o WHERE o.clientId=?1 " +
            "AND o.orderDate=?2 AND o.price=?3 AND o.status IN ('pending', 'open')")
    Optional<Order> getOpenOrdersByPrice(String clientId, String orderDate, float price);
}

