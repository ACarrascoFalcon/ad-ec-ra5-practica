package org.educa.dao;

import org.bson.Document;
import org.educa.wrappers.IngresosVehiculo;

import java.util.List;

public interface VehiculoDAO {

    List<Document> ingresosPorVehiculo();
}
