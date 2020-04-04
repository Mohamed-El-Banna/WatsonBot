package com.dev_m_elbanna.watsonbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dev_m_elbanna.watsonbot.network.WatsonClient;
import com.dev_m_elbanna.watsonbot.network.pojo.WatsonRequest;
import com.dev_m_elbanna.watsonbot.network.pojo.WatsonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Mohamed El Banna On 4/3/2020
 **/

public class MainActivity extends AppCompatActivity {

    private EditText etMessage;
    private TextView tvBotReplay;
    private Button btnSend;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.et_message);
        tvBotReplay = findViewById(R.id.tv_bot_replay);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(view -> {
            sendMessage();
        });
    }


    void sendMessage() {
        WatsonRequest.InputBean inputBean = new WatsonRequest.InputBean();
        inputBean.setText(etMessage.getText().toString().trim());
        WatsonRequest mRequest = new WatsonRequest();
        mRequest.setInput(inputBean);

        WatsonClient.getInstance().getBotReplay(mRequest).enqueue(new Callback<WatsonResponse>() {
            @Override
            public void onResponse(Call<WatsonResponse> call, Response<WatsonResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().toString());
                WatsonResponse mResponse = response.body();
                String mBotReplay = mResponse.getOutput().getText().get(0);
                runOnUiThread(() -> {
                    tvBotReplay.setText(mBotReplay);
                });
            }

            @Override
            public void onFailure(Call<WatsonResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
