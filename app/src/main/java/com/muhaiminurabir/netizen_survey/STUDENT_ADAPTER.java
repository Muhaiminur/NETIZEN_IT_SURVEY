package com.muhaiminurabir.netizen_survey;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhaiminurabir.netizen_survey.DATABASE.STUDENT;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class STUDENT_ADAPTER extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<STUDENT> name_list;

    public STUDENT_ADAPTER(List<STUDENT> list) {
        this.name_list = list;
    }

    public class Name_List_Adapter_View extends RecyclerView.ViewHolder {
        @BindView(R.id.student_name)
        TextView studentName;
        @BindView(R.id.student_gender)
        TextView studentGender;
        @BindView(R.id.student_father)
        TextView studentFather;
        @BindView(R.id.student_mother)
        TextView studentMother;
        @BindView(R.id.student_date)
        TextView studentDate;
        @BindView(R.id.student_school)
        TextView studentSchool;
        @BindView(R.id.student_group)
        TextView studentGroup;
        @BindView(R.id.student_row)
        CardView studentRow;

        public Name_List_Adapter_View(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);

        return new Name_List_Adapter_View(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        STUDENT nameList = (STUDENT) name_list.get(position);
        Name_List_Adapter_View name_view = (Name_List_Adapter_View) holder;
        name_view.studentName.setText(nameList.getName());
        name_view.studentGender.setText(nameList.getGender());
        name_view.studentFather.setText(nameList.getFather_name());
        name_view.studentMother.setText(nameList.getMother_name());
        name_view.studentDate.setText(nameList.getBirth_date());
        name_view.studentSchool.setText(nameList.getSchool_name());
        name_view.studentGroup.setText(nameList.getGroup());
    }

    @Override
    public int getItemCount() {
        return name_list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    public void filterList(List<STUDENT> filterdNames) {
        this.name_list = filterdNames;
        notifyDataSetChanged();
    }
}