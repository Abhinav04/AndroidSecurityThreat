package com.rapidbox.myapplication;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by manoj on 18-01-2018.
 */

public interface IAPIInterface {


    @POST("send")
    Call<Boolean> sendClipdata(@Body AppData Data);

}
