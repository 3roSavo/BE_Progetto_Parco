

package savoginEros.ParkprojectBE.urlValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    private static final String URL_REGEX = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            // Tratta come valido se l'URL è vuoto o nullo
            return true;
        }

        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
