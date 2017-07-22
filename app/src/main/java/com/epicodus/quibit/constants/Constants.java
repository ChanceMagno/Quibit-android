package com.epicodus.quibit.constants;

import com.epicodus.quibit.BuildConfig;

public class Constants {

    //api
    public static final String WALMART_API_KEY = BuildConfig.WALMART_API_KEY;
    public static final String WALMART_BASE_URL = "http://api.walmartlabs.com/v1/search";
    public static final String WALMART_QUERY_PARAMETER = "query";
    public static final String WALMART_FORMAT_PARAMETER = "format";
    public static final String WALMART_NUMBER_PARAMETER = "numItems";
    public static final String PREFERENCES_GOALVALUE_KEY = "goalValue";

    //database references
    public static final String FIREBASE_QUERY_INDEX = "index";
    public static final String FIREBASE_QUERY_USERS = "users";
    public static final String FIREABASE_QUERY_GOAL = "goal";
    public static final String FIREABASE_QUERY_SALE_PRICE = "salePrice";
}

