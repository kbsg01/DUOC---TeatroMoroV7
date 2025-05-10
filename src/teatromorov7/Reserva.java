package teatromorov7;

/**
 * Clase que representa una reserva realizada por un cliente para un asiento específico.
 * No implica compra inmediata, pero bloquea el asiento.
 */
public class Reserva {

    // Contador estático para generar IDs únicos para reservas
    private static int contadorReservaGlobal = 0;

    // Atributos de la reserva
    int idReserva;
    Cliente cliente;
    String tipo; // Tipo de entrada (VIP, Palco, etc.)
    String asiento; // Código del asiento reservado (ej: VIP-1)

    /**
     * Constructor para crear una nueva reserva.
     * @param cliente Cliente que realiza la reserva
     * @param tipo Tipo de asiento reservado
     * @param asiento Identificador del asiento
     */
    public Reserva(Cliente cliente, String tipo, String asiento) {
        this.idReserva = ++contadorReservaGlobal;
        this.cliente = cliente;
        this.tipo = tipo;
        this.asiento = asiento;
    }

     // Métodos de acceso (getters)
    public int getIdReserva() {
        return idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAsiento() {
        return asiento;
    }

    /**
     * Devuelve una representación de la reserva en formato de línea.
     */
    @Override
    public String toString() {
        return String.format("Reserva ID : %d | %s | Asiento: %s | Tipo: %s",
                idReserva, cliente.getNombreCompleto(), asiento, tipo);
    }
}
