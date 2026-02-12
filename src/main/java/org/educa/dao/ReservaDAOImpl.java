package org.educa.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.educa.entity.ReservaEntity;
import org.educa.settings.DatabaseSettings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ReservaDAOImpl implements ReservasDAO {

    public static final String COLECCION = "reservas";
    private final Gson gson = new GsonBuilder().create();

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
        return 0L;
    }

    @Override
    public List<ReservaEntity> findReservasByCantidad(int cantidad) {
        return List.of();
    }
}
