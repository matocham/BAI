package pl.edu.pb.wi.bai.register;

public class UserExistsException extends Throwable {

    public UserExistsException() {
        super("Username already taken");
    }
}
