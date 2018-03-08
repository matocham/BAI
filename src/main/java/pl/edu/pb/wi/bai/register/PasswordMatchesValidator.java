package pl.edu.pb.wi.bai.register;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatch, RegisterDto> {
    private String errorMessage;
    public void initialize(PasswordMatch constraint) {
        errorMessage = constraint.message();
    }

    public boolean isValid(RegisterDto obj, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        boolean passwordMatch = obj.getPassword().equals(obj.getPasswordRepeat());
        if(!passwordMatch){
            context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode("passwordRepeat").addConstraintViolation();
        }
        return passwordMatch;
    }
}
