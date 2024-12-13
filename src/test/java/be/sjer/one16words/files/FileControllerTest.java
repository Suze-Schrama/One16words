package be.sjer.one16words.files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    private final int length = 5;

    @Test
    void processFilWithInvalidFileType() {
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "application/pdf", "Dummy Content".getBytes());

        ResponseEntity<Set<String>> response = fileController.processFile(length, file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsExactly("Only .txt files are allowed");
    }

    @Test
    void processFileWithInvalidSize() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", new byte[1048577]);

        ResponseEntity<Set<String>> response = fileController.processFile(length, file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsExactly("File size exceeds the maximum limit of 1 MB");
    }

    @Test
    void processValidFileIsASuccess() {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "content".getBytes());
        Set<String> mockResponse = new HashSet<>();
        mockResponse.add("w+or+ld=world");
        mockResponse.add("wor+ld=world");
        when(fileService.processWordsFromFile(length, file)).thenReturn(mockResponse);

        ResponseEntity<Set<String>> response = fileController.processFile(length, file);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockResponse);
    }
}