package org.lamisplus.modules.central.utility;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Slf4j
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
                    //.addHeader("authorization", token)
                    .post(body)
                    .build();
        }
        try (Response response = httpClient.newBuilder()
                .connectTimeout(10000, TimeUnit.MINUTES)
                .writeTimeout(10000, TimeUnit.MINUTES)
                .readTimeout(10000, TimeUnit.MINUTES)
                .build()
                .newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + Objects.requireNonNull(response.body()).string());


            return Objects.requireNonNull(response.body()).string();
        }
    }

    public String post(LoginVM loginVM, String token, String url) throws IOException {
        log.info("username",loginVM.getUsername());
        log.info("password",loginVM.getPassword());
        // create your json here
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", loginVM.getUsername().trim());
            jsonObject.put("password", loginVM.getPassword().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // put your json here
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = null;
        request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newBuilder()
                .connectTimeout(10000, TimeUnit.MINUTES)
                .writeTimeout(10000, TimeUnit.MINUTES)
                .readTimeout(10000, TimeUnit.MINUTES)
                .build()
                .newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + Objects.requireNonNull(response.body()).string());

            return Objects.requireNonNull(response.body()).string();
        }
    }
}
