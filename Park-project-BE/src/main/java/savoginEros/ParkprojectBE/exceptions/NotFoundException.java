package savoginEros.ParkprojectBE.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String object,long id) {
        super(object +  " con id " + id + " non presente nel database");
    }

    public NotFoundException(String message) {
        super(message);
    }

}
