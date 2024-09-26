package joannegs.com.encrypt_api.services;

import joannegs.com.encrypt_api.domain.operation.Operation;
import joannegs.com.encrypt_api.domain.operation.exceptions.OperationNotFoundException;
import joannegs.com.encrypt_api.dto.OperationDTO;
import joannegs.com.encrypt_api.dto.OperationResponseDTO;
import joannegs.com.encrypt_api.repositories.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperationService {
    private final OperationRepository operationRepository;
    private final EncryptService encryptService;

    public OperationService(OperationRepository operationRepository, EncryptService encryptService){
        this.operationRepository = operationRepository;
        this.encryptService = encryptService;
    }

    public Operation create(OperationDTO operationDTO) {
        Operation operation = new Operation();

        System.out.println(operationDTO);

        String userDocumentHashed = encryptService.encryptData(operationDTO.userDocument());
        String creditCardHashed = encryptService.encryptData(operationDTO.creditCardToken());

        operation.setUserDocument(userDocumentHashed);
        operation.setCreditCardToken(creditCardHashed);
        operation.setAmount(operationDTO.amount());

        operationRepository.save(operation);

        return operation;
    }

    public OperationResponseDTO read(Long id) throws OperationNotFoundException {
        Operation operation = this.operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));

        String userDocument = encryptService.decryptData(operation.getUserDocument());
        String creditCard = encryptService.decryptData(operation.getCreditCardToken());

        return new OperationResponseDTO(operation.getId(), userDocument, creditCard, operation.getAmount());
    }
}
