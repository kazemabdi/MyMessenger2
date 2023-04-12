package ir.kazix.mymessenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import ir.kazix.mymessenger.Classes.MyRequest;
import ir.kazix.mymessenger.Classes.Constants;

public class MainRegisterFragment extends Fragment {


    CardView registerCardView;
    EditText emailEditText;
    EditText passwordEditText;
    MyRequest myRequest;
    Map<String, String> reqBody;
    ProgressBar progressBar;
    View progressBarBackgroundView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailEditText = view.findViewById(R.id.edit_text_email);
        passwordEditText = view.findViewById(R.id.edit_text_password);
        registerCardView = view.findViewById(R.id.card_view_register_btn);

        progressBar = view.findViewById(R.id.fragment_register_progress_bar);
        progressBarBackgroundView = view.findViewById(R.id.fragment_register_progress_bar_card_background);

        reqBody = new HashMap<>();
        myRequest = new MyRequest(view.getContext(), Constants.SERVER_NAME);

        registerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.toString().isEmpty()) {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);

                    reqBody.put("email", emailEditText.getText().toString());
                    reqBody.put("password", passwordEditText.getText().toString());

                    myRequest.setStringRequest(Request.Method.POST, Constants.REQUEST_URI_REGISTER, reqBody, () -> {

                        Toast.makeText(view.getContext(), "Congratulation! You are signed up.", Toast.LENGTH_LONG).show();

                        progressBar.setIndeterminate(false);
                        progressBar.setVisibility(View.INVISIBLE);
                    });

                    myRequest.sendRequest();
                }
            } // end onClick
        }); // end setOnClickListener
    }
}