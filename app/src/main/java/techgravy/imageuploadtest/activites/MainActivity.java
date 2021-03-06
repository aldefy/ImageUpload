package techgravy.imageuploadtest.activites;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import techgravy.imageuploadtest.BuildConfig;
import techgravy.imageuploadtest.R;
import techgravy.imageuploadtest.api.ImageUploadApi;
import techgravy.imageuploadtest.generator.ApiGenerator;
import techgravy.imageuploadtest.models.ImageResponse;
import techgravy.imageuploadtest.utils.CommonUtils;
import techgravy.imageuploadtest.utils.Logger;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_REQUEST = 1;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "techgravy.imageuploadtest.fileprovider";
    private static final String SHARED_PREFERENCES_NAME = "ImageTest";

    File imageFile;
    @Bind(R.id.cameraButton)
    Button cameraButton;
    @Bind(R.id.galleryButton)
    Button galleryButton;
    @Bind(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.resultImageView)
    ImageView resultImageView;
    @Bind(R.id.clearImage)
    ImageButton clearImage;
    @Bind(R.id.uploadButton)
    Button uploadButton;
    String base64;
    Bitmap bitmap;


    ImageUploadApi imageUploadApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageUploadApi = ApiGenerator.createService(ImageUploadApi.class);
    }


    @OnClick(R.id.cameraButton)
    void clickCamera() {
        File path = new File(MainActivity.this.getFilesDir(), "/Pictures/");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, "image.jpg");
        Uri imageUri = FileProvider.getUriForFile(MainActivity.this, CAPTURE_IMAGE_FILE_PROVIDER, image);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @OnClick(R.id.galleryButton)
    void clickGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
    }

    @OnClick(R.id.clearImage)
    void clearImage() {
        clearImage.setVisibility(View.INVISIBLE);
        resultImageView.setImageDrawable(null);
        uploadButton.setEnabled(false);
    }

    @OnClick(R.id.uploadButton)
    void uploadButton() {
        Logger.d("Base64Util", "Upload " + base64);
        CommonUtils.displayProgressDialog(MainActivity.this, "Uploading");
        imageUploadApi.postImage("Client-ID " + BuildConfig.CLIENT_ID, "base64", "Photo", "Desc", base64, new Callback<ImageResponse>() {
            @Override
            public void success(ImageResponse response, Response response2) {
                Logger.d("Image", response.toString());

                CommonUtils.createUploadedNotification(MainActivity.this, response);
                CommonUtils.dismissProgressDialog();
                clearImage.setVisibility(View.INVISIBLE);
                resultImageView.setImageDrawable(null);
                uploadButton.setEnabled(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Logger.d("Image", error.getMessage());
                CommonUtils.dismissProgressDialog();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                File path = new File(getFilesDir(), "/Pictures/");
                if (!path.exists()) path.mkdirs();
                imageFile = new File(path, "image.jpg");
                // use imageFile to open your image
                Log.d("File", imageFile.length() + "");
                Picasso.with(MainActivity.this).load(imageFile).into(resultImageView);
               /* bitmap = ((BitmapDrawable) resultImageView.getDrawable()).getBitmap();
                base64 = CommonUtils.getBase64ForBitmap(bitmap);*/

                Handler handler = new Handler();

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        bitmap = BitmapFactory.decodeFile(imageFile.getPath());
                        base64 = CommonUtils.getBase64ForBitmap(bitmap);
                    }
                };
                handler.post(myRunnable);

                clearImage.setVisibility(View.VISIBLE);
                clearImage.bringToFront();
                uploadButton.setEnabled(true);
            }
        }
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && intent != null && intent.getData() != null) {

            Uri uri = intent.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                resultImageView.setImageBitmap(bitmap);
                bitmap = ((BitmapDrawable) resultImageView.getDrawable()).getBitmap();
                base64 = CommonUtils.getBase64ForBitmap(bitmap);
                clearImage.setVisibility(View.VISIBLE);
                clearImage.bringToFront();
                uploadButton.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }


}
