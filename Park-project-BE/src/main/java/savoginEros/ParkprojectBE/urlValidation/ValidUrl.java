package savoginEros.ParkprojectBE.urlValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrl {

    String message() default "Deve essere un URL valido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
