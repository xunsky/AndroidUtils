package xunsky.utils.android_utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    /**
     * 判断是否为13位手机号
     */
    public static boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^[1]+[3,4,5,7,8]+\\d{9}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    /**
     * 判断是否为银行卡卡号
     */
    public static boolean isBankCard(String cardNo) {
        Pattern p = Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
        Matcher m = p.matcher(cardNo);
        return m.matches();
    }

    /**
     * 判断是否为6位数字
     */
    public static boolean isCode(String str) {
        return str.matches("^\\d{6}$");
    }

    /**
     * 判断是否为邮箱
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\\.]+@[a-zA-Z0-9-]+[\\.a-zA-Z]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断是否为纯数字
     */
    public static boolean isfigure(String figure) {
        Pattern p = Pattern.compile("^\\d+");
        Matcher m = p.matcher(figure);
        return m.matches();
    }

    /**
     * 判断是否包含数字
     */
    public static boolean isContainNumber(String str) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     *  判断是否为纯字母
     */
    public static boolean isAllLetter(String letter) {
        Pattern p = Pattern.compile("^[a-zA-Z]+$");
        Matcher m = p.matcher(letter);
        return m.matches();
    }

    /**
     * 判断是否为数字和字母组成的字符串
     */
    public static boolean isletterfigure(String str) {
        Pattern p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{6,12}$");
        Matcher m = p.matcher(str);

        return m.matches();
    }

    /**
     * 判断是否为身份证
     */
    public static boolean isIdcard(String idCard) {
        String regIdCard = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        Pattern p = Pattern.compile(regIdCard);
        return p.matcher(idCard).matches();
    }
}
