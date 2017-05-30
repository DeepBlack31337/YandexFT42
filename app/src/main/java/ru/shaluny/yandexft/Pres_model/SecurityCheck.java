package ru.shaluny.yandexft.Pres_model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import ru.shaluny.yandexft.UI_model.MainActivity;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by administrator on 29.05.2017.
 */

public class SecurityCheck {

    private static final int ACCESS_NETWORK_STATE = 47;
    private static final int INTERNET = 42;

    ////Версия
    private static Boolean VerCheck() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    ////Проверка на интернет, если необходимо
    public static Boolean SecCheck(Context context, Activity activity) {
        if (VerCheck()) {

            if (checkSelfPermission(context,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(activity,new String[]{ Manifest.permission.ACCESS_NETWORK_STATE }, ACCESS_NETWORK_STATE);
                return false;
            }

            if (checkSelfPermission(context,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(activity,new String[]{ Manifest.permission.INTERNET }, INTERNET);
                return false;
            }

        }
        return true;
    }

    ////Проверка на доступность интернета
    public static Boolean InetCheck(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
