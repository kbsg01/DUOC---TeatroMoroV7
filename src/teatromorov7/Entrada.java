package teatromorov7;

import java.text.DecimalFormat;

/**
 * Clase que representa una entrada vendida del Teatro Moro.
 * Almacena información como tipo de asiento, cliente, precio, y descuento aplicado.
 */
public class Entrada {

     // Contador estático para generar IDs únicos de entradas
    private static int contadorGlobal = 0;

    // Atributos de la entrada
    int idEntrada;
    String tipo;
    String asiento;
    Cliente cliente;
    double precioBase;
    double descuento;
    double precioFinal;

    // Herramientas de formato para salida visual
    DecimalFormat df = new DecimalFormat("#,###");
    String border = "===============================";
    
    /**
     * Constructor para crear una nueva entrada.
     * @param tipo Tipo de asiento (VIP, Palco, etc.)
     * @param asiento Identificador del asiento
     * @param precioBase Precio sin descuento
     * @param descuento Porcentaje de descuento aplicado
     * @param cliente Cliente que compró la entrada
     */
    public Entrada(String tipo, String asiento, double precioBase, double descuento, Cliente cliente){
        this.idEntrada = ++contadorGlobal;
        this.tipo = tipo;
        this.asiento = asiento;
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.cliente = cliente;
        this.precioFinal = calcularPrecioFinal(precioBase, descuento);
    }

    /**
     * Calcula el precio final aplicando el descuento.
     * @param precioBase Precio sin descuento
     * @param descuento Porcentaje de descuento
     * @return Precio con descuento aplicado
     */
    public double calcularPrecioFinal(double precioBase, double descuento){
        return precioBase - (precioBase * (descuento / 100));
    }

    /**
     * Imprime la entrada con formato de boleto.
     */
    public void imprimirEntrada(){
        System.out.println(border);
        System.out.println("         TEATRO MORO");
        System.out.println(border);
        System.out.printf("Entrada ID      : %d%n", idEntrada);
        System.out.printf("Tipo            : %s%n", tipo);
        System.out.printf("Asiento         : %s%n", asiento);
        System.out.printf("Cliente         : %s%n", cliente.getNombreCompleto());
        System.out.printf("Precio Original : $%s%n", df.format(precioBase));
        System.out.printf("Descuento       : %.0f%%%n", descuento);
        System.out.printf("Precio Final    : $%s%n", df.format(precioFinal));
        System.out.println(border);
    }

    // Métodos de acceso (getters)
    public int getIdEntrada(){
        return idEntrada;
    }
    public String getAsiento(){
        return asiento;
    }
    public double getDescuento(){
        return descuento;
    }
    public double getPrecioFinal(){
        return precioFinal;
    }

    /**
     * Devuelve una descripción de la entrada en una línea.
     */
    public String toString(){
        return String.format("ID Entrada: %d | %s | Asiento: %s | Cliente: %s | Precio: $%s",
                idEntrada, tipo, asiento, cliente.toString(), df.format(precioFinal));
    }
}