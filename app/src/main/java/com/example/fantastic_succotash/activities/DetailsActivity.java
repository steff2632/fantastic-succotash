package com.example.fantastic_succotash.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.fantastic_succotash.R;
import com.example.fantastic_succotash.data.News;
import com.example.fantastic_succotash.views.SquareImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by stefanmay on 27/10/2016.
 */

public class DetailsActivity extends AppCompatActivity {

    public static News newsItem;
    private float DENSITY_SCALE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        final SquareImageView image = (SquareImageView) findViewById(R.id.image);
        TextView txtDescription = (TextView) findViewById(R.id.txt_desc);
        TextView txtTitle = (TextView) findViewById(R.id.txt_title);

        Resources resources = getResources();
        DENSITY_SCALE = resources.getDisplayMetrics().density;

        if(newsItem == null)
            finish();

        Picasso.with(this)
                .load(newsItem.image)
                .error(R.drawable.touchnote)
                .into(image);

        txtDescription.setText(newsItem.description);
        txtTitle.setText(newsItem.title);

        AppCompatSeekBar seekBar = (AppCompatSeekBar) findViewById(R.id.slider);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                image.setCornerRadius(((i*2) * DENSITY_SCALE));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
