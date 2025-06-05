// src/main/java/citasmedicas/Cita.java
package citasmedicas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cita implements Agendable {
    private Doctor doctor;
    private Paciente paciente;
    private LocalDateTime fechaHora;

    public Cita(Doctor doctor, Paciente paciente, LocalDateTime fechaHora) {
        this.doctor = doctor;
        this.paciente = paciente;
        this.fechaHora = fechaHora;
    }

    @Override
    public void agendar() {
        System.out.println("Cita agendada -> " + this);
    }

    @Override
    public void cancelar() {
        System.out.println("Cita cancelada -> " + this);
    }

    @Override
    public String toString() {
        String fechaFormateada = fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        return "Dr. " + doctor.getNombre()
                + " con Paciente " + paciente.getNombre()
                + " el " + fechaFormateada;
    }

    // ========================
    // A continuación, DEFINIMOS LOS GETTERS que faltaban:
    // ========================

    /**
     * Devuelve el doctor asociado a esta cita.
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Devuelve el paciente asociado a esta cita.
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Devuelve la fecha y hora de la cita.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
