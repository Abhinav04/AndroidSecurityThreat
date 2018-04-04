package com.rapidbox.myapplication;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by manoj on 18-01-2018.
 */

public class APIService {
    private static String URLvalue="http://10.0.2.2:3000/";

    private static APIService instance = new APIService();
    private static Retrofit retrofit = null;
    private static  IAPIInterface iapiInterface;
	//35.229.95.94
    //http://10.0.2.2:8080
    //http://localhost:8080/web-service/v2/rapidbox/getHomeScreenData/9611050680
    //IAPIInterface apiResponse;
    private APIService()
    {
        String test = null;
        retrofit = new Retrofit.Builder()
                .baseUrl(URLvalue)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iapiInterface = retrofit.create(IAPIInterface.class);

    }

    public static APIService getInstance() {
        return instance;    }

    public IAPIInterface getIapiInterface() {
        return iapiInterface;
    }

}

