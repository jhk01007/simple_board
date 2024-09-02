package org.example.recipe_board_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String process;

    @Column(name = "read_count")
    private Integer readCount = 0;

    @Column(name = "created_at", updatable = false)
    private String createdAt = String.valueOf(LocalDateTime.now());

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Recipe(Long id, Long writerId, String foodName, String ingredients, String process) {
        this.id = id;
        this.writer = new Member();
        writer.setId(writerId);
        this.foodName = foodName;
        this.ingredients = ingredients;
        this.process = process;
    }
}
