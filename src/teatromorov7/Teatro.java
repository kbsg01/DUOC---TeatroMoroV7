package teatromorov7;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el sistema del Teatro Moro.
 * Maneja la lógica de reservas, ventas, descuentos y control de asientos.
 */
public class Teatro {

    // Formateador para mostrar precios en formato moneda
    DecimalFormat df = new DecimalFormat("#,###");

    // Tipos de entrada y sus respectivos precios
    public String[] tipos = { "VIP", "Palcos", "Platea Baja", "Platea Alta", "Galería" };
    public double[] precios = {
            30000, // VIP
            18000, // Palcos
            15000, // Platea Baja
            13000, // Platea Alta
            10000 // Galería
    };

    // Aforo general del teatro y distribución por tipo de entrada
    public int capacidad = 300;
    public int[] aforo = {
            30, // VIP
            30, // Palcos
            105, // Platea Baja
            75, // Platea Alta
            60 // Galería
    };

    // Datos del sistema: entradas vendidas, clientes registrados, estructura de
    // asientos
    public Entrada[] entradasVendidas = new Entrada[capacidad];
    public Cliente[] clientes = new Cliente[capacidad];
    public String[][] asientosPorTipo = new String[5][];
    public int[] nextAsientoIndex = new int[5];

    // Listas para almacenar descuentos definidos y reservas activas
    List<Descuento> descuentos = new ArrayList<>();
    List<Reserva> reservas = new ArrayList<>();

    // Contadores internos
    int contadorVentas = 0;
    int contadorClientes = 0;

    /**
     * Constructor del teatro.
     * Inicializa los asientos y define los descuentos por tipo de cliente.
     */
    public Teatro() {
        for (int i = 0; i < tipos.length; i++) {
            asientosPorTipo[i] = new String[aforo[i]];
            for (int j = 0; j < aforo[i]; j++) {
                asientosPorTipo[i][j] = tipos[i] + "-" + (j + 1);
            }
            nextAsientoIndex[i] = 0;
        }

        // Definición de descuentos por condición del cliente
        descuentos.add(new Descuento("Niño", 10));
        descuentos.add(new Descuento("Estudiante", 15));
        descuentos.add(new Descuento("Mujer", 20));
        descuentos.add(new Descuento("Tercera Edad", 25));
    }

    /**
     * Registra un nuevo cliente si no existe previamente.
     */
    public int registrarCliente(Cliente cliente) {
        for (int i = 0; i < contadorClientes; i++) {
            Cliente c = clientes[i];
            if (c.getNombre().equalsIgnoreCase(cliente.getNombre()) &&
                    c.getApellido().equalsIgnoreCase(cliente.getApellido()) &&
                    c.getSexo().equalsIgnoreCase(cliente.getSexo()) &&
                    c.getEdad() == cliente.getEdad()) {
                return i;
            }
        }
        clientes[contadorClientes] = cliente;
        return contadorClientes++;
    }

    /**
     * Permite comprar una entrada si el asiento está disponible.
     */
    public Entrada comprarEntrada(Cliente cliente, int tipoId, int nAsiento) {
        if (nAsiento <= 0 || nAsiento > aforo[tipoId]) {
            System.out.println("\nNúmero de asiento no válido. Por favor intenta de nuevo.");
            return null;
        }

        int asientoIndex = nAsiento - 1;
        String asiento = asientosPorTipo[tipoId][asientoIndex];

        if (esAsientoOcupado(asiento)) {
            System.out.println("\nAsiento ya reservado o vendido. Por favor selecciona otro.");
            return null;
        }

        double precioBase = precios[tipoId];
        double descuento = obtenerDescuento(cliente);
        Entrada entrada = new Entrada(tipos[tipoId], asiento, precioBase, descuento, cliente);
        entradasVendidas[contadorVentas++] = entrada;

        return entrada;
    }

    /**
     * Retorna la lista de reservas activas.
     */
    public List<Reserva> getReservas() {
        return reservas;
    }

    /**
     * Crea una reserva si el asiento no está ocupado ni reservado por el mismo
     * cliente.
     */
    public Reserva realizarReserva(Cliente cliente, int tipoId, int nAsiento) {
        if (nAsiento <= 0 || nAsiento > aforo[tipoId]) {
            return null;
        }

        String asiento = asientosPorTipo[tipoId][nAsiento - 1];

        if (clienteYaReservo(cliente, asiento)) {
            System.out.println("Ya existe una reserva para este cliente y asiento.");
            return null;
        }

        if (esAsientoOcupado(asiento)) {
            System.out.println("El asiento ya fue reservado o vendido.");
            return null;
        }

        Reserva reserva = new Reserva(cliente, tipos[tipoId], asiento);
        reservas.add(reserva);
        return reserva;
    }

    /**
     * Busca una entrada por su ID y la imprime si existe.
     */
    public void imprimirEntrada(int idEntrada) {
        for (int i = 0; i < contadorVentas; i++) {
            if (entradasVendidas[i].getIdEntrada() == idEntrada) {
                entradasVendidas[i].imprimirEntrada();
                return;
            }
        }
        System.out.println("\nEntrada con ID: " + idEntrada + " no encontrada.");
    }

    /**
     * Muestra un resumen general de todas las ventas realizadas.
     */
    public void mostrarResumen() {
        double totalVentas = 0;

        System.out.println("===============================");
        System.out.println("      Resumen de Ventas");
        for (int i = 0; i < contadorVentas; i++) {
            System.out.println(entradasVendidas[i]);
            totalVentas += entradasVendidas[i].getPrecioFinal();
        }
        System.out.println("===============================");
        System.out.println("Total entradas vendidas: " + contadorVentas);
        System.out.println("Total recaudado: $" + df.format(totalVentas));
    }

    /**
     * Calcula el descuento correspondiente al cliente según edad y sexo.
     */
    public double obtenerDescuento(Cliente cliente) {
        int edad = cliente.getEdad();
        String sexo = cliente.getSexo();

        double descuento = 0;

        for (Descuento d : descuentos) {
            switch (d.getNombre()) {
                case "Niño":
                    if (edad < 12)
                        descuento = Math.max(descuento, d.getDescuento());
                    break;
                case "Estudiante":
                    if (edad >= 12 && edad < 25)
                        descuento = Math.max(descuento, d.getDescuento());
                    break;
                case "Tercera Edad":
                    if (edad >= 60)
                        descuento = Math.max(descuento, d.getDescuento());
                    break;
                case "Mujer":
                    if (sexo.equalsIgnoreCase("F"))
                        descuento = Math.max(descuento, d.getDescuento());
                    break;
                default:
                    break;
            }
        }
        return descuento;
    }

    /**
     * Busca una reserva por su ID.
     */
    public Reserva buscarReservaPorId(int idReserva) {
        for (Reserva r : reservas) {
            if (r.getIdReserva() == idReserva) {
                return r;
            }
        }
        return null;
    }

    /**
     * Convierte una reserva en una entrada válida si el asiento está disponible.
     */
    public Entrada comprarDesdeReserva(Reserva reserva) {
        for (int i = 0; i < contadorVentas; i++) {
            if (entradasVendidas[i] != null && entradasVendidas[i].getAsiento().equals(reserva.getAsiento())) {
                return null;
            }
        }

        int tipoIndex = obtenerIndiceTipo(reserva.getTipo());
        if (tipoIndex == -1)
            return null;

        double precioBase = precios[tipoIndex];
        double descuento = obtenerDescuento(reserva.getCliente());
        Entrada entrada = new Entrada(reserva.getTipo(), reserva.getAsiento(), precioBase, descuento,
                reserva.getCliente());
        entradasVendidas[contadorVentas++] = entrada;
        reservas.remove(reserva);
        return entrada;
    }

    /**
     * Retorna el índice del tipo de entrada (ej: VIP → 0).
     */
    private int obtenerIndiceTipo(String tipo) {
        for (int i = 0; i < tipos.length; i++) {
            if (tipos[i].equalsIgnoreCase(tipo))
                return i;
        }
        return -1;
    }

    /**
     * Verifica si un asiento ya fue reservado o vendido.
     */
    private boolean esAsientoOcupado(String asiento) {
        for (Reserva r : reservas) {
            if (r.getAsiento().equalsIgnoreCase(asiento)) {
                return true;
            }
        }
        for (int i = 0; i < contadorVentas; i++) {
            if (entradasVendidas[i] != null && entradasVendidas[i].getAsiento().equalsIgnoreCase(asiento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un cliente ya tiene una reserva para un asiento específico.
     */
    private boolean clienteYaReservo(Cliente cliente, String asiento) {
        for (Reserva r : reservas) {
            if (r.getCliente().getNombreCompleto().equalsIgnoreCase(cliente.getNombreCompleto())
                    && r.getCliente().getEdad() == cliente.getEdad()
                    && r.getCliente().getSexo().equalsIgnoreCase(cliente.getSexo())
                    && r.getAsiento().equalsIgnoreCase(asiento)) {
                return true;
            }
        }
        return false;
    }
}
