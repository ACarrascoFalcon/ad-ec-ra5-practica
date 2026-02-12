package org.educa.service;

import org.bson.Document;
import org.educa.dao.VehiculoDAO;
import org.educa.dao.VehiculoDAOImpl;
import org.educa.entity.ConcesionarioEntity;
import org.educa.entity.VehiculoWithRelations;
import org.educa.wrappers.IngresosVehiculo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VehiculoService {

    private final VehiculoDAOImpl vehiculoDAO = new VehiculoDAOImpl();

    public List<IngresosVehiculo> ingresosPorVehiculo() {
        List<IngresosVehiculo> listaFinal = new ArrayList<>();
        List<Document> docs = vehiculoDAO.ingresosPorVehiculo();

        for (Document doc : docs) {
            VehiculoWithRelations v = mapToVehiculoWithRelations(doc);
            BigDecimal total = calcularIngresos(doc);
            listaFinal.add(new IngresosVehiculo(v, total));
        }
        return listaFinal;
    }

    private BigDecimal calcularIngresos(Document doc) {
        BigDecimal total = BigDecimal.ZERO;
        List<Document> reservas = (List<Document>) doc.get("reservas");
        if (reservas != null) {
            for (Document res : reservas) {
                Number p = (Number) res.get("precio");
                BigDecimal precio = p != null ? new BigDecimal(p.toString()) : BigDecimal.ZERO;

                if ("Cancelada".equals(res.getString("estado"))) {
                    total = total.add(precio.divide(new BigDecimal("2")));
                } else {
                    total = total.add(precio);
                }
            }
        }
        return total;
    }

    private VehiculoWithRelations mapToVehiculoWithRelations(Document doc) {
        VehiculoWithRelations v = new VehiculoWithRelations();
        v.setId(doc.getObjectId("_id"));
        v.setMatricula(doc.getString("matricula"));
        v.setMarca(doc.getString("marca"));
        v.setModelo(doc.getString("modelo"));

        Document cDoc = (Document) doc.get("concesionario");
        if (cDoc != null) {
            v.setConcesionario(new ConcesionarioEntity(cDoc.getObjectId("_id"), cDoc.getString("codigo"), cDoc.getString("nombre"), cDoc.getString("ciudad"), cDoc.getString("pais"), cDoc.getString("cp")));
        }
        return v;
    }

}
