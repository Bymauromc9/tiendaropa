package controllerTests;

import org.example.conexion.ConexionBD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConexionBDTest {

    @BeforeEach
    void setUp() {
        ConexionBD.borrarDatos();
    }

    @Test
    void testGetConnection_Exito() {
        try {
            Connection conn = ConexionBD.getConnection();
            assertNotNull(conn, "La conexión no debería ser nula");
            assertFalse(conn.isClosed(), "La conexión debería estar abierta");
            conn.close();
        } catch (SQLException e) {
            fail("No debería lanzar excepción de SQL: " + e.getMessage());
        }
    }

    @Test
    void testCreateAndDelete_Exito() {
        assertDoesNotThrow(ConexionBD::createAndDelete);
    }
}