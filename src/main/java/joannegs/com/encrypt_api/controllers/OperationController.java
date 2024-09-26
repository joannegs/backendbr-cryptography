package joannegs.com.encrypt_api.controllers;

import joannegs.com.encrypt_api.domain.operation.Operation;
import joannegs.com.encrypt_api.dto.OperationDTO;
import joannegs.com.encrypt_api.dto.OperationResponseDTO;
import joannegs.com.encrypt_api.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/operation")
public class OperationController {
    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService){
        this.operationService = operationService;
    }

    @PostMapping
    public ResponseEntity<Operation> create(@RequestBody OperationDTO operationDTO, UriComponentsBuilder uriBuilder) {
        Operation newOperation = this.operationService.create(operationDTO);
        var uri = uriBuilder.path("api/operation/{id}").buildAndExpand(newOperation.getId()).toUri();
        return ResponseEntity.created(uri).body(newOperation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationResponseDTO> read(@PathVariable Long id) {
        OperationResponseDTO operation = this.operationService.read(id);

        return ResponseEntity.ok(operation);
    }
}
