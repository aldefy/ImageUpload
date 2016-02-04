package techgravy.imageuploadtest.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import techgravy.imageuploadtest.R;

/**
 * Created by aditlal on 04/02/16.
 */
public class CommonUtils {

    private static Dialog mProgressDialog;

    public static void displayProgressDialog(Context context, String message) {
        dismissProgressDialog();
        View loadingView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        mProgressDialog = new Dialog(context);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.getWindow().setDimAmount(0.8f);
        if (message != null && message.trim().length() > 0) {
            ((TextView) loadingView.findViewById(R.id.messageTextView)).setText(message);
        }
        mProgressDialog.setContentView(loadingView);
        mProgressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
