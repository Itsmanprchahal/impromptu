package com.mandywebdesign.impromptu.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mandywebdesign.impromptu.Interfaces.WebAPI;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.Retrofit.RetroDeleteQUEs;
import com.mandywebdesign.impromptu.SettingFragmentsOptions.NormalGetProfile;
import com.mandywebdesign.impromptu.SettingFragmentsOptions.NormalPublishProfile;
import com.mandywebdesign.impromptu.SettingFragmentsOptions.QuesIDIF;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Normal_user_profile_QA_adapter extends RecyclerView.Adapter<Normal_user_profile_QA_adapter.ViewHolder> {

    Context context;
    public ArrayList<String> ques1 = new ArrayList<>();
    public ArrayList<String> ansewer1 = new ArrayList<>();
    String[] arryString;
    EditText answerET;
    TextView qust_text;
    Spinner spinner;
    String userToken, QuesID;
    QuesIDIF quesIDIF;

    public void Normal_user_profile_QA_adapter(QuesIDIF quesIDIF) {
        this.quesIDIF = quesIDIF;
    }

    private QuestionAdapter.OnItemClickListener itemClickListener;


    public void setOnItemClickListener(QuestionAdapter.OnItemClickListener onItemClickListener) {

        itemClickListener = onItemClickListener;
    }

    public Normal_user_profile_QA_adapter(Context context, ArrayList<String> ques1, ArrayList<String> ansewer1, String[] arryString, String userToken,QuesIDIF quesIDIF) {
        this.context = context;
        this.ques1 = ques1;
        this.ansewer1 = ansewer1;
        this.arryString = arryString;
        this.userToken = userToken;
        this.quesIDIF = quesIDIF;
    }

    @NonNull
    @Override
    public Normal_user_profile_QA_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.custom_normal_profile, viewGroup, false);
        return new Normal_user_profile_QA_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Normal_user_profile_QA_adapter.ViewHolder viewHolder, final int i) {

        viewHolder.ques.setText(ques1.get(i));
        viewHolder.answer.setText(ansewer1.get(i));
        QuesID = NormalPublishProfile.QA_id.get(i);


        viewHolder.edit_Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.queston_dilog_question);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setCanceledOnTouchOutside(false);


                answerET = dialog.findViewById(R.id.user_profile_answer);
                spinner = dialog.findViewById(R.id.user_profile_sppiner);
                qust_text = dialog.findViewById(R.id.question_text);
                ImageView close = dialog.findViewById(R.id.close_questtionDialog);

                final List<String> stringList = new ArrayList<String>(Arrays.asList(arryString));
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, stringList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                stringList.add(ques1.get(i));
                adapter.notifyDataSetChanged();


                String answer = viewHolder.answer.getText().toString();

                answerET.setText(answer);

                Button done = dialog.findViewById(R.id.user_profile_button);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String answer = answerET.getText().toString();
                        String ques_ = spinner.getSelectedItem().toString();
                        if (TextUtils.isEmpty(answer)) {
                            answerET.setError("Write your Answer");
                        } else {
                            viewHolder.answer.setText(answer);
                            viewHolder.ques.setText(ques_);
                            NormalPublishProfile.ANSWER.remove(i);
                            NormalPublishProfile.QUES.remove(i);
                            NormalPublishProfile.ANSWER.add(i, answer);
                            NormalPublishProfile.QUES.add(i, ques_);
                            notifyDataSetChanged();
                            dialog.dismiss();

                        }

                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        viewHolder.close_Q.setVisibility(View.VISIBLE);
        viewHolder.close_Q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i==ques1.size())
                {
                    ques1.remove(ques1.size()-1);
                    ansewer1.remove(ansewer1.size()-1);
                    notifyDataSetChanged();
                }
                else
                {
                    quesIDIF.questID(String.valueOf(i), NormalGetProfile.QA_id.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ques1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ques;
        TextView answer;
        ImageView edit_Q, close_Q;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ques = itemView.findViewById(R.id.recycler_question);
            answer = itemView.findViewById(R.id.recycler_answer);
            edit_Q = itemView.findViewById(R.id.normla_user_edit);
            close_Q = itemView.findViewById(R.id.normal_user_close);
        }
    }
}