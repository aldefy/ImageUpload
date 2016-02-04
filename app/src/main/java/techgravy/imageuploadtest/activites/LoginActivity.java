package techgravy.imageuploadtest.activites;

/**
 * Created by aditlal on 04/02/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import techgravy.imageuploadtest.BuildConfig;
import techgravy.imageuploadtest.utils.Logger;

public class LoginActivity extends Activity {

    private static final String SHARED_PREFERENCES_NAME = "ImageTest";
    private WebView mWebView;

    private static final Pattern accessTokenPattern = Pattern.compile("access_token=([^&]*)");
    private static final Pattern refreshTokenPattern = Pattern.compile("refresh_token=([^&]*)");
    private static final Pattern expiresInPattern = Pattern.compile("expires_in=(\\d+)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout root = new FrameLayout(this);
        mWebView = new WebView(this);
        root.addView(mWebView);
        setContentView(root);

        setupWebView();

        mWebView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=" + BuildConfig.CLIENT_ID + "&response_type=token");
    }

    private void setupWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // intercept the tokens
                // http://example.com#access_token=ACCESS_TOKEN&token_type=Bearer&expires_in=3600
                boolean tokensURL = false;
                if (url.startsWith("https://aditlal.net")) {
                    tokensURL = true;
                    Matcher m;

                    m = refreshTokenPattern.matcher(url);
                    m.find();
                    String refreshToken = m.group(1);

                    m = accessTokenPattern.matcher(url);
                    m.find();
                    String accessToken = m.group(1);

                    m = expiresInPattern.matcher(url);
                    m.find();
                    long expiresIn = Long.valueOf(m.group(1));
                    Logger.d("AuthToken", accessToken);
                    LoginActivity.this.getSharedPreferences(SHARED_PREFERENCES_NAME, 0)
                            .edit()
                            .putString("access_token", accessToken)
                            .putString("refresh_token", refreshToken)
                            .putLong("expires_in", expiresIn)
                            .commit();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
                return tokensURL;
            }
        });
    }

}
