package com.panguin.android.thinkmaximum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.panguin.android.thinkmaximum.remote.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class customersactivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout _drawer_layout;
    @BindView(R.id.nav_view) NavigationView _nav_view;
    @BindView(R.id.toolbar) Toolbar _toolbar;
    @BindView(R.id.webview) WebView _browser;


    String key;
    String url;


    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersactivity);
        ButterKnife.bind(this);
        _toolbar.setTitle("Think Maximum");
        this.key = SharedPrefManager.getKey();
        this.url = "http://192.168.43.48:5000/";
        changeurl("");



        _nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                int id = item.getItemId();
                Log.e("Menu selected",Integer.toString(id));
                // close drawer when item is tapped
                _drawer_layout.closeDrawers();
                switch (id){
                    case R.id.home:
                        _toolbar.setTitle("Think Maximum");
                        changeurl("");
                        break;

                    case R.id.nav_camper:
                        _toolbar.setTitle("Camper Delivered");
                        changeurl("camper/months/");
                        Log.e("Menu camper",Integer.toString(id));
                        break;
                    case R.id.nav_bills:
                        _toolbar.setTitle("Your Payments");
                        changeurl("payments/");
                        break;
                    case R.id.nav_faq:
                        _toolbar.setTitle("Faqs");
                        Log.e("Menu faq",Integer.toString(id));
                        break;
                    case R.id.logout:
                        SharedPrefManager.getInstance(customersactivity.this).logout();
                        Intent intent = new Intent(customersactivity.this, MainActivity.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();


                }

                return true;
            }
        });

        setSupportActionBar(_toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawer_layout.openDrawer(GravityCompat.START);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (_browser.canGoBack()) {
                        _browser.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    public void changeurl(String url){
        String pre = "http://192.168.43.48:5000/customer/";
        _browser.getSettings().setLoadsImagesAutomatically(true);
        _browser.getSettings().setJavaScriptEnabled(true);
        _browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        _browser.loadUrl(url+this.key);
        _browser.loadUrl(pre + url +this.key);
        _browser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlx) {
                view.loadUrl(urlx);
                return false;
            }
        });
    }

}