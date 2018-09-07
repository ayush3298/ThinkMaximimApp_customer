package com.panguin.android.thinkmaximum;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.panguin.android.thinkmaximum.model.Result;
import com.panguin.android.thinkmaximum.remote.ApiUtils;
import com.panguin.android.thinkmaximum.remote.SharedPrefManager;
import com.panguin.android.thinkmaximum.remote.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class signupactivity extends Activity {

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.birthday_picker) TextView _birthday_picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

//        if (!validate()) {
//            onSignupFailed();
//            return;
//        }

        _signupButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(signupactivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        JsonObject person = new JsonObject();
        person.addProperty("username", email);
        person.addProperty("password", password);


        JsonObject customer = new JsonObject();
        customer.addProperty("phone", mobile);
        customer.addProperty("address", address);
        customer.addProperty("name",name);
        customer.addProperty("email",email);


        person.add("customer", customer);

        dosignup(email,password,person);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success

                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
    }

    public void onSignupSucces(){
        Toast.makeText(getBaseContext(), "Signup Succes", Toast.LENGTH_LONG).show();


    }

    private void dosignup(final String username, final String password , JsonObject person){
        final ProgressDialog progressDialog = new ProgressDialog(signupactivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService service = retrofit.create(UserService.class);


        Call call = service.register(person);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                if(response.code()== 201) {
                    onSignupSucces();
                    doLogin(username,password);


                }else if(response.code()==400){
                    onSignupFailed();
                }


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );
            }
        });
    }
    private void doLogin(final String username,final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(signupactivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        UserService service = retrofit.create(UserService.class);

        Call call = service.login(username,password);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //hiding progress dialog
                progressDialog.dismiss();

                if(response.code() == 200){
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getKey());

                    Toast.makeText(getApplicationContext(), response.body().getKey(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(signupactivity.this, customersactivity.class);
                    startActivity(intent);

                }else if(response.code()==400){
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Signing Failed", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );
            }
        });



    }




    public boolean validate () {
            boolean valid = true;

            String name = _nameText.getText().toString();
            String address = _addressText.getText().toString();
            String email = _emailText.getText().toString();
            String mobile = _mobileText.getText().toString();
            String password = _passwordText.getText().toString();
            String reEnterPassword = _reEnterPasswordText.getText().toString();

            if (name.isEmpty() || name.length() < 3) {
                _nameText.setError("at least 3 characters");
                valid = false;
            } else {
                _nameText.setError(null);
            }

            if (address.isEmpty()) {
                _addressText.setError("Enter Valid Address");
                valid = false;
            } else {
                _addressText.setError(null);
            }


            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (mobile.isEmpty() || mobile.length() != 10) {
                _mobileText.setError("Enter Valid Mobile Number");
                valid = false;
            } else {
                _mobileText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
                _reEnterPasswordText.setError("Password Do not match");
                valid = false;
            } else {
                _reEnterPasswordText.setError(null);
            }

            return valid;
        }

}
