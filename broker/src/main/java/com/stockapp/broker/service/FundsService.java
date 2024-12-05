package com.stockapp.broker.service;

import com.stockapp.broker.dto.OrderInfo;
import com.stockapp.broker.models.Funds;
import com.stockapp.broker.models.Order;
import com.stockapp.broker.repository.FundsRepository;
import org.springframework.stereotype.Service;

@Service
public class FundsService {

    private final FundsRepository fundsRepository;

    public FundsService(FundsRepository fundsRepository) {
        this.fundsRepository = fundsRepository;
    }
    public Funds getFundsInfo(String clientId) {
        return fundsRepository.findFundsByClientId(clientId);
    }

    public int loadFunds(String clientId, float cash) {
        Funds oldFunds = getFundsInfo(clientId);
        float newAvailableMargin =  oldFunds.getAvailableMargin() + cash;
        float newTotalMargin = oldFunds.getTotalMargin() + cash;
        return fundsRepository.updateTotalAndAvailableFunds(clientId, newAvailableMargin, newTotalMargin);
    }

    public void createFunds(String clientId) {
        Funds funds = new Funds(clientId, 0, 0, 0 );
        fundsRepository.save(funds);
    }

    public void updateFunds(String clientId, float usedMargin) {
        Funds oldFunds = getFundsInfo(clientId);
        float newUsedMargin = oldFunds.getUsedMargin() + usedMargin;
        float newAvailableMargin =  oldFunds.getAvailableMargin() - newUsedMargin;
        fundsRepository.updateUsedAndAvailableFunds(clientId, newAvailableMargin, newUsedMargin);
    }

    public float getAvailableFunds(String username) {
       Funds funds =  getFundsInfo(username);
       return funds.getAvailableMargin();
    }


    public OrderInfo isFundsAvailable(Order order){
        float totalFundsNeeded =  order.getPrice() * order.getOrderQuantity();
        if(getAvailableFunds(order.getClientId()) >= totalFundsNeeded){
            updateFunds(order.getClientId(), totalFundsNeeded);
            return new OrderInfo(true, "Funds is available!");
        }
        return new OrderInfo(false, "Insufficient cash!");


    }

}
