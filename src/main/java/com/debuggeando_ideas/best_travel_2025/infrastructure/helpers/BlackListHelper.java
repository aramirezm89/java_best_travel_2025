package com.debuggeando_ideas.best_travel_2025.infrastructure.helpers;


import com.debuggeando_ideas.best_travel_2025.util.Exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

    public void isBlackListed(String customerId){
        if(customerId.equals("BBMB771012HMCRR022")){
            throw new ForbiddenCustomerException();
        }
    }
}
