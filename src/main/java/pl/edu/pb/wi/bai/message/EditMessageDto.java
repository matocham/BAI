package pl.edu.pb.wi.bai.message;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class EditMessageDto {
    @NotEmpty(message = "Treść wiadomości nie może być pusta")
    @NotNull
    String text;
    @NotNull
    Long id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
