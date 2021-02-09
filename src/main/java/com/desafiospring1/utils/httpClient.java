package com.desafiospring1.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class httpClient {

    String status = "";
    HttpClient client = HttpClients.createDefault();
    String id = "0";
    String json = null;

    public void post(String url, String cadena, String tipoDado) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        json(tipoDado, cadena);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            status = " [UPLOADED]";
        } else {
            status = " [FAILED]";
        }
    }

    public void put(String url, String cadena, String tipoDado) throws IOException {
        getId("http://localhost:8080/api/" + tipoDado, cadena);
        url+="/" + this.id;
        HttpPut httpPut = new HttpPut(url);
        json(tipoDado, cadena);
        StringEntity entity = new StringEntity(json);
        httpPut.setEntity(entity);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpPut);
        if (response.getStatusLine().getStatusCode() == 200) {
            status = " [UPLOADED]";
        } else {
            status = " [FAILED]";
        }
    }

    public void getId(String url, String cadena) throws IOException {
        String tipoId = "";
        switch (cadena.substring(0, 3)) {
            case "001":
                tipoId = "cpf";
                break;
            case "002":
                tipoId = "cnpj";
                break;
            case "003":
                tipoId = "vendas";
                break;
        }
        url+="/" + tipoId + "/" + cadena.substring(4, cadena.indexOf("ç", 4));
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity);
            this.id = result.substring(14, result.indexOf(",", 14));
        }
    }

    public void json(String tipoDado, String cadena) throws IOException {
        switch (tipoDado) {
            case "vendedores":

            case "clientes":
                String cnpj = cadena.substring(4, cadena.indexOf("ç", 4));
                String name = cadena.substring(cadena.indexOf("ç", 4)+1, cadena.lastIndexOf("ç"));
                String area = cadena.substring(cadena.lastIndexOf("ç")+1);
                json = "{\n" +
                        "    \"businessArea\": \"" + area + "\",\n" +
                        "    \"cnpj\": \"" + cnpj + "\",\n" +
                        "    \"name\": \"" + name + "\"\n" +
                        "}";
                break;
            case "ventas":

            break;
        }
    }

}
