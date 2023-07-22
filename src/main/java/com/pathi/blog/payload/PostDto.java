package com.pathi.blog.payload;

import com.pathi.blog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, message = "Title should be more than 3 characters")
    private String title;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotEmpty(message = "Content should not be empty")
    private String content;

    private Long categoryId;
}
