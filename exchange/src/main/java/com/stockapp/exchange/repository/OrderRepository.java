package com.stockapp.exchange.repository;

import com.stockapp.exchange.models.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;



@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.status = 'open' and o.orderType ='limit' and o.ticker =?1")
    List<Order> findOpenLimitOrdersByTicker(String ticker);

    @Query("SELECT o FROM Order o WHERE o.orderTime =?1")
    List<Order> findOrderByDate(Date today);

    @Query("select o from Order o where o.status = 'open' and o.orderType ='limit'")
    List<Order> findOpenLimitOrders();

    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'open' and " +
            "o.orderType ='limit' and " +
            "o.ticker=?1 and " +
            "o.price=?2 and " +
            "o.transactionType=?3 ")
   List<Order> findOpenLimitOrder(String ticker, float order_price,
                                  String transactionType, Pageable pageable);


    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'open' and " +
            "o.transactionType='buy' and "+
            "o.orderType='limit' and " +
            "o.ticker=?1 ORDER BY o.price")
    List<Order> findOpenBuyLimitOrder(String ticker, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'open' and " +
            "o.transactionType='sell' and "+
            "o.orderType='limit' and " +
            "o.ticker=?1 ORDER BY o.price DESC")
    List<Order> findOpenSellLimitOrder(String ticker, Pageable pageable);
    @Transactional
    @Modifying
    @Query("Update Order o SET o.tradedQuantity = ?2, " +
            "o.remainingQuantity = ?3, o.status = ?4 " +
            "Where o.orderId =?1")
    void updateOrder(long orderId,
                     long tradedQuantity,
                     long remainingQuantity,
                     String status);

    @Query("SELECT o from Order o where o.status = 'open'")
    List<Order> getActiveOrders();

    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'open' and " +
            "o.transactionType='sell' and "+
            "o.orderType='market' and " +
            "o.ticker=?1")
    List<Order> findOpenSellMarketOrder(String ticker, Pageable pageable);


    @Query("SELECT o FROM Order o WHERE " +
            "o.status = 'open' and " +
            "o.transactionType='buy' and "+
            "o.orderType='market' and " +
            "o.ticker=?1")
    List<Order> findOpenBuyMarketOrder(String ticker, Pageable pageable);
}

