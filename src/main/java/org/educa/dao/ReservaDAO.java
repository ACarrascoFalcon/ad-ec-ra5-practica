package org.educa.dao;

import org.bson.types.ObjectId;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;
import java.util.List;

public interface ReservaDAO {

    /**
     * Actualiza el estado y precio de una reserva existente.
     *
     * @param reservaToUpdate Objeto con los nuevos datos y el ID de la reserva.
     * @return El número de documentos modificados.
     */
    Long update(ReservaEntity reservaToUpdate);

    /**
     * Busca una reserva simple por su identificador único.
     *
     * @param id id Identificador en formato String (ObjectId).
     * @return La entidad ReservaEntity encontrada o null.
     */
    ReservaEntity findById(String id);

    /**
     * Inserta una nueva reserva en la base de datos.
     *
     * @param reserva reserva Objeto reserva a persistir.
     * @return El ObjectId generado por MongoDB.
     */
    ObjectId save (ReservaEntity reserva);


    /**
     * Elimina una reserva de la base de datos.
     *
     * @param id Identificador de la reserva a borrar.
     * @return El número de documentos eliminados.
     */
    Long delete(String id);

    /**
     * Recupera todas las reservas en formato simple.
     *
     * @return Lista de todas las entidades ReservaEntity.
     */
    List<ReservaEntity> findAll();

    /**
     * Obtiene reservas que superen un precio determinado, incluyendo datos de cliente y vehículo.
     *
     * @param cantidad Precio mínimo de filtrado.
     * @return Lista de reservas con sus relaciones completas.
     */
    List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad);
}
