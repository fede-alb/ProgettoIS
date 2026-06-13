package eseguibile;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// *** HELPER PER CREARE LE TABELLE IN MYSQL ***
public class HelperCreaTabelle {
    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("stabilimento");
        emf.close();
        System.out.println("Avvio di Hibernate completato.");
    }
}
