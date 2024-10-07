package com.ashu.blogapp.Payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Getter
//@Setter

@Builder
public class CategoryDto {

    private Integer categoryId;
    @NotBlank
    @Size(min = 4, message = "Minimum size of Category Title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Minimum size of Category Description  is 10")
    private String categoryDescription;

}
