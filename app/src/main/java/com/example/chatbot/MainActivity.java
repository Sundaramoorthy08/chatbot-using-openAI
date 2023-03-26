package com.example.chatbot;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ChatRVAdapter;
import com.ChatsModal;
import com.MsgModal;
import com.RetrofitAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView ChatsRV;
    private EditText UserUsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String BOT_KEY="bot";
    private final String USER_KEY="user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatsRV =findViewById(R.id.idRVChats);
        /*UserUsgEdt=findViewById(R.id.idRVChats);*/
        sendMsgFAB=findViewById(R.id.idFABSend);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager =new LinearLayoutManager(this);
        ChatsRV.setLayoutManager(manager);
        ChatsRV.setAdapter(chatRVAdapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserUsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(UserUsgEdt.getText().toString());
                UserUsgEdt.setText("");
            }
        });

    }
    private void  getResponse(String message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url="http://api.brainshop.ai/get?bid=173806&key=tjq31eC3XZQfjLH8&uid=[uid]&msg="+message;
        String BASE_URL= "http://api.brainshop.ai/";
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
        .build();
RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
Call<MsgModal> call = retrofitAPI.getmessage(url);
call.enqueue(new Callback<MsgModal>() {
    @Override
    public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
        if(response.isSuccessful()){
            MsgModal modal=response.body();
            chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
            chatRVAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<MsgModal> call, Throwable t) {
        chatsModalArrayList.add(new ChatsModal("please revert your question",BOT_KEY));
        chatRVAdapter.notifyDataSetChanged();

    }
});



    }
}