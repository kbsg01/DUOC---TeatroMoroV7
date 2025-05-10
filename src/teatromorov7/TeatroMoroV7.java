package teatromorov7;

import java.util.Scanner;

import utils.MenuController;

/**
 * @author kabes
 */


/**
 * Clase principal que inicia la ejecución del sistema Teatro Moro.
 * Se encarga de mostrar el menú, recibir opciones y delegar tareas.
 */
public class TeatroMoroV7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

         // Inicializa el escáner para entrada de datos
        Scanner sc = new Scanner(System.in);

        // Instancia principal del sistema de teatro
        Teatro teatro = new Teatro();

        // Variable de control para el bucle del menú
        boolean salir = false;
        int opcion = 0;

        // Bucle principal que muestra el menú hasta que el usuario desee salir
        do {
            MenuController.mostrarMenu(); // Muestra las opciones en consola
            System.out.println("> Seleccione una opción (1-5): ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpia salto de línea
            try{
                // Selección de acción según opción ingresada
                switch (opcion) {
                    case 1 -> MenuController.handleCompra(sc, teatro);   
                    case 2 -> MenuController.handleReserva(sc, teatro);
                    case 3 ->  MenuController.handleCancelaReserva(sc, teatro);
                    case 4 -> MenuController.handleImpresion(sc, teatro);
                    case 5 -> MenuController.handleResumen(teatro);      
                    case 6 -> salir = MenuController.handleSalida(sc);   
                    default -> System.out.println("Opción no válida. Por favor intenta de nuevo.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } while (!salir); // Se repite hasta que el usuario seleccione salir

        sc.close(); // Cierra el escáner para liberar recursos
    }
}