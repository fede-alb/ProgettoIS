package eseguibile;

import database.GestorePersistenza;
import entity.Cliente;

public class MainAggiungiClienti {
    static void main() {
        GestorePersistenza gestorePersistenza = new GestorePersistenza();
        Cliente cliente1 = new Cliente("Pietro", "Pedro", "ciao@gmail.com", "password", "333");
        Cliente cliente2 = new Cliente("Fabio", "Fizio", "fabio@gmail.com", "napoli", "380");
        gestorePersistenza.salva(cliente1);
        gestorePersistenza.salva(cliente2);
    }
}
