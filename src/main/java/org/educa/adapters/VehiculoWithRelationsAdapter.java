package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ConcesionarioEntity;
import org.educa.entity.ReservaEntity;
import org.educa.entity.VehiculoWithRelations;

import java.util.ArrayList;
import java.util.List;

public class VehiculoWithRelationsAdapter {

    public static VehiculoWithRelations documentToVehiculoWithRelations(Document doc) {

        VehiculoWithRelations vehiculoWithRelations = new VehiculoWithRelations();

        vehiculoWithRelations.setId(doc.getObjectId("_id"));
        vehiculoWithRelations.setMatricula(doc.getString("matricula"));
        vehiculoWithRelations.setMarca(doc.getString("marca"));
        vehiculoWithRelations.setModelo(doc.getString("modelo"));
        vehiculoWithRelations.setColor(doc.getString("color"));

        Document cDoc = (Document) doc.get("concesionario");

        if (cDoc != null) {
            vehiculoWithRelations.setConcesionario(new ConcesionarioEntity
                    (cDoc.getObjectId("_id"),
                            cDoc.getString("nombre"),
                            cDoc.getString("ciudad"),
                            cDoc.getString("pais"),
                            cDoc.getString("cp")));

            List<Document> rDocs = doc.getList("reservas", Document.class);
            List<ReservaEntity> reservas = new ArrayList<>();

            if (rDocs != null) {
                for (Document rDoc : rDocs) {

                    reservas.add(ReservaAdapter.documentToReservaEntity(rDoc));

                }
            }

            vehiculoWithRelations.setReservas(reservas);

        }
        return vehiculoWithRelations;
    }
}
