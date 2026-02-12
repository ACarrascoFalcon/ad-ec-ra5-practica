package org.educa.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaWithRelations implements Serializable {

    @SerializedName("_id")
    private ObjectId id;
    private VehiculoEntity vehiculo;
    private ClienteEntity cliente;
    @SerializedName("estado")
    private String estado;
    @SerializedName("precio")
    private BigDecimal precio;
    @SerializedName("fecha_ini")
    private String fechaIni;
    @SerializedName("fecha_fin")
    private String fechaFin;
}

