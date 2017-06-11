package com.epicodus.quibit.models;

import org.parceler.Parcel;

import java.text.DateFormat;
import java.util.Date;
@Parcel
public class Quibit {
    String exchangeItem;
    String exchangeCost;
    String exchangeOccurenceRate;
    String quibitCreationDate;

    Quibit(){}

    public Quibit(String exchangeItem, String exchangeCost, String exchangeOccurenceRate) {
        this.exchangeItem = exchangeItem;
        this.exchangeCost = exchangeCost;
        this.exchangeOccurenceRate = exchangeOccurenceRate;
        this.quibitCreationDate =  DateFormat.getDateTimeInstance().format(new Date());

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

    public String getQuibitCreationDate() {
        return quibitCreationDate;
    }
}

