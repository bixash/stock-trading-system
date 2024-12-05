package com.stockapp.broker.repository;


import com.stockapp.broker.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("SELECT t from Transaction t " +
            "WHERE t.clientId=?1 and t.ticker=?2 " +
            "ORDER BY t.id DESC")
    List<Transaction> getLatestTransactionByTicker(String clientId,
                                                   String ticker, Pageable pageable);


}
