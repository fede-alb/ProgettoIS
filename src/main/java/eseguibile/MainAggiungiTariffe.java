package eseguibile;

import database.GestorePersistenza;
import entity.Fila;
import entity.ServizioAggiuntivo;
import entity.PeriodoTariffa;
import entity.TariffaFila;
import entity.TariffaServizio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

// *** NOTA: Da fare dopo aver popolato il DB di file e servizi ***
public class MainAggiungiTariffe {
    static final GestorePersistenza gestorePersistenza = new GestorePersistenza();

    static void main() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("stabilimento");
        EntityManager em = emf.createEntityManager();
        PeriodoTariffa estate = PeriodoTariffa.ALTA_STAGIONE;
        PeriodoTariffa inverno = PeriodoTariffa.BASSA_STAGIONE;

        /* Tariffe fila (da fare una sola volta)
        List<Fila> fileRecuperate = em.createQuery(
                "SELECT f FROM Fila f LEFT JOIN FETCH f.tariffe", Fila.class
        ).getResultList();
        for(int i = 0; i < 3; i++) {
            TariffaFila tariffaFilaEstate = new TariffaFila(30 - (i * 10), estate);
            TariffaFila tariffaFilaInverno = new TariffaFila(5 - (i * 2), inverno);
            Fila fila = fileRecuperate.get(i);
            fila.aggiungiTariffa(tariffaFilaEstate);
            fila.aggiungiTariffa(tariffaFilaInverno);
            try {
                em.getTransaction().begin();
                em.merge(fila);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            } finally {
                em.close();
                emf.close();
            }
        }
         */

        /* Tariffe servizi (da fare una sola volta)
        List<ServizioAggiuntivo> serviziRecuperati = em.createQuery(
                "SELECT s FROM ServizioAggiuntivo s LEFT JOIN FETCH s.tariffe", ServizioAggiuntivo.class
        ).getResultList();
        for(int i = 0; i < serviziRecuperati.size(); i++) {
            TariffaServizio tariffaServizioEstate = new TariffaServizio(50 + (i * 10), estate);
            TariffaServizio tariffaServizioInverno = new TariffaServizio(20 + (i * 2), inverno);
            ServizioAggiuntivo servizio = serviziRecuperati.get(i);
            servizio.aggiungiTariffa(tariffaServizioEstate);
            servizio.aggiungiTariffa(tariffaServizioInverno);
            try {
                em.getTransaction().begin();
                em.merge(servizio);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().rollback();
            } finally {
                em.close();
                emf.close();
            }
        }
        */

        em.close();
        emf.close();
    }
}
