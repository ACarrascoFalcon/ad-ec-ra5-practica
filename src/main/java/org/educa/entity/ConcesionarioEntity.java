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
public class ConcesionarioEntity implements Serializable {

    @SerializedName("_id")
    private ObjectId id;
    @SerializedName("codigo")
    private String codigo;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("ciudad")
    private String ciudad;
    @SerializedName("pais")
    private String pais;
    @SerializedName("cp")
    private String cp;
}