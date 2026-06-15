package entity;

public class SMSAdapter implements ServizioMessaggistica {
    private final SMS adaptee = new SMS();

    @Override
    public boolean inviaMessaggio(String messaggio) {
        return adaptee.sendMessage(messaggio) == 0;
    }
}
