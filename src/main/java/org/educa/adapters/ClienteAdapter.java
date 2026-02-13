package org.educa.adapters;

import org.bson.Document;
import org.educa.entity.ClienteEntity;

public class ClienteAdapter {

    public static ClienteEntity documentToClienteEntity(Document doc) {
        if (doc == null) return null;

        return new ClienteEntity(
                doc.getObjectId("_id"),
                doc.getString("nombre"),
                doc.getString("apellidos"),
                doc.getString("dni")
        );

    }
}
