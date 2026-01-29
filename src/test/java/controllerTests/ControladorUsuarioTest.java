package controllerTests;

import org.example.conexion.ConexionBD;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorUsuarioTest {

    @BeforeEach
    void setUp() {
        ConexionBD.createAndDelete();
    }

    @Test
    void registrarUsuario_True() {
        UsuarioService controller = new UsuarioService();

        Usuario u = new Usuario("12345678A", "Calle X", LocalDate.now(),
                "600123123", "1234@gmail.com", "1234");

        boolean resultado = controller.registrarUsuario(u);

        assertTrue(resultado);
    }

    @Test
    void registrarUsuario_False() {

        Usuario u = new Usuario();

        assertThrows(IllegalArgumentException.class, () -> {
            u.setEmail(null);
        });
    }

    @Test
    void loguearUsuario_True() {
        UsuarioService controller = new UsuarioService();
        Usuario u = new Usuario("87654321Z", "Calle Y", LocalDate.now(),
                "600987654", "usuario@mail.com", "paa");
        controller.registrarUsuario(u);

        Usuario logueado = controller.loguearUsuario("usuario@mail.com", "paa");
        assertNotNull(logueado);
        assertEquals("87654321Z", logueado.getDni());
    }

    @Test
    void loguearUsuario_False() {
        UsuarioService controller = new UsuarioService();
        Usuario logueado = controller.loguearUsuario("noexiste@mail.com", "paa");
        assertNull(logueado);
    }

}
