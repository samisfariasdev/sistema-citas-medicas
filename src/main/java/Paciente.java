// src/main/java/citasmedicas/Paciente.java
package citasmedicas;

/**
 * Clase Paciente, hereda de Persona e incluye edad.
 */
public class Paciente extends Persona {
    private int edad;

    public Paciente(String nombre, int edad) {
        super(nombre);
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String toString() {
        // Ejemplo de salida: "[5] Luis Gómez (Edad: 27)"
        return "[" + getId() + "] " + getNombre() + " (Edad: " + edad + ")";
    }
}

