package tp.backend.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import tp.backend.entities.Camion;

public class CamionDAO extends GenericDAO<Camion, Long> {

    public CamionDAO(EntityManager entityManager) {
        super(entityManager, Camion.class);
    }

    public Camion findByPatente(String patente) {
        List<Camion> results = entityManager.createQuery(
                "SELECT c FROM Camion c WHERE c.patente = :patente", Camion.class)
                .setParameter("patente", patente)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    public List<Camion> findDisponibles(LocalDateTime fechaInicio, LocalDateTime fechaFin, Double volumenRequerido) {
        String jpql = "SELECT c FROM Camion c "
                + "WHERE c.estado = :estado "
                + "AND c.capacidadVolumen >= :volumen "
                + "AND NOT EXISTS ("
                + "    SELECT r.id FROM Reserva r "
                + "    WHERE r.camion = c "
                + "    AND r.estado = :estadoReserva "
                + "    AND r.fechaInicio <= :fechaFin "
                + "    AND r.fechaFin >= :fechaInicio"
                + ")";

        return entityManager.createQuery(jpql, Camion.class)
                .setParameter("estado", "ACTIVO")
                .setParameter("volumen", volumenRequerido)
                .setParameter("estadoReserva", "CONFIRMADA")
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }
}
