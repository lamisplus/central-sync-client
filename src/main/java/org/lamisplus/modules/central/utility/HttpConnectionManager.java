package org.lamisplus.modules.central.utility;

import com.google.common.hash.Hashing;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class HttpConnectionManager {
    private final OkHttpClient httpClient = new OkHttpClient();


    public String get(String url) throws Exception {

        Request request = new Request.Builder()
                .url(url)
                .addHeader("custom-key", "lamisplus")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot")
                .build();

        try (Response response = httpClient.newBuilder()
                .connectTimeout(60, TimeUnit.MINUTES)
                .writeTimeout(60, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
                .newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + Objects.requireNonNull(response.body()).string());

            // Get response body
            return Objects.requireNonNull(response.body()).string();
        }
    }

    public String post(byte[] bytes, String token, String url) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bytes);
        String hash = Hashing.sha256().hashBytes(bytes).toString();

        Request request = null;
        if(token == null) {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .addHeader("Hash-Value", hash)
                    .addHeader("token", "lamisplus")
                    .post(body)
                    .build();
        }else {
            request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .addHeader("Hash-Value", hash)
                    .addHeader("token", "lamisplus")
                    .addHeader("authorization", token)
                    .post(body)
                    .build();
        }
        try (Response response = httpClient.newBuilder()
                .connectTimeout(300, TimeUnit.MINUTES)
                .writeTimeout(300, TimeUnit.MINUTES)
                .readTimeout(300, TimeUnit.SECONDS)
                .build()
                .newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + Objects.requireNonNull(response.body()).string());

            return Objects.requireNonNull(response.body()).string();
        }
    }
}
