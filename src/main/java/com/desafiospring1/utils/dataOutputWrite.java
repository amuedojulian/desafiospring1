package com.desafiospring1.utils;

import com.desafiospring1.services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class dataOutputWrite {

    @Qualifier("vendedorService")
    @Autowired
    VendedorService vendedorService;

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

            EntityManagerFactory emf= Persistence.
                    createEntityManagerFactory("jpa");
            EntityManager em=emf.createEntityManager();
            try{
                EntityTransaction entr=em.getTransaction();
                entr.begin();
                Query query=em.createQuery("SELECT COUNT (v.id) FROM Vendedor v");
                Number cResults=(Number) query.getSingleResult();
                System.out.println("Total Count result = "
                        +cResults);
                entr.commit();
            }
            finally{
                em.close();
            }

            String clientes = " » O número de clientes inserido no arquivo de entrada é: " + vendedorService.count(f.substring(1)) + " «";
            String vendedores = " » O número de vendedores inserido no arquivo de entrada é: " + "" + " «";
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
