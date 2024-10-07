package com.ashu.blogapp.Payloads;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Getter
//@Setter

@Builder
public class PostResponse {
    // this class is made to display Various details mentioned below of Posts to client nd all page would be displayed in array_form

    private List<PostDto> content;

    private int pageNumber;

    private int pageSize;

    private long totalElements; // total Records variable in DB

    private int totalPages;

    private boolean lastPage;

}
