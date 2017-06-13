package com.epicodus.quibit.services;
import android.util.Log;

import com.epicodus.quibit.constants.Constants;
import com.epicodus.quibit.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class walmartService {

    public static void searchItems(String item, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WALMART_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.WALMART_QUERY_PARAMETER, item);
        urlBuilder.addQueryParameter(Constants.WALMART_FORMAT_PARAMETER, "json");
        urlBuilder.addQueryParameter(Constants.WALMART_NUMBER_PARAMETER, "25");
        urlBuilder.addQueryParameter("apiKey", Constants.WALMART_API_KEY);

        String url = urlBuilder.build().toString();

        Log.d("url", url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Item> processResults(Response response) {
        ArrayList<Item> items = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject itemAPIJSON = new JSONObject(jsonData);
                JSONArray itemListJSON = itemAPIJSON.getJSONArray("items");
                for (int i = 0; i < itemListJSON.length(); i++) {
                    JSONObject itemJSON = itemListJSON.getJSONObject(i);
                    String id = itemJSON.getString("itemId");
                    String name = itemJSON.optString("name");
                    String msrp = itemJSON.optString("msrp");
                    String salePrice = itemJSON.optString("salePrice");
                    String description = itemJSON.optString("shortDescription");
                    String thumbnailImage = itemJSON.optString("thumbnailImage");
                    String mediumImage = itemJSON.optString("mediumImage");
                    String largeImage = itemJSON.optString("largeImage");
                    String rating = itemJSON.optString("customerRating") + "000";
                    String purchaseLink = itemJSON.optString("addToCartUrl");

                    Item itemInstance = new Item(id, name, msrp, salePrice, description, thumbnailImage, mediumImage, largeImage, rating, purchaseLink);
                    items.add(itemInstance);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } return items;

    }

}