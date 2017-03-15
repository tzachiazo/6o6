package com.example.tazo.glasseson;

import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by tazo on 15/03/2017.
 */

public interface CameraInterface {

    void stopCamera();

    void capture(ContentResolver contentResolver , Context context);

}
