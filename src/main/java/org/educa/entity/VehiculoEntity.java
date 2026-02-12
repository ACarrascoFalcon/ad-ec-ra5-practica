package org.educa.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity implements Serializable {

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
    @SerializedName("concesionario")
    private String concesionario;
}
