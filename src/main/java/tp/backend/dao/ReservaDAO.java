package tp.backend.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import tp.backend.entities.Reserva;

public class ReservaDAO extends GenericDAO<Reserva, Long> {

    public ReservaDAO(EntityManager entityManager) {
        super(entityManager, Reserva.class);
    }

    public List<Reserva> findByCamionId(Long camionId) {
        return entityManager.createQuery(
                "SELECT r FROM Reserva r WHERE r.camion.id = :camionId", Reserva.class)
                .setParameter("camionId", camionId)
                .getResultList();
    }

    public List<Reserva> findActivasByCamionAndPeriodo(Long camionId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String jpql = "SELECT r FROM Reserva r "
                + "WHERE r.camion.id = :camionId "
                + "AND r.estado = :estado "
                + "AND r.fechaInicio <= :fechaFin "
                + "AND r.fechaFin >= :fechaInicio";

        return entityManager.createQuery(jpql, Reserva.class)
                .setParameter("camionId", camionId)
                .setParameter("estado", "CONFIRMADA")
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }
}
