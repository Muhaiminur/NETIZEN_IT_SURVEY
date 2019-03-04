package com.muhaiminurabir.netizen_survey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.muhaiminurabir.netizen_survey.DATABASE.STUDENT;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class SURVEY_RESULT extends AppCompatActivity {

    @BindView(R.id.student_recycler)
    RecyclerView studentRecycler;
    @BindView(R.id.student_search)
    EditText studentSearch;

    STUDENT_ADAPTER mAdapter;
    List<STUDENT> mRecyclerViewItems = new ArrayList<STUDENT>();

    private Realm realm;
    RealmResults<STUDENT> name_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey__result);
        ButterKnife.bind(this);

        //adddata();
        studentRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        studentRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        studentRecycler.setLayoutManager(mLayoutManager);
        studentRecycler.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new STUDENT_ADAPTER(mRecyclerViewItems);
        studentRecycler.setAdapter(mAdapter);


        try {
            Realm.init(getApplicationContext());
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("netizen.realm")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);
            name_lists = realm.where(STUDENT.class).findAll().sort("name", Sort.ASCENDING);
            mRecyclerViewItems.addAll(name_lists);
            mAdapter.notifyDataSetChanged();
            Log.d("size", name_lists.size() + "");
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
            if (realm != null) {
                realm.close();
            }
        } finally {

        }

        studentSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<STUDENT> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (STUDENT s : name_lists) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filterdNames);
    }
}
