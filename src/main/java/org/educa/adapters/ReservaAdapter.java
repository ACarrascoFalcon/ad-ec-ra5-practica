package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ReservaEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservaAdapter {

    public static ReservaEntity documentToReservaEntity(Document doc) {
        ReservaEntity reserva = new ReservaEntity();

        reserva.setId(doc.getObjectId("_id"));
        reserva.setDni(doc.getString("dni"));
        reserva.setMatricula(doc.getString("matricula"));
        reserva.setEstado(doc.getString("estado"));

        Number precio = (Number) doc.get("precio");
        reserva.setPrecio(precio != null ? new BigDecimal(precio.toString()) : BigDecimal.ZERO);

        reserva.setFechaIni(LocalDate.parse(doc.getString("fecha_ini")));
        reserva.setFechaFin(LocalDate.parse(doc.getString("fecha_fin")));

        return reserva;
    }
}
