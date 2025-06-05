// Archivo: src/main/java/citasmedicas/PersistenceUtil.java
package citasmedicas;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PersistenceUtil {

    private static final String DB_DIR = "db";
    private static final String DOCTORES_FILE = "db/doctores.txt";
    private static final String PACIENTES_FILE = "db/pacientes.txt";
    private static final String CITAS_FILE = "db/citas.txt";

    /**
     * Verifica (y crea si faltan) la carpeta db/ y los archivos doctores.txt, pacientes.txt, citas.txt
     * Debe llamarse al iniciar la aplicación.
     */
    public static void inicializarDbFolder() {
        try {
            Path dbPath = Paths.get(DB_DIR);
            if (Files.notExists(dbPath)) {
                Files.createDirectory(dbPath);
            }
            // Crear el archivo de doctores si no existe
            Path fileDoctores = Paths.get(DOCTORES_FILE);
            if (Files.notExists(fileDoctores)) {
                Files.createFile(fileDoctores);
            }
            // Crear el archivo de pacientes si no existe
            Path filePacientes = Paths.get(PACIENTES_FILE);
            if (Files.notExists(filePacientes)) {
                Files.createFile(filePacientes);
            }
            // Crear el archivo de citas si no existe
            Path fileCitas = Paths.get(CITAS_FILE);
            if (Files.notExists(fileCitas)) {
                Files.createFile(fileCitas);
            }
        } catch (IOException e) {
            System.err.println("No se pudo inicializar db/: " + e.getMessage());
        }
    }

    /**
     * Lee todos los doctores desde db/doctores.txt, retorna una lista de objetos Doctor.
     * Cada línea esperada: id,nombre,especialidad
     */
    public static List<Doctor> cargarDoctoresDesdeDisco() {
        List<Doctor> lista = new ArrayList<>();
        Path file = Paths.get(DOCTORES_FILE);
        if (Files.exists(file)) {
            try (var br = Files.newBufferedReader(file)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] partes = linea.split(",", 3);
                    if (partes.length == 3) {
                        String nombre = partes[1];
                        String especialidad = partes[2];
                        Doctor d = new Doctor(nombre, especialidad);
                        lista.add(d);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer doctores.txt: " + e.getMessage());
            }
        }
        return lista;
    }

    /**
     * Lee todos los pacientes desde db/pacientes.txt, retorna una lista de objetos Paciente.
     * Cada línea esperada: id,nombre,edad
     */
    public static List<Paciente> cargarPacientesDesdeDisco() {
        List<Paciente> lista = new ArrayList<>();
        Path file = Paths.get(PACIENTES_FILE);
        if (Files.exists(file)) {
            try (var br = Files.newBufferedReader(file)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] partes = linea.split(",", 3);
                    if (partes.length == 3) {
                        String nombre = partes[1];
                        int edad = Integer.parseInt(partes[2]);
                        Paciente p = new Paciente(nombre, edad);
                        lista.add(p);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer pacientes.txt: " + e.getMessage());
            }
        }
        return lista;
    }

    /**
     * Lee todas las citas desde db/citas.txt, retorna una lista de objetos Cita.
     * Cada línea esperada: idDoctor,idPaciente,dd/MM/yyyy HH:mm
     */
    public static List<Cita> cargarCitasDesdeDisco(List<Doctor> doctores, List<Paciente> pacientes) {
        List<Cita> lista = new ArrayList<>();
        Path file = Paths.get(CITAS_FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (Files.exists(file)) {
            try (var br = Files.newBufferedReader(file)) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.trim().isEmpty()) continue;
                    String[] partes = linea.split(",", 3);
                    if (partes.length == 3) {
                        int idDoc = Integer.parseInt(partes[0]);
                        int idPac = Integer.parseInt(partes[1]);
                        LocalDateTime fechaHora = LocalDateTime.parse(partes[2], formatter);

                        // Buscar el doctor por ID en la lista en memoria
                        Doctor doc = doctores.stream()
                                .filter(d -> d.getId() == idDoc)
                                .findFirst()
                                .orElse(null);
                        // Buscar el paciente por ID en la lista en memoria
                        Paciente pac = pacientes.stream()
                                .filter(p -> p.getId() == idPac)
                                .findFirst()
                                .orElse(null);
                        if (doc != null && pac != null) {
                            Cita c = new Cita(doc, pac, fechaHora);
                            lista.add(c);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error al leer citas.txt: " + e.getMessage());
            }
        }
        return lista;
    }

    /**
     * Sobrescribe db/doctores.txt con la lista actual de doctores.
     * Formato: id,nombre,especialidad
     */
    public static void guardarDoctoresEnDisco(List<Doctor> doctores) {
        Path file = Paths.get(DOCTORES_FILE);
        try (var bw = Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Doctor d : doctores) {
                String linea = d.getId() + "," + d.getNombre() + "," + d.getEspecialidad();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar doctores.txt: " + e.getMessage());
        }
    }

    /**
     * Sobrescribe db/pacientes.txt con la lista actual de pacientes.
     * Formato: id,nombre,edad
     */
    public static void guardarPacientesEnDisco(List<Paciente> pacientes) {
        Path file = Paths.get(PACIENTES_FILE);
        try (var bw = Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Paciente p : pacientes) {
                String linea = p.getId() + "," + p.getNombre() + "," + p.getEdad();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes.txt: " + e.getMessage());
        }
    }

    /**
     * Sobrescribe db/citas.txt con la lista actual de citas.
     * Formato: idDoctor,idPaciente,dd/MM/yyyy HH:mm
     */
    public static void guardarCitasEnDisco(List<Cita> citas) {
        Path file = Paths.get(CITAS_FILE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try (var bw = Files.newBufferedWriter(file, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Cita c : citas) {
                int idDoc = c.getDoctor().getId();
                int idPac = c.getPaciente().getId();
                String fechaStr = c.getFechaHora().format(formatter);
                String linea = idDoc + "," + idPac + "," + fechaStr;
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar citas.txt: " + e.getMessage());
        }
    }
}
