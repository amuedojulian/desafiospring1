package com.desafiospring1;

import com.desafiospring1.utils.dataInputReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableCaching
public class Desafiospring1Application {

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(Desafiospring1Application.class, args);
        dataInputReader dataInputReader = new dataInputReader();
        dataInputReader.listen("data\\in");
    }

}
