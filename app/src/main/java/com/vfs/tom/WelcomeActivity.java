package com.vfs.tom;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vfs.tom.adapters.TutorialPagerAdapter;
import com.vfs.tom.managers.PersistenceManager;
import com.vfs.tom.model.Player;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnNext, btnSkip;
    private ImageView firstCircle, secondCircle,thirdCircle, firstCircleInactive, secondCircleInactive, thirdCircleInactive;
    private TutorialPagerAdapter tutorialPagerAdapter;
    private PersistenceManager persistenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        persistenceManager = PersistenceManager.getInstance(getApplicationContext());
        boolean shouldshow = true;
        try{
            shouldshow = persistenceManager.retriveShouldShowTutorial();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(shouldshow){
            initView();
        }else{
            gotoMain();
        }

    }

    private void initView() {
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        firstCircle= findViewById(R.id.first_circle);
        secondCircle=findViewById(R.id.second_circle);
        thirdCircle=findViewById(R.id.third_circle);
        firstCircleInactive= findViewById(R.id.first_circle_inactive);
        secondCircleInactive=findViewById(R.id.second_circle_inactive);
        thirdCircleInactive=findViewById(R.id.third_circle_inactive);
        int[] layouts = new int[]{
                R.layout.tutorial_page_1,
                R.layout.tutorial_page_2,
                R.layout.tutorial_page_3
        };
        tutorialPagerAdapter = new TutorialPagerAdapter(this, layouts);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(tutorialPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                Log.e("es",String.valueOf(i));
                switch (i){
                    case 0:
                        btnNext.setText("NEXT");
                        btnSkip.setVisibility(View.VISIBLE);
                        firstCircle.setVisibility(View.VISIBLE);
                        firstCircleInactive.setVisibility(View.GONE);
                        secondCircle.setVisibility(View.GONE);
                        secondCircleInactive.setVisibility(View.VISIBLE);
                        thirdCircle.setVisibility(View.GONE);
                        thirdCircleInactive.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        btnNext.setText("NEXT");
                        btnSkip.setVisibility(View.VISIBLE);
                        firstCircle.setVisibility(View.GONE);
                        firstCircleInactive.setVisibility(View.VISIBLE);
                        secondCircle.setVisibility(View.VISIBLE);
                        secondCircleInactive.setVisibility(View.GONE);
                        thirdCircle.setVisibility(View.GONE);
                        thirdCircleInactive.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        btnNext.setText("CLOSE");
                        btnSkip.setVisibility(View.GONE);
                        firstCircle.setVisibility(View.GONE);
                        firstCircleInactive.setVisibility(View.VISIBLE);
                        secondCircle.setVisibility(View.GONE);
                        secondCircleInactive.setVisibility(View.VISIBLE);
                        thirdCircle.setVisibility(View.VISIBLE);
                        thirdCircleInactive.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()<2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }else{
                    gotoMain();
                }

            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });

    }

    private void gotoMain(){
        persistenceManager.persistShouldSHowTutorial(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

}
