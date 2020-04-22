package ru.alexneuro.alfatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alexneuro.alfatest.xml.XMLParser;

import java.io.File;

@SpringBootApplication
public class AlfaTestApplication implements ApplicationRunner {

    @Autowired
    private XMLParser xmlParser;

    public static void main(String[] args) {
        SpringApplication.run(AlfaTestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        String fileName = args.getSourceArgs()[0];
        xmlParser.parse(new File(fileName));
    }
}