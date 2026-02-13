package org.educa.service;

import org.bson.types.ObjectId;
import org.educa.dao.ReservaDAO;
import org.educa.dao.ReservaDAOImpl;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;
import java.util.List;

public class ReservaService {

    private final ReservaDAO reservaDAO = new ReservaDAOImpl();

    /**
     * Filtra reservas por cantidad mínima asegurando que nunca devuelva un nulo.
     *
     * @param cantidad Precio de minimo de la reserva
     * @return Lista de reservas
     */
    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {
        return reservaDAO.findReservasByCantidad(cantidad);
    }

    /**
     * Guarda las reservas
     *
     * @param reserva La reserva a guardar
     * @return ObjectId de la reserva
     */
    public ObjectId save(ReservaEntity reserva) {
        return reservaDAO.save(reserva);
    }

    /**
     * Encuantra la reserva por id
     *
     * @param id id de la reserva a buscar
     * @return La entidad ReservaEntity encontrada o null.
     */
    public ReservaEntity findById(String id) {
        return reservaDAO.findById(id);
    }

    /**
     * Actualiza el estado y precio de una reserva existente.
     *
     * @param reservaToUpdate Objeto con los nuevos datos y el ID de la reserva.
     * @return El número de documentos modificados.
     */
    public Long update(ReservaEntity reservaToUpdate) {
        return reservaDAO.update(reservaToUpdate);
    }

    /**
     * Elimina una reserva de la base de datos.
     *
     * @param id Identificador de la reserva a borrar.
     * @return El número de documentos eliminados.
     */
    public Long delete(String id) {
        return reservaDAO.delete(id);
    }

    /**
     * Recoge todas las reservas en formato simple.
     *
     * @return Lista de todas las entidades ReservaEntity.
     */
    public List<ReservaEntity> findAll() {
        return reservaDAO.findAll();
    }
}
