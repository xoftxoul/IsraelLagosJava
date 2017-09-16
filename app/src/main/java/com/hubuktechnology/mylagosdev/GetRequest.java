package com.hubuktechnology.mylagosdev;

import android.app.DownloadManager;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by israelxoftsoul  on 14/09/2017 AD.
 */

public class GetRequest {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
       Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
