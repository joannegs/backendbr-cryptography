package joannegs.com.encrypt_api.service;

import joannegs.com.encrypt_api.services.EncryptService;
import org.jasypt.util.text.AES256TextEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EncryptServiceTest {

    private EncryptService encryptService;
    private AES256TextEncryptor mockTextEncryptor;

    @BeforeEach
    void setUp() {
        mockTextEncryptor = Mockito.mock(AES256TextEncryptor.class);
        encryptService = new EncryptService(mockTextEncryptor);
    }

    @Test
    void testEncryptDataSuccess() {
        String plainText = "testData";
        String encryptedText = "encryptedData";

        when(mockTextEncryptor.encrypt(plainText)).thenReturn(encryptedText);

        String result = encryptService.encryptData(plainText);

        assertEquals(encryptedText, result);
        verify(mockTextEncryptor, times(1)).encrypt(plainText);
    }

    @Test
    void testDecryptDataSuccess() {
        String encryptedText = "encryptedData";
        String decryptedText = "testData";

        when(mockTextEncryptor.decrypt(encryptedText)).thenReturn(decryptedText);

        String result = encryptService.decryptData(encryptedText);

        assertEquals(decryptedText, result);
        verify(mockTextEncryptor, times(1)).decrypt(encryptedText);
    }

    @Test
    void testEncryptDataWithEmptyInput() {
        String emptyText = "";
        String encryptedText = "encryptedEmptyText";

        when(mockTextEncryptor.encrypt(emptyText)).thenReturn(encryptedText);

        String result = encryptService.encryptData(emptyText);

        assertEquals(encryptedText, result);
        verify(mockTextEncryptor, times(1)).encrypt(emptyText);
    }

    @Test
    void testDecryptDataWithEmptyInput() {
        String emptyText = "";
        String decryptedText = "";

        when(mockTextEncryptor.decrypt(emptyText)).thenReturn(decryptedText);

        String result = encryptService.decryptData(emptyText);

        assertEquals(decryptedText, result);
        verify(mockTextEncryptor, times(1)).decrypt(emptyText);
    }

    @Test
    void testEncryptDataWithNullInput() {
        String encryptedNullText = "encryptedNullText";

        when(mockTextEncryptor.encrypt(null)).thenReturn(encryptedNullText);

        String result = encryptService.encryptData(null);

        assertEquals(encryptedNullText, result);
        verify(mockTextEncryptor, times(1)).encrypt(null);
    }

    @Test
    void testDecryptDataWithNullInput() {
        String decryptedNullText = "decryptedNullText";

        when(mockTextEncryptor.decrypt(null)).thenReturn(decryptedNullText);

        String result = encryptService.decryptData(null);

        assertEquals(decryptedNullText, result);
        verify(mockTextEncryptor, times(1)).decrypt(null);
    }
}