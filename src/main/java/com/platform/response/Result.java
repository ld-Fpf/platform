package com.platform.response;


public class Result {
    public static final String RESPONSE_RESULT_SUCCESS = "SUCCESS";
    public static final String RESPONSE_RESULT_ERROR = "ERROR";
    public static final String RESPONSE_RESULT_EXCEPTION = "EXCEPTION";
    protected String result;
    protected String message;

    public Result(){

    }

    public Result(String result, String message){
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
