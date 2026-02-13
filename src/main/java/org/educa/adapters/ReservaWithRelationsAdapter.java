package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ReservaWithRelations;

import java.math.BigDecimal;

public class ReservaWithRelationsAdapter {

    public static ReservaWithRelations documentToReservaWithRelations(Document doc) {
        ReservaWithRelations reservaWithRelations = new ReservaWithRelations();

        reservaWithRelations.setId(doc.getObjectId("_id"));
        reservaWithRelations.setEstado(doc.getString("estado"));

        Number precio = (Number) doc.get("precio");
        reservaWithRelations.setPrecio(precio != null ? new BigDecimal(precio.toString()) : BigDecimal.ZERO);

        reservaWithRelations.setFechaIni(doc.getString("fecha_ini"));
        reservaWithRelations.setFechaFin(doc.getString("fecha_fin"));

        reservaWithRelations.setCliente(ClienteAdapter.documentToClienteEntity((Document) doc.get("cliente")));
        reservaWithRelations.setVehiculo(VehiculoAdapter.documentToVehiculoEntity((Document) doc.get("vehiculo")));

        return reservaWithRelations;
    }
}
