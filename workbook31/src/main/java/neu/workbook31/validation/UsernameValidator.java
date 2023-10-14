package neu.workbook31.validation;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator  implements ConstraintValidator<Username, String> {
    List<String> specialCharacters = Arrays.asList("$", "%", "#", "@", "^", "*");

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext text){
        Pattern pattern = Pattern.compile("[^a-z0-9 ]");
        Matcher matcher = pattern.matcher("String");
        boolean badCharacters = matcher.find(); //false if characters are a-z or 0-9
        return !badCharacters;
        
    }
    
}
