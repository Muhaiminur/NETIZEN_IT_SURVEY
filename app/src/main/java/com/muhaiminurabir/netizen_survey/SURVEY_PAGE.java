package com.muhaiminurabir.netizen_survey;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.muhaiminurabir.netizen_survey.DATABASE.STUDENT;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SURVEY_PAGE extends AppCompatActivity {

    @BindView(R.id.survey_name)
    EditText surveyName;
    @BindView(R.id.survey_male)
    RadioButton surveyMale;
    @BindView(R.id.survey_female)
    RadioButton surveyFemale;
    @BindView(R.id.gender_radioGroup)
    RadioGroup genderRadioGroup;
    @BindView(R.id.survey_father_name)
    EditText surveyFatherName;
    @BindView(R.id.survey_mother_email)
    EditText surveyMotherEmail;
    @BindView(R.id.survay_birth_date)
    EditText survayBirthDate;
    @BindView(R.id.survey_school)
    EditText surveySchool;
    @BindView(R.id.survey_division)
    MaterialSpinner surveyDivision;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.fab)
    FloatingActionButton login;

    Context context;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Calendar calendar;
    private int year, month, day;
    ArrayAdapter<String> division_adapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey__page);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

            context = SURVEY_PAGE.this;
            //initialize sharedpreferences
            FastSave.init(getApplicationContext());
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            division_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.division_array));
            division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            surveyDivision.setAdapter(division_adapter);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @OnClick({R.id.submit, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (!TextUtils.isEmpty(surveyName.getText().toString())) {
                    if (genderRadioGroup.getCheckedRadioButtonId() == surveyMale.getId() || genderRadioGroup.getCheckedRadioButtonId() == surveyFemale.getId()) {
                        if (!TextUtils.isEmpty(surveyFatherName.getText().toString())) {
                            if (!TextUtils.isEmpty(surveyMotherEmail.getText().toString())) {
                                if (!TextUtils.isEmpty(survayBirthDate.getText().toString())) {
                                    if (!TextUtils.isEmpty(surveySchool.getText().toString())) {
                                        if (surveyDivision.getSelectedItem() != null) {
                                            try {
                                                realm.beginTransaction();
                                                STUDENT nameList = realm.createObject(STUDENT.class);
                                                nameList.setName(surveyName.getText().toString());
                                                RadioButton r = findViewById(genderRadioGroup.getCheckedRadioButtonId());
                                                nameList.setGender(r.getText().toString());
                                                nameList.setFather_name(surveyFatherName.getText().toString());
                                                nameList.setMother_name(surveyMotherEmail.getText().toString());
                                                nameList.setBirth_date(survayBirthDate.getText().toString());
                                                nameList.setSchool_name(surveySchool.getText().toString());
                                                nameList.setGroup(surveyDivision.getSelectedItem().toString());
                                                Toast.makeText(this, "Saved Data", Toast.LENGTH_SHORT).show();
                                                realm.commitTransaction();
                                                Intent i = getBaseContext().getPackageManager()
                                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();
                                            } catch (Exception e) {
                                                Log.d("Error Line Number", Log.getStackTraceString(e));
                                            }


                                        } else {
                                            Toast.makeText(context, getString(R.string.survey_division_string), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, getString(R.string.survey_school_string), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(context, getString(R.string.survey_birth_string), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, getString(R.string.survey_mother_name_string), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, getString(R.string.survey_father_name_string), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, getString(R.string.survey_gender_string), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, getString(R.string.survey_name_string), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fab:
                startActivity(new Intent(SURVEY_PAGE.this, LOGIN.class));
                finish();
                break;
        }
    }

    @OnClick(R.id.survay_birth_date)
    public void onViewClicked() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        survayBirthDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}
