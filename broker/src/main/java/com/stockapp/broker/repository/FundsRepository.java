package com.stockapp.broker.repository;

import com.stockapp.broker.models.Funds;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FundsRepository extends JpaRepository<Funds, Long> {

    @Query("SELECT f from Funds f WHERE f.clientId=?1")
    Funds findFundsByClientId(String clientId);

    @Transactional
    @Modifying
    @Query("UPDATE Funds f SET availableMargin=?2, totalMargin=?3 WHERE clientId=?1")
    int updateTotalAndAvailableFunds(String clientId, float newAvailableMargin, float newTotalMargin);


    @Transactional
    @Modifying
    @Query("UPDATE Funds f SET availableMargin=?2, usedMargin=?3 WHERE clientId=?1")
    void updateUsedAndAvailableFunds(String clientId, float newAvailableMargin, float usedMargin);
}
