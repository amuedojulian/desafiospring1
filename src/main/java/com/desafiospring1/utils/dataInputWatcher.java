package com.desafiospring1.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


import static java.nio.file.StandardWatchEventKinds.*;

public class dataInputWatcher {

    private static  final Logger log = LoggerFactory.getLogger(dataInputWatcher.class);

    public Path directoryToWatch;

    private LinkedList<String> listDataInput;

    public void doWath(String directory) throws IOException {

        // Obtemos o diretório
        if (!new File(directory).exists()) {
            log.error("Diretório não  encontrado, criando diretório em '" + directory + "'");
            new File(directory).mkdirs();
        }
        directoryToWatch = Paths.get(directory);

        // Solicitamos o serviço WatchService
        WatchService watchService = directoryToWatch.getFileSystem().newWatchService();

        // Registramos os eventos que queremos monitorar
        directoryToWatch.register(watchService, new WatchEvent.Kind[] {ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY});

        log.info("Observando mudanças no diretório: " + directoryToWatch.toString());

        try {

            // Esperamos que algo aconteça com o diretório
            WatchKey key = watchService.take();

            // Algo aconteceu no diretório dos eventos registrados
            while (key != null) {
                for (WatchEvent event : key.pollEvents()) {
                    String eventKind = event.kind().toString();
                    String file = event.context().toString();
                    System.out.println("Event : " + eventKind + " in File " +  file);

                    switch (eventKind) {
                        case "ENTRY_CREATE":
                            sort(directory+"\\"+file);
                        break;

                        case "ENTRY_DELETE":

                        break;

                        case "ENTRY_MODIFY":
                            sort(directory+"\\"+file);
                        break;
                    }
                }

                // Ouvimos novamente. Mantemos em loop para ouvir indefinidamente.
                key.reset();
                key = watchService.take();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sort(String archivo) throws IOException {

        listDataInput= new LinkedList<String>();

        FileReader f = new FileReader(archivo);
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
            cadena = (String) iter.next();
            System.out.println(cadena);
        }
    }
}
