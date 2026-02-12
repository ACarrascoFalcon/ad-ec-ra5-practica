package org.educa.dao;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;
import org.educa.entity.ReservaWithRelations;
import org.educa.entity.VehiculoEntity;
import org.educa.settings.DatabaseSettings;

import java.math.BigDecimal;
import java.time.LocalDate;
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

                return documentToReservaEntity(doc);

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
                reservas.add(documentToReservaEntity(doc));
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

                resultados.add(documentToReservaWithRelations(doc));

            }
        }
        return resultados;
    }

    private ReservaEntity documentToReservaEntity(Document doc) {
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

    private ReservaWithRelations documentToReservaWithRelations(Document doc) {
        ReservaWithRelations reservaWithRelations = new ReservaWithRelations();

        reservaWithRelations.setId(doc.getObjectId("_id"));
        reservaWithRelations.setEstado(doc.getString("estado"));

        Number precio = (Number) doc.get("precio");
        reservaWithRelations.setPrecio(precio != null ? new BigDecimal(precio.toString()) : BigDecimal.ZERO);

        reservaWithRelations.setFechaIni(doc.getString("fecha_ini"));
        reservaWithRelations.setFechaFin(doc.getString("fecha_fin"));

        reservaWithRelations.setCliente(documentToClienteEntity((Document) doc.get("cliente")));
        reservaWithRelations.setVehiculo(documentToVehiculoEntity((Document) doc.get("vehiculo")));

        return reservaWithRelations;
    }

    private VehiculoEntity documentToVehiculoEntity(Document doc) {
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

    private ClienteEntity documentToClienteEntity(Document doc) {

        if (doc == null) return null;

        return new ClienteEntity(

                doc.getObjectId("_id"),

                doc.getString("nombre"),

                doc.getString("apellidos"),

                doc.getString("dni")

        );

    }
}
