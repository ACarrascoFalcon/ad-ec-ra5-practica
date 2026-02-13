package org.educa.dao;

import org.educa.entity.ClienteWithRelations;

public interface ClienteDAO {

    /**
     * Busca un cliente por su DNI incluyendo su historial de reservas y vehículos asociados.
     *
     * @param dni El DNI del cliente a buscar.
     * @return Un objeto ClienteWithRelations con toda la información vinculada, o null si no se encuentra.
     */
    ClienteWithRelations findClienteByDni(String dni);
}
