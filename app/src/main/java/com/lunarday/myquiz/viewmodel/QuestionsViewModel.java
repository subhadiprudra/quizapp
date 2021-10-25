package com.lunarday.myquiz.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lunarday.myquiz.res.GetQuestionService;
import com.lunarday.myquiz.models.Result;
import com.lunarday.myquiz.models.Root;
import com.lunarday.myquiz.res.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsViewModel extends ViewModel {

    GetQuestionService service;
    private MutableLiveData<List<Result>> qusetionList = new MutableLiveData<>();

    public void init(int category){
        if(service==null) {
            service =RetrofitClientInstance.getRetrofitInstance().create(GetQuestionService.class);
            Call<Root> call = service.getQuestions(category);
            call.enqueue(new Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {
                    qusetionList.setValue(response.body().results);
                    Log.i("data","got");
                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {
                    Log.i("data","error");

                }
            });
        }
    }

    public LiveData<List<Result>> getQuestions(){
        return qusetionList;
    }


}
