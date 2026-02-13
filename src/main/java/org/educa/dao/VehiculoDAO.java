package org.educa.dao;

import org.bson.Document;

import java.util.List;

public interface VehiculoDAO {

    /**
     * Realiza una agregación para obtener vehículos, sus concesionarios y su historial de reservas.
     *
     * @return Una lista de documentos BSON con la información cruda de la base de datos.
     */
    List<Document> ingresosPorVehiculo();
}
