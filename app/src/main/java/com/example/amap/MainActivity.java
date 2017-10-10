package com.example.amap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {

    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.textview);
        requestPermissions();
        Log.i("xiong", "start");
    }

    /**
     * 请求全部权限
     */
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.READ_CONTACTS
                                , Manifest.permission.READ_PHONE_STATE
                                , Manifest.permission.CALL_PHONE},
                        111);
            }
        }
    }


    /*
     * 更新manager数据的同时，更新sp
     *
     * 1.欢迎界面请求权限，将缓存值读入manager，然后定位，如果成功，更新manager数据。
     * 2.登录界面定位，更新manager数据
     * 3.MainActivity定位，更新数据
     * LocationManager和sharedPreference保存。
     */

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
//        Log.e("onRequest","permissions[]"+permissions[0]+permissions[1]+permissions[2]+permissions[3]+permissions[4]+permissions[5]+
//                "   grantResults"+grantResults[0]+grantResults[1]+grantResults[2]+grantResults[3]+grantResults[4]+grantResults[5]);
        switch (requestCode) {
            case 111: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED
                        ) {
                } else {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "ACCESS_FINE_LOCATION未授权", Toast.LENGTH_SHORT).show();
                    }
                    if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "WRITE_EXTERNAL_STORAGE未授权", Toast.LENGTH_SHORT).show();
                    }
                    if (grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "READ_CONTACTS未授权", Toast.LENGTH_SHORT).show();
                    }
                    if (grantResults[3] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "READ_PHONE_STATE未授权", Toast.LENGTH_SHORT).show();
                    }
                    if (grantResults[4] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "CALL_PHONE未授权", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Log.i("xiong", "onLocationChanged");
        mText.setText("onLocationChanged");

        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = amapLocation.getLatitude();
                double longitude = amapLocation.getLongitude();
                Log.i("xiong", latitude + "");
                Log.i("xiong", longitude + "");
                mText.setText("坐标为" + latitude + "-" + longitude);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("xiong", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                mText.setText("location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
