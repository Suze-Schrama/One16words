package be.sjer.one16words.combinations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)

public class CombinationService {
    private final CombinationRepository combinationRepository;

    public CombinationService(CombinationRepository combinationRepository) {
        this.combinationRepository = combinationRepository;
    }

     public List<Combination> findAll(){
        return combinationRepository.findAllByOrderByIdDesc();
    }

    @Transactional
    public void saveCombinations(Set<Combination> combinations) {
        combinationRepository.saveAll(combinations);
    }
}
