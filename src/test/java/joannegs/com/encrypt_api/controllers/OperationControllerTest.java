package joannegs.com.encrypt_api.controllers;

import joannegs.com.encrypt_api.domain.operation.Operation;
import joannegs.com.encrypt_api.dto.OperationDTO;
import joannegs.com.encrypt_api.dto.OperationResponseDTO;
import joannegs.com.encrypt_api.services.OperationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(OperationController.class)
class OperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperationService operationService;

    private OperationDTO operationDTO;
    private Operation operation;
    private OperationResponseDTO operationResponseDTO;
    private OperationDTO invalidOperationDTO;

    @BeforeEach
    void setUp() {
        operationDTO = new OperationDTO("12345678901", "token123", 1000L);
        operation = new Operation(1L, "12345678901", "token123", 1000L);
        operationResponseDTO = new OperationResponseDTO(1L, "12345678901", "token123", 1000L);

        invalidOperationDTO = new OperationDTO(null, "invalidToken", -100L);  // Document null, amount negativo
    }

    @Test
    void testCreateOperation() throws Exception {
        Mockito.when(operationService.create(any(OperationDTO.class))).thenReturn(operation);

        ObjectMapper objectMapper = new ObjectMapper();
        String operationDTOJson = objectMapper.writeValueAsString(operationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(operationDTOJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/operation/1"))
                .andExpect(content().json(objectMapper.writeValueAsString(operation)))
                .andDo(print());
    }

    @Test
    void testReadOperation() throws Exception {
        Mockito.when(operationService.read(eq(1L))).thenReturn(operationResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operation/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(operationResponseDTO)))
                .andDo(print());
    }

    @Test
    void testCreateOperationWithInvalidData() throws Exception {
        Mockito.when(operationService.create(any(OperationDTO.class)))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid data"));

        ObjectMapper objectMapper = new ObjectMapper();
        String invalidOperationDTOJson = objectMapper.writeValueAsString(invalidOperationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOperationDTOJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testReadOperationNotFound() throws Exception {
        Mockito.when(operationService.read(eq(999L)))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Operation not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/operation/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}