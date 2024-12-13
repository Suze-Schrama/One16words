package be.sjer.one16words.files;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/file")
public class FileController {
private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/process-file")
    public ResponseEntity<Set<String>> processFile(@RequestParam("length") @NotNull @Positive int length, @RequestParam("file") @NotNull MultipartFile file) {
        Set<String> error = new HashSet<>();
        if (!file.getContentType().equals("text/plain")) {
            error.add("Only .txt files are allowed");
            return ResponseEntity.badRequest().body(error);}
        if (file.getSize() > 1048576){
            error.add("File size exceeds the maximum limit of 1 MB");
            return ResponseEntity.badRequest().body(error);}
        Set<String> combinations = fileService.processWordsFromFile(length, file);
        if (combinations.isEmpty()){
            combinations.add("No combinations found");
            return ResponseEntity.ok(combinations);
        }
        return ResponseEntity.ok(combinations);
    }
    }
