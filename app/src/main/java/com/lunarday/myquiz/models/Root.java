package com.lunarday.myquiz.models;

import java.util.List;



public class Root{
    public int response_code;
    public List<Result> results;

    public Root(int response_code, List<Result> results) {
        this.response_code = response_code;
        this.results = results;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}

