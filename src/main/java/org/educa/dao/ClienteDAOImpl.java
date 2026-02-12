package org.educa.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.educa.entity.ClienteEntity;
import org.educa.entity.ClienteWithRelations;
import org.educa.entity.ReservaWithRelations;
import org.educa.entity.VehiculoEntity;
import org.educa.settings.DatabaseSettings;

import java.math.BigDecimal;
import java.util.ArrayList;
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
                    new Document("$lookup", new Document("from", "reservas").append("localField", "dni").append("foreignField", "dni").append("as", "reservas")),
                    new Document("$unwind", new Document("path", "$reservas").append("preserveNullAndEmptyArrays", true)),
                    new Document("$lookup", new Document("from", "vehiculos").append("localField", "reservas.matricula").append("foreignField", "matricula").append("as", "reservas.vehiculo")),
                    new Document("$unwind", new Document("path", "$reservas.vehiculo").append("preserveNullAndEmptyArrays", true)),
                    new Document("$group", new Document("_id", "$_id")
                            .append("nombre", new Document("$first", "$nombre"))
                            .append("apellidos", new Document("$first", "$apellidos"))
                            .append("dni", new Document("$first", "$dni"))
                            .append("reservas", new Document("$push", "$reservas")))
            );

            Document result = db.getCollection(COLECCION).aggregate(pipeline).first();
            return result != null ? mapToClienteWithRelations(result) : null;
        }
    }

    private ClienteWithRelations mapToClienteWithRelations(Document doc) {
        ClienteWithRelations cliente = new ClienteWithRelations();
        cliente.setId(doc.getObjectId("_id"));
        cliente.setNombre(doc.getString("nombre"));
        cliente.setApellidos(doc.getString("apellidos"));
        cliente.setDni(doc.getString("dni"));

        List<ReservaWithRelations> reservas = new ArrayList<>();
        List<Document> resDocs = (List<Document>) doc.get("reservas");

        if (resDocs != null) {
            for (Document resDoc : resDocs) {
                if (resDoc.getObjectId("_id") != null) {
                    ReservaWithRelations r = new ReservaWithRelations();
                    r.setId(resDoc.getObjectId("_id"));
                    r.setEstado(resDoc.getString("estado"));
                    Number p = (Number) resDoc.get("precio");
                    r.setPrecio(p != null ? new BigDecimal(p.toString()) : BigDecimal.ZERO);
                    r.setFechaIni(resDoc.getString("fecha_ini"));
                    r.setFechaFin(resDoc.getString("fecha_fin"));

                    Document vDoc = (Document) resDoc.get("vehiculo");
                    if (vDoc != null) {
                        r.setVehiculo(new VehiculoEntity(vDoc.getObjectId("_id"), vDoc.getString("matricula"), vDoc.getString("marca"), vDoc.getString("modelo"), vDoc.getString("color"), vDoc.getString("concesionario")));
                    }
                    reservas.add(r);
                }
            }
        }
        cliente.setReservas(reservas);
        return cliente;
    }
}
