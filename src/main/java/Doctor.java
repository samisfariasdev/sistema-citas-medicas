// src/main/java/citasmedicas/Doctor.java
package citasmedicas;

/**
 * Clase Doctor, hereda de Persona e incluye una especialidad.
 */
public class Doctor extends Persona {
    private String especialidad;

    public Doctor(String nombre, String especialidad) {
        super(nombre);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        // Ejemplo de salida: "[2] Ana López (Esp: Cardiología)"
        return "[" + getId() + "] " + getNombre() + " (Esp: " + especialidad + ")";
    }
}
