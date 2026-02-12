package org.educa.service;

import org.bson.types.ObjectId;
import org.educa.dao.ReservaDAOImpl;
import org.educa.dao.ReservasDAO;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ReservaService {

    private final ReservasDAO reservasDAO = new ReservaDAOImpl();


    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {

        List<ReservaWithRelations> resultados = reservasDAO.findReservasByCantidad(cantidad);

        if (resultados.isEmpty()) {
            return Collections.emptyList();
        }else {
            return resultados;
        }
    }


    public ObjectId save(ReservaEntity reserva) {
        return reservasDAO.save(reserva);
    }


    public ReservaEntity findById(String id) {
        return reservasDAO.findById(id);
    }

    public Long update(ReservaEntity reservaToUpdate) {
        return reservasDAO.update(reservaToUpdate);
    }

    public Long delete(String id) {
        return reservasDAO.delete(id);
    }

    public List<ReservaEntity> findAll() {
        return reservasDAO.findAll();
    }
}
