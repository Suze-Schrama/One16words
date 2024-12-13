package be.sjer.one16words.combinations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/combinations")
public class CombinationController {
    private final CombinationService combinationService;

    public CombinationController(CombinationService combinationService) {
        this.combinationService = combinationService;
    }

    @GetMapping
    public List<Combination> findAllCombinationsFromDb(){
        return combinationService.findAll();
    }

    @PostMapping("/save-combinations")
    public void saveCombinations(@RequestBody List<String> combinations) {
        combinationService.saveCombinations(combinations.stream().map(Combination::new).collect(Collectors.toSet()));
    }
}
