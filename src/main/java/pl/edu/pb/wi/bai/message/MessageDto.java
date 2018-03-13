package pl.edu.pb.wi.bai.message;

import pl.edu.pb.wi.bai.models.User;

import java.util.List;
import java.util.Objects;

public class MessageDto {
    Long id;
    private String text;
    private User owner;
    private List<String> allowedUsers;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<String> getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(List<String> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDto that = (MessageDto) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getText(), that.getText()) &&
                Objects.equals(getOwner(), that.getOwner()) &&
                Objects.equals(getAllowedUsers(), that.getAllowedUsers());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getText(), getOwner(), getAllowedUsers());
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", owner=" + owner +
                ", allowedUsers=" + allowedUsers +
                '}';
    }
}
