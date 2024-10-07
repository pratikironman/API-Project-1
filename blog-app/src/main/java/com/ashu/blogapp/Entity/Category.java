package com.ashu.blogapp.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "categories"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter

@Builder
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Integer categoryId;

    @Column(name = "Title", length = 100, nullable = false)
    private String categoryTitle;

    @Column(name = "Descriptions", length = 100, nullable = false)
    private String categoryDescription;


    // relations between category & Posts (1 category can have many posts)
    @OneToMany(
            mappedBy = "category",
            // "CascadeType.ALL" indicates that child would not exist if parent won't exist and on 2nd hand if we need to add parent class so child class would be add automatically
            cascade = CascadeType.ALL,

            // "FetchType.LAZY" indicated if only we need to get only parent without child then we can, orElse we can use "FetchType.EAGER" if we need both entity to come out
            fetch = FetchType.LAZY
    )
    private List<Post> posts = new ArrayList<>();

}
