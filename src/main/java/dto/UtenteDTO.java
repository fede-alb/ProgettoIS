package dto;

public class UtenteDTO {
    private final Long idUtente;
    private final String nome;
    private final String cognome;
    private final String email;
    private final String telefono;
    private final String ruolo;

    public UtenteDTO(Long idUtente, String nome, String cognome, String email, String telefono, String ruolo) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.ruolo = ruolo;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRuolo() {
        return ruolo;
    }
}