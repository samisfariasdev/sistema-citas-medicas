package citasmedicas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1) Inicializar carpeta y archivos de db/ si no existen
        PersistenceUtil.inicializarDbFolder();

        // 2) Cargar datos existentes (si los hubiera) en memoria
        List<Doctor> listaDoctores    = PersistenceUtil.cargarDoctoresDesdeDisco();
        List<Paciente> listaPacientes = PersistenceUtil.cargarPacientesDesdeDisco();
        List<Cita> listaCitas         = PersistenceUtil.cargarCitasDesdeDisco(listaDoctores, listaPacientes);

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Registrar doctor");
            System.out.println("2. Registrar paciente");
            System.out.println("3. Agendar cita");
            System.out.println("4. Listar citas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            switch (opcion) {
                case 1:
                    // Registrar doctor
                    System.out.print("Ingrese nombre del doctor: ");
                    String nombreDoc = sc.nextLine();
                    System.out.print("Ingrese especialidad: ");
                    String espec = sc.nextLine();
                    Doctor nuevoDoc = new Doctor(nombreDoc, espec);
                    listaDoctores.add(nuevoDoc);
                    PersistenceUtil.guardarDoctoresEnDisco(listaDoctores);
                    System.out.println("Doctor registrado: " + nuevoDoc);
                    break;

                case 2:
                    // Registrar paciente
                    System.out.print("Ingrese nombre del paciente: ");
                    String nombrePac = sc.nextLine();
                    System.out.print("Ingrese edad: ");
                    int edad = sc.nextInt();
                    sc.nextLine();
                    Paciente nuevoPac = new Paciente(nombrePac, edad);
                    listaPacientes.add(nuevoPac);
                    PersistenceUtil.guardarPacientesEnDisco(listaPacientes);
                    System.out.println("Paciente registrado: " + nuevoPac);
                    break;

                case 3:
                    // Agendar cita
                    if (listaDoctores.isEmpty() || listaPacientes.isEmpty()) {
                        System.out.println("Debe registrar al menos un doctor y un paciente primero.");
                        break;
                    }

                    // Mostrar doctores y elección válida
                    System.out.println("\nLista de doctores:");
                    for (int i = 0; i < listaDoctores.size(); i++) {
                        System.out.println((i + 1) + ". " + listaDoctores.get(i));
                    }
                    int idxDoc;
                    while (true) {
                        System.out.print("Seleccione doctor (número): ");
                        idxDoc = sc.nextInt() - 1;
                        if (idxDoc >= 0 && idxDoc < listaDoctores.size()) break;
                        System.out.println("Índice de doctor no válido.");
                    }
                    sc.nextLine(); // Consumir salto de línea

                    // Mostrar pacientes y elección válida
                    System.out.println("\nLista de pacientes:");
                    for (int i = 0; i < listaPacientes.size(); i++) {
                        System.out.println((i + 1) + ". " + listaPacientes.get(i));
                    }
                    int idxPac;
                    while (true) {
                        System.out.print("Seleccione paciente (número): ");
                        idxPac = sc.nextInt() - 1;
                        if (idxPac >= 0 && idxPac < listaPacientes.size()) break;
                        System.out.println("Índice de paciente no válido.");
                    }
                    sc.nextLine(); // Consumir salto de línea

                    // Leer fecha y hora
                    System.out.print("Ingrese fecha y hora (dd/MM/yyyy HH:mm): ");
                    String fechaHoraStr = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime fechaHora;
                    try {
                        fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);
                    } catch (Exception e) {
                        System.out.println("Formato de fecha/hora inválido. La cita no se creó.");
                        break;
                    }

                    // Crear y guardar la cita
                    Cita nuevaCita = new Cita(
                            listaDoctores.get(idxDoc),
                            listaPacientes.get(idxPac),
                            fechaHora
                    );
                    listaCitas.add(nuevaCita);
                    nuevaCita.agendar();
                    PersistenceUtil.guardarCitasEnDisco(listaCitas);
                    break;

                case 4:
                    // Listar todas las citas
                    if (listaCitas.isEmpty()) {
                        System.out.println("No hay citas registradas.");
                    } else {
                        System.out.println("\n--- LISTA DE CITAS ---");
                        for (int i = 0; i < listaCitas.size(); i++) {
                            System.out.println((i + 1) + ". " + listaCitas.get(i));
                        }
                    }
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (opcion != 5);

        sc.close();
        System.out.println("Programa finalizado.");
    }
}
