package ru.alexneuro.alfatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import ru.alexneuro.alfatest.service.BoxService;
import ru.alexneuro.alfatest.service.ItemService;

import java.io.File;

@SpringBootApplication
public class AlfaTestApplication implements ApplicationRunner {

    @Autowired
    private BoxService boxService;
    @Autowired
    private ItemService itemService;
    private static String fileName;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AlfaTestApplication.class, args);

    }


    @EventListener(ApplicationReadyEvent.class)
    public void testCreate() {
        XMLParser xmlParser = new XMLParser(boxService, itemService);
        xmlParser.parse(new File(fileName));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileName = args.getSourceArgs()[0];
    }
}