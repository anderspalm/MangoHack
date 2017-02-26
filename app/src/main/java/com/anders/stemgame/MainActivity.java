package com.anders.stemgame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    DBhelper mDBhelper;
    Context mContect;
    Button mMButton, mDButton, mEButton;
    Button mMath, mTech, mScience;
    TextView mQuizSizeSmall, mQuizSizeMedium, getmQuizSizeLarge;
    ArrayList<QuestionObject> array;
    Boolean mMathBool, mScienceBool, mTechBool;
    Boolean mQuestionSize;
    Integer mQuestionSizeNum;

    Integer mScore;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContect = MainActivity.this;
        mDBhelper = DBhelper.getmInstance(mContect);

        mMathBool = false;
        mTechBool = false;
        mScienceBool = false;



        mMath = (Button) findViewById(R.id.maths);
        mScience = (Button) findViewById(R.id.science);
        mTech = (Button) findViewById(R.id.technology);

        mMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mMathBool) {
                    mMathBool = true;
                } else {
                    mMathBool = false;
                }
            }
        });
        mTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mTechBool) {
                    mTechBool = true;
                } else {
                    mTechBool = false;
                }
            }
        });
        mScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mScienceBool) {
                    mScienceBool = true;
                } else {
                    mScienceBool = false;
                }
            }
        });

        mQuestionSize = false;

        mQuizSizeSmall = (TextView) findViewById(R.id.smallSize);
        mQuizSizeMedium = (TextView) findViewById(R.id.normalSize);
        getmQuizSizeLarge = (TextView) findViewById(R.id.largeSize);

        mQuizSizeSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionSize = true;
                mQuestionSizeNum = 5;
            }
        });

        mQuizSizeMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionSize = true;
                mQuestionSizeNum = 10;
            }
        });


        mQuizSizeMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionSize = true;
                mQuestionSizeNum = 15;
            }
        });


        mMButton = (Button) findViewById(R.id.medium);
        mDButton = (Button) findViewById(R.id.difficult);
        mEButton = (Button) findViewById(R.id.easy);

        mEButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMathBool || mScienceBool || mTechBool) {
                    if (mQuestionSize) {
                        Intent intent = new Intent(mContect, GamePage.class);
                        intent.putExtra("easy", true);
                        intent.putExtra("size",mQuestionSizeNum);
                        if (mMathBool){
                            intent.putExtra("math",true);
                        } else if (mTechBool) {
                            intent.putExtra("tech",true);
                        } else {
                            intent.putExtra("science",true);
                        }

                        startActivity(intent);
                    }
                }
            }
        });

        mMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMathBool || mScienceBool || mTechBool) {
                    if (mQuestionSize) {
                        Intent intent = new Intent(mContect, GamePage.class);
                        intent.putExtra("medium", true);
                        intent.putExtra("size",mQuestionSizeNum);
                        if (mMathBool){
                            intent.putExtra("math",true);
                        } else if (mTechBool) {
                            intent.putExtra("tech",true);
                        } else {
                            intent.putExtra("science",true);
                        }
                        startActivity(intent);
                    }
                }
            }
        });

        mDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMathBool || mScienceBool || mTechBool) {
                    if (mQuestionSize) {
                        Intent intent = new Intent(mContect, GamePage.class);
                        intent.putExtra("hard", true);
                        intent.putExtra("size",mQuestionSizeNum);
                        if (mMathBool){
                            intent.putExtra("math",true);
                        } else if (mTechBool) {
                            intent.putExtra("tech",true);
                        } else {
                            intent.putExtra("science",true);
                        }
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
