package lk.intelleon.springbootrestfulwebservices.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Service {
    static SimpleDateFormat timeFormat;
    static SimpleDateFormat dayFormat;

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]{3,50}$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidNic(String nic) {
        Pattern pattern = Pattern.compile("^[0-9]{9}[vVxX]||[0-9]{12}$");
        Matcher matcher = pattern.matcher(nic);
        return matcher.matches();
    }

    public static boolean isValidTelephoneNumber(String telNumber) {
        Pattern pattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
        Matcher matcher = pattern.matcher(telNumber);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPrice(String price) {
        Pattern pattern = Pattern.compile("^(\\d+)||((\\d+\\.)(\\d){2})$");
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    public static boolean receivedQty(String receivedQty) {
        Pattern pattern = Pattern.compile("^[0-9A-Za-z]{1,10}");
        Matcher matcher = pattern.matcher(receivedQty);
        return matcher.matches();
    }

    /*
    Password should be between 4 and 32 characters long.
    It must contain at least one letter and one digit.*/


//    Abcd1234
//    SecurePwd789
//    Password123
    public static boolean isValidPassword(String userName) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,32}$");
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    public static String setDate() {
        dayFormat = new SimpleDateFormat("MMMM dd, YYYY");
        String date = dayFormat.format(Calendar.getInstance().getTime());
        return date;
    }

    public static String setTime() {
        timeFormat = new SimpleDateFormat("hh:mm a");
        String time = timeFormat.format(Calendar.getInstance().getTime());
        return time;
    }

    //(P-001 OR p-001)
    public static boolean isValidItemCode(String ItemCode) {
        Pattern pattern = Pattern.compile("^[Pp]-[0-9]{3}");
        Matcher matcher = pattern.matcher(ItemCode);
        return matcher.matches();
    }

    //(C-001 OR c-001)
    public static boolean isValidCategoryCode(String categoryCode) {
        Pattern pattern = Pattern.compile("^[Cc]-[0-9]{3}");
        Matcher matcher = pattern.matcher(categoryCode);
        return matcher.matches();
    }

    //(U-001 OR u-001)
    public static boolean isValidUnitCode(String unitCode) {
        Pattern pattern = Pattern.compile("^[Uu]-[0-9]{3}");
        Matcher matcher = pattern.matcher(unitCode);
        return matcher.matches();
    }

    //(S-001 OR s-001)
    public static boolean isValidSupplierCode(String SupplierCode) {
        Pattern pattern = Pattern.compile("^[Ss]-[0-9]{3}");
        Matcher matcher = pattern.matcher(SupplierCode);
        return matcher.matches();
    }
}
