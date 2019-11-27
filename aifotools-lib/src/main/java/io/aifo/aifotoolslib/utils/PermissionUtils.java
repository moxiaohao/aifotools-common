package io.aifo.aifotoolslib.utils;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;


public class PermissionUtils {


    public boolean isGranted(FragmentActivity activity, String[] requestPermissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        for (String permission : requestPermissions) {
            boolean isGranted = rxPermissions.isGranted(permission);
            if (!isGranted) {
                return isGranted;
            }
        }
        return true;
    }

    @SuppressLint("CheckResult")
    public static void requestPermissions(final FragmentActivity activity, final String[] requestPermissions,
                                   final OnPermissionRequestListener listner) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        requestPermission(rxPermissions, requestPermissions, listner);
    }

    @SuppressLint("CheckResult")
    public static void requestPermissions(final Fragment fragment, final String[] requestPermissions,
                                   final OnPermissionRequestListener listner) {
        RxPermissions rxPermissions = new RxPermissions(fragment);
        requestPermission(rxPermissions, requestPermissions, listner);
    }
    @SuppressLint("CheckResult")
    private static void requestPermission(RxPermissions rxPermissions, String[] requestPermissions, OnPermissionRequestListener listner) {
        rxPermissions.requestEach(requestPermissions)
                .subscribe(permission -> { // will emit 1 Permission object
                    if (permission.granted) {

                        listner.onPermissionRequest(true);
                        // All permissions are granted !
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // At least one denied permission without ask never again
                        listner.onPermissionRequest(false);
                    } else {
                        // At least one denied permission with ask never again
                        // Need to go to the settings
                        listner.onPermissionRequest(false);
                    }
                });
    }

    public interface OnPermissionRequestListener {
        void onPermissionRequest(boolean isOk);
    }
}
