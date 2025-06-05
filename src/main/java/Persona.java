// src/main/java/citasmedicas/Persona.java
package citasmedicas;

/**
 * Clase abstracta que representa a una persona genérica.
 * Contiene un id único generado automáticamente y un nombre.
 */
public abstract class Persona {
    private static int contadorId = 1;
    private int id;
    private String nombre;

    protected Persona(String nombre) {
        this.id = contadorId++;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre;
    }
}
