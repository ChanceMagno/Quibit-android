package com.epicodus.quibit.models;

import java.text.DateFormat;
import java.util.Date;

public class Goal {
    String exchangeItem;
    String exchangeCost;
    String exchangeOccurenceRate;
    String goalCreationDate;
    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

    public Goal(String exchangeItem, String exchangeCost, String exchangeOccurenceRate) {
        this.exchangeItem = exchangeItem;
        this.exchangeCost = exchangeCost;
        this.exchangeOccurenceRate = exchangeOccurenceRate;
        this.goalCreationDate = currentDateTimeString;

    }

    public String getExchangeItem() {
        return exchangeItem;
    }

    public String getExchangeCost() {
        return exchangeCost;
    }

    public String getExchangeOccurenceRate() {
        return exchangeOccurenceRate;
    }

    public String getGoalCreationDate() {
        return goalCreationDate;
    }
}

