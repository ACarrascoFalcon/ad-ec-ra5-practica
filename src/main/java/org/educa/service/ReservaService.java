package org.educa.service;

import org.bson.types.ObjectId;
import org.educa.dao.ReservaDAO;
import org.educa.dao.ReservaDAOImpl;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();


    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {

        List<ReservaWithRelations> resultados = reservaDAO.findReservasByCantidad(cantidad);

        if (resultados.isEmpty()) {
            return Collections.emptyList();
        }else {
            return resultados;
        }
    }


    public ObjectId save(ReservaEntity reserva) {
        return reservaDAO.save(reserva);
    }


    public ReservaEntity findById(String id) {
        return reservaDAO.findById(id);
    }

    public Long update(ReservaEntity reservaToUpdate) {
        return reservaDAO.update(reservaToUpdate);
    }

    public Long delete(String id) {
        return reservaDAO.delete(id);
    }

    public List<ReservaEntity> findAll() {
        return reservaDAO.findAll();
    }
}
