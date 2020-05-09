package com.panguin.android.thinkmaximum;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.panguin.android.thinkmaximum.model.Result;
import com.panguin.android.thinkmaximum.model.forgot;
import com.panguin.android.thinkmaximum.notifications.MyFirebaseInstanceIDService;
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

public class login extends AppCompatActivity {
    private static final String TAG = "login";
    private static final int REQUEST_SIGNUP = 0;
    private String m_Text = "";

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.forgot_username) TextView _forgot_username;
    @BindView(R.id.forgot_password) TextView _forgot_password;

//    final ProgressDialog progressDialog = new ProgressDialog(login.this);

    @Override
    public void onResume(){
        super.onResume();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, customersactivity.class));

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        ViewDialog dialog = new ViewDialog();
//        dialog.isConnectedToServer(login.this);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, customersactivity.class));
        }



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, signupactivity.class));

            }
        });
        _forgot_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                builder.setTitle("Enter Email");

                final EditText input = new EditText(login.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        forgotusername(m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        _forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                builder.setTitle("Enter Email");

                final EditText input = new EditText(login.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT );
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = input.getText().toString();
                        forgotpass(email);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });


    }
    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(true);




        String mail = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        doLogin(mail,password);



        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        // onLoginFailed();
                        //progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void doLogin(final String username,final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
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


                    MyFirebaseInstanceIDService MyFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                    MyFirebaseInstanceIDService.send_token(refreshedToken);
                    Toast.makeText(getApplicationContext(), "Loging in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(login.this, customersactivity.class);
                    startActivity(intent);

                }else if(response.code()==400){
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Username or Password is Incorrect", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("TAG", "onFailure: "+t.toString() );
                ViewDialog error_dialog = new ViewDialog();
                error_dialog.showDialog(login.this,"Please Connect To Internet.");
            }
        });



    }

    public void forgotusername(String email ){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        UserService service = retrofit.create(UserService.class);

        Call call = service.forgotusername(email);
        call.enqueue(new Callback<forgot>() {
            @Override
            public void onResponse(Call<forgot> call, Response<forgot> response) {
                //hiding progress dialog


                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), response.body().getStatus() , Toast.LENGTH_LONG).show();



                }else if(response.code()==400){

                    Toast.makeText(getApplicationContext(), "Username or Password is Incorrect", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<forgot> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.toString() );
                ViewDialog error_dialog = new ViewDialog();
                error_dialog.showDialog(login.this,"Please Connect To Internet.");
            }
        });

    }
    public void forgotpass(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        UserService service = retrofit.create(UserService.class);

        Call call = service.forgotpassword(email);
        call.enqueue(new Callback<forgot>() {
            @Override
            public void onResponse(Call<forgot> call, Response<forgot> response) {
                //hiding progress dialog


                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), response.body().getStatus() , Toast.LENGTH_LONG).show();



                }else if(response.code()==400){

                    Toast.makeText(getApplicationContext(), "Username or Password is Incorrect", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<forgot> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.toString() );
                ViewDialog error_dialog = new ViewDialog();
                error_dialog.showDialog(login.this,"Please Connect To Internet.");
            }
        });

    }

}
