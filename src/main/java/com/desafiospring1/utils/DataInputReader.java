package com.desafiospring1.utils;

import com.desafiospring1.services.ClienteService;
import com.desafiospring1.services.VendaService;
import com.desafiospring1.services.VendedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;
import static java.nio.file.StandardWatchEventKinds.*;

@Service
@Component
public class DataInputReader implements Runnable {

    private static  final Logger log = LoggerFactory.getLogger(DataInputReader.class);

    private WatchService watchService;

    HttpClient client = new HttpClient();

    @Autowired
    DataOutputWrite dataWriter;

    @Autowired
    ClienteService clienteService;

    @Autowired
    VendaService vendaService;

    @Autowired
    VendedorService vendedorService;

    public DataInputReader() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void run() {
        setup("data\\in");
        for (;;) {
            try {
                executeExistsFiles("data\\in");
                listen("data\\in");
            } catch (IOException | InterruptedException e) {
                log.error("Error DataInputReader.run()");
                throw new RuntimeException(e);
            }
        }
    }

    public void listen(String directory) throws InterruptedException, IOException {
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                String eventKind = event.kind().toString();
                Object context = event.context();
                String file = context.toString();

                switch (eventKind) {
                    case "ENTRY_CREATE":
                        System.out.println("Event : " + eventKind + " in File " +  file);
                        runReadFile(directory, file);
                        dataWriter.createReport("data\\out\\"+file, file);
                    break;
                    case "ENTRY_DELETE":
                        System.out.println("Event : " + eventKind + " in File " +  file);
                        clienteService.delete(file);
                        vendaService.delete(file);
                        vendedorService.delete(file);
                    break;
                }
            }
            key.reset();
        }
    }

    public static Stream<String> readFile(Path directory, String file) throws IOException {
        Path path = Paths.get(directory+"\\"+file);
        return Files.lines(path, StandardCharsets.UTF_8).filter(l -> !l.isEmpty());
    }

    public void runReadFile(String directory, String file) throws IOException{
        Path path = Paths.get(directory);
        Stream<String> fileR = readFile(path, file);
        fileR.forEach(cadena -> {
            try {
                processLine(cadena, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void processLine(String cadena, String file) throws IOException {
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
                optionalFile = "/" + file;
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

    private void setup(String directory) {
        if (!new File(directory).exists()) {
            log.error("Diretório não  encontrado, criando diretório em '" + directory + "'");
            new File(directory).mkdirs();
        }
        try {
            File dir = new File(directory);
            dir.mkdirs();
            this.watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(dir.getAbsolutePath());
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, ENTRY_DELETE);
            log.info("Observando novas entradas de arquivo no diretório: " + dir.getAbsolutePath());
        } catch (IOException e) {
            log.error("Setup problems");
        }
    }

    private void executeExistsFiles(String directory) throws IOException {
        File file = new File(directory);
        String[] files = file.list();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                runReadFile(directory, files[i]);
            }
        }
    }


}
