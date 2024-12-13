package be.sjer.one16words.combinations;

import jakarta.persistence.*;

@Entity
public class Combination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String combination;

    protected Combination() {
    }

    public Combination(String combination) {
        this.combination = combination;
    }

    Combination(long id, String combination) {
        this.id = id;
        this.combination = combination;
    }

    public Long getId() {
        return id;
    }

    public String getCombination() {
        return combination;
    }
}
