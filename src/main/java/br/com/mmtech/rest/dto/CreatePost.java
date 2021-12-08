package br.com.mmtech.rest.dto;

import javax.validation.constraints.NotBlank;

public class CreatePost {

    @NotBlank(message = "O texto não pode ser nulo ou branco")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
