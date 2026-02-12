package org.educa;

import org.bson.types.ObjectId;
import org.educa.entity.ClienteWithRelations;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.service.ClienteService;
import org.educa.service.ReservaService;
import org.educa.service.VehiculoService;
import org.educa.wrappers.IngresosVehiculo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ReservaService reservaService = new ReservaService();
            ClienteService clienteService = new ClienteService();
            VehiculoService vehiculoService = new VehiculoService();
            int opcion = 0;

            do {
                System.out.println("\nI.E.S. EL CAÑAVERAL - PRÁCTICA RA5 (MongoDB)");
                System.out.println("1. CRUD de reservas (Actividad 1)");
                System.out.println("2. Consultar reservas a partir de un precio (Actividad 2)");
                System.out.println("3. Consultar cliente por DNI (Actividad 3)");
                System.out.println("4. Informe de ingresos por vehículo (Actividad 4)");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");

                try {
                    opcion = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    opcion = -1;
                }

                switch (opcion) {
                    case 1:
                        menuCrud(sc, reservaService);
                        break;
                    case 2:
                        System.out.print("Introduzca la cantidad mínima de dinero: ");
                        BigDecimal precioMin = sc.nextBigDecimal();
                        sc.nextLine();
                        List<ReservaWithRelations> reservas = reservaService.findReservasByCantidad(precioMin);
                        for (ReservaWithRelations reserva : reservas) {
                            System.out.println(reserva);
                        }
                        break;
                    case 3:
                        System.out.print("Introduzca el DNI del cliente: ");
                        String dni = sc.nextLine();
                        ClienteWithRelations cliente = clienteService.findClienteByDni(dni);
                        System.out.println(cliente);
                        break;
                    case 4:
                        List<IngresosVehiculo> ingresos = vehiculoService.ingresosPorVehiculo();
                        for (IngresosVehiculo ingreso : ingresos) {
                            System.out.println(ingreso);
                        }
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }

            } while (opcion != 5);
        }
    }

    private static void menuCrud(Scanner sc, ReservaService reservaService) {
        System.out.println("\n--- SUBMENÚ CRUD RESERVAS ---");
        System.out.println("A. Ver todas");
        System.out.println("B. Buscar por ID");
        System.out.println("C. Insertar nueva");
        System.out.println("D. Actualizar existente");
        System.out.println("E. Eliminar");
        System.out.print("Opción: ");
        String op = sc.nextLine().toUpperCase();

        switch (op.toUpperCase()) {
            case "A":
                List<ReservaEntity> reservas = reservaService.findAll();
                for (ReservaEntity reserva : reservas) {
                    System.out.println(reserva);
                }
                break;
            case "B":
                System.out.print("ID (ObjectId hex): ");
                ReservaEntity reserva = reservaService.findById(sc.nextLine());
                System.out.println(reserva);
                break;
            case "C":
                System.out.println("Ingrese datos (DNI, Matricula, Estado, Precio, Fechas):");
                ReservaEntity reservaEntity = new ReservaEntity();
                System.out.print("DNI: ");
                reservaEntity.setDni(sc.nextLine());
                System.out.print("Matrícula: ");
                reservaEntity.setMatricula(sc.nextLine());
                System.out.print("Estado (Confirmada/Cancelada): ");
                reservaEntity.setEstado(sc.nextLine());
                System.out.print("Precio: ");
                reservaEntity.setPrecio(sc.nextBigDecimal());
                sc.nextLine();
                System.out.print("Fecha Ini (YYYY-MM-DD): ");
                String fechaI = sc.nextLine();
                reservaEntity.setFechaIni(LocalDate.parse(fechaI));
                System.out.print("Fecha Fin (YYYY-MM-DD): ");
                String fechaF = sc.nextLine();
                reservaEntity.setFechaFin(LocalDate.parse(fechaF));
                ObjectId objectId = reservaService.save(reservaEntity);
                System.out.println("Reserva insertada con id: " + objectId.toHexString());
                break;
            case "D":
                System.out.print("ID de reserva a actualizar: ");
                String idUpd = sc.nextLine();
                ReservaEntity reservaToUpdate = reservaService.findById(idUpd);
                if (reservaToUpdate != null) {
                    System.out.print("Nuevo Precio: ");
                    BigDecimal nPrecio = sc.nextBigDecimal();
                    sc.nextLine();
                    reservaToUpdate.setPrecio(nPrecio);
                    System.out.print("Nuevo Estado: ");
                    String nEstado = sc.nextLine();
                    reservaToUpdate.setEstado(nEstado);
                    reservaService.update(reservaToUpdate);
                    System.out.println("Reserva actualizada");
                } else {
                    System.out.println("Reserva no encontrada");
                }
                break;
            case "E":
                System.out.print("ID de reserva a eliminar: ");
                reservaService.delete(sc.nextLine());
                System.out.println("Reserva eliminada");
                break;
            default:
                System.out.println("Opción desconocida.");
        }
    }

}
