package com.muhaiminurabir.netizen_survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.muhaiminurabir.netizen_survey.DATABASE.STUDENT;
import com.muhaiminurabir.netizen_survey.DATABASE.USER;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class LOGIN extends AppCompatActivity {

    @BindView(R.id.login_email)
    EditText loginEmail;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_remember_me)
    AppCompatCheckBox loginRememberMe;
    @BindView(R.id.login_forgot)
    TextView loginForgot;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.login_registration)
    Button loginRegistration;

    Context context;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        try {
            context=LOGIN.this;
            try {
                Realm.init(getApplicationContext());
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("netizen.realm")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                realm = Realm.getInstance(config);
            }catch (Exception e){
                Log.d("Error Line Number", Log.getStackTraceString(e));
                if (realm!=null){
                    realm.close();
                }
            }finally {

            }

            //checking already log in credential
            if (FastSave.getInstance().isKeyExists("email") && FastSave.getInstance().isKeyExists("pass")) {
                loginEmail.setText(FastSave.getInstance().getString("email", getString(R.string.login_email_string)));
                loginPassword.setText(FastSave.getInstance().getString("pass", getString(R.string.login_password_string)));
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.login, R.id.login_registration})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (!TextUtils.isEmpty(loginEmail.getText().toString())) {
                    if (!TextUtils.isEmpty(loginPassword.getText().toString())) {
                        USER user = realm.where(USER.class).equalTo("email", loginEmail.getText().toString()).findFirst();
                        if (user==null||user.getEmail()==loginEmail.getText().toString()||user.getPassword()==loginPassword.getText().toString()){
                            Toast.makeText(this, "USER NOT FOUND", Toast.LENGTH_SHORT).show();
                        }else {
                            //saving credintinal
                            if (loginRememberMe.isChecked()){
                                FastSave.getInstance().saveString("email", loginEmail.getText().toString());
                                FastSave.getInstance().saveString("pass", loginPassword.getText().toString());
                            }
                            startActivity(new Intent(context,SURVEY_RESULT.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.registration_password_hint), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, getString(R.string.registration_email_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_registration:
                startActivity(new Intent(context,REGISTRATION.class));
                finish();
                break;
        }
    }
}
