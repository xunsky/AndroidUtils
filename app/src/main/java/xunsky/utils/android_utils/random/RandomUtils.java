package xunsky.utils.android_utils.random;

import java.util.Random;

public class RandomUtils {
    private static Random sRandom;

    private static Random get(){
        if (sRandom==null){
            synchronized (RandomUtils.class){
                sRandom=new Random();
            }
        }
        return sRandom;
    }

    public static int integer(int min,int max){
        Random random = get();
        return random.nextInt(max-min)+min;
    }


    private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String string(int length){
        Random random = get();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append( allChar.charAt( random.nextInt( allChar.length() ) ) );
        }
        return sb.toString();
    }
}
