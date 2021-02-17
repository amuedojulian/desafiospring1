package com.desafiospring1.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;

public class HttpClient {

    String status = "";
    org.apache.http.client.HttpClient client = HttpClients.createDefault();
    HttpPost httpPost;
    String json = null;
    HttpResponse response;

    public void post(String url, String cadena, String tipoDado, String vendaId, String file) throws IOException {

        httpPost = new HttpPost(url);

        file = file.substring(1);

        switch (tipoDado) {
            case "vendedores":
                String cpf = cadena.substring(cadena.indexOf("ç")+1, cadena.indexOf("ç", 4));
                String nome = cadena.substring(cadena.indexOf("ç", 4)+1,cadena.lastIndexOf("ç"));
                String salary = cadena.substring(cadena.lastIndexOf("ç")+1);
                json = "{\n" +
                        "    \"salary\": \"" + salary + "\",\n" +
                        "    \"cpf\": \"" + cpf + "\",\n" +
                        "    \"name\": \"" + nome + "\",\n" +
                        "    \"file\": \"" + file + "\"\n" +
                        "}";
            break;

            case "clientes":
                String cnpj = cadena.substring(4, cadena.indexOf("ç", 4));
                String name = cadena.substring(cadena.indexOf("ç", 4)+1, cadena.lastIndexOf("ç"));
                String area = cadena.substring(cadena.lastIndexOf("ç")+1);
                json = "{\n" +
                        "    \"businessArea\": \"" + area + "\",\n" +
                        "    \"cnpj\": \"" + cnpj + "\",\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"file\": \"" + file + "\"\n" +
                        "}";
            break;

            case "ventas":
                json = "{\n" +
                        "    \"file\": \"" + file + "\"\n" +
                        "}";
            break;

            case "item":
                String code = cadena.substring(cadena.indexOf("|")+1, cadena.indexOf("-"));
                String qtd = cadena.substring(cadena.indexOf("-")+1, cadena.lastIndexOf("-"));
                String price = cadena.substring(cadena.lastIndexOf("-")+1);

                json = "{\n" +
                        "    \"itemCode\": \"" + code + "\",\n" +
                        "    \"price\": \"" + price + "\",\n" +
                        "    \"qtd\": \"" + qtd + "\",\n" +
                        "    \"venda\": {\n" +
                        "            \"id\": \"" + vendaId + "\",\n" +
                        "            \"file\": \"" + file + "\"\n" +
                        "    }\n" +
                        "}";
            break;
        }
        StringEntity entity = new StringEntity(json);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setEntity(entity);
        response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 200) {
            status = " [UPLOADED]";
        } else {
            status = " [FAILED]";
        }
        httpPost.reset();
    }
}
