package teatromorov7;

/** 
 * Clase que representa a un cliente del Teatro.
 * Contiene sus datos personales básicos.
 */
public class Cliente {

    // Atributos que almacenan información personal del cliente
    String nombre;
    String apellido;
    int edad;
    String sexo;

    /**
     * Constructor del cliente.
     * @param nombre Nombre del cliente
     * @param apellido Apellido del cliente
     * @param edad Edad del cliente
     * @param sexo Sexo del cliente (M/F)
     */
    public Cliente(String nombre, String apellido, int edad, String sexo){
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.sexo = sexo;
    }

    // Métodos para obtener los datos del cliente

    public String getNombre() {
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    /**
     * Devuelve el nombre completo del cliente.
     */
    public String getNombreCompleto(){
        return nombre + " " + apellido;
    }

    public int getEdad(){
        return edad;
    }

    public String getSexo(){
        return sexo;
    }

    // Métodos para modificar los datos del cliente
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }

    public void setSexo(String sexo){
        this.sexo = sexo;
    }

    /**
     * Representación del cliente en formato texto.
     */
    @Override
    public String toString(){
        return  "Nombre: "+ nombre + " "+ apellido +
                " | Edad: "+ edad +" Años | "+
                "Sexo: " + sexo;
    }

}
