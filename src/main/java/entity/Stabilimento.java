package entity;

import database.GestorePersistenza;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

//Classe singleton
public class Stabilimento {
    private static Stabilimento istanza;
    private final GestorePersistenza gestorePersistenza;

    private Stabilimento() {
        gestorePersistenza = new GestorePersistenza();
    }

    public static Stabilimento getIstanza() {
        if(istanza == null) {
            istanza = new Stabilimento();
        }
        return istanza;
    }

    //Controlla se è stato configurato, se trova almeno una fila allora sì
    public boolean isGiaConfigurato() {
        List<Fila> file = gestorePersistenza.cercaPerCampi(Fila.class, new HashMap<>());
        return !file.isEmpty();
    }

    //Crea la struttura e salva nel database
    //Crea 3 file, salva ogni fila e crea il num. di ombrelloni richiesto per ciascuna, collegandoli alla fila giusta
    //Salva i servizi aggiuntivi passati nella map
    public boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {
        if (isGiaConfigurato()) return false;
        Fila primaFila = new Fila();
        boolean ok = gestorePersistenza.salva(primaFila);
        if (!ok) return false;

        for (int i = 0; i < nPrimaFila; i++) {
            Ombrellone ombrellone = new Ombrellone(primaFila);
            primaFila.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        Fila filaIntermedia = new Fila();
        ok = gestorePersistenza.salva(filaIntermedia);
        if (!ok) return false;

        for (int i = 0; i < nFilaIntermedia; i++) {
            Ombrellone ombrellone = new Ombrellone(filaIntermedia);
            filaIntermedia.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        Fila ultimaFila = new Fila();
        ok = gestorePersistenza.salva(ultimaFila);
        if (!ok) return false;

        for (int i = 0; i < nUltimaFila; i++) {
            Ombrellone ombrellone = new Ombrellone(ultimaFila);
            ultimaFila.aggiungiOmbrellone(ombrellone);
            ok = gestorePersistenza.salva(ombrellone);
            if (!ok) return false;
        }

        for (Map.Entry<String, Integer> entry : servizi.entrySet()) {
            ServizioAggiuntivo servizio = new ServizioAggiuntivo(
                    entry.getKey(),
                    entry.getValue(),
                    new ArrayList<>()
            );
            ok = gestorePersistenza.salva(servizio);
            if (!ok) return false;
        }

        return true;
    }

    public Map<Fila, Map<Ombrellone, Boolean>> visualizzaOmbrelloni(Date data) {
        // Converto Date in LocalDate
        LocalDate localDate = data.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        // Ottengo tutte le prenotazioni della data selezionata
        Map<String, Object> campi = new HashMap<>();
        campi.put("data", localDate);
        campi.put("stato", StatoPrenotazione.PRENOTATA);
        List<Prenotazione> prenotazioni = gestorePersistenza.cercaPerCampi(Prenotazione.class, campi);

        // Salvo gli ombrelloni prenotati
        List<Ombrellone> prenotati = new ArrayList<>();
        for(Prenotazione prenotazione : prenotazioni) {
            prenotati.add(prenotazione.getOmbrellone());
        }

        // Costruisco una mappa di file e coppie <Ombrellone, Prenotato>
        Map<Fila, Map<Ombrellone, Boolean>> mappa = new LinkedHashMap<>();
        for(int i = 1; i < 4; i++) {
            Fila filaCorrente = gestorePersistenza.trovaPerId(Fila.class, (long) i);
            Map<Ombrellone, Boolean> ombrelloniFila = new LinkedHashMap<>();
            List<Ombrellone> ombrelloni = gestorePersistenza.cercaPerCampo(Ombrellone.class, "fila", filaCorrente);
            for(Ombrellone ombrellone : ombrelloni) {
                ombrelloniFila.put(ombrellone, prenotati.contains(ombrellone));
            }
            mappa.put(filaCorrente, ombrelloniFila);
        }
        return mappa;
    }

    public List<ServizioAggiuntivo> getServizi() {
        List<ServizioAggiuntivo> servizi = new ArrayList<>();
        int index = 1;
        while(true) {
            ServizioAggiuntivo servizio = gestorePersistenza.trovaPerId(ServizioAggiuntivo.class, (long) index);
            if(servizio == null) break;
            servizi.add(servizio);
            index++;
        }
        return servizi;
    }

    public  List<ServizioAggiuntivo> getServiziByID(List<Integer> idServizi) {
        List<ServizioAggiuntivo> servizi = new ArrayList<>();
        for(int idServizio : idServizi) {
            servizi.add(gestorePersistenza.trovaPerId(ServizioAggiuntivo.class, (long) idServizio));
        }
        return servizi;
    }

    public Ombrellone getOmbrelloneByID(int idOmbrellone) {
        return gestorePersistenza.trovaPerId(Ombrellone.class, (long) idOmbrellone);
    }

    public int effettuaPrenotazione(Date data, Ombrellone ombrellone, List<ServizioAggiuntivo> servizi, Cliente cliente) {
        LocalDate localDate = data.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();
        Set<ServizioAggiuntivo> serviziScelti = new HashSet<>(servizi);

        Prenotazione prenotazione = new Prenotazione(localDate, ombrellone, serviziScelti, cliente);
        prenotazione.setPrezzo(calcolaTariffa(localDate, ombrellone, serviziScelti));
        if(isDisponibile(ombrellone, localDate)) {
            prenotazione.setStato();
            gestorePersistenza.salva(prenotazione);
            ServizioMessaggistica sms = new SMSAdapter();
            sms.inviaMessaggio("Prenotazione effettuata. Data: " + localDate.toString() + ", posto: " + ombrellone.getNumero());
            return prenotazione.getPrezzo();
        } else {
            return 0;
        }
    }

    private int calcolaTariffa(LocalDate data, Ombrellone ombrellone, Set<ServizioAggiuntivo> serviziScelti) {
        int prezzo = 0;
        PeriodoTariffa periodoTariffa = null;
        if(data.getMonth() == Month.JULY || data.getMonth() == Month.AUGUST) {
            periodoTariffa = PeriodoTariffa.ALTA_STAGIONE;
        } else {
            periodoTariffa = PeriodoTariffa.BASSA_STAGIONE;
        }

        // Tariffa Fila
        Fila filaDB = gestorePersistenza.trovaPerId(Fila.class, (long) ombrellone.getFila().getPosizione());
        TariffaFila tariffaFila = null;
        for(TariffaFila t : filaDB.getTariffe()) {
            if(t.getPeriodo().equals(periodoTariffa)) {
                tariffaFila = t;
            }
        }
        prezzo += tariffaFila.getImporto();

        // Se non sono stati selezionati servizi, la funzione termina qui
        if(serviziScelti.isEmpty()) {
            return prezzo;
        }

        // Tariffa Servizi
        ServizioAggiuntivo servizioDB = null;
        TariffaServizio tariffaServizio = null;
        for(ServizioAggiuntivo servizio : serviziScelti) {
            servizioDB = gestorePersistenza.trovaPerId(ServizioAggiuntivo.class, servizio.getId());
            for(TariffaServizio t : servizioDB.getTariffe()) {
                if(t.getPeriodo().equals(periodoTariffa)) {
                    tariffaServizio = t;
                }
            }
        }
        prezzo += tariffaServizio.getImporto();

        return prezzo;
    }

    private boolean isDisponibile(Ombrellone ombrellone, LocalDate data) {
        Map<String, Object> campi = new HashMap<>();
        campi.put("data", data);
        campi.put("ombrellone", ombrellone);
        campi.put("stato", StatoPrenotazione.PRENOTATA);

        List<Prenotazione> effettuate = gestorePersistenza.cercaPerCampi(Prenotazione.class, campi);
        return effettuate.isEmpty();
    }

    public List<Prenotazione> ottieniPrenotazioniDiCliente(long idCliente) {
    Cliente clienteAttuale = gestorePersistenza.trovaPerId(Cliente.class, idCliente);

        Map<String, Object> campi = new HashMap<>();
        campi.put("cliente", clienteAttuale);

        List<Prenotazione> prenotazioni = gestorePersistenza.cercaPerCampi(Prenotazione.class, campi);

        return prenotazioni;
    }
    public boolean annullaPrenotazione(long idPrenotazione){
        Prenotazione prenotazione = gestorePersistenza.trovaPerId(Prenotazione.class, (long) idPrenotazione);

        if(prenotazione == null) return false;

        //dovremmo implementare anche lo stato annullata? in caso possiamo
        //cambiare anche lo stato stesso con
        //prenotazione.setStato(StatoPrenotazione.ANNULLATA);
        boolean update = gestorePersistenza.elimina(Prenotazione.class, idPrenotazione);

        return update;

    }
}
