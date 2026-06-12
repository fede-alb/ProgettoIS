package entity;

import database.GestorePersistenza;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

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

    public boolean isGiaConfigurato() {
        List<Fila> file = gestorePersistenza.cercaPerCampi(Fila.class, new HashMap<>());
        return !file.isEmpty();
    }

    public boolean configuraStabilimento(int nPrimaFila,
                                         int nFilaIntermedia,
                                         int nUltimaFila,
                                         Map<String, Integer> servizi) {
        if (isGiaConfigurato()) return false;

        List<Integer> nOmbrelloniFila = new ArrayList<>();
        nOmbrelloniFila.add(nPrimaFila);
        nOmbrelloniFila.add(nFilaIntermedia);
        nOmbrelloniFila.add(nUltimaFila);

        for(int f = 0; f < 3; f++) {
            Fila filaCorrente = new Fila();
            if(!gestorePersistenza.salva(filaCorrente)) return false;
            for (int i = 0; i < nOmbrelloniFila.get(f); i++) {
                Ombrellone ombrellone = new Ombrellone(filaCorrente);
                filaCorrente.aggiungiOmbrellone(ombrellone);
                if(!gestorePersistenza.salva(ombrellone)) return false;
            }
        }

        for (Map.Entry<String, Integer> entry : servizi.entrySet()) {
            ServizioAggiuntivo servizio = new ServizioAggiuntivo(
                    entry.getKey(),
                    entry.getValue(),
                    new ArrayList<>()
            );
            if(!gestorePersistenza.salva(servizio)) return false;
        }

        return true;
    }

    public Map<Fila, Map<Ombrellone, Boolean>> visualizzaOmbrelloni(Date data) {
        LocalDate localDate = data.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        Map<String, Object> campi = new HashMap<>();
        campi.put("data", localDate);
        campi.put("stato", StatoPrenotazione.PRENOTATA);
        List<Prenotazione> prenotazioni = gestorePersistenza.cercaPerCampi(Prenotazione.class, campi);

        List<Ombrellone> prenotati = new ArrayList<>();
        for(Prenotazione prenotazione : prenotazioni) {
            prenotati.add(prenotazione.getOmbrellone());
        }

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

    public List<ServizioAggiuntivo> getServiziByID(List<Integer> idServizi) {
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

        Fila filaDB = gestorePersistenza.trovaPerId(Fila.class, (long) ombrellone.getFila().getPosizione());
        TariffaFila tariffaFila = null;
        for(TariffaFila t : filaDB.getTariffe()) {
            if(t.getPeriodo().equals(periodoTariffa)) {
                tariffaFila = t;
            }
        }
        assert tariffaFila != null;
        prezzo += tariffaFila.getImporto();

        if(serviziScelti.isEmpty()) {
            return prezzo;
        }

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
        assert tariffaServizio != null;
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
        return gestorePersistenza.cercaPerCampi(Prenotazione.class, campi);
    }
    public boolean annullaPrenotazione(long idPrenotazione){
        Prenotazione prenotazione = gestorePersistenza.trovaPerId(Prenotazione.class, (long) idPrenotazione);
        if(prenotazione == null) return false;
        return gestorePersistenza.elimina(Prenotazione.class, idPrenotazione);
    }

    public List<Prenotazione> ottieniPrenotazioni() {

        return gestorePersistenza.cercaPerCampi(
                Prenotazione.class,
                Map.of()
        );
    }

    public Prenotazione ottieniPrenotazione(long idPrenotazione) {

        return gestorePersistenza.trovaPerId(
                Prenotazione.class,
                idPrenotazione
        );
    }


    public List<Prenotazione> ottieniPrenotazioniPerData(
            LocalDate giorno) {

        return gestorePersistenza.cercaPerCampi(
                Prenotazione.class,
                Map.of("data", giorno)
        );
    }

}
