package fa.training.common;

import java.util.regex.Pattern;

public class ValidationUtils {
    public static boolean isValidPattern(String regex, String value){
        return Pattern.matches(regex, value);
    }
}
