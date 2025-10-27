package org.example.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.example.model.pedido.Pedido;
import org.example.model.producto.Producto;
import org.example.model.producto.Camisa;
import org.example.model.producto.Chaqueta;
import org.example.model.producto.Pantalon;

public class GsonPedidoUtil {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate date, Type type, JsonSerializationContext context) {
                    return new JsonPrimitive(date.toString());
                }
            })
            .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date date, Type type, JsonSerializationContext context) {
                    return new JsonPrimitive(date.getTime());
                }
            })
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
                    return new Date(json.getAsLong());
                }
            })
            .registerTypeAdapter(Producto.class, new JsonDeserializer<Producto>() {
                @Override
                public Producto deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
                        throws JsonParseException {
                    
                    String jsonString = json.toString();
                    
                    if (jsonString.contains("\"botones\"")) {
                        return context.deserialize(json, Camisa.class);
                    } else if (jsonString.contains("\"conCapucha\"")) {
                        return context.deserialize(json, Chaqueta.class);
                    } else if (jsonString.contains("\"bolsillos\"")) {
                        return context.deserialize(json, Pantalon.class);
                    }
                    
                    throw new JsonParseException("Tipo de producto desconocido");
                }
            })
            .create();

    public static void exportarPedidos(List<Pedido> pedidos, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            gson.toJson(pedidos, writer);
        } catch (IOException e) {
            throw new RuntimeException("-- Error exportando pedidos", e);
        }
    }

    public static List<Pedido> importarPedidos(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Type listType = new TypeToken<List<Pedido>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new RuntimeException("-- Error importando pedidos", e);
        }
    }
}