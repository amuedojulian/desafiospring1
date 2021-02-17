package com.desafiospring1.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


import static java.nio.file.StandardWatchEventKinds.*;

public class DataInputReader {

    private static  final Logger log = LoggerFactory.getLogger(DataInputReader.class);
    public Path directoryToWatch;
    HttpClient client = new HttpClient();
    String file;
    DataOutputWrite dataWriter =  new DataOutputWrite();

    public void listen(String directory) throws IOException {

        // Obtemos o diretório
        if (!new File(directory).exists()) {
            log.error("Diretório não  encontrado, criando diretório em '" + directory + "'");
            new File(directory).mkdirs();
        }
        directoryToWatch = Paths.get(directory);

        // Solicitamos o serviço WatchService
        WatchService watchService = directoryToWatch.getFileSystem().newWatchService();

        // Registramos os eventos que queremos monitorar
        directoryToWatch.register(watchService, new WatchEvent.Kind[] {ENTRY_CREATE});

        log.info("Observando mudanças no diretório: " + directoryToWatch.toString());

        try {

            // Esperamos que algo aconteça com o diretório
            WatchKey key = watchService.take();

            // Algo aconteceu no diretório dos eventos registrados
            while (key != null) {
                for (WatchEvent event : key.pollEvents()) {
                    String eventKind = event.kind().toString();
                    file = event.context().toString();

                    System.out.println("Event : " + eventKind + " in File " +  file);

                    sortPost(directory, file);
                    dataWriter.createReport("data\\out\\"+file, file);
                }
                // Ouvimos novamente. Mantemos em loop para ouvir indefinidamente.
                key.reset();
                key = watchService.take();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sortPost(String directory, String file) throws IOException{
        LinkedList<String> listDataInput = new LinkedList<String>();
        FileReader f = new FileReader(directory+"\\"+file);
        BufferedReader b = new BufferedReader(f);
        String linea = null;
        while ((linea = b.readLine()) != null){
            listDataInput.add(linea);
        }
        Collections.sort(listDataInput);
        b.close();
        f.close();

        Iterator iter = listDataInput.iterator();
        String cadena;
        while (iter.hasNext())
        {
            cadena = iter.next().toString();
            String id = cadena.substring(0, 3);
            switch (id) {
                case "001":
                    id = "vendedores";
                    break;
                case "002":
                    id = "clientes";
                    break;
                case "003":
                    id = "vendas";
                    break;
            }
            try {
                String venda_id = "";
                String vendedorName = "";
                String optionalFile = "";
                if (id.equals("vendas")) {
                    venda_id = "/" + cadena.substring(4, cadena.indexOf("ç", 4));
                    vendedorName = "/" + cadena.substring(cadena.lastIndexOf("ç")+1);
                    optionalFile = "/" + file.substring(1);
                }
                client.post("http://localhost:8080/api/" + id + "/add" + venda_id + vendedorName + optionalFile, cadena, id, cadena.substring(4, cadena.indexOf("ç", 4)), file);
                venda_id = "";
                vendedorName = "";
                optionalFile = "";
                if (id.equals("vendas")) {
                    String cadena_item = "";
                    for (int i = 3; i <= cadena.length()-1; i++) {
                        if (cadena.substring(i,i+1).equals("[") || cadena.substring(i,i+1).equals(",")) {
                            if (cadena.lastIndexOf(",") == i) {
                                cadena_item = cadena.substring(i+1, cadena.indexOf("]", i+1));
                            } else {
                                cadena_item = cadena.substring(i+1, cadena.indexOf(",", i+1));
                            }
                            client.post("http://localhost:8080/api/items/add", cadena_item, "item", cadena.substring(cadena.indexOf("ç")+1, cadena.indexOf("[")-1), file);
                        }
                    }
                }
            } catch (Exception e) { System.out.println(e.toString() + " Erro ao inserir, tente inserir os dados corretamente."); }
            cadena+=client.status;
            System.out.println(cadena);
        }
    }
}
