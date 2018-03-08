package pl.edu.pb.wi.bai.register;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@PasswordMatch(message = "Hasła muszą pasować")
public class RegisterDto {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 32, message = "Nazwa użytkownika powinna mieć od 5 do 32 znaków")
    private String username;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 32, message = "Hasło powinno mieć od 6 do 32 znaków")
    private String password;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 32, message = "Hasło powinno mieć od 6 do 32 znaków")
    private String passwordRepeat;

    public RegisterDto() {
    }

    public RegisterDto(String username, String password, String passwordRepeat) {
        this.username = username;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDto that = (RegisterDto) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getPasswordRepeat(), that.getPasswordRepeat());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUsername(), getPassword(), getPasswordRepeat());
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordRepeat='" + passwordRepeat + '\'' +
                '}';
    }
}
