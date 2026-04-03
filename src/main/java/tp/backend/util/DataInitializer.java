package tp.backend.util;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import tp.backend.dao.CamionDAO;
import tp.backend.dao.ReservaDAO;
import tp.backend.entities.Camion;
import tp.backend.entities.Reserva;

public final class DataInitializer {

    private DataInitializer() {
    }

    public static void seedData() {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            CamionDAO camionDAO = new CamionDAO(entityManager);
            ReservaDAO reservaDAO = new ReservaDAO(entityManager);

            // Si ya hay camiones, no vuelve a insertar para evitar duplicados.
            if (!camionDAO.findAll().isEmpty()) {
                transaction.commit();
                return;
            }

            Camion camion1 = new Camion("AA123BB", "Mercedes", "Actros", 2020, 45.0);
            Camion camion2 = new Camion("AC456DE", "Scania", "R450", 2021, 60.0);
            Camion camion3 = new Camion("AD789FG", "Volvo", "FH", 2019, 38.0);

            camionDAO.save(camion1);
            camionDAO.save(camion2);
            camionDAO.save(camion3);

            Reserva reserva1 = new Reserva(
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(3),
                    30.0,
                    "Cordoba",
                    "Rosario",
                    camion1
            );

            Reserva reserva2 = new Reserva(
                    LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(4),
                    50.0,
                    "Buenos Aires",
                    "Mendoza",
                    camion2
            );

            Reserva reserva3 = new Reserva(
                    LocalDateTime.now().plusDays(5),
                    LocalDateTime.now().plusDays(7),
                    20.0,
                    "Santa Fe",
                    "Cordoba",
                    camion3
            );
            reserva3.setEstado("CANCELADA");

            reservaDAO.save(reserva1);
            reservaDAO.save(reserva2);
            reservaDAO.save(reserva3);

            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        } finally {
            entityManager.close();
        }
    }
}
