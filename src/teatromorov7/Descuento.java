package teatromorov7;

/**
 * Clase que representa un descuento aplicable a ciertos tipos de clientes.
 */
public class Descuento {
    // Nombre del tipo de descuento (ej: Niño, Estudiante, Mujer, Tercera Edad)
    String nombre;
    // Porcentaje de descuento (ej: 10 para 10%)
    double descuento;

    /**
     * Constructor de la clase Descuento.
     * @param nombre Nombre del tipo de descuento
     * @param desccuento Porcentaje de descuento a aplicar
     */
    public Descuento(String nombre, double desccuento){
        this.nombre = nombre;
        this.descuento = desccuento;
    }

     // Métodos de acceso a los atributos

    /**
     * Devuelve el nombre del tipo de descuento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el porcentaje de descuento.
     */
    public double getDescuento() {
        return descuento;
    }
}
