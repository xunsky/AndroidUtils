package xunsky.utils.android_utils.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by junx on 2018/8/23.
 */

public class IoUtils {
    public static byte[] InputStream2Bytes(InputStream in) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[6 * 1024];
        int count = -1;
        while ((count = in.read(data, 0, 4 * 1024)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return outStream.toByteArray();
    }
    public static InputStream byte2InputStream(byte[] in) {
        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }

    public static byte[] OutputStream2Bytes(OutputStream out)
            throws IOException {
        byte[] data = new byte[6 * 1024];
        out.write(data);
        return data;
    }
    public static OutputStream bytes2OutputStream(byte[] in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(in);
        return out;
    }

    /** 关闭流 */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
}
