package controllerTests;

import org.example.model.Etiqueta;
import org.example.util.CsvEtiquetaUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvEtiquetaUtilTest {

    private static final String RUTA_TEST = "test_etiquetas.csv";

    @AfterEach
    void limpiarArchivos() {
        new File(RUTA_TEST);
    }

    @Test
    void testExportarEImportarEtiquetas_Correcto() {
        //Crear etiquetas de prueba
        List<Etiqueta> etiquetasOriginales = new ArrayList<>();
        etiquetasOriginales.add(new Etiqueta("Verano", LocalDate.of(2024, 6, 1)));
        etiquetasOriginales.add(new Etiqueta("Oferta", LocalDate.of(2024, 10, 15)));
        etiquetasOriginales.add(new Etiqueta("Nuevo", LocalDate.of(2024, 11, 1)));

        //Exportar
        CsvEtiquetaUtil.exportarEtiquetas(etiquetasOriginales, RUTA_TEST);

        //Verificar que el archivo existe
        File archivo = new File(RUTA_TEST);
        assertTrue(archivo.exists(), "El archivo CSV debería existir");

        //Importar
        List<Etiqueta> etiquetasImportadas = CsvEtiquetaUtil.importarEtiquetas(RUTA_TEST);

        //Verificar que se importaron correctamente
        assertNotNull(etiquetasImportadas, "Las etiquetas importadas no deberían ser null");
        assertEquals(3, etiquetasImportadas.size(), "Deberían importarse 3 etiquetas");
        
        // Verificar contenido de la primera etiqueta
        assertEquals("Verano", etiquetasImportadas.get(0).getNombre());
        assertEquals(LocalDate.of(2024, 6, 1), etiquetasImportadas.get(0).getFechaCreacion());
    }

    @Test
    void testImportarEtiquetas_Incorrecto() {
        String archivoInexistente = "archivo_que_no_existe.csv";

        //Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            CsvEtiquetaUtil.importarEtiquetas(archivoInexistente);
        });

        // Verificar que el mensaje contiene información del error
        assertTrue(exception.getMessage().contains("Error importando etiquetas"),
                "El mensaje de error debería indicar que hubo un problema al importar");
    }

}