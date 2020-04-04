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
    private static final String BASE_URl = "https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/0f3156f1-c501-4d4b-b562-bffc15caae51/v1/workspaces/5c5f025a-67d1-4d3f-98a1-c4c97cdca75b/";
    String baseAuth = "apikey" + ":" + "rj0tYNAsBZViBKlTWdmzfNlcVE2OouJuHD8wCRWMoqfs";
    String apiKey = Base64.encodeToString(baseAuth.getBytes(), Base64.NO_WRAP);
    String authorization = "Basic " + apiKey;

    private WatsonService mService;
    private static WatsonClient INSTANCE;

    public WatsonClient() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URl)
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
        return mService.getBotReplay(authorization, "2020-04-01", mRequest);
    }

}
