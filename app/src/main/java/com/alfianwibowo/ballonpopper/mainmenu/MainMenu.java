package com.alfianwibowo.ballonpopper.mainmenu;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfianwibowo.ballonpopper.DBManager;
import com.alfianwibowo.ballonpopper.R;

public class MainMenu extends AppCompatActivity {

    private ViewGroup mContentView;
    DBManager dbManager;
    int level = 1;
    boolean kondisi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mContentView = findViewById(R.id.main_menu);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToFullScreen();
            }
        });

        dbManager = new DBManager(this);

        Button b1 = findViewById(R.id.startb1);
        GradientDrawable db1 = (GradientDrawable) b1.getBackground();
        db1.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue, null));

        Button b2 = findViewById(R.id.scoreb2);
        GradientDrawable db2 = (GradientDrawable) b2.getBackground();
        db2.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_green, null));

        Button b3 = findViewById(R.id.howtob3);
        GradientDrawable db3 = (GradientDrawable) b3.getBackground();
        db3.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_yellow, null));

        Button b4 = findViewById(R.id.aboutb4);
        GradientDrawable db4 = (GradientDrawable) b4.getBackground();
        db4.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_black, null));

        Button b5 = findViewById(R.id.exitb5);
        GradientDrawable db5 = (GradientDrawable) b5.getBackground();
        db5.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_red, null));

        Button bmulai = findViewById(R.id.bmulai);
        GradientDrawable gdmulai = (GradientDrawable) bmulai.getBackground();
        gdmulai.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue_light, null));

        Button bkembali = findViewById(R.id.tutuplayoutlevel);
        GradientDrawable gdkembali = (GradientDrawable) bkembali.getBackground();
        gdkembali.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue_light, null));

        TextView judul = findViewById(R.id.judul);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        judul.startAnimation(scale);
        GradientDrawable gdjudul = (GradientDrawable) judul.getBackground();
        gdjudul.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue, null));
        ObjectAnimator animY = ObjectAnimator.ofFloat(judul, "translationY",
                -10f, 10f);
        animY.setDuration(1000);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        animY.setRepeatMode(ValueAnimator.REVERSE);
        animY.start();

        NumberPicker np = findViewById(R.id.numberpicker);
        np.setMinValue(1);
        np.setMaxValue(20);

        np.setOnValueChangedListener(onValueChangeListener);

    }

    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            level = newVal;
        }
    };

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

    public void intent1(View v) {

        Button b1 = findViewById(R.id.startb1);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(b1, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);*/
                RelativeLayout rl = findViewById(R.id.layoutpilihlevel);
                rl.setVisibility(View.VISIBLE);
                kondisi = true;
            }
        }, 500);
    }

    public void intent2(View v) {

        Button b2 = findViewById(R.id.scoreb2);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(b2, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(getApplicationContext(), NilaiTertinggi.class);
                startActivity(intent2);
            }
        }, 500);
    }

    public void intent3(View v) {

        Button b3 = findViewById(R.id.howtob3);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(b3, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent3 = new Intent(getApplicationContext(), CaraBermain.class);
                startActivity(intent3);
            }
        }, 500);
    }

    public void intent4(View v) {

        Button b4 = findViewById(R.id.aboutb4);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(b4, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent4 = new Intent(getApplicationContext(), Tentang.class);
                startActivity(intent4);
            }
        }, 500);
    }

    public void intent5(View v) {

        Button b5 = findViewById(R.id.exitb5);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(b5, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        new AlertDialog.Builder(this)
                .setMessage("apakah Kamu Ingin Keluar?")
                .setCancelable(true)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainMenu.this.finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void Mulai(View view) {
        Button bmulai = findViewById(R.id.bmulai);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(bmulai, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout rl = findViewById(R.id.layoutpilihlevel);
                rl.setVisibility(View.GONE);
                kondisi = false;
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("level", level);
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        }, 500);
    }

    public void tutuplevel(View v) {

        Button bkembali = findViewById(R.id.tutuplayoutlevel);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.button);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        ObjectAnimator animY = ObjectAnimator.ofFloat(bkembali, "translationY",
                -10f, 0f);
        animY.setDuration(1000);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout rl = findViewById(R.id.layoutpilihlevel);
                rl.setVisibility(View.GONE);
                kondisi = false;
            }
        }, 500);
    }

    @Override
    public void onBackPressed() {
        if (kondisi) {
            RelativeLayout rl = findViewById(R.id.layoutpilihlevel);
            rl.setVisibility(View.GONE);
            kondisi = false;
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("apakah Kamu Ingin Keluar?")
                    .setCancelable(true)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainMenu.this.finish();
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        }
    }
}
