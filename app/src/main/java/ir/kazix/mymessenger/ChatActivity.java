package ir.kazix.mymessenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import ir.kazix.mymessenger.Classes.MyRequest;
import ir.kazix.mymessenger.Classes.MySocket;
import ir.kazix.mymessenger.Classes.MyUser;
import ir.kazix.mymessenger.Classes.Constants;
import ir.kazix.mymessenger.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    public static MySocket socket;

    public static FragmentManager fragmentManager;

    ActivityChatBinding binding;

    String response;

    Intent intent;

    public static MyUser user;

    MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((TextView) findViewById(R.id.activity_chat_text_view)).setText("My Messenger");

        intent = getIntent();
        response = intent.getStringExtra("response");
        ArrayList<JSONObject> jsonObjectArrayList = new ArrayList<>();
        try {

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObjectArrayList.add(jsonArray.getJSONObject(i));
                Log.d("KA-ChatActivity", "jsonObjectArrayList.get(" + i + ")" + jsonObjectArrayList.get(i));
            }

            user = new MyUser();
            user.setEmail(jsonObjectArrayList.get(0).getString("email"));
            user.setSessionId(jsonObjectArrayList.get(jsonObjectArrayList.size() - 1).getString("session_id"));

            Toast.makeText(this, user.getSessionId(), Toast.LENGTH_SHORT).show();

        } catch (JSONException jsonException) {

            jsonException.printStackTrace();
        }

        request = new MyRequest(this, Constants.SERVER_NAME);

        socket = new MySocket();
        socket.connectSocket();
        setSocketListener();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_chat_fragment, ChatConnectionsFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();
    }

    private void setSocketListener() {

        socket.getSocket().on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                MySocket.mySocketID = socket.getSocket().id();

                ChatActivity.socket.getSocket().emit("join_socket", ChatActivity.user.getEmail());

                ((TextView) findViewById(R.id.activity_chat_text_view)).post(() -> {

                    binding.activityChatProgressBar.setIndeterminate(false);
                    binding.activityChatProgressBar.setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.activity_chat_text_view)).setText("My Messenger");
                });
            } // end call
        });

        socket.getSocket().on("connect_error", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                for (Object arg : args) {
                    Log.d("KA-ChatActivity", "connect_error:" + arg);

                    ((TextView) findViewById(R.id.activity_chat_text_view)).post(() -> {

                        ((TextView) findViewById(R.id.activity_chat_text_view)).setText("Connecting...");
                        binding.activityChatProgressBar.setVisibility(View.VISIBLE);
                        binding.activityChatProgressBar.setIndeterminate(true);
                    });
                }
            }
        });
    } // setSocketListener

    @Override
    protected void onDestroy() {

        request.setStringRequest(Constants.VOLLEY_GET, Constants.REQUEST_URI_LOG_OUT, null, () -> {
        });

        request.sendRequest();

        socket.getSocket().disconnect();
        super.onDestroy();
    }
}