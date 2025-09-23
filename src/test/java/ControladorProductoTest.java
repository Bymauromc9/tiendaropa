

import org.example.controller.ControladorProducto;
import org.example.model.descuento.Descuento;
import org.example.model.descuento.DescuentoFijo;
import org.example.model.producto.Camisa;
import org.example.model.producto.Pantalon;
import org.example.model.producto.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class ControladorProductoTest {

    private ControladorProducto controlador;
    private Producto producto1;
    private Producto producto2;
    private Descuento descuento;

    @BeforeEach
    void setUp() {
        controlador = new ControladorProducto();


        // Crear productos de prueba (necesitarás ajustar el constructor según tu clase Producto)
        producto1 = new Pantalon("Hola2", "nike", 10, Producto.TALLA.L, Producto.COLOR.ROJO);
        producto2 = new Camisa("Hola", "primark", 11, Producto.TALLA.M, Producto.COLOR.AZUL);

        // Crear descuento de prueba (ajustar constructor según tu clase Descuento)
        descuento = new DescuentoFijo(10);
    }

    // Tests para registrarProducto()
    @Test
    @DisplayName("Debe registrar un producto exitosamente cuando no existe")
    void testRegistrarProducto_Exitoso() {
        // Act
        boolean resultado = controlador.registrarProducto(producto1);

        // Assert
        assertTrue(resultado);
        assertEquals(producto1, controlador.obtenerProductoPorId(1L));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar registrar un producto que ya existe")
    void testRegistrarProducto_ProductoYaExiste() {
        // Arrange
        controlador.registrarProducto(producto1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controlador.registrarProducto(producto1)
        );

        assertEquals("--ERROR. El producto ya existe", exception.getMessage());
    }

    @Test
    @DisplayName("Debe permitir registrar productos con diferentes IDs")
    void testRegistrarProducto_ProductosDiferentes() {
        // Act
        boolean resultado1 = controlador.registrarProducto(producto1);
        boolean resultado2 = controlador.registrarProducto(producto2);

        // Assert
        assertTrue(resultado1);
        assertTrue(resultado2);
        assertEquals(producto1, controlador.obtenerProductoPorId(1L));
        assertEquals(producto2, controlador.obtenerProductoPorId(2L));
    }

    // Tests para eliminarProducto()
    @Test
    @DisplayName("Debe eliminar un producto existente exitosamente")
    void testEliminarProducto_Exitoso() {
        // Arrange
        controlador.registrarProducto(producto1);

        // Act
        boolean resultado = controlador.eliminarProducto(producto1);

        // Assert
        assertTrue(resultado);
        assertNull(controlador.obtenerProductoPorId(1L));
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar eliminar un producto que no existe")
    void testEliminarProducto_ProductoNoExiste() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controlador.eliminarProducto(producto1)
        );

        assertEquals("-- ERROR. Producto no encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Debe eliminar solo el producto correcto cuando hay múltiples productos")
    void testEliminarProducto_MultipleProductos() {
        // Arrange
        controlador.registrarProducto(producto1);
        controlador.registrarProducto(producto2);

        // Act
        boolean resultado = controlador.eliminarProducto(producto1);

        // Assert
        assertTrue(resultado);
        assertNull(controlador.obtenerProductoPorId(producto1.getId()));
        assertNotNull(controlador.obtenerProductoPorId(producto2.getId()));
    }

    // Tests para actualizarProducto()
    @Test
    @DisplayName("Debe actualizar un producto existente exitosamente")
    void testActualizarProducto_Exitoso() {
        // Arrange
        controlador.registrarProducto(producto1);
        String nombreOriginal = producto1.getNombre();
        String nuevoNombre = "Nombre Actualizado";
        String nuevaMarca = "Marca Actualizada";
        Producto.COLOR nuevoColor = Producto.COLOR.VERDE;
        Producto.TALLA nuevaTalla = Producto.TALLA.XL;

        // Act
        controlador.actualizarProdcuto(producto1, nuevoNombre, nuevaMarca, descuento, nuevoColor, nuevaTalla);

        // Assert
        Producto productoActualizado = controlador.obtenerProductoPorId(producto1.getId());
        assertEquals(nuevoNombre, productoActualizado.getNombre());
        assertEquals(nuevaMarca, productoActualizado.getMarca());
        //assertEquals(descuento, productoActualizado.getDescuento());
        assertEquals(nuevoColor, productoActualizado.getColor());
        assertEquals(nuevaTalla, productoActualizado.getTalla());

        // Verificar que el nombre cambió
        assertNotEquals(nombreOriginal, productoActualizado.getNombre());
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar actualizar un producto que no existe")
    void testActualizarProducto_ProductoNoExiste() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controlador.actualizarProdcuto(producto1, "Nuevo Nombre", "Nueva Marca",
                        descuento, Producto.COLOR.AZUL, Producto.TALLA.L)
        );

        assertEquals("-- ERROR. Producto no encontrado", exception.getMessage());
    }




    // REVISAR TEST
    @Test
    @DisplayName("Debe lanzar excepción cuando el nombre es null")
    void testActualizarProducto_NombreNull() {
        // Arrange
        controlador.registrarProducto(producto1);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controlador.actualizarProdcuto(producto1, null, null, null, null, null)
        );

        assertEquals("-- ERROR. El nombre no debe estar vacio", exception.getMessage());
    }

    // Tests para obtenerProductoPorId()
    @Test
    @DisplayName("Debe obtener un producto existente por su ID")
    void testObtenerProductoPorId_ProductoExiste() {
        // Arrange
        controlador.registrarProducto(producto1);

        // Act
        Producto resultado = controlador.obtenerProductoPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(producto1.getId(), resultado.getId());
        assertEquals(producto1.getNombre(), resultado.getNombre());
    }

    @Test
    @DisplayName("Debe retornar null cuando el producto no existe")
    void testObtenerProductoPorId_ProductoNoExiste() {
        // Act
        Producto resultado = controlador.obtenerProductoPorId(999L);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Debe retornar null cuando se busca con ID null")
    void testObtenerProductoPorId_IdNull() {
        // Arrange
        controlador.registrarProducto(producto1);

        // Act
        Producto resultado = controlador.obtenerProductoPorId(null);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("Debe obtener el producto correcto cuando hay múltiples productos")
    void testObtenerProductoPorId_MultipleProductos() {
        // Arrange
        controlador.registrarProducto(producto1);
        controlador.registrarProducto(producto2);

        // Act
        Producto resultado1 = controlador.obtenerProductoPorId(producto1.getId());
        Producto resultado2 = controlador.obtenerProductoPorId(producto2.getId());

        // Assert
        assertEquals(producto1.getId(), resultado1.getId());
        assertEquals(producto2.getId(), resultado2.getId());
        assertEquals("Hola2", resultado1.getNombre());
        assertEquals("Hola", resultado2.getNombre());
    }

    // Tests de integración
    @Test
    @DisplayName("Flujo completo: registrar, actualizar y eliminar producto")
    void testFlujoCompleto() {
        // Registrar
        assertTrue(controlador.registrarProducto(producto1));
        assertNotNull(controlador.obtenerProductoPorId(1L));

        // Actualizar
        String nombreNuevo = "Camiseta Premium";
        controlador.actualizarProdcuto(producto1, nombreNuevo, "Nike Pro",
                descuento, Producto.COLOR.NEGRO, Producto.TALLA.XL);

        Producto productoActualizado = controlador.obtenerProductoPorId(1L);
        assertEquals(nombreNuevo, productoActualizado.getNombre());
        assertEquals("Nike Pro", productoActualizado.getMarca());

        // Eliminar
        assertTrue(controlador.eliminarProducto(producto1));
        assertNull(controlador.obtenerProductoPorId(1L));
    }

    @Test
    @DisplayName("Debe manejar lista vacía correctamente")
    void testListaVacia() {
        // Assert
        assertNull(controlador.obtenerProductoPorId(1L));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> controlador.eliminarProducto(producto1)
        );
        assertEquals("-- ERROR. Producto no encontrado", exception.getMessage());
    }
}