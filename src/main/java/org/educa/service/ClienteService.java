package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteWithRelations;

public class ClienteService {

    ClienteDAO clienteDAO = new ClienteDAOImpl();

    public ClienteWithRelations findClienteByDni(String dni) {
        return clienteDAO.findClienteByDni(dni);
    }
}
