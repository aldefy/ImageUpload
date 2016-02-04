package techgravy.imageuploadtest.utils;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import techgravy.imageuploadtest.R;
import techgravy.imageuploadtest.models.ImageResponse;

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


    public static String getBase64ForBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        String base64Image = Base64Util.encode(b);
        byte[] b2 = Base64.decode(base64Image, Base64.DEFAULT);
        boolean equals = Arrays.equals(b, b2);
        Log.d("Base64Util",equals+"");
        Log.d("Base64Util",base64Image);
        return base64Image;

    }
    public static void createUploadedNotification(Context mContext,ImageResponse response) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_gallery);
        mBuilder.setContentTitle(mContext.getResources().getString(R.string.notifaction_success));


        mBuilder.setContentText(response.getData().getLink());


        mBuilder.setColor(mContext.getResources().getColor(R.color.colorPrimary));




        Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getData().getLink()));
        PendingIntent intent = PendingIntent.getActivity(mContext, 0, resultIntent, 0);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);


        Intent shareIntent = new Intent(Intent.ACTION_SEND, Uri.parse(response.getData().getLink()));
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, response.getData().getLink());
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, shareIntent, 0);
        mBuilder.addAction(new NotificationCompat.Action(R.drawable.abc_ic_menu_share_mtrl_alpha,
                mContext.getString(R.string.notification_share_link), pIntent));


        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(mContext.getString(R.string.app_name).hashCode(), mBuilder.build());
    }
}
