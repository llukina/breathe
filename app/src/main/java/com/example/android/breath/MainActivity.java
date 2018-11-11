package com.example.android.breath;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.breath.utils.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btnStart;
    private TextView breathsTxt, timeTxt, sessionTxt, guideTxt;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
        breathsTxt = (TextView) findViewById(R.id.breaths_taken_txt);
        timeTxt = (TextView) findViewById(R.id.last_breath_txt);
        sessionTxt = (TextView) findViewById(R.id.today_minutes_txt);
        guideTxt = (TextView) findViewById(R.id.guide_txt);
        prefs = new Prefs(this);

        startIntroAnimation();

        sessionTxt.setText(MessageFormat.format("{0} min total", prefs.getSessions()));
        breathsTxt.setText(MessageFormat.format("{0} Breaths", prefs.getBreaths()));
        timeTxt.setText(prefs.getDate());

        btnStart = (Button) findViewById(R.id.start_button);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();

            }
        });

    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideTxt)
                .scale(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .duration(1500)
                .start();

    }

    private void startAnimation() {

        ViewAnimator
                .animate(imageView)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale...Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.1f, 1.5f, 0.1f)
                .rotation(360)
                .repeatCount(6)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good Job!");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long l) {
                                //put code to show ticking

                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
                .start();
    }

}
