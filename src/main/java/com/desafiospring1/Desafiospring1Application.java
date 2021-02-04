package com.desafiospring1;

import com.desafiospring1.utils.dataInputWatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class Desafiospring1Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Desafiospring1Application.class, args);
        dataInputWatcher dataInputWatcher = new dataInputWatcher();
        dataInputWatcher.doWath("C:\\data\\in");
    }

}
