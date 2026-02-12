package org.educa.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEntity implements Serializable {

    @SerializedName("_id")
    private ObjectId id;
    @SerializedName("matricula")
    private String matricula;
    @SerializedName("dni")
    private String dni;
    @SerializedName("estado")
    private String estado;
    @SerializedName("precio")
    private BigDecimal precio;
    @SerializedName("fecha_ini")
    private LocalDate fechaIni;
    @SerializedName("fecha_fin")
    private LocalDate fechaFin;

    /**
     * Sobreescribimos el metodo toString
     *
     * @return la cadena resultante
     */
    public String toString() {
        return "ReservaEntity(id=" + this.getId().toHexString() + ", matricula=" + this.getMatricula() +
                ", dni=" + this.getDni() + ", estado=" + this.getEstado() +
                ", precio=" + this.getPrecio() +
                ", fechaIni=" + this.getFechaIni() +
                ", fechaFin=" + this.getFechaFin() + ")";
    }
}
