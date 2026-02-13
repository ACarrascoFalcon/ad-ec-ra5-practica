package org.educa.dao;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.educa.adapters.ReservaAdapter;
import org.educa.adapters.ReservaWithRelationsAdapter;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.settings.DatabaseSettings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ReservaDAOImpl implements ReservaDAO {

    public static final String COLECCION = "reservas";

    @Override
    public Long update(ReservaEntity reservaToUpdate) {
        try(MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())){
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(COLECCION);

            Bson filter = eq("_id", reservaToUpdate.getId());
            Bson updates = Updates.combine(

                    Updates.set("precio", reservaToUpdate.getPrecio().doubleValue()),
                    Updates.set("estado", reservaToUpdate.getEstado())
            );
            return mongoCollection.updateOne(filter, updates).getModifiedCount();
        }
    }

    @Override
    public ReservaEntity findById(String id) {
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(COLECCION);

            Document doc = mongoCollection.find(Filters.eq("_id", new ObjectId(id))).first();

            if (doc != null) {

                return ReservaAdapter.documentToReservaEntity(doc);

            }
            return null;
        }
    }

    @Override
    public ObjectId save(ReservaEntity reserva) {

        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(COLECCION);

            Document doc = new Document()

                    .append("matricula", reserva.getMatricula())
                    .append("dni", reserva.getDni())
                    .append("estado", reserva.getEstado())
                    .append("precio", reserva.getPrecio().doubleValue())
                    .append("fecha_ini", reserva.getFechaIni().toString())
                    .append("fecha_fin", reserva.getFechaFin().toString());

            mongoCollection.insertOne(doc);
            return doc.getObjectId("_id");
        }
    }

    @Override
    public Long delete(String id) {
        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(COLECCION);

            Bson filter = eq("_id", new ObjectId(id));

            return mongoCollection.deleteOne(filter).getDeletedCount();
        }
    }

    @Override
    public List<ReservaEntity> findAll() {
        List<ReservaEntity> reservas = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(COLECCION);

            FindIterable<Document> documents = mongoCollection.find();

            for (Document doc : documents) {
                reservas.add(ReservaAdapter.documentToReservaEntity(doc));
            }
        }

        return reservas;
    }

    @Override
    public List<ReservaWithRelations> findReservasByCantidad(BigDecimal cantidad) {

        List<ReservaWithRelations> resultados = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(DatabaseSettings.getURL())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DatabaseSettings.getDB());

            List<Document> pipeline = Arrays.asList(
                    new Document("$match",
                            new Document("precio", new Document("$gte", cantidad.doubleValue()))
                    ),
                    new Document("$lookup", new Document()
                            .append("from", "clientes")
                            .append("localField", "dni")
                            .append("foreignField", "dni")
                            .append("as", "cliente")
                    ),
                    new Document("$lookup", new Document()
                            .append("from", "vehiculos")
                            .append("localField", "matricula")
                            .append("foreignField", "matricula")
                            .append("as", "vehiculo")
                    ),
                    new Document("$unwind", new Document()
                            .append("path", "$cliente")
                            .append("preserveNullAndEmptyArrays", false)
                    ),
                    new Document("$unwind", new Document()
                            .append("path", "$vehiculo")
                            .append("preserveNullAndEmptyArrays", false)
                    )
            );

            for (Document doc : mongoDatabase.getCollection(COLECCION).aggregate(pipeline)) {

                resultados.add(ReservaWithRelationsAdapter.documentToReservaWithRelations(doc));

            }
        }
        return resultados;
    }
}
