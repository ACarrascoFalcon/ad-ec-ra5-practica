package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.VehiculoEntity;

public class VehiculoAdapter {

    public static VehiculoEntity documentToVehiculoEntity(Document doc) {
        if (doc == null) return null;

        return new VehiculoEntity(
                doc.getObjectId("_id"),
                doc.getString("matricula"),
                doc.getString("marca"),
                doc.getString("modelo"),
                doc.getString("color"),
                doc.getString("concesionario")
        );
    }
}
