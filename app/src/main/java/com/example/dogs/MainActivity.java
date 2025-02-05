package com.example.dogs;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainViewModel";

    private ImageView imageViewDog;
    private ProgressBar progressBar;
    private Button buttonLoadImage;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading){
                    progressBar.setVisibility(View.VISIBLE);
                } else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        viewModel.getDogImage().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageViewDog);

            }
        });
        viewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError){
                    Toast.makeText(MainActivity.this, "Нет интернета!", LENGTH_SHORT).show();
                }
            }
        });

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initViews(){
        imageViewDog = findViewById(R.id.imageViewDog);
        progressBar = findViewById(R.id.progressBar);
        buttonLoadImage = findViewById(R.id.buttonLoadImage);
    }


}