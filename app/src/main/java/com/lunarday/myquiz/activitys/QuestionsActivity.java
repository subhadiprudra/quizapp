package com.lunarday.myquiz.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lunarday.myquiz.R;
import com.lunarday.myquiz.models.Result;
import com.lunarday.myquiz.viewmodel.QuestionsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionsActivity extends AppCompatActivity {

    List<Result> resultList;
    String TAG = "MainActivity_";


    QuestionsViewModel questionsViewModel;
    TextView question,ans1,ans2,ans3,ans4;
    List<TextView> optionTextviewList;
    int totalTime =0;

    TextView indexTv,totalQuestionsTv,pointsTV;
    com.owl93.dpb.CircularProgressView progressView;

    ImageView close;
    Dialog dialog;

    int index =0;
    int cI=0;
    boolean isWaiting = false;
    int point=0;
    boolean isOnActivity=true;
    LinearLayout content;
    Dialog loading;

    int c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        close = findViewById(R.id.close);
        progressView = findViewById(R.id.progress_view);
        content = findViewById(R.id.content);
        content.setVisibility(View.GONE);

        indexTv = findViewById(R.id.index);
        totalQuestionsTv = findViewById(R.id.total_question);
        pointsTV = findViewById(R.id.points);
        c = getIntent().getIntExtra("c",21);


        loading = new Dialog(this);
        loading.setContentView(R.layout.loading);
        loading.show();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.close_dialog);

        CardView yes = dialog.findViewById(R.id.yes);
        CardView no = dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });



        optionTextviewList=new ArrayList<>();
        optionTextviewList.add(ans1);
        optionTextviewList.add(ans2);
        optionTextviewList.add(ans3);
        optionTextviewList.add(ans4);


        questionsViewModel = new ViewModelProvider(this).get(QuestionsViewModel.class);
        questionsViewModel.init(c);
        questionsViewModel.getQuestions().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                resultList = results;
                totalTime = 10*results.size();
                progressView.setMaxValue(totalTime);
                stratCoundDown();
                totalQuestionsTv.setText("/"+resultList.size());
                for(Result result : results){
                    Log.i(TAG,result.question);
                }
                manageQuestions();

                loading.dismiss();
                content.setVisibility(View.VISIBLE);

            }
        });

        for(int i=0;i<optionTextviewList.size();i++){
            int finalI = i;
            optionTextviewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isWaiting) {
                        isWaiting=true;

                        if (cI == finalI) {
                            optionTextviewList.get(finalI).setBackground(getResources().getDrawable(R.drawable.coreect_ans_back));
                            point++;
                            pointsTV.setText(point+"");
                        } else {
                            optionTextviewList.get(finalI).setBackground(getResources().getDrawable(R.drawable.wrong_ans_back));
                            optionTextviewList.get(cI).setBackground(getResources().getDrawable(R.drawable.coreect_ans_back));
                        }

                        waitAndGotoNext();
                    }

                }
            });
        }


    }

    void stratCoundDown(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (totalTime > 0 && isOnActivity) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    totalTime--;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressView.setProgress(totalTime);
                            progressView.setText(totalTime + "s");
                            if(totalTime<=1) {
                                isOnActivity= false;
                                Intent intent = new Intent(QuestionsActivity.this, com.lunarday.myquiz.activitys.Result.class);
                                intent.putExtra("score", point);
                                intent.putExtra("total", resultList.size());
                                startActivity(intent);
                                finish();

                            }

                        }
                    });

                }

            }
        }).start();

    }

    void waitAndGotoNext(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try { Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(index<resultList.size()-1) {
                            for (int i = 0; i < optionTextviewList.size(); i++) {
                                optionTextviewList.get(i).setBackground(getResources().getDrawable(R.drawable.border));
                            }
                            index++;
                            manageQuestions();
                            isWaiting = false;
                        }else {
                            isOnActivity=false;
                            Intent intent = new Intent(QuestionsActivity.this, com.lunarday.myquiz.activitys.Result.class);
                            intent.putExtra("score",point);
                            intent.putExtra("total",resultList.size());
                            startActivity(intent);
                            finish();

                        }
                    }
                });
            }
        }).start();
    }

    void manageQuestions(){
        Result result = resultList.get(index);
        int a = index+1;
        indexTv.setText("Question "+a);
        question.setText(result.question);
        Random random = new Random();
        cI = random.nextInt(4);
        List<String> options = result.getIncorrect_answers();

        int j=0;
        for(int i=0;i<4;i++){
            if(i==cI){
                optionTextviewList.get(i).setText(result.correct_answer);
            }else {
                optionTextviewList.get(i).setText(options.get(j));
                j++;
            }
        }



    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        // Saving variables
        savedInstanceState.putInt("totaltime", totalTime);
        savedInstanceState.putInt("index", index);
        savedInstanceState.putInt("cI", cI);
        savedInstanceState.putInt("point", point);

        // Call at the end
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){

        totalTime = savedInstanceState.getInt("totaltime");
        index = savedInstanceState.getInt("index");
        cI = savedInstanceState.getInt("cI");
        point = savedInstanceState.getInt("point");

        Result result = resultList.get(index);
        int a = index+1;
        indexTv.setText("Question "+a);
        question.setText(result.question);
        pointsTV.setText(point+"");
        List<String> options = result.getIncorrect_answers();

        int j=0;
        for(int i=0;i<4;i++){
            if(i==cI){
                optionTextviewList.get(i).setText(result.correct_answer);
            }else {
                optionTextviewList.get(i).setText(options.get(j));
                j++;
            }
        }

        super.onRestoreInstanceState(savedInstanceState);

    }


    @Override
    public void onBackPressed() {
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOnActivity=false;
    }
}