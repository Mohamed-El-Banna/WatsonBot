package com.dev_m_elbanna.watsonbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev_m_elbanna.watsonbot.network.WatsonClient;
import com.dev_m_elbanna.watsonbot.network.pojo.Message;
import com.dev_m_elbanna.watsonbot.network.pojo.WatsonRequest;
import com.dev_m_elbanna.watsonbot.network.pojo.WatsonResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Mohamed El Banna On 4/3/2020
 **/

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etMessage;
    private Button btnSend;
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManger;
    List<Message> mMessageList = new ArrayList<>();
    MessageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        mRecyclerView = findViewById(R.id.rv_messages);

        initRecyclerView();


        btnSend.setOnClickListener(view -> {
            String mMessage = etMessage.getText().toString().trim();
            if (mMessage == null && mMessage.isEmpty())
                return;
            Message userMessage = new Message("Me", mMessage);
            mAdapter.addSingleItem(userMessage);
            sendMessage();
            etMessage.setText("");
        });
    }

    private void initRecyclerView() {
        mLayoutManger = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mAdapter = new MessageAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
    }


    void sendMessage() {
        WatsonRequest.InputBean inputBean = new WatsonRequest.InputBean();
        inputBean.setText(etMessage.getText().toString().trim());
        WatsonRequest mRequest = new WatsonRequest();
        mRequest.setInput(inputBean);

        doChatBotApiCall(mRequest);


    }

    void doChatBotApiCall(WatsonRequest mWatsonRequest) {
        WatsonClient.getInstance().getBotReplay(mWatsonRequest).enqueue(new Callback<WatsonResponse>() {
            @Override
            public void onResponse(Call<WatsonResponse> call, Response<WatsonResponse> response) {
                Log.d(TAG, "onResponse: " + response.body().getOutput().getText().get(0));
                WatsonResponse mResponse = response.body();
                Message botMessage = new Message("Afaq Bot", mResponse.getOutput().getText().get(0));
                mAdapter.addSingleItem(botMessage);
            }

            @Override
            public void onFailure(Call<WatsonResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
