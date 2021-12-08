package br.com.mmtech.rest.dto;

import br.com.mmtech.domain.model.Post;

import java.time.LocalDateTime;

public class PostDto {

    private String text;
    private LocalDateTime data;

    public PostDto(Post post) {
        text = post.getText();
        data = post.getDataCriacao();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
