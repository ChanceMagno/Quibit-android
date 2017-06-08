package com.epicodus.quibit.models;

import java.text.DateFormat;
import java.util.Date;

public class Goal {
    String RewardId;
    String exchangeItem;
    String exchangeCost;
    String exchangeOccurenceRate;
    String goalCreationDate;
    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

    public Goal(String rewardId, String exchangeItem, String exchangeCost, String exchangeOccurenceRate) {
        RewardId = rewardId;
        this.exchangeItem = exchangeItem;
        this.exchangeCost = exchangeCost;
        this.exchangeOccurenceRate = exchangeOccurenceRate;
        this.goalCreationDate = currentDateTimeString;

    }

    public String getRewardId() {
        return RewardId;
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

