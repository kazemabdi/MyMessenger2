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
import ir.kazix.mymessenger.Classes.Constants;
import ir.kazix.mymessenger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FragmentManager fragmentManager;

    public static MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        .replace(R.id.activity_main_fragment, MainLoginFragment.class, null)
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
                        .replace(R.id.activity_main_fragment, MainRegisterFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();

                binding.activityMainCardViewLogin.setCardBackgroundColor(null);
                binding.activityMainCardViewRegister.setCardBackgroundColor(Color.WHITE);
            }
        });
    }
}