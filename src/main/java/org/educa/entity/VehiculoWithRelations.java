package org.educa.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoWithRelations implements Serializable {

    @SerializedName("_id")
    private ObjectId id;
    @SerializedName("matricula")
    private String matricula;
    @SerializedName("marca")
    private String marca;
    @SerializedName("modelo")
    private String modelo;
    @SerializedName("color")
    private String color;
    private ConcesionarioEntity concesionario;
    private List<ReservaEntity> reservas;
}
