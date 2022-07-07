package ir.kazix.mymessenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import ir.kazix.mymessenger.Classes.Connection;
import ir.kazix.mymessenger.Classes.MyConnectionAdapter;
import ir.kazix.mymessenger.Classes.MyRequest;
import ir.kazix.mymessenger.Constants.Constants;

public class ChatFragmentConnections extends Fragment {

    RecyclerView recyclerView;
    MyConnectionAdapter connectionAdapter;
    Connection connection;
    ArrayList<Connection> connectionArrayList;
    ArrayList<JSONObject> jsonObjectArrayList;

    public MyRequest request;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_chat_connections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("KA-ChatFragConnections", "onViewCreated");

        ((AppCompatImageView) view.findViewById(R.id.fragment_chat_connections_fab_refresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.socket.getSocket().emit("get_connections");
            }
        });
        ((AppCompatImageView) view.findViewById(R.id.fragment_chat_connections_fab_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new MyRequest(view.getContext(), Constants.SERVER_NAME);

                request.setStringRequest(Constants.VOLLEY_GET, Constants.REQUEST_URI_LOG_OUT, null, () -> {
                    Log.d("KA-ChatFragConnections", request.getResponseString());
                });

                request.sendRequest();
            }
        });
        ((AppCompatImageView) view.findViewById(R.id.fragment_chat_connections_fab_message)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChatActivity.fragmentManager.beginTransaction()
                        .replace(R.id.activity_chat_fragment, ChatFragmentRoom.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();
            }
        });

        recyclerView = view.findViewById(R.id.fragment_connections_recycler_view);
        connectionAdapter = new MyConnectionAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(connectionAdapter);

        setSocketListener();
    }

    private void setSocketListener() {

        ChatActivity.socket.getSocket().on("get_connections", args -> {
            Log.d("KA-ChatFragConnections", "get_connections");

            connectionArrayList = new ArrayList<>();
            jsonObjectArrayList = new ArrayList<>();
            try {

                JSONObject jsonObject = new JSONObject((String) args[0]);
                JSONArray jsonArray = jsonObject.names();
                Log.d("KA-ChatFragConnections", "get_connections/jsonObject: " + jsonObject);
                Log.d("KA-ChatFragConnections", "get_connections/jsonArray: " + jsonArray);

                if (jsonArray != null) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObjectArrayList.add(new JSONObject(jsonObject.getString(jsonArray.getString(i))));

                        connection = new Connection();

                        connection.setClientId(jsonObjectArrayList.get(i).getString("clientId"));
                        Log.d("KA-ChatFragConnections", "get_connections/clientId " + i + ": " + jsonObjectArrayList.get(i).getString("clientId"));
                        connection.setSocketId(jsonObjectArrayList.get(i).getString("socketId"));
                        Log.d("KA-ChatFragConnections", "get_connections/socketId " + i + ": " + jsonObjectArrayList.get(i).getString("socketId"));
                        connection.setRemotePort(jsonObjectArrayList.get(i).getString("remotePort"));
                        Log.d("KA-ChatFragConnections", "get_connections/remotePort " + i + ": " + jsonObjectArrayList.get(i).getString("remotePort"));
                        connection.setRemoteAddress(jsonObjectArrayList.get(i).getString("remoteAddress"));
                        Log.d("KA-ChatFragConnections", "get_connections/remoteAddress " + i + ": " + jsonObjectArrayList.get(i).getString("remoteAddress"));
                        connection.setUserAgent(jsonObjectArrayList.get(i).getString("userAgent"));
                        Log.d("KA-ChatFragConnections", "get_connections/userAgent " + i + ": " + jsonObjectArrayList.get(i).getString("userAgent"));
                        connection.setHandshakeTime(jsonObjectArrayList.get(i).getString("time"));
                        Log.d("KA-ChatFragConnections", "get_connections/time " + i + ": " + jsonObjectArrayList.get(i).getString("time"));

                        if (!connection.getClientId().equals(ChatActivity.user.getEmail())) {

                            connectionArrayList.add(connection);
                            MyConnectionAdapter.localDataSet = connectionArrayList;
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {

                                    connectionAdapter.notifyDataSetChanged();
                                } // end run
                            }); // end post
                        } else {

                            Log.d("KA-ChatFragConnections", "get_connections/connection.getClientId equals user.getEmail");
                        }
                    } // end for
                } else {

                    Log.d("KA-ChatFragConnections", "get_connections/jsonArray == null");
                } // end else

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        });

        ChatActivity.socket.getSocket().on("new_connection", args -> {
            Log.d("KA-ChatFragConnections", "new_connection");

            try {

                JSONObject jsonObject = new JSONObject((String) args[0]);
                Log.d("KA-ChatFragConnections", "new_connection/jsonObject: " + jsonObject);

                connection = new Connection();

                connection.setClientId(jsonObject.getString("clientId"));
                connection.setSocketId(jsonObject.getString("socketId"));
                connection.setRemotePort(jsonObject.getString("remotePort"));
                connection.setRemoteAddress(jsonObject.getString("remoteAddress"));
                connection.setUserAgent(jsonObject.getString("userAgent"));
                connection.setHandshakeTime(jsonObject.getString("time"));

                if (!connection.getClientId().equals(ChatActivity.user.getEmail())) {

                    MyConnectionAdapter.localDataSet.add(connection);

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            connectionAdapter.notifyItemInserted(MyConnectionAdapter.localDataSet.size() - 1);
                        }
                    }); // end post
                } else {

                    Log.d("KA-ChatFragConnections", "new_connection/connection.getClientId equals user.getEmail");
                }

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        });

        ChatActivity.socket.getSocket().on("disconnection", args -> {

            Log.d("KA-MySocket", "disconnection/Arrays.toString: " + Arrays.toString(args));

            for (int i = 0; i < MyConnectionAdapter.localDataSet.size(); i++) {

                if (MyConnectionAdapter
                        .localDataSet.get(i).getSocketId().equals(args[0].toString())) {

                    Log.d("KA-MySocket", "disconnection/DataSet.index= " + i);

                    int finalI = i;
                    recyclerView.post(() -> {

                        MyConnectionAdapter.localDataSet.remove(finalI);
                        connectionAdapter.notifyItemRemoved(finalI);
                    }); // end post
                }
            }
        });
    } // setSocketListener
}
