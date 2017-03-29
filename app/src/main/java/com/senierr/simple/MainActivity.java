package com.senierr.simple;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.request.BaseRequest;
import com.senierr.sehttp.SeHttp;
import com.senierr.sehttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

//    private String urlStr = "http://app.wz-tech.com:8091/k3dxapi";
//    private String urlStr = "http://192.168.2.155:8088/index";
    private String urlStr = "http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe";


    private String path = Environment.getExternalStorageDirectory() + "/Download/AA/aa";

    private void logSe(String logStr) {
        Log.e("MainActivity", logStr);
    }

    private void logGo(String logStr) {
        Log.e("OkGo", logStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv_text);

        SeHttp.getInstance()
                .setConnectTimeout(10000)
                .setReadTimeout(10000)
                .setWriteTimeout(10000)
                .setDebug("SeHttp");

        OkGo.init(getApplication());
        OkGo.getInstance().debug("OkGo")
                .setConnectTimeout(10000)
                .setCacheMode(CacheMode.NO_CACHE)
                .setReadTimeOut(10000)
                .setWriteTimeOut(10000);

        seHttpTest();
//        okGoTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SeHttp.getInstance().cancelTag(this);
        OkGo.getInstance().cancelTag(this);
    }

    private void seHttpTest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "这里是需要提交的json格式数据");
        params.put("key3", "也可以使用三方工具将对象转成json字符串");
        params.put("key4", "其实你怎么高兴怎么写都行");
        JSONObject jsonObject = new JSONObject(params);

        SeHttp.post(Urls.URL_TEXT_UPLOAD)
                .addHeader("head11", "aaaaa")
                .jsonRequestBody(jsonObject.toString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onStart() {
                        logSe("onStart");
                    }

                    @Override
                    public void onSuccess(String s) throws Exception {
                        logSe("onSuccess: " + s);
                    }

                    @Override
                    public void onError(Exception e) {
                        logSe("onError: " + e.toString());
                    }

                    @Override
                    public void onAfter() {
                        logSe("onAfter");
                    }
                });

//        SeHttp.get(urlStr)
//                .tag(this)
//                .execute(new FileCallback(path + "SeHttp.txt") {
//                    @Override
//                    public void onSuccess(File file) throws Exception {
//                        logSe("onSuccess: " + file.getPath());
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        logSe("onError: " + e.toString());
//                    }
//
//                    @Override
//                    public void onAfter() {
//                        logSe("onAfter");
//                    }
//                });
    }

    private void okGoTest() {
        OkGo.get(urlStr)
                .tag(this)
                .execute(new com.lzy.okgo.callback.StringCallback() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        logGo("onBefore");
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        logGo("onSuccess: " + s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        logGo("onError");
                    }

                    @Override
                    public void onAfter(String s, Exception e) {
                        logGo("onAfter");
                    }
                });

//        OkGo.get(urlStr)
//                .tag(this)
//                .execute(new com.lzy.okgo.callback.FileCallback(path, "OkGo.txt") {
//                    @Override
//                    public void onSuccess(File file, Call call, Response response) {
//                        logGo("onSuccess: " + file.getPath());
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        logGo("onError: " + e.toString());
//                    }
//
//                    @Override
//                    public void onAfter(File file, Exception e) {
//                        logGo("onAfter");
//                    }
//                });
    }



}
