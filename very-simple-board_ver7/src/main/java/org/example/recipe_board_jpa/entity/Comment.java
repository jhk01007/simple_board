package org.example.recipe_board_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(length = 300)
    private String content;

    @Column(name = "created_at", updatable = false)
    private String createdAt = LocalDateTime.now().toString();


    public Comment(Long id, Comment parent, Recipe recipe, Member writer, String content, String createdAt) {
        this.id = id;
        this.parent = parent;
        this.recipe = recipe;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }
}
