package ir.kazix.mymessenger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import ir.kazix.mymessenger.Classes.MyRequest;
import ir.kazix.mymessenger.Classes.User;
import ir.kazix.mymessenger.Constants.Constants;
import ir.kazix.mymessenger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FragmentManager fragmentManager;

    public static MyRequest request;

//    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        request = new MyRequest(view.getContext(), Constants.SERVER_NAME);

        fragmentManager = getSupportFragmentManager();

        binding.activityMainCardViewLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                fragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment, MainFragmentLogin.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();

                binding.activityMainCardViewLogin.setCardBackgroundColor(Color.WHITE);
                binding.activityMainCardViewRegister.setCardBackgroundColor(null);
            }
        });

        binding.activityMainCardViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment, MainFragmentRegister.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();

                binding.activityMainCardViewLogin.setCardBackgroundColor(null);
                binding.activityMainCardViewRegister.setCardBackgroundColor(Color.WHITE);
            }
        });
    }
}