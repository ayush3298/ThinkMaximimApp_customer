package com.panguin.android.thinkmaximum;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.panguin.android.thinkmaximum.model.customer;
import com.panguin.android.thinkmaximum.remote.SharedPrefManager;
import com.panguin.android.thinkmaximum.remote.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class customersactivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout _drawer_layout;
    @BindView(R.id.nav_view) NavigationView _nav_view;
    @BindView(R.id.toolbar) Toolbar _toolbar;
    @BindView(R.id.webview) WebView _browser;
//    @BindView(R.id.welcome_text) TextView _welcome_text;
    TextView _welcometext;


    String key;
    String url;


    @Override
    public void onResume(){
        super.onResume();
        get_customer();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customersactivity);
        ButterKnife.bind(this);
        _toolbar.setTitle("Think Maximum");
        View headerView = _nav_view.getHeaderView(0);
        this._welcometext = (TextView) headerView.findViewById(R.id.welcome_text);
        get_customer();
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
                        changeurl("faqs/");
                        Log.e("Menu faq",Integer.toString(id));
                        break;
                    case R.id.nav_health:
                        _toolbar.setTitle("Health History");
                        break;
                    case R.id.logout:
                        SharedPrefManager.getInstance(customersactivity.this).logout();
                        Intent intent = new Intent(customersactivity.this, MainActivity.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        startActivity(intent);
                        finish();
                        break;


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
        if(checkInternetConnection(this)){
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
        });}
        else{
            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
        }
    }

    public void get_customer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.48:5000/customer/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        UserService service = retrofit.create(UserService.class);
        String token = "Token "+this.key;
        Log.e("token",token);

        Call call = service.getMyJSON(token);
        call.enqueue(new Callback<customer>() {
            @Override
            public void onResponse(Call<customer> call, Response<customer> response) {
                //hiding progress dialog
                Log.e("responce code ", Integer.toString(response.code()));


                if(response.code() == 200){
                    Log.e("get cust",response.body().toString());
                    Log.e("get cust",response.body().getName());
                    _welcometext.setText("Welcome  " + response.body().getName());



                }else if(response.code()==400){



                }else if (response.code() == 301){
                    SharedPrefManager.getInstance(customersactivity.this).logout();
                    Intent intent = new Intent(customersactivity.this, MainActivity.class);
                    intent.putExtra("finish", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                    startActivity(intent);
                    finish();

                }else if (response.code() == 4001){
                    SharedPrefManager.getInstance(customersactivity.this).logout();
                    Intent intent = new Intent(customersactivity.this, MainActivity.class);
                    intent.putExtra("finish", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                    startActivity(intent);
                    finish();

                }


            }

            @Override
            public void onFailure(Call<customer> call, Throwable t) {

                Log.e("TAG", "onFailure: "+t.toString() );
                ViewDialog error_dialog = new ViewDialog();
                error_dialog.showDialog(customersactivity.this,"Please Connect To Internet.");
            }
        });



    }


        public  boolean checkInternetConnection(Context context) {

            ConnectivityManager con_manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return (con_manager.getActiveNetworkInfo() != null
                    && con_manager.getActiveNetworkInfo().isAvailable()
                    && con_manager.getActiveNetworkInfo().isConnected());
        }

}

