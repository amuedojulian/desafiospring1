package com.desafiospring1.utils;

import com.desafiospring1.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class dataOutputWrite {

    @Autowired
    VendedorRepository vendedorRepository;

    public void createReport(String ruta) {
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
            String vendedores = " » O número de vendedores inserido no arquivo de entrada é: " + " «";
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
