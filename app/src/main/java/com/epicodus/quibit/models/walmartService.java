package com.epicodus.quibit.models;
import android.app.DownloadManager;
import android.util.Log;

import com.epicodus.quibit.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class walmartService {
    public static final String TAG = walmartService.class.getSimpleName();

    public static void searchItems(String item, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WALMART_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.WALMART_QUERY_PARAMETER, item);
        urlBuilder.addQueryParameter(Constants.WALMART_FORMAT_PARAMETER, "json");
        urlBuilder.addQueryParameter("apiKey", Constants.WALMART_API_KEY);
        String url = urlBuilder.build().toString();

        Log.d("url", url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
