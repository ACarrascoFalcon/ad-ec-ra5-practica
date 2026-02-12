package org.educa.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.educa.dao.ReservasDAO;

import java.io.Serializable;
import java.lang.annotation.Documented;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity implements Serializable {

    @SerializedName("_id")
    private ObjectId id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellidos")
    private String apellidos;
    @SerializedName("dni")
    private String dni;
}
