package com.dev_m_elbanna.watsonbot.network;

import android.util.Base64;

import com.dev_m_elbanna.watsonbot.network.pojo.WatsonRequest;
import com.dev_m_elbanna.watsonbot.network.pojo.WatsonResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created By Mohamed El Banna On 4/3/2020
 **/

public class WatsonClient {
    //ToDo Change your base url , workspace_id , api_key and version date(skill creation date)
    private static final String BASE_URL = "https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/xx....xx/v1/workspaces/";
    private static final String AR_WORKSPACE_ID = "xx....xx/";
    private static final String EN_WORKSPACE_ID = "xx....xx/";
    //    final String WORKSPACE_LANGUAGE_ID = getDeviceLanguage();
    String baseAuth = "apikey" + ":" + "xx....xx";
    String apiKey = Base64.encodeToString(baseAuth.getBytes(), Base64.NO_WRAP);
    String authorization = "Basic " + apiKey;

    private WatsonService mService;
    private static WatsonClient INSTANCE;

    public WatsonClient() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL + EN_WORKSPACE_ID)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(WatsonService.class);
    }

    public static WatsonClient getInstance() {
        if (INSTANCE == null)
            INSTANCE = new WatsonClient();
        return INSTANCE;
    }

    public Call<WatsonResponse> getBotReplay(WatsonRequest mRequest) {
        return mService.getBotReplay(authorization, "2013-01-25", mRequest);
    }

//    private String getDeviceLanguage() {
//        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en"))
//            return EN_WORKSPACE_ID;
//        else return AR_WORKSPACE_ID;
//    }

}
