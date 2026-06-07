package eseguibile;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainCreaTabelle {
    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("stabilimento");
        emf.close();
        System.out.println("Avvio di Hibernate completato.");
    }
}
