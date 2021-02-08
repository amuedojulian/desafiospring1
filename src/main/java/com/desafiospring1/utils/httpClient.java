package com.desafiospring1.utils;

import com.desafiospring1.controller.ClienteController;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

public class httpClient {

    String status = "OK";

    HttpClient client = HttpClients.createDefault();

    public void post(String url, String cadena) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        String json = "{\n" +
                "    \"businessArea\": \"Mirar TV\",\n" +
                "    \"cnpj\": \"9999999999999999\",\n" +
                "    \"name\": \"MONI ARGENTO\"\n" +
                "}";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == 200) {
            status = "OK";
        } else {
            status = "NULL";
        }
    }

    public void put(String url, String cadena) throws IOException {
        HttpPut httpPut = new HttpPut(url);
        String json = "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"businessArea\": \"Mirar TV\",\n" +
                "    \"cnpj\": \"9999999999999999\",\n" +
                "    \"name\": \"MONI ARGENTO\"\n" +
                "}";
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpPut);
        System.out.println(response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == 200) {
            status = "OK";
        } else {
            status = "NULL";
        }
    }


}
