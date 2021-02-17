package com.desafiospring1.utils;

import com.desafiospring1.services.VendedorService;
import com.desafiospring1.services.impl.VendedorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class DataOutputWrite {

    private static final Logger log = LoggerFactory.getLogger(DataOutputWrite.class);

    @Autowired
    private VendedorService vendedorService;

    public void createReport(String ruta, String f) {
        try {
            Calendar calendario = new GregorianCalendar();
            Date date = calendario.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = dateFormat.format(date);
            LocalDateTime locaDate = LocalDateTime.now();
            int h  = locaDate.getHour();
            int m = locaDate.getMinute();
            int s = locaDate.getSecond();
            File file = new File(ruta.substring(0, ruta.length()-4) + " - Report date " + strDate + " as " + h + "hs " + m + "min " + s + "s" + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            String clientes = " » O número de clientes inserido no arquivo de entrada é: " + " «";
            String vendedores = " » O número de vendedores inserido no arquivo de entrada é: " + vendedorService.countByFile("0001.txt") + " «";
            String venda = " » A venda mais cara foi a venda de ID: " + " «";
            String vendedor = " » O vendedor com menos vendas foi: " + " «";

            bw.append("\r\n");
            bw.write(clientes);
            bw.append("\r\n");
            bw.newLine();
            bw.write(vendedores);
            bw.append("\r\n");
            bw.newLine();
            bw.write(venda);
            bw.append("\r\n");
            bw.newLine();
            bw.write(vendedor);

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
