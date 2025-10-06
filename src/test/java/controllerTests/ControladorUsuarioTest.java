package controllerTests;
import org.example.controller.ControladorUsuario;
import org.example.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControladorUsuarioTest {

    private ControladorUsuario controlador;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        controlador = new ControladorUsuario();
        usuario = new Usuario("12345678A", "Calle Falsa 123", LocalDate.of(1990, 1, 1), "612345678", "test@test.com", "password");
    }

    @Test
    void registrarUsuario_verdadero() {
        assertTrue(controlador.registrarUsuario(usuario));
    }

    @Test
    void registrarUsuario_falso() {
        controlador.registrarUsuario(usuario);
        assertFalse(controlador.registrarUsuario(usuario));
    }

    @Test
    void loguearUsuario_verdadero() {
        controlador.registrarUsuario(usuario);
        assertTrue(controlador.loguearUsuario("test@test.com", "password").isPresent());
    }

    @Test
    void loguearUsuario_falso() {
        controlador.registrarUsuario(usuario);
        assertTrue(controlador.loguearUsuario("fake@test.com", "password").isEmpty());
    }

    @Test
    void eliminarUsuario_verdadero() {
        controlador.registrarUsuario(usuario);
        controlador.eliminarUsuario(usuario);
        assertTrue(controlador.loguearUsuario("test@test.com", "password").isEmpty());
    }

    @Test
    void eliminarUsuario_falso() {
        controlador.eliminarUsuario(usuario);
        assertTrue(controlador.loguearUsuario("test@test.com", "password").isEmpty());
    }
}
