package neu.workbook31.validation;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, Date>{

    @Override
    public boolean isValid (Date value, ConstraintValidatorContext text){
        long diff = new Date().getTime() - value.getTime();
        int age = (int) (TimeUnit.MILLISECONDS.toDays(diff) / 30);
        return age > 18;  
    }
    
}
