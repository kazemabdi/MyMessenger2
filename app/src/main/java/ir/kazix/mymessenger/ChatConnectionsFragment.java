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

import ir.kazix.mymessenger.Classes.MyConnection;
import ir.kazix.mymessenger.Classes.MyConnectionAdapter;
import ir.kazix.mymessenger.Classes.MyRequest;
import ir.kazix.mymessenger.Classes.Constants;

public class ChatConnectionsFragment extends Fragment {

    RecyclerView recyclerView;
    MyConnectionAdapter connectionAdapter;
    MyConnection myConnection;
    ArrayList<MyConnection> myConnectionArrayList;
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
                ChatActivity.socket.getSocket().emit("get_connections", ChatActivity.user.getEmail());
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
                        .replace(R.id.activity_chat_fragment, ChatRoomFragment.class, null)
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

            myConnectionArrayList = new ArrayList<>();
            jsonObjectArrayList = new ArrayList<>();

            try {

                JSONObject jsonObject = new JSONObject((String) args[0]);
                Log.d("KA-ChatConnectionsFrg", "jsonObject" + jsonObject);

                JSONArray jsonArray = jsonObject.names();
                Log.d("KA-ChatConnectionsFrg", "jsonArray" + jsonArray);


                if (jsonArray != null) {

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObjectArrayList.add(new JSONObject(jsonObject.getString(jsonArray.getString(i))));

                        myConnection = new MyConnection();

                        myConnection.setClientId(jsonObjectArrayList.get(i).getString("clientId"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/clientId " + i + ": " + jsonObjectArrayList.get(i).getString("clientId"));
                        myConnection.setSocketId(jsonObjectArrayList.get(i).getString("socketId"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/socketId " + i + ": " + jsonObjectArrayList.get(i).getString("socketId"));
                        myConnection.setRemotePort(jsonObjectArrayList.get(i).getString("remotePort"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/remotePort " + i + ": " + jsonObjectArrayList.get(i).getString("remotePort"));
                        myConnection.setRemoteAddress(jsonObjectArrayList.get(i).getString("remoteAddress"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/remoteAddress " + i + ": " + jsonObjectArrayList.get(i).getString("remoteAddress"));
                        myConnection.setUserAgent(jsonObjectArrayList.get(i).getString("userAgent"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/userAgent " + i + ": " + jsonObjectArrayList.get(i).getString("userAgent"));
                        myConnection.setHandshakeTime(jsonObjectArrayList.get(i).getString("handshakeTime"));
                        Log.d("KA-ChatConnectionsFrg", "get_connections/time " + i + ": " + jsonObjectArrayList.get(i).getString("handshakeTime"));


                        if (!myConnection.getClientId().equals(ChatActivity.user.getEmail())) {

                            myConnectionArrayList.add(myConnection);
                            MyConnectionAdapter.localDataSet = myConnectionArrayList;
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {

                                    connectionAdapter.notifyDataSetChanged();
                                } // end run
                            }); // end post
                        } else {

                            Log.d("KA-ChatFragConnections", "get_connections/myConnection.getClientId equals myUser.getEmail");
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

                myConnection = new MyConnection();

                myConnection.setClientId(jsonObject.getString("clientId"));
                myConnection.setSocketId(jsonObject.getString("socketId"));
                myConnection.setRemotePort(jsonObject.getString("remotePort"));
                myConnection.setRemoteAddress(jsonObject.getString("remoteAddress"));
                myConnection.setUserAgent(jsonObject.getString("userAgent"));
                myConnection.setHandshakeTime(jsonObject.getString("handshakeTime"));

                if (!myConnection.getClientId().equals(ChatActivity.user.getEmail())) {

                    MyConnectionAdapter.localDataSet.add(myConnection);

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            connectionAdapter.notifyItemInserted(MyConnectionAdapter.localDataSet.size() - 1);
                        }
                    }); // end post
                } else {

                    Log.d("KA-ChatFragConnections", "new_connection/myConnection.getClientId equals myUser.getEmail");
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
