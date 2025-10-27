package org.example.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.example.model.producto.Producto;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonProductoUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void exportarProductos(List<Producto> productos, String rutaArchivo) {
    try {
        mapper.writeValue(new File(rutaArchivo), productos);
    } catch (IOException e) {
        System.err.println("-- ERROR al exportar productos: " + e.getMessage());
    }
}

public static List<Producto> importarProductos(String rutaArchivo) {
    try {
        return mapper.readValue(
                new File(rutaArchivo),
                mapper.getTypeFactory().constructCollectionType(List.class, Producto.class)
        );
    } catch (IOException e) {
        System.err.println("-- ERROR al importar productos: " + e.getMessage());
        return null;
    }
}
}

