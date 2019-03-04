package com.muhaiminurabir.netizen_survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.muhaiminurabir.netizen_survey.DATABASE.STUDENT;
import com.muhaiminurabir.netizen_survey.DATABASE.USER;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class REGISTRATION extends AppCompatActivity {

    @BindView(R.id.registration_name)
    EditText registrationName;
    @BindView(R.id.registration_email)
    EditText registrationEmail;
    @BindView(R.id.registration_pass)
    EditText registrationPass;
    @BindView(R.id.registration_confirm_pass)
    EditText registrationConfirmPass;
    @BindView(R.id.registration_create)
    Button registrationCreate;
    @BindView(R.id.registration_login)
    Button registrationLogin;

    Context context;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        try {
            try {
                Realm.init(getApplicationContext());
                RealmConfiguration config = new RealmConfiguration.Builder()
                        .name("netizen.realm")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                realm = Realm.getInstance(config);
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
                if (realm != null) {
                    realm.close();
                }
            } finally {

            }

            context = REGISTRATION.this;
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.registration_create, R.id.registration_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registration_create:
                if (!TextUtils.isEmpty(registrationName.getText().toString())) {
                    if (!TextUtils.isEmpty(registrationEmail.getText().toString())) {
                        if (!TextUtils.isEmpty(registrationPass.getText().toString())) {
                            if (!TextUtils.isEmpty(registrationConfirmPass.getText().toString())) {
                                if (registrationPass.getText().toString().equals(registrationConfirmPass.getText().toString())){
                                    try {
                                        realm.beginTransaction();
                                        USER user = realm.createObject(USER.class);
                                        user.setName(registrationName.getText().toString());
                                        user.setEmail(registrationEmail.getText().toString());
                                        user.setPassword(registrationPass.getText().toString());
                                        Toast.makeText(this, "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                        realm.commitTransaction();

                                        FastSave.getInstance().saveString("email", registrationEmail.getText().toString());
                                        FastSave.getInstance().saveString("pass", registrationPass.getText().toString());

                                        startActivity(new Intent(context,LOGIN.class));
                                        finish();

                                    } catch (Exception e) {
                                        Log.d("Error Line Number", Log.getStackTraceString(e));
                                    }
                                }else {
                                    Toast.makeText(context, "CONFIRM PASSWORD NOT MATCHED", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.registration_confirm_password_hint), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.registration_password_hint), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.registration_email_hint), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, getString(R.string.registration_username_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registration_login:
                startActivity(new Intent(context, LOGIN.class));
                finish();
                break;
        }
    }
}
