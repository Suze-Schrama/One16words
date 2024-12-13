package be.sjer.one16words.combinations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombinationRepository extends JpaRepository<Combination, Long> {
    List<Combination> findAllByOrderByIdDesc();
}
