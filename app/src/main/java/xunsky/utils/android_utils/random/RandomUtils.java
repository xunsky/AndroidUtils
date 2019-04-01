package xunsky.utils.android_utils.random;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
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

    public static boolean coin(){
        return integer(0,2)==1;
    }

    public static boolean random(float probability){
        Random random = get();
        float nextFloat = random.nextFloat();
        return probability>=nextFloat;
    }

    private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String string(int length){
        Random random = get();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append( allChar.charAt( random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    public static int color(){
        Random random = get();
        List<Integer> colors=new ArrayList<>();
        colors.add(Color.BLACK);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.GREEN);
        colors.add(Color.DKGRAY);
        colors.add(Color.LTGRAY);

        return random.nextInt(colors.size());
    }
}
