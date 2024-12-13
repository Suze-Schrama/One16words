package be.sjer.one16words.combinations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CombinationController.class)
@ExtendWith(MockitoExtension.class)
class CombinationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CombinationService combinationService;
    @InjectMocks
    private CombinationController combinationController;

    @Test
    void findAllCombinationsFromDbIsOkAndReturnsCombinations() throws Exception {
        List<Combination> mockCombinations = List.of(new Combination(2, "w+or+ld=world"), new Combination(1, "wor+ld=world"));
        when(combinationService.findAll()).thenReturn(mockCombinations);

        mockMvc.perform(MockMvcRequestBuilders.get("/combinations"))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findAllCombinationsFromEmptyDbGivesEmptyList() throws Exception {
        when(combinationService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/combinations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}