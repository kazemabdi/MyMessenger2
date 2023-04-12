package ir.kazix.mymessenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import ir.kazix.mymessenger.Classes.Constants;

public class MainLoginFragment extends Fragment {

    CardView loginCardView;
    EditText emailEditText;
    EditText passwordEditText;
    ProgressBar progressBar;
    View progressBarBackgroundView;

    Map<String, String> reqBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginCardView = view.findViewById(R.id.fragment_login_card_view);
        emailEditText = view.findViewById(R.id.fragment_login_edit_text_email);
        passwordEditText = view.findViewById(R.id.fragment_login_edit_text_password);
        progressBar = view.findViewById(R.id.fragment_login_progress_bar);
        progressBarBackgroundView = view.findViewById(R.id.fragment_login_progress_bar_card_background);

        reqBody = new HashMap<>();

        loginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.toString().isEmpty()) {

                    reqBody.put("email", emailEditText.getText().toString());
                    reqBody.put("password", passwordEditText.getText().toString());

                    MainActivity.request.setStringRequest(Request.Method.POST, Constants.REQUEST_URI_SIGN_IN, reqBody, () -> {

                        Intent intent = new Intent(view.getContext(), ChatActivity.class);
                        intent.putExtra("response", MainActivity.request.getResponseString());

                        view.getContext().startActivity(intent);
                    });

                    MainActivity.request.sendRequest();

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                }
            } // end onClick
        }); // end setOnClickListener

    } // end onViewCreated
}