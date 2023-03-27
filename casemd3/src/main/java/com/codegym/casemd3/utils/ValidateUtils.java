package utils;

import java.util.regex.Pattern;

public class ValidateUtils {
    public static final String ADDRESS_REGEX = "^[A-Za-z0-9][A-Za-z0-9\\s]{4,14}$";

    public static final String NAME_REGEX = "^[A-Za-z0-9][A-Za-z0-9\\s]{2,20}$";
    public static boolean isAddressValid(String address) {
        return Pattern.matches(ADDRESS_REGEX, address);
    }
    public static boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }
    public static final String PHONE_REGEX = "^0[1-9][0-9]{8}$";

    public static boolean isPhoneValid(String number) {
        return Pattern.compile(PHONE_REGEX).matcher(number).matches();
    }

}
