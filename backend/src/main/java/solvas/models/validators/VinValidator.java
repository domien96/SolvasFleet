package solvas.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates VINs. This is a very basic check, it checks the check digit of the number. It is based on the algorithm
 * from Wikipedia.
 *
 * More advanced checking (and information decoding) could happen with a service like https://vindecoder.eu/.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Vehicle_identification_number">Wikipedia</a>
 *
 * @author Niko Strijbol
 */
public class VinValidator implements ConstraintValidator<Vin, String> {

    @Override
    public void initialize(Vin constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        String processed = value.replaceAll("-", "")
                .replaceAll("\\s+", "")
                .toUpperCase();
        return processed.length() == 17 && getCheckDigit(processed) == processed.charAt(8);
    }

    /**
     * Transliterate the letters to numbers.
     *
     * @param c The character.
     *
     * @return The value.
     */
    private static int transliterate(char c) {
        return "0123456789.ABCDEFGH..JKLMN.P.R..STUVWXYZ".indexOf(c) % 10;
    }

    /**
     * Calculate the check digit for a given vin.
     *
     * @param vin The vin to check.
     *
     * @return The character.
     */
    private static char getCheckDigit(String vin) {
        String map = "0123456789X";
        String weights = "8765432X098765432";
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            sum += transliterate(vin.charAt(i)) * map.indexOf(weights.charAt(i));
        }
        return map.charAt(sum % 11);
    }
}