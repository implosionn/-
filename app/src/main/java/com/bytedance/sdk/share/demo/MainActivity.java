package com.bytedance.sdk.share.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.base.ImageObject;
import com.bytedance.sdk.open.aweme.base.MediaContent;
import com.bytedance.sdk.open.aweme.base.MixObject;
import com.bytedance.sdk.open.aweme.base.VideoObject;
import com.bytedance.sdk.open.aweme.share.Share;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;

import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.bytedance.sdk.open.douyin.ShareToContact;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.bytedance.sdk.open.douyin.model.ContactHtmlObject;
import com.bytedance.sdk.open.douyin.model.OpenRecord;
import com.bytedance.sdk.share.demo.douyinapi.DouYinEntryActivity;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import classes;
//import com.douyin.open.ApiException;
//import com.douyin.open.api.OauthCodeApi;

public class MainActivity extends AppCompatActivity {

    public static final String CODE_KEY = "code";
    public static Boolean isBoe = false;
    public static String client_key="awgbg6d164ck9qqr";
    public static String client_secret="1b819dfa303d97629fa9d59976643720";

    DouYinOpenApi douYinOpenApi;

    OpenRecord.Request request = new OpenRecord.Request();

    String[] mPermissionList = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    Button mShareToDouyin;


    EditText mMediaPathList;


    Button mClearMedia;

    EditText mSetDefaultHashTag;
    EditText mSetDefaultHashTag2;

    private boolean useFileProvider = true;


    static final int PHOTO_REQUEST_GALLERY = 10;
    static final int SET_SCOPE_REQUEST = 11;

    int currentShareType = Share.UNKNOWN;

    private ArrayList<String> mUri = new ArrayList<>();

    //private String mScope = "user_info,trial.whitelist,renew_refresh_token,discovery.ent";
    private String mScope = "trial.whitelist,user_info,following.list,fans.list,video.list,video.data";
    private String mOptionalScope1 = "friend_relation";
    private String mOptionalScope2 = "message";
    private String access_token=DouYinEntryActivity.access_token;

    public static boolean IS_AUTH_BY_M = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String clientkey = "awgbg6d164ck9qqr"; // 需要到开发者网站申请
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientkey));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        //授权主要集成在go_to_auth里面，主要看这里就行
        findViewById(R.id.go_to_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果本地未安装抖音或者抖音的版本过低，会直接自动调用 web页面 进行授权
                sendAuth();

            }
        });
        findViewById(R.id.go_to_system_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DouYinEntryActivity.VideoAllGet();
                //ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);
            }
        });
        findViewById(R.id.require_mobile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DouYinEntryActivity.itemGet(1,0);
                //DouYinEntryActivity.GetitemMessage(DouYinEntryActivity.json,1,0);
            }
        });

        findViewById(R.id.open_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (douYinOpenApi != null && douYinOpenApi.isSupportOpenRecordPage()) {
                    request.mState = "state";
                    //douYinOpenApi.openRecordPage(request);
                    DouYinEntryActivity.FollowingGet();
                } else {
                    Toast.makeText(MainActivity.this, "抖音版本不支持", Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.share_to_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (douYinOpenApi.isAppSupportShareToContacts()) {
                    DouYinEntryActivity.VideoGet();
                } else {
                    Toast.makeText(MainActivity.this, "当前抖音版本不支持", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.share_to_contact_html).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (douYinOpenApi.isAppSupportShareToContacts()) {
                    DouYinEntryActivity.FansGet();
                } else {
                    Toast.makeText(MainActivity.this, "当前抖音版本不支持", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean sendAuth() {
        Authorization.Request request = new Authorization.Request();
        //request.optionalScope0 = "trial.whitelist";
        request.scope = mScope;                          // 用户授权时必选权限
        //request.optionalScope0 = "mobile";     // 用户授权时可选权限（默认选择）
        //request.optionalScope0 = mOptionalScope1;    // 用户授权时可选权限（默认不选）
        request.state = "ww";                                   // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        request.callerLocalEntry = "com.bytedance.sdk.share.demo.douyinapi.DouYinEntryActivity";//回调
        return douYinOpenApi.authorize(request);               // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
    }


}