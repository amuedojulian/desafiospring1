package com.desafiospring1;

import com.desafiospring1.utils.DataInputReader;
import com.desafiospring1.utils.DataOutputWrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

@SpringBootApplication
@EnableCaching
public class Desafiospring1Application {

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(Desafiospring1Application.class, args);
        DataInputReader dataInputReader = new DataInputReader();
        DataOutputWrite dataWrite =  new DataOutputWrite();
        Files.walk(Paths.get("data\\in")).forEach(ruta-> {
            if (Files.isRegularFile(ruta)) {
                try {
                    String file = ruta.toString().substring(7);
                    dataInputReader.sortPost("data\\in", file);
                    if (!new File("data\\out").exists()) {
                        new File("data\\out").mkdirs();
                    }
                    dataWrite.createReport("data\\out"+ruta.toString().substring(7), file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dataInputReader.listen("data\\in");
    }

}
