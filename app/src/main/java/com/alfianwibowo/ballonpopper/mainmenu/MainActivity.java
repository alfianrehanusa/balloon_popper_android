package com.alfianwibowo.ballonpopper.mainmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfianwibowo.ballonpopper.Balloon;
import com.alfianwibowo.ballonpopper.NameHighscore;
import com.alfianwibowo.ballonpopper.R;
import com.alfianwibowo.ballonpopper.Utils.SoundHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
    implements Balloon.BalloonListener {

    private static final int MIN_ANIMATION_DELAY = 500;
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int MIN_ANIMATION_DURATION = 1000;
    private static final int MAX_ANIMATION_DURATION = 8000;
    private static final int NUMBER_OF_PINS = 5;
    private static final int BALLOONS_PER_LEVEL = 20;

    private int mScreenWidth, mScreenHeight, mLevel, mScore, mPinsUsed, mBalloonsPopped;
    private boolean mPlaying, mGameStopped = true;
    private List<ImageView> mPinImages = new ArrayList<>();
    private List<Balloon> mBalloons = new ArrayList<>();
    private Button mGoButton;
    private ViewGroup mContentView;
    private SoundHelper mSoundHelper;
    TextView mScoreDisplay, mLevelDisplay;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.drawable.modern_background);

        mContentView = findViewById(R.id.activity_main);
        setToFullScreen();

        ViewTreeObserver viewTreeObserver = mContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mScreenWidth = mContentView.getWidth();
                    mScreenHeight = mContentView.getHeight();
                }
            });
        }

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToFullScreen();
            }
        });

        mScoreDisplay = findViewById(R.id.score_display);
        mLevelDisplay = findViewById(R.id.level_display);
        mPinImages.add((ImageView) findViewById(R.id.pushpin1));
        mPinImages.add((ImageView) findViewById(R.id.pushpin2));
        mPinImages.add((ImageView) findViewById(R.id.pushpin3));
        mPinImages.add((ImageView) findViewById(R.id.pushpin4));
        mPinImages.add((ImageView) findViewById(R.id.pushpin5));

        mGoButton = findViewById(R.id.go_button);
        GradientDrawable db1 = (GradientDrawable) mGoButton.getBackground();
        db1.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_blue_light, null));
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        mGoButton.startAnimation(scale);

        updateDisplay();

        mSoundHelper = new SoundHelper(this);
        mSoundHelper.prepareMusicPlayer(this);

    }

    private void setToFullScreen() {
        findViewById(R.id.activity_main);
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

    private void startGame() {
        setToFullScreen();
        mScore = 0;

        intent = getIntent();
        bundle = intent.getExtras();
        assert bundle != null;
        mLevel = bundle.getInt("level");
        mLevel--;

        //mLevel= 0;
        mPinsUsed = 0;
        for (ImageView pin :
                mPinImages) {
            pin.setImageResource(R.drawable.pin);
        }
        mGameStopped = false;
        startLevel();
        mSoundHelper.playMusic();
    }

    private void startLevel() {
        mLevel++;
        updateDisplay();
        BalloonLauncher launcher = new BalloonLauncher();
        launcher.execute(mLevel);
        mPlaying = true;
        mBalloonsPopped = 0;
        mGoButton.setText(R.string.Stop_Game);
        mGoButton.clearAnimation();
        mGoButton.setVisibility(View.INVISIBLE);
    }

    private void finishLevel(){
        Toast.makeText(this, String.format(getString(R.string.Finish_Level), mLevel),
                Toast.LENGTH_SHORT).show();
        mPlaying = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                mGoButton.startAnimation(scale);
                mGoButton.setVisibility(View.VISIBLE);
                mGoButton.setText(String.format(getString(R.string.Start_Level), mLevel + 1));
            }
        }, 500);
    }

    public void goButtonClickHandler(View view) {

        if (mPlaying) {
            gameOver();
        } else if (mGameStopped) {
            startGame();
        } else {
            startLevel();
        }
    }

    @Override
    public void popBalloon(Balloon balloon, boolean userTouch) {

        mBalloonsPopped++;
        mSoundHelper.playSound();
        mContentView.removeView(balloon);
        mBalloons.remove(balloon);

        if (userTouch){
            mScore++;
        } else {
            mPinsUsed++;
            if(mPinsUsed <= mPinImages.size()) {
                mPinImages.get(mPinsUsed - 1)
                        .setImageResource(R.drawable.pin_off);
            }
            if(mPinsUsed == NUMBER_OF_PINS) {
                gameOver();
                return;
            } else {
                Toast.makeText(this, "Balon Lolos!", Toast.LENGTH_SHORT).show();
            }
        }
        updateDisplay();

        if(mBalloonsPopped == BALLOONS_PER_LEVEL) {
            finishLevel();
        }

    }

    private void gameOver() {
        mSoundHelper.pauseMusic();

        for (Balloon balloon :
                mBalloons) {
            mContentView.removeView(balloon);
            balloon.setPopped(true);
        }
        mBalloons.clear();
        mPlaying = false;
        mGameStopped = true;
        mGoButton.setText(R.string.Start_Game);

        if(mScore==0){
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            menuNameHighScore();
        }

    }

    public void menuNameHighScore() {

        Intent actNameHighscore = new Intent(this, NameHighscore.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", mScore);
        bundle.putInt("level", mLevel);
        actNameHighscore.putExtras(bundle);
        startActivity(actNameHighscore);

    }

    private void updateDisplay() {
        mScoreDisplay.setText(String.valueOf(mScore));
        mLevelDisplay.setText(String.valueOf(mLevel));
    }

    private class BalloonLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];
            int maxDelay = Math.max(MIN_ANIMATION_DELAY,
                    (MAX_ANIMATION_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;

            int balloonsLaunched = 0;
            while (mPlaying && balloonsLaunched < BALLOONS_PER_LEVEL) {

//              Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                int xPosition = random.nextInt(mScreenWidth - 200);
                publishProgress(xPosition);
                balloonsLaunched++;

//              Wait a random number of milliseconds before looping
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int xPosition = values[0];
            launchBalloon(xPosition);
        }

    }

    private void launchBalloon(int x) {

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(255), random.nextInt(255),
                random.nextInt(255));
        Balloon balloon = new Balloon(this, color, 150);
        mBalloons.add(balloon);

//      Set balloon vertical position and dimensions, add to container
        balloon.setX(x);
        balloon.setY(mScreenHeight + balloon.getHeight());
        mContentView.addView(balloon);

//      Let 'er fly
        int duration = Math.max(MIN_ANIMATION_DURATION, MAX_ANIMATION_DURATION - (mLevel * 1000));
        if(mLevel >= 9) {
            int duration2 = Math.max(50, 1000 - ((mLevel - 8) * 20));
            balloon.releaseBalloon(mScreenHeight, duration2);
        }
        else {
            balloon.releaseBalloon(mScreenHeight, duration);
        }

    }

    @Override
    public void onBackPressed() {
        if(!mPlaying && mScore==0) {
            super.onBackPressed();
        }
        else {
            new AlertDialog.Builder(this)
                    .setMessage("apakah Kamu Ingin Berhenti?")
                    .setCancelable(true)
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gameOver();
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        }
    }
}
