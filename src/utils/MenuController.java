/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.DecimalFormat;
import java.util.Scanner;

import teatromorov7.Cliente;
import teatromorov7.Entrada;
import teatromorov7.Reserva;
import teatromorov7.Teatro;

/**
 * Clase que controla la interacción del usuario con el sistema a través de
 * consola.
 */
public class MenuController {

    // Formateador para mostrar precios en formato chileno
    public static DecimalFormat df = new DecimalFormat("#,###");
    // Línea decorativa para separar secciones visualmente
    public static String border = "==============================================";

    /**
     * Muestra el menú principal en consola.
     */
    public static void mostrarMenu() {
        System.out.println(border);
        System.out.println("  Bienvenido a Teatro Moro - Menú principal  ");
        System.out.println(border);
        System.out.println("1. Comprar entrada");
        System.out.println("2. Realizar reserva");
        System.out.println("3. Imprimir boleto");
        System.out.println("4. Mostrar resumen de ventas");
        System.out.println("5. Salir");
        System.out.println(border);
    }

    /**
     * Controla el proceso completo de compra de entrada, con o sin reserva previa.
     */
    public static void handleCompra(Scanner sc, Teatro teatro) {
        boolean repetir = true;

        while (repetir) {
            System.out.println("\n> ¿Tiene una reserva pendiente? (S/N): ");
            String tieneReserva = sc.nextLine().trim().toUpperCase();

            // Intenta convertir una reserva en compra
            if (tieneReserva.equals("S")) {
                System.out.println("\n> Ingrese el ID de la reserva: ");
                int idReserva = sc.nextInt();
                sc.nextLine();

                Reserva reserva = teatro.buscarReservaPorId(idReserva);
                if (reserva != null) {
                    Entrada entrada = teatro.comprarDesdeReserva(reserva);
                    if (entrada != null) {
                        System.out.println("\n> Reserva convertida en compra exitosamente:");
                        entrada.imprimirEntrada();
                        continue; // Salta flujo normal si ya se usó la reserva
                    } else {
                        System.out.println("\n> La reserva ya fue utilizada o el asiento ya fue vendido.");
                    }
                } else {
                    System.out.println("\n> Reserva no encontrada. Se continuará con la compra normal.");
                }
            }

            // Flujo de compra tradicional
            Cliente cliente = capturarDatosCliente(sc);
            if (cliente == null)
                return;
            teatro.registrarCliente(cliente);

            int[] datosEntrada = seleccionarEntradaYAsiento(sc, teatro);
            if (datosEntrada == null)
                return;

            Entrada entrada = teatro.comprarEntrada(cliente, datosEntrada[0], datosEntrada[1]);
            if (entrada != null) {
                System.out.println("\n> ¡Compra realizada con éxito! Imprimiendo entrada...");
                entrada.imprimirEntrada();
            } else {
                System.out.println("\n> Error al procesar la compra.");
            }

            System.out.println("\n> ¿Desea realizar otra compra? (S/N): ");
            repetir = sc.nextLine().trim().equalsIgnoreCase("S");
        }
    }

    /**
     * Permite realizar reservas para un cliente.
     */
    public static void handleReserva(Scanner sc, Teatro teatro) {
        boolean repetir = true;

        while (repetir) {
            Cliente cliente = capturarDatosCliente(sc);
            if (cliente == null)
                return;
            teatro.registrarCliente(cliente);

            int[] datosEntrada = seleccionarEntradaYAsiento(sc, teatro);
            if (datosEntrada == null)
                return;

            Reserva reserva = teatro.realizarReserva(cliente, datosEntrada[0], datosEntrada[1]);

            if (reserva != null) {
                System.out.println("\n> Reserva realizada con éxito. Su ID es: " + reserva.getIdReserva());
                System.out.println("\n> Por favor finalice su compra seleccionando la opción 1 del menú principal.");
            } else {
                System.out.println("\n> El asiento ya fue reservado o vendido.");
            }

            System.out.println("\n> ¿Desea realizar otra reserva? (S/N): ");
            repetir = sc.nextLine().trim().equalsIgnoreCase("S");
        }
    }

    /**
     * Elimina una reserva existente según su ID.
     */
    public static void handleCancelaReserva(Scanner sc, Teatro teatro) {
        System.out.println("\n> Ingrese el ID de la reserva a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consumir salto de línea

        Reserva reserva = teatro.buscarReservaPorId(id);
        if (reserva != null) {
            teatro.getReservas().remove(reserva);
            System.out.println("\n>Reserva eliminada exitosamente.");
        } else {
            System.out.println("\n> Reserva no encontrada.");
        }
    }

    /**
     * Permite imprimir una entrada existente por ID.
     */
    public static void handleImpresion(Scanner sc, Teatro teatro) {
        System.out.println("\n> Ingrese el ID de la entrada a imprimir: ");
        int id = sc.nextInt();
        teatro.imprimirEntrada(id);
    }

    /**
     * Muestra resumen general de ventas y recaudación.
     */
    public static void handleResumen(Teatro teatro) {
        teatro.mostrarResumen();
    }

    /**
     * Confirma si el usuario desea salir del sistema.
     */
    public static boolean handleSalida(Scanner sc) {
        System.out.println("\n> ¿Está seguro de que desea salir? (S/N): ");
        String respuesta = sc.nextLine().trim().toUpperCase();
        if (respuesta.equals("S")) {
            System.out.println("\n> Gracias por preferir Teatro Moro. ¡Hasta luego!");
            return true;
        } else if (respuesta.equals("N")) {
            return false;
        } else {
            System.out.println("\n> Opción no válida. Por favor intenta de nuevo.");
            return handleSalida(sc);
        }
    }

    // Métodos auxiliares privados para capturar datos y seleccionar opciones

    /**
     * Captura los datos del cliente desde consola.
     */
    private static Cliente capturarDatosCliente(Scanner sc) {
        System.out.println("\n> Ingrese su nombre: ");
        String nombre = sc.nextLine();

        System.out.println("\n> Ingrese su apellido: ");
        String apellido = sc.nextLine();

        System.out.println("\n> Ingrese su edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        if (edad < 0) {
            System.out.println("\n> Edad no válida.");
            return null;
        }

        System.out.println("\n> Ingrese su sexo (M/F): ");
        String sexo = sc.nextLine().trim().toUpperCase();
        if (!sexo.equals("M") && !sexo.equals("F")) {
            System.out.println("\n> Sexo no válido.");
            return null;
        }

        return new Cliente(nombre, apellido, edad, sexo);
    }

    /**
     * Permite al usuario seleccionar tipo de entrada y asiento.
     */
    private static int[] seleccionarEntradaYAsiento(Scanner sc, Teatro teatro) {
        System.out.println("\n> Seleccione el tipo de entrada: ");
        for (int i = 0; i < teatro.tipos.length; i++) {
            int disponible = teatro.aforo[i] - teatro.nextAsientoIndex[i];
            System.out.printf("%d. %s ($%s) - Aforo disponible: %d\n", i + 1, teatro.tipos[i],
                    df.format(teatro.precios[i]), disponible);
        }

        int tipoEntrada = sc.nextInt() - 1;
        if (tipoEntrada < 0 || tipoEntrada >= teatro.tipos.length) {
            System.out.println("\n> Tipo de entrada no válido.");
            return null;
        }

        System.out.println("\n> Seleccione el número de asiento (1-" + teatro.aforo[tipoEntrada] + "): ");
        int nAsiento = sc.nextInt();
        sc.nextLine();
        if (nAsiento < 1 || nAsiento > teatro.aforo[tipoEntrada]) {
            System.out.println("\n> Número de asiento no válido.");
            return null;
        }

        return new int[] { tipoEntrada, nAsiento };
    }
}
