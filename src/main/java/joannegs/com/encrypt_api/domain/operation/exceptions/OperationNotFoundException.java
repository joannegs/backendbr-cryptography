package joannegs.com.encrypt_api.domain.operation.exceptions;


public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(Long id){
        super("Operation not found with ID " + id);
    }
}
