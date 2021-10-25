package com.lunarday.myquiz.res;

import com.lunarday.myquiz.models.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetQuestionService {

    @GET("?amount=15&type=multiple")
    Call<Root> getQuestions(@Query("category")  int catagory);
}
