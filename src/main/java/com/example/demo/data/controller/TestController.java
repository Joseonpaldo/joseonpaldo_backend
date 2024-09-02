package com.example.demo.data.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
public class TestController {
    
    @GetMapping("/returnCookie") 
    public String returnCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("accessToken")) {
                System.out.println("getting cookie at return cookie");
                accessToken = cookie.getValue();
            }
        }
    
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/user?user_id=20"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + accessToken)
            .GET()
            .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String responseBody = httpResponse.body();
            System.out.println(responseBody + " from return cookie");
            return responseBody;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        return null; // Add this line to return a default value if an exception occurs.
    }
}
