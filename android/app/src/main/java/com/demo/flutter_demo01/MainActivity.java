package com.demo.flutter_demo01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterRunArguments;
import io.flutter.view.FlutterView;

public class MainActivity extends FlutterActivity implements View.OnClickListener {
    private FlutterView flutterView;
    private Button btn1;
    private Button btn2;

    private MethodChannel methodChannel;
    private static final String CHANNEL = "increment";

    private String[] getArgsFromIntent(Intent intent) {
        // Before adding more entries to this list, consider that arbitrary
        // Android applications can generate intents with extra data and that
        // there are many security-sensitive args in the binary.
        ArrayList<String> args = new ArrayList<>();
        if (intent.getBooleanExtra("trace-startup", false)) {
            args.add("--trace-startup");
        }
        if (intent.getBooleanExtra("start-paused", false)) {
            args.add("--start-paused");
        }
        if (intent.getBooleanExtra("enable-dart-profiling", false)) {
            args.add("--enable-dart-profiling");
        }
        if (!args.isEmpty()) {
            String[] argsArray = new String[args.size()];
            return args.toArray(argsArray);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] args = getArgsFromIntent(getIntent());
        FlutterMain.ensureInitializationComplete(getApplicationContext(), args);

        setContentView(R.layout.main_activity);

        FlutterRunArguments runArguments = new FlutterRunArguments();
        runArguments.bundlePath = FlutterMain.findAppBundlePath(getApplicationContext());
        runArguments.entrypoint = "main";

        flutterView = findViewById(R.id.flutter_view);
        flutterView.runFromBundle(runArguments);


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        methodChannel = new MethodChannel(flutterView, CHANNEL);
        methodChannel.setMethodCallHandler((methodCall, result) -> {
            if (methodCall.method.equals("androidMethod1")) {
                result.success(androidMethod1());
            } else if (methodCall.method.equals("androidMethod2")) {
                result.success(androidMethod2());
            }
        });

    }

    private String androidMethod2() {
        return "这是来自安卓的方法二";
    }

    private String androidMethod1() {
        return "这是来自安卓的方法一";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                methodChannel.invokeMethod("flutterMethod1", null, new MethodChannel.Result() {
                    @Override
                    public void success(Object o) {
                        String message = (String) o;
                        System.out.println(message);
                    }

                    @Override
                    public void error(String s, String s1, Object o) {

                    }

                    @Override
                    public void notImplemented() {

                    }
                });
                System.out.println("android 按钮1====================>");
                break;
            case R.id.btn2:
                methodChannel.invokeMethod("flutterMethod2", null, new MethodChannel.Result() {
                    @Override
                    public void success(Object o) {
                        String message = (String) o;
                        System.out.println(message);
                    }

                    @Override
                    public void error(String s, String s1, Object o) {

                    }

                    @Override
                    public void notImplemented() {

                    }
                });
                System.out.println("android 按钮2====================>");
                break;
        }
    }
}
