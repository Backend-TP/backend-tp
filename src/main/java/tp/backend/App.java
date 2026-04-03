package tp.backend;

import tp.backend.util.DataInitializer;
import tp.backend.util.JPAUtil;

public class App {
    public static void main(String[] args) {
        try {
            DataInitializer.seedData();
            System.out.println("Base de datos inicializada correctamente.");
        } finally {
            JPAUtil.close();
        }
    }
}
