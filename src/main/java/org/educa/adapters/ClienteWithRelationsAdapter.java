package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ClienteWithRelations;
import org.educa.entity.ReservaWithRelations;

import java.util.ArrayList;
import java.util.List;

public class ClienteWithRelationsAdapter {

    public static ClienteWithRelations documentToClienteWithRelations(Document doc) {
        ClienteWithRelations cliente = new ClienteWithRelations();

        cliente.setId(doc.getObjectId("_id"));
        cliente.setNombre(doc.getString("nombre"));
        cliente.setApellidos(doc.getString("apellidos"));
        cliente.setDni(doc.getString("dni"));

        List<ReservaWithRelations> reservas = new ArrayList<>();
        List<Document> resDocs = doc.getList("reservas", Document.class);

        if (resDocs != null) {
            for (Document resDoc : resDocs) {
                if (resDoc.getObjectId("_id") != null) {

                    ReservaWithRelations reservaWithRelations = ReservaWithRelationsAdapter.documentToReservaWithRelations(resDoc);

                    reservas.add(reservaWithRelations);
                }
            }
        }
        cliente.setReservas(reservas);
        return cliente;
    }
}
