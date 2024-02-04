package savoginEros.ParkprojectBE.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(long id) {
        super("Utente con id " + id + " non presente nel database");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
