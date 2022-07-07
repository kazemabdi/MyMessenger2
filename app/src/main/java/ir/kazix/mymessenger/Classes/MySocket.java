package ir.kazix.mymessenger.Classes;

import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ir.kazix.mymessenger.Constants.Constants;

public class MySocket {

    private Socket mSocket;

    @Nullable
    public static String mySocketID;

    public static ArrayList<JSONObject> connections = new ArrayList<>();

    Object[] objects;

    public MySocket() {

        try {

            mSocket = IO.socket(Constants.SERVER_NODE_JS + Constants.SERVER_NODE_JS_PORT);
        } catch (URISyntaxException e) {

            Log.d("KA-MySocket", "socketOnFail" + e.toString());
        }
    }

    public void connectSocket() {

        Log.d("KA-MySocket", "setSocketConnection");
        mSocket.connect();
    } // setSocketConnection

    public Socket getSocket() {
        return mSocket;
    } // getSocket

    public Object[] getObjectOnEvent(String event) {
        mSocket.on(event, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                objects = args;
            } // call
        });
        return objects;
    } // onSocketEvent

    public void setEmitSocket(String message) {
        mSocket.emit("new_message", message);
    } // setEmitSocket

}
