package xunsky.utils.android_utils.random;

import java.util.Random;

public class RandomUtils {
    private static Random sRandom;

    public static Random get(){
        if (sRandom==null){
            synchronized (RandomUtils.class){
                sRandom=new Random();
            }
        }
        return sRandom;
    }

    public int integer(int min,int max){
        Random random = get();
        return random.nextInt(max-min)+min;
    }


    public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String string(int length){
        Random random = get();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append( allChar.charAt( random.nextInt( allChar.length() ) ) );
        }
        return sb.toString();
    }
}
