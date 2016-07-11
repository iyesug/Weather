package com.vis.iyesug.weather.util;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.vis.iyesug.weather.R;


/**
 * Created by Administrator on 2016/7/4.
 */
public class SnackbarUtil {

    private static Snackbar mSnackbar;

    public static void show(View view, String msg, int flag) {

        if (flag == 0) {
            // 短时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        } else {
            // 长时显示
            mSnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        }

        mSnackbar.show();

        //点击关闭
        mSnackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override public void onClick(View v) {

                mSnackbar.dismiss();
            }
        });
    }
}
