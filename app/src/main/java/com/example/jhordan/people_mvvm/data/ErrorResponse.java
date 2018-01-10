package com.example.jhordan.people_mvvm.data;

import org.json.JSONObject;

/**
 * Created by nijandhanl on 1/9/18.
 */

public class ErrorResponse extends Throwable {

    public String getErrorMessage(){
        String errorJson = getMessage();
        try {
            JSONObject errorJsonObject = new JSONObject(errorJson);
            return errorJsonObject.getString("error");
        } catch (Exception e){

        }
        return null;
    }
}
