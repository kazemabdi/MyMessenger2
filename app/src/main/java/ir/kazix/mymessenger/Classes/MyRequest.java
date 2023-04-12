package ir.kazix.mymessenger.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

import ir.kazix.mymessenger.ChatActivity;


public class MyRequest {

    Context context;
    String baseUrl;
    StringRequest stringRequest;
    RequestQueue queue;
    String responseString;

    public String getResponseString() {
        return responseString;
    }

    public interface MyListener {
        /**
         * Called when a response is received.
         */
        void onResponse();
    }

    public MyRequest(Context context, String baseUrl) {

        this.context = context;
        this.baseUrl = baseUrl;
        queue = Volley.newRequestQueue(this.context);
    }

    public void setStringRequest(int method, String route, @Nullable Map<String, String> map, MyListener myListener) {

        switch (method) {

            case Request.Method.POST:

                stringRequest = new StringRequest(
                        Request.Method.POST,
                        baseUrl + route,
                        response -> {

                            responseString = response;
                            myListener.onResponse();
                        },
                        error -> {

                            Toast.makeText(this.context, "error! :(", Toast.LENGTH_SHORT).show();
                            Log.d("KA-MyRequest", error.toString());
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
//                        return super.getParams();
                        return map;
                    }
                };

                break;

            case Request.Method.GET:

                stringRequest = new StringRequest(
                        Request.Method.GET,
                        baseUrl + route,
                        response -> {

                            responseString = response;
                            myListener.onResponse();
                        },
                        error -> {
                            Log.d("KA-MyRequest", error.toString());
                        });
                break;
        }
    }

    public void sendRequest() {

        queue.add(stringRequest);
//        return responseString;
    }
}