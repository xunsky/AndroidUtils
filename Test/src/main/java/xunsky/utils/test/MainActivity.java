package xunsky.utils.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import xunsky.utils.android_utils.assert_.AssertUtils;
import xunsky.utils.android_utils.file.FileUtils;
import xunsky.utils.android_utils.log.LogUtils;
import xunsky.utils.android_utils.string.PrintUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        boolean flag=FileUtils.copyAssetFile2Cache(this,"test.mobi");
//        Log.d("meee","("+Thread.currentThread().getStackTrace()[2].getFileName()+":"+Thread.currentThread().getStackTrace()[2].getLineNumber()+")\n"
//                +flag);

        AssertUtils.makeSure(false);
    }
}
