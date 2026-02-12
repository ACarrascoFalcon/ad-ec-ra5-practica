package org.educa.dao;

import org.bson.types.ObjectId;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;
import java.util.List;

public interface ReservasDAO {
    Long update(ReservaEntity reservaToUpdate);

    ReservaEntity findById(String id);

    ObjectId save (ReservaEntity reserva);

    Long delete(String id);

    List<ReservaEntity> findAll();

    List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad);
}
