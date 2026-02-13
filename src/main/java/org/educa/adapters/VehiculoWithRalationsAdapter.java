package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ConcesionarioEntity;
import org.educa.entity.VehiculoWithRelations;

public class VehiculoWithRalationsAdapter {

    public static VehiculoWithRelations documentToVehiculoWithRelations(Document doc) {
        VehiculoWithRelations vehiculoWithRelations = new VehiculoWithRelations();
        vehiculoWithRelations.setId(doc.getObjectId("_id"));
        vehiculoWithRelations.setMatricula(doc.getString("matricula"));
        vehiculoWithRelations.setMarca(doc.getString("marca"));
        vehiculoWithRelations.setModelo(doc.getString("modelo"));

        Document cDoc = (Document) doc.get("concesionario");
        if (cDoc != null) {
            vehiculoWithRelations.setConcesionario(new ConcesionarioEntity(cDoc.getObjectId("_id"), cDoc.getString("nombre"), cDoc.getString("ciudad"), cDoc.getString("pais"), cDoc.getString("cp")));
        }
        return vehiculoWithRelations;
    }
}
