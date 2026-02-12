package org.educa.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.educa.entity.VehiculoWithRelations;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngresosVehiculo {
    private VehiculoWithRelations vehiculo;
    private BigDecimal ingresos;
}
