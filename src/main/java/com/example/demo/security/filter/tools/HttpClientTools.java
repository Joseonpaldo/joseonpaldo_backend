package com.example.demo.security.filter.tools;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

import java.net.http.HttpClient;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpClientTools {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    Dotenv dotenv = Dotenv.load();

    public void sendRequest(String provider, String token) {
        if(provider == "google") {
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("https://oauth2.googleapis.com/revoke?token=" + token))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

            try {
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }else if(provider == "kakao") {
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("https://kapi.kakao.com/v1/user/logout" ))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
            try {
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }    
        }else if(provider == "naver") {
            String url = "https://nid.naver.com/oauth2.0/token?grant_type=delete"
                    + "&client_id=" + dotenv.get("NAVER_CLIENT_ID")
                    + "&client_secret=" + dotenv.get("NAVER_CLIENT_SECRET")
                    + "access_token=" + token + "&service_provider=NAVER";

            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
            try {
                httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } 
        }
    }
}