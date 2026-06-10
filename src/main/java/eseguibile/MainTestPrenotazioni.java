package eseguibile;
import boundary.ElencoPrenotazioniView;


public class MainTestPrenotazioni {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            new ElencoPrenotazioniView();
        });

    }
}
