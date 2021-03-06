package pl.edu.pb.wi.bai.message;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class MessageDto {
    @NotEmpty(message = "Treść wiadomości nie może być pusta")
    @NotNull
    String text;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
