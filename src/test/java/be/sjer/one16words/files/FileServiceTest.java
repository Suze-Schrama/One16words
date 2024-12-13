package be.sjer.one16words.files;

import be.sjer.one16words.util.CombinationFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    private FileService fileService;

    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    private final int targetLength = 5;

    @Test
    void readWordsFromValidFileIsSuccess() throws IOException {

        String fileContent = "or\nw\nwor\nworld\nld";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        when(mockFile.getInputStream()).thenReturn(inputStream);

        Set<String> result = fileService.readWordsFromFile(mockFile);

        Set<String> expectedWords = new HashSet<>(Set.of("or", "w", "wor", "world", "ld"));
        assertThat(expectedWords).isEqualTo(result);
    }

    @Test
    void readWordsFromEmptyFileReturnsEmptyList() throws IOException {

        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        when(mockFile.getInputStream()).thenReturn(inputStream);

        Set<String> result = fileService.readWordsFromFile(mockFile);

        Set<String> emptySet = new HashSet<>();
        assertThat(result).isEqualTo(emptySet);
    }

    @Test
    void readWordsFromFileThrowsIOException() throws IOException {
        when(mockFile.getInputStream()).thenThrow(new IOException("IOException"));

        assertThrows(IOException.class, () -> {
            fileService.readWordsFromFile(mockFile);
        }, "Error reading file: error.txt");
    }

    @Test
    void processWordsFromFileWorks() throws IOException {
        String fileContent = "or\nw\nwor\nworld\nld";
        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
        when(mockFile.getInputStream()).thenReturn(inputStream);
        Set<String> result = fileService.readWordsFromFile(mockFile);
        Set<String> expectedCombinations = new HashSet<>();
        expectedCombinations.add("w+or+ld=world");
        expectedCombinations.add("wor+ld=world");

        Set<String> actualCombinations = CombinationFinder.findTargetLengthCombinations(result, targetLength);

        assertThat(actualCombinations).isEqualTo(expectedCombinations);
        }

    @Test
    void processWordsFromFileThrowsExceptionWhenFileNotFound() throws IOException {

        when(mockFile.getInputStream()).thenThrow(new IOException("File not found"));

        Set<String> result = fileService.processWordsFromFile(10, mockFile);

        Set<String> emptySet = new HashSet<>();
        assertThat(result).isEqualTo(emptySet);
    }
}