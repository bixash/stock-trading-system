package com.stockapp.broker.service;

import com.stockapp.broker.dto.OrderDto;
import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.models.Holdings;
import com.stockapp.broker.models.Order;
import com.stockapp.broker.repository.HoldingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoldingsService {
    private final HoldingsRepository holdingsRepository;
    public HoldingsService(HoldingsRepository holdingsRepository) {
        this.holdingsRepository = holdingsRepository;
    }

    public OrderInfo isSellUnitsAvailable(Order order) {
        Optional<Holdings> optionalHoldings = holdingsRepository.getHoldingsInfoByTicker(order.getClientId(),
                order.getTicker());

        if (optionalHoldings.isPresent()) {
            Holdings holdings = optionalHoldings.get();

            if (holdings.getFreeUnits() >= order.getOrderQuantity()) {
                long newFreeUnits = holdings.getFreeUnits()-order.getOrderQuantity();
                holdingsRepository.updateHoldingsFreeAndFreezeUnits(holdings.getId(),
                        newFreeUnits, order.getOrderQuantity());
                return new OrderInfo(true, "Valid holdings!");
            }
            return new OrderInfo(false,
                    STR."Insufficent holdings of \{holdings.getTicker()}! Available units: \{holdings.getFreeUnits()} ");
        }
        return new OrderInfo(
                false,
                STR."There's no holdings of \{order.getTicker()}!");
    }

    public void updateHoldingsCurrentAndFreezeUnits(long id, float averageCost, long freezeUnits,
                                                    long freeUnits, long currentUnits) {
        holdingsRepository.updateHoldingsCurrentAndFreezeUnits( id, averageCost, freezeUnits, freeUnits, currentUnits);
    }

    public Holdings getHoldingsInfo(String clientId, String ticker) {
        Optional<Holdings> optionalHoldings = holdingsRepository.getHoldingsInfoByTicker(clientId, ticker);
        return optionalHoldings.orElse(null);
    }
    public List<Holdings> getAllHoldingsInfo(String clientId) {
        return holdingsRepository.getAllHoldings(clientId);
    }

    public void saveHoldings(Holdings holdings) {
        holdingsRepository.save(holdings);
    }
}