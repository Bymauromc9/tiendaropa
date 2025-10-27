package org.example.util;

import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.util.List;

import org.example.model.Etiqueta;

public class CsvEtiquetaUtil {

    private static final char SEPARADOR = ';';

    public static void exportarEtiquetas(List<Etiqueta> etiquetas, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            StatefulBeanToCsv<Etiqueta> beanToCsv = new StatefulBeanToCsvBuilder<Etiqueta>(writer)
                    .withSeparator(SEPARADOR)
                    .build();
            beanToCsv.write(etiquetas);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException("-- Error exportando etiquetas", e);
        }
    }

    public static List<Etiqueta> importarEtiquetas(String rutaArchivo) {
        try (Reader reader = new FileReader(rutaArchivo)) {
            CsvToBean<Etiqueta> csvToBean = new CsvToBeanBuilder<Etiqueta>(reader)
                    .withSeparator(SEPARADOR)
                    .withType(Etiqueta.class)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("-- Error importando etiquetas", e);
        }
    }
}