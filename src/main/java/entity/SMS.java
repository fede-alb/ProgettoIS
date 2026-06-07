package entity;

public class SMS {
    // Classe offerta dal sistema esterno, che supponiamo abbia il metodo:
    public int sendMessage(String msg) {
        // Supponiamo ritorni un intero pari a 0 in caso di corretto invio
        // Numeri diversi da 0 implicano un caso di errore
        return 0;
    }
}
