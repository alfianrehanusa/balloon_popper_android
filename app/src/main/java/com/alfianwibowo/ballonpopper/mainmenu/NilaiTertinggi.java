package com.alfianwibowo.ballonpopper.mainmenu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Handler;

import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alfianwibowo.ballonpopper.AdapterItems;
import com.alfianwibowo.ballonpopper.DBManager;
import com.alfianwibowo.ballonpopper.R;

import java.util.ArrayList;

public class NilaiTertinggi extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private ViewGroup mContentView;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai_tertinggi);

        mContentView = findViewById(R.id.NilaiTertinggi);

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToFullScreen();
            }
        });

        dbManager = new DBManager(this);

        loadtableHS();

        ListView lv1 = findViewById(R.id.lvhighscore);
        GradientDrawable gd1 = (GradientDrawable) lv1.getBackground();
        gd1.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_green_light, null));

        Button m2bk = findViewById(R.id.m2bk);
        GradientDrawable gd2 = (GradientDrawable) m2bk.getBackground();
        gd2.setColor(ResourcesCompat.getColor(getResources(),
                R.color.google_green_light, null));
    }

    ArrayList<AdapterItems> listnewsData = new ArrayList<>();
    MyCustomAdapter myadapter;

    private void loadtableHS() {
        listnewsData.clear();
        String[] projection = {"Nama", "Nilai"};
        Cursor cursor = dbManager.Query(projection,null, null, DBManager.Nilai);
        int a = 0;
        if (cursor.moveToFirst()) {
            do {
                if(a < 5){
                    a++;
                    listnewsData.add(new AdapterItems(cursor.getString(cursor.getColumnIndex(DBManager.Nama)),
                            cursor.getString(cursor.getColumnIndex(DBManager.Nilai))));
                }
            } while (cursor.moveToNext());
        }

        myadapter=new MyCustomAdapter(listnewsData);
        ListView lsNews = findViewById(R.id.lvhighscore);
        lsNews.setAdapter(myadapter);
    }

    public void Kembali(View v) {

        Button b1 = findViewById(R.id.m2bk);

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
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, 500);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }

    private class MyCustomAdapter extends BaseAdapter {
        ArrayList<AdapterItems> listnewsDataAdpater ;

        MyCustomAdapter(ArrayList<AdapterItems> listnewsDataAdpater) {
            this.listnewsDataAdpater=listnewsDataAdpater;
        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.layout_ticket,null);

            final AdapterItems s = listnewsDataAdpater.get(position);

            TextView tvhnama = myView.findViewById(R.id.tvhnama);
            tvhnama.setText(s.Nama);

            TextView tvhnilai = myView.findViewById(R.id.tvhnilai);
            tvhnilai.setText(s.Nilai);

            return myView;
        }

    }

}
