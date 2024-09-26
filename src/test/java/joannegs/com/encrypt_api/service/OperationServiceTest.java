package joannegs.com.encrypt_api.service;

import joannegs.com.encrypt_api.domain.operation.Operation;
import joannegs.com.encrypt_api.domain.operation.exceptions.OperationNotFoundException;
import joannegs.com.encrypt_api.dto.OperationDTO;
import joannegs.com.encrypt_api.dto.OperationResponseDTO;
import joannegs.com.encrypt_api.repositories.OperationRepository;
import joannegs.com.encrypt_api.services.EncryptService;
import joannegs.com.encrypt_api.services.OperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OperationServiceTest {

    private OperationService operationService;
    private OperationRepository operationRepository;
    private EncryptService encryptService;

    @BeforeEach
    void setUp() {
        operationRepository = Mockito.mock(OperationRepository.class);
        encryptService = Mockito.mock(EncryptService.class);
        operationService = new OperationService(operationRepository, encryptService);
    }

    @Test
    void testCreateOperationSuccess() {
        String encryptedUserDocument = "encryptedUserDoc";
        String encryptedCreditCardToken = "encryptedCardToken";
        when(encryptService.encryptData("12345678901")).thenReturn(encryptedUserDocument);
        when(encryptService.encryptData("creditToken")).thenReturn(encryptedCreditCardToken);

        OperationDTO operationDTO = new OperationDTO("12345678901", "creditToken", 1000L);

        Operation savedOperation = new Operation(1L, encryptedUserDocument, encryptedCreditCardToken, 1000L);
        when(operationRepository.save(any(Operation.class))).thenReturn(savedOperation);

        Operation result = operationService.create(operationDTO);

        assertNotNull(result);
        assertEquals(encryptedUserDocument, result.getUserDocument());
        assertEquals(encryptedCreditCardToken, result.getCreditCardToken());
        assertEquals(1000L, result.getAmount());

        verify(encryptService, times(1)).encryptData("12345678901");
        verify(encryptService, times(1)).encryptData("creditToken");
        verify(operationRepository, times(1)).save(any(Operation.class));
    }

    @Test
    void testReadOperationSuccess() throws OperationNotFoundException {
        Operation operation = new Operation(1L, "encryptedUserDoc", "encryptedCardToken", 1000L);
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        when(encryptService.decryptData("encryptedUserDoc")).thenReturn("12345678901");
        when(encryptService.decryptData("encryptedCardToken")).thenReturn("creditToken");

        OperationResponseDTO response = operationService.read(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("12345678901", response.userDocument());
        assertEquals("creditToken", response.creditCardToken());
        assertEquals(1000L, response.amount());

        verify(operationRepository, times(1)).findById(1L);
        verify(encryptService, times(1)).decryptData("encryptedUserDoc");
        verify(encryptService, times(1)).decryptData("encryptedCardToken");
    }

    @Test
    void testReadOperationNotFound() {
        when(operationRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(OperationNotFoundException.class, () -> operationService.read(1L));

        assertEquals("Operation not found with ID 1", exception.getMessage());

        verify(operationRepository, times(1)).findById(1L);
        verify(encryptService, times(0)).decryptData(any());
    }
}
