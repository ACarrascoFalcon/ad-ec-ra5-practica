package org.educa.dao;

import org.educa.entity.ClienteWithRelations;

public interface ClienteDAO {

    ClienteWithRelations findClienteByDni(String dni);
}
