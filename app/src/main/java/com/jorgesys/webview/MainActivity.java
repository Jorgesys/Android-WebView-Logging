package com.jorgesys.webview;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity";
    private static String url = "http://www.stackoverflow.com"; //my favourite site!
    private ProgressDialog progressBar;
    private WebView webview;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        progressBar = ProgressDialog.show(MainActivity.this, "WebView Android", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                writeToLog("Processing webview url " + url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    writeToLog("Processing webview url " + request.getUrl().toString());
                }else{
                    writeToLog("Processing webview url " + request.toString());
                }

                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                writeToLog("Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                writeToLog("Error: " + description, 1);
            }
        });
        webview.loadUrl(url);
    }

    private void writeToLog(String msg) {
        writeToLog(msg, 0);
    }

    private void writeToLog(String msg, int error){
        String color ="#00AA00";
        if(error == 1)color = "#00AA00";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
             editText.setText(Html.fromHtml("<font color=" + color + "> • " + msg + "</font><br>" + editText.getText().toString() + "<br>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            editText.setText(Html.fromHtml("<font color=" + color + "> • " + msg + "</font><br>" + editText.getText().toString() + "<br>"));
        }
    }

}
