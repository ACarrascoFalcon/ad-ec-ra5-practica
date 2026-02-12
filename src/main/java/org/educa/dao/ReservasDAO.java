package org.educa.dao;

import org.bson.types.ObjectId;
import org.educa.entity.ReservaEntity;

import java.util.List;

public interface ReservasDAO {
    Long update(ReservaEntity reservaToUpdate);

    ReservaEntity findById(String id);

    ObjectId save (ReservaEntity reserva);

    Long delete(String id);

    List<ReservaEntity> findAll();

    List<ReservaEntity> findReservasByCantidad(int cantidad);
}
