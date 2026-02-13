package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteWithRelations;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    /**
     * Obtiene el perfil completo de un cliente mediante su DNI.
     *
     * @param dni El dni del cliente
     * @return Cliente con sus relaciones cargadas.
     */
    public ClienteWithRelations findClienteByDni(String dni) {
        return clienteDAO.findClienteByDni(dni);
    }
}
