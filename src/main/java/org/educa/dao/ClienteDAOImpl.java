package org.educa.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.educa.adapters.ClienteWithRelationsAdapter;
import org.educa.entity.ClienteWithRelations;
import org.educa.settings.DatabaseSettings;

import java.util.Arrays;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    private final String COLECCION = "clientes";

    @Override
    public ClienteWithRelations findClienteByDni(String dni) {

        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase db = mongoClient.getDatabase(DatabaseSettings.getDB());

            List<Document> pipeline = Arrays.asList(
                    new Document("$match", new Document("dni", dni)),

                    new Document("$lookup", new Document("from", "reservas")
                            .append("localField", "dni")
                            .append("foreignField", "dni")
                            .append("as", "reservas")),

                    new Document("$unwind", new Document("path", "$reservas")
                            .append("preserveNullAndEmptyArrays", true)),

                    new Document("$lookup", new Document("from", "vehiculos")
                            .append("localField", "reservas.matricula")
                            .append("foreignField", "matricula")
                            .append("as", "reservas.vehiculo")),

                    new Document("$unwind", new Document("path", "$reservas.vehiculo")
                            .append("preserveNullAndEmptyArrays", true)),

                    new Document("$group", new Document("_id", "$_id")
                            .append("nombre", new Document("$first", "$nombre"))
                            .append("apellidos", new Document("$first", "$apellidos"))
                            .append("dni", new Document("$first", "$dni"))
                            .append("reservas", new Document("$push", "$reservas")))
            );

            Document result = db.getCollection(COLECCION).aggregate(pipeline).first();

            return result != null ? ClienteWithRelationsAdapter.documentToClienteWithRelations(result) : null;
        }
    }


}
