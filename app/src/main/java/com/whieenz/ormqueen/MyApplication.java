package com.whieenz.ormqueen;

import android.app.Application;
import android.os.Environment;

import com.whieenz.LogCook;

/**
 * Created by whieenz  on 2017/12/2.
 *
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String logPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.whieenz.logCook/log";
        LogCook.getInstance() // 单例获取LogCook实例
                .setLogPath(logPath) //设置日志保存路径
                .setLogName("test.log") //设置日志文件名
                .isOpen(true)  //是否开启输出日志
                .isSave(true)  //是否保存日志
                .initialize(); //完成吃初始化Crash监听
    }
}
