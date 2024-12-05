package com.stockapp.broker.repository;

import com.stockapp.broker.models.Holdings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoldingsRepository extends JpaRepository<Holdings, Long> {

    @Query("SELECT h from Holdings h WHERE h.clientId=?1 and h.ticker=?2")
    Optional<Holdings> getHoldingsInfoByTicker(String clientId, String ticker);

    @Transactional
    @Modifying
    @Query("Update Holdings h SET h.averageCost=?2, h.freezeUnits=?3, h.freeUnits=?4, h.currentUnits=?5 " +
            "WHERE h.id=?1")
    void updateHoldingsCurrentAndFreezeUnits(long id, float averageCost, long freezeUnits,
                                             long freeUnits,  long currentUnits);

    @Transactional
    @Modifying
    @Query("Update Holdings h SET h.freeUnits=?2, h.freezeUnits=?3 " +
            "WHERE h.id=?1")
    void updateHoldingsFreeAndFreezeUnits(long id, long freeUnits, long freezeUnits);

    @Query("SELECT h from Holdings h WHERE h.clientId=?1 and h.currentUnits>0")
    List<Holdings> getAllHoldings(String clientId);
}
