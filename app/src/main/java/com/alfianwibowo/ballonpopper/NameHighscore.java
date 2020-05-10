package com.alfianwibowo.ballonpopper;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.alfianwibowo.ballonpopper.mainmenu.MainMenu;

public class NameHighscore extends AppCompatActivity {

    private ViewGroup mContentView;

    TextView tvscore;
    EditText etnama;
    Intent intent;
    Bundle bundle;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_highscore);

        mContentView = findViewById(R.id.NameHighScore);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToFullScreen();
            }
        });

        tvscore = findViewById(R.id.tvscore);
        showscore();

        dbManager = new DBManager(this);

        LinearLayout linlay = findViewById(R.id.linlay);
        GradientDrawable gd1 = (GradientDrawable) linlay.getBackground();
        gd1.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue_light, null));

        Button bhss = findViewById(R.id.bhss);
        GradientDrawable gd2 = (GradientDrawable) bhss.getBackground();
        gd2.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue, null));
    }

    private void setToFullScreen() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToFullScreen();
    }

    public void Kembali(View v){
        ContentValues values = new ContentValues();
        etnama = findViewById(R.id.etnama);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        if (TextUtils.isEmpty(etnama.getText())){
            etnama.setError("Masukkan Nama Anda");
        } else {
            values.put(DBManager.Nama, etnama.getText().toString());
            values.put(DBManager.Nilai, tvscore.getText().toString());
            dbManager.Insert(values);

            Button b1 = findViewById(R.id.bhss);
            ObjectAnimator animY = ObjectAnimator.ofFloat(b1, "translationY",
                    -10f, 0f);
            animY.setDuration(1000);
            animY.setInterpolator(new BounceInterpolator());
            animY.start();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }, 500);
        }
    }

    private void showscore() {

        intent = getIntent();
        bundle = intent.getExtras();
        assert bundle != null;
        int score = bundle.getInt("score");
//        int level = bundle.getInt("level");

        tvscore.setText(String.valueOf(score));
        //TextView tvlevel = findViewById(R.id.tvscore);
        //tvlevel.setText(String.valueOf(level));

    }

    @Override
    public void onBackPressed() {
        etnama = findViewById(R.id.etnama);
        etnama.setError("Masukkan Nama Anda");
    }
}
