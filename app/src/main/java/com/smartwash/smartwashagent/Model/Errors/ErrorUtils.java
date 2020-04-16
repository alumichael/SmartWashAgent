package com.smartwash.smartwashagent.Model.Errors;



import com.smartwash.smartwashagent.retrofit_interface.ServiceGenerator;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError (Response<?> response){
        Converter<ResponseBody , APIError> converter = ServiceGenerator.retrofit.responseBodyConverter(APIError.class , new Annotation[0]);
        APIError errorResponce;
        try{
            errorResponce = converter.convert(response.errorBody());
        }catch (IOException e){
            return new APIError();
        }
        return errorResponce;
    }
}