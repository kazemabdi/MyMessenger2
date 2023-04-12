package ir.kazix.mymessenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ir.kazix.mymessenger.Classes.MyMessage;
import ir.kazix.mymessenger.Classes.MyMessageAdapter;

public class ChatRoomFragment extends Fragment {

    MyMessageAdapter messageAdapter;
    RecyclerView recyclerView;

    FloatingActionButton sendFab;
    EditText messageEditText;

    Map<String, String> messageMap;
    JSONObject jsonObject;

    public static String receiverId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_chat_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_room_chat_recycler_view);
        sendFab = view.findViewById(R.id.fragment_room_chat_fab);
        messageEditText = view.findViewById(R.id.fragment_room_chat_edit_text);

        messageAdapter = new MyMessageAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(messageAdapter);

        // emit
        sendFab.setOnClickListener(view1 -> {

            if (messageEditText.getText() != null) {

                if (!messageEditText.getText().toString().equals("")) {

                    MyMessage message = new MyMessage();
                    message.setMessageSrcID(ChatActivity.user.getEmail());
                    message.setMessageText(messageEditText.getText().toString());
                    message.setMessageDstID(receiverId);

                    MyMessageAdapter.localDataSet.add(message);

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            messageAdapter.notifyItemInserted(MyMessageAdapter.localDataSet.size() - 1);
                            recyclerView.scrollToPosition(MyMessageAdapter.localDataSet.size() - 1);
                        }
                    });

                    messageMap = new HashMap<>();
                    messageMap.put("senderId", ChatActivity.user.getEmail());
                    messageMap.put("text", message.getMessageText());
                    messageMap.put("receiverId", receiverId);

                    jsonObject = new JSONObject(messageMap);
                    ChatActivity.socket.getSocket().emit("new_message", jsonObject);

                    messageEditText.setText("");
                }
            }
        });

        // end call
        ChatActivity.socket.getSocket().on("new_message", args -> {

            Log.d("KA-MySocket", "new_message: " + Arrays.toString(args));

            MyMessage myMessage = new MyMessage();

            try {

                JSONObject jsonObject = new JSONObject((String) args[0]);

                myMessage.setMessageSrcID(jsonObject.getString("senderId"));
                myMessage.setMessageDstID(jsonObject.getString("receiverId"));
                myMessage.setMessageText(jsonObject.getString("text"));

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

            if (myMessage.getMessageSrcID() != null && !myMessage.getMessageSrcID().equals(ChatActivity.user.getEmail())) {

                MyMessageAdapter.localDataSet.add(myMessage);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {

                        messageAdapter.notifyItemInserted(MyMessageAdapter.localDataSet.size() - 1);
                        recyclerView.scrollToPosition(MyMessageAdapter.localDataSet.size() - 1);
                    }
                }); // end post
            }
        });
    }
}
