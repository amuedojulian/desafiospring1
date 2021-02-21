package com.desafiospring1;

import com.desafiospring1.utils.DataInputReader;
import com.desafiospring1.utils.DataOutputWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
@EnableCaching
public class Desafiospring1Application {

    @Autowired
    private DataInputReader dataInputReader;

    public static void main(String[] args) {
        SpringApplication.run(Desafiospring1Application.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return new CommandLineRunner() {
            public void run(String... strings) throws IOException{
                new Thread(dataInputReader, "watcher-service").start();
            }
        };
    }










}
