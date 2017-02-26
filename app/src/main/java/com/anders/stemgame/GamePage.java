package com.anders.stemgame;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

public class GamePage extends AppCompatActivity {


    private static final String TAG = "GamePage";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    TextToSpeech mTextAloud;
    Boolean easy, medium, difficult;
    String mTopic;
    Integer difficulty;
    Integer mQuestionSize;
    Boolean mBeginning;
    ArrayList<QuestionObject> mQuestionArray;
    DBhelper mDB;
    Context mContext;
    TextView mTime, mPointsText;
    String text;
    SpeechRecognizer speech;
    Integer mTimeLeft, mIndex, mPoints, mListLoopNum;
    ArrayList<QuestionObject> mResizedQList;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        mContext = GamePage.this;
        mBeginning = false;
        mDB = DBhelper.getmInstance(mContext);
        mTime = (TextView) findViewById(R.id.time);
        mPointsText = (TextView) findViewById(R.id.points);

        mPoints = 0;
        mPointsText.setText("0");

        Bundle bundle = getIntent().getExtras();
        easy = bundle.getBoolean("easy");
        medium = bundle.getBoolean("medium");
        difficult = bundle.getBoolean("difficult");
        mQuestionSize = bundle.getInt("size");
        if (bundle.getBoolean("math")) {
            mTopic = "math";
        } else if (bundle.getBoolean("tech")) {
            mTopic = "tech";
        } else {
            mTopic = "science";
        }
        Log.i(TAG, "onCreate: mTopic " + mTopic);

        if (easy) {
            difficulty = 1;
        } else if (medium) {
            difficulty = 2;
        } else {
            difficulty = 3;
        }

        mQuestionArray = new ArrayList<>();

        switch (mTopic) {

            case "math":
                mQuestionArray = mDB.getQuestionsMath(difficulty);
                break;
            case "tech":
                mQuestionArray = mDB.getQuestionsTech(difficulty);
                break;
            case "science":
                mQuestionArray = mDB.getQuestionsScience(difficulty);
                break;
            default:
                Log.i(TAG, "onActivityResult: error in mTopic");
                break;

        }

        mResizedQList = randQAndGetSnippet(mQuestionArray, mQuestionSize);
        mListLoopNum = 0;
        resizedQlistLoop();


    }

    public void resizedQlistLoop() {
        if (mListLoopNum < mResizedQList.size()) {

            mTextAloud = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {


                    mTextAloud.setLanguage(Locale.UK);
                    mTextAloud.setSpeechRate((float) 0.6);
                    Log.i(TAG, "onCreate: k index " + mListLoopNum);
                    mTime.setText(" " + 30 + " s");
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            mIndex = mListLoopNum;
                            if (!mBeginning) {
                                mTextAloud.speak("Let's begin", TextToSpeech.QUEUE_ADD, null, null);
                                mBeginning = true;
                            }

                            if (mListLoopNum < mResizedQList.size()) {
                                mTextAloud.speak("The Question is " + mResizedQList.get(mListLoopNum).getmQuestion(), TextToSpeech.QUEUE_ADD, null, null);
                                mTextAloud.speak("Is the answer " + mResizedQList.get(mListLoopNum).getmAnswer1(), TextToSpeech.QUEUE_ADD, null, null);
                                mTextAloud.speak("or " + mResizedQList.get(mListLoopNum).getmAnswer2(), TextToSpeech.QUEUE_ADD, null, null);
                                mTextAloud.speak("or " + mResizedQList.get(mListLoopNum).getmAnswer3(), TextToSpeech.QUEUE_ADD, null, null);
                                mTextAloud.speak("or " + mResizedQList.get(mListLoopNum).getmAnswer4(), TextToSpeech.QUEUE_ADD, null, null);
                                mTextAloud.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                                    @Override
                                    public void onStart(String s) {

                                    }

                                    @Override
                                    public void onDone(String s) {
                                        Log.i(TAG, "onDone: ");
                                    }

                                    @Override
                                    public void onError(String s) {

                                    }
                                });

                                text = mResizedQList.get(mListLoopNum).getmQuestion();
                                try {
                                    sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                setListener();
                            }
                        }
                    };
                    thread.start();
                }
            });
        }
    }

    public void setListener() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTimeLeft = 30;
                    if (thread != null && thread.isAlive()) {
                        thread.interrupt();
                        thread = null;
                        Log.i(TAG, "onActivityResultthread: if");
                    } else {
                        Log.i(TAG, "onActivityResultthread: else");
                    }
                    thread = new Thread() {
                        @Override
                        public void run() {
                            while (!interrupted()) {
                                for (int i = 30; i > 0; i--) {
                                    try {
                                        Log.i(TAG, "run: index " + i);
                                        sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (mTimeLeft >= 0) {
                                                    Log.i(TAG, "run: time " + mTimeLeft);
                                                    mTime.setText(" " + mTimeLeft + " s");
                                                }
                                            }
                                        });
                                        mTimeLeft = mTimeLeft - 1;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    };
                    thread.start();


                    if (mTimeLeft > 0) {
                        Log.i(TAG, "onActivityResult: mListLoopNum " + mListLoopNum);
//                        Log.i(TAG, "onActivityResult: Integer.parseInt(result.get(0)) " + Integer.parseInt(result.get(0)));
                        int num = 0;

                        try {
                            Log.i(TAG, "onActivityResult: Integer.parseInt(mResizedQList.get( ");
                            num = Integer.parseInt(mResizedQList.get(mListLoopNum).getmRealAnswer());

                            if ()
                            Log.i(TAG, "onActivityResult: Integer.parseInt(mResizedQList.get(mListLoopNum).getmRealAnswer()) " + Integer.parseInt(mResizedQList.get(mListLoopNum).getmRealAnswer()));
                            if ((result.get(0).substring(0, 1).equals(mResizedQList.get(mListLoopNum).getmRealAnswer().substring(0, 1)) &&
                                    result.get(0).substring(result.get(0).length() - 1, result.get(0).length()).equals(mResizedQList.get(mListLoopNum).getmRealAnswer().substring(
                                            mResizedQList.get(mListLoopNum).getmRealAnswer().length() - 1, mResizedQList.get(mListLoopNum).getmRealAnswer().length()))
                                    || Integer.parseInt(result.get(0)) == num || result.get(0).equals("Ford") || result.get(0).equals("for"))) {
                                mPoints += 1;
                                mPointsText.setText(String.valueOf(mPoints));
                                mTextAloud.speak("Correct, the answer is " + mResizedQList.get(mListLoopNum).getmRealAnswer(), TextToSpeech.QUEUE_FLUSH, null, null);
                            }
                        } catch (NumberFormatException e) {
                            Log.i(TAG, "onActivityResult: " + result.get(0));
                            mTextAloud.speak("Sorry that is incorrect, the answer is " + mResizedQList.get(mListLoopNum).getmRealAnswer(), TextToSpeech.QUEUE_FLUSH, null, null);
                        }


                        if (mListLoopNum < mResizedQList.size()) {
                            resizedQlistLoop();
                            mTimeLeft = 30;
                            mTime.setText("30");
                            thread.interrupt();
                            mListLoopNum += 1;
                        } else {
                            thread.interrupt();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTime.setText("Finished");
                                }
                            });
                        }
                    } else {
                        mTextAloud.speak("You ran out of time, the answer is " + mResizedQList.get(mListLoopNum).getmRealAnswer(), TextToSpeech.QUEUE_FLUSH, null, null);
                        if (mListLoopNum < mResizedQList.size()) {
                            resizedQlistLoop();
                            mTimeLeft = 30;
                            mTime.setText("30");
                            mListLoopNum += 1;
                            thread.interrupt();
                        } else {
                            thread.interrupt();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTime.setText("Finished");
                                }
                            });
                        }
                        break;
                    }
                }
            }
            break;
        }


    }


    public ArrayList<QuestionObject> randQAndGetSnippet(ArrayList<QuestionObject> array, int size) {

        HashSet<QuestionObject> set = new HashSet<QuestionObject>();
        int randomNum = 0;
        Random random = new Random();
        if (array.size() >= 5) {
            for (int i = 0; i < size; i++) {
                randomNum = random.nextInt(array.size() - 1);
                set.add(array.get(randomNum));
            }
        }

        ArrayList<QuestionObject> arrayNew = new ArrayList<>(set);
        return arrayNew;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTextAloud != null && mTextAloud.isSpeaking()) {
            mTextAloud.stop();
        }
    }
}
