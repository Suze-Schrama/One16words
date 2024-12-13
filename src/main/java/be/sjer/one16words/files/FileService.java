package be.sjer.one16words.files;

import be.sjer.one16words.util.CombinationFinder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileService {

    public Set<String> readWordsFromFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader.lines().map(String::trim).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new IOException("Error reading file: " + file.getOriginalFilename(), e);
        }
    }

    public Set<String> processWordsFromFile(int targetLength, MultipartFile filePath) {
        try {
            Set<String> words = readWordsFromFile(filePath);
            return CombinationFinder.findTargetLengthCombinations(words, targetLength);
        } catch (IOException ex) {
            System.err.println("File not found: " + ex.getMessage());
            return new HashSet<>();
        }
    }
}
