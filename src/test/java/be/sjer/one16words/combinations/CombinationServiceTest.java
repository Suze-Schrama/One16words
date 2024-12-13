package be.sjer.one16words.combinations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CombinationServiceTest {
    @InjectMocks
    private CombinationService combinationService;

    @Mock
    private CombinationRepository combinationRepository;

    @Test
    void FindAllGetsAllCombinationsInExpectedOrder() {
        List<Combination> expectedCombinations = List.of(
                new Combination(2, "abc"),
                new Combination(1, "def")
        );
        when(combinationRepository.findAllByOrderByIdDesc()).thenReturn(expectedCombinations);
        List<Combination> actualCombinations = combinationService.findAll();
        assertThat(actualCombinations).isEqualTo(expectedCombinations);
    }

    @Test
    void SaveCombinationsCalsSaveAllOnceWhenUsed() {
        Set<Combination> combinations = Set.of(
                new Combination("abc"),
                new Combination("def")
        );
        combinationService.saveCombinations(combinations);
        verify(combinationRepository, times(1)).saveAll(combinations);
    }
}