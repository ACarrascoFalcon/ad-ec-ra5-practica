package org.educa.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.educa.settings.DatabaseSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {

    private final String COLECCION = "vehiculos";

    @Override
    public List<Document> ingresosPorVehiculo() {

        List<Document> resultados = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());

            List<Document> pipeline = Arrays.asList(
                    new Document("$lookup", new Document("from", "concesionarios")
                            .append("localField", "concesionario")
                            .append("foreignField", "codigo")
                            .append("as", "concesionario")),

                    new Document("$unwind", new Document("path", "$concesionario")
                            .append("preserveNullAndEmptyArrays", true)),

                    new Document("$lookup", new Document("from", "reservas")
                            .append("localField", "matricula")
                            .append("foreignField", "matricula")
                            .append("as", "reservas"))
            );

            mongoDatabase.getCollection(COLECCION).aggregate(pipeline).into(resultados);
        }

        return resultados;
    }
}
