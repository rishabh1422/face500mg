package com.example.face500mg;

import android.content.pm.PackageManager;

public class RuntimePermissionUtil {
    private RuntimePermissionUtil() {

    }

    public static void onRequestPermissionsResult(int[] grantResults,
                                                  RPResultListener RPResultListener) {
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    RPResultListener.onPermissionGranted();
                } else {
                    RPResultListener.onPermissionDenied();
                }
            }
        }
    }
}
