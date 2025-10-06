import java.time.LocalDate;

import org.example.controller.ControladorUsuario;
import org.example.model.Usuario;
import org.junit.jupiter.api.BeforeEach;

public class ControladorUsuarioTest {


      ControladorUsuario controlador;
      Usuario usuario1;
      Usuario usuario2;

      @BeforeEach
    void setUp() {
        controlador = new ControladorUsuario();
        // Crear productos de prueba (necesitarás ajustar el constructor según tu clase Producto)
        usuario1 = new Usuario("20517286K","Azorin 48, Pilar de la Hoadada",LocalDate.of(2006, 01, 5),"630720540",
                              "mauromircam@gmail.com","1234567890");
        usuario2 = new Usuario("20517286K","Azorin 48, Pilar de la Hoadada",LocalDate.of(2006, 01, 5),"630720540",
                              "mauromircam@gmail.com","1234567890");
    }
}
