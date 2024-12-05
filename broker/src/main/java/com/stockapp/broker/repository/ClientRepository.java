package com.stockapp.broker.repository;



import com.stockapp.broker.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("SELECT c.brokerId FROM Client c where c.id=?1")
    Long findBrokerId(String username);

    @Query("SELECT c.watchlist FROM Client c where c.id=?1")
    String[] getWatchList(String username);

}
