package com.anders.stemgame;

/**
 * Created by anders on 2/25/2017.
 */
public class QuestionObject {
    String mAnswer1, mAnswer2, mAnswer3, mAnswer4, mQuestion, mRealAnswer;
    Integer mDifficulty;

    public QuestionObject(String mAnswer1, String mAnswer2, String mAnswer3, String mAnswer4, String mQuestion, Integer mDifficulty, String realAns) {
        this.mAnswer1 = mAnswer1;
        this.mAnswer2 = mAnswer2;
        this.mAnswer3 = mAnswer3;
        this.mAnswer4 = mAnswer4;
        this.mQuestion = mQuestion;
        this.mDifficulty = mDifficulty;
        mRealAnswer = realAns;
    }

    public String getmRealAnswer() {
        return mRealAnswer;
    }

    public void setmRealAnswer(String mRealAnswer) {
        this.mRealAnswer = mRealAnswer;
    }

    public String getmAnswer1() {
        return mAnswer1;
    }

    public void setmAnswer1(String mAnswer1) {
        this.mAnswer1 = mAnswer1;
    }

    public String getmAnswer2() {
        return mAnswer2;
    }

    public void setmAnswer2(String mAnswer2) {
        this.mAnswer2 = mAnswer2;
    }

    public String getmAnswer3() {
        return mAnswer3;
    }

    public void setmAnswer3(String mAnswer3) {
        this.mAnswer3 = mAnswer3;
    }

    public String getmAnswer4() {
        return mAnswer4;
    }

    public void setmAnswer4(String mAnswer4) {
        this.mAnswer4 = mAnswer4;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public Integer getmDifficulty() {
        return mDifficulty;
    }

    public void setmDifficulty(Integer mDifficulty) {
        this.mDifficulty = mDifficulty;
    }
}
