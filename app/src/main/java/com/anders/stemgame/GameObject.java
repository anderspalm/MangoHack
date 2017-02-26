package com.anders.stemgame;

import java.util.ArrayList;

/**
 * Created by anders on 2/25/2017.
 */

public class GameObject {

    ArrayList<QuestionObject> arrayList;

    public GameObject(ArrayList<QuestionObject> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<QuestionObject> getArrayList() {
        if (arrayList != null) {
            return arrayList;
        } else {
            arrayList = new ArrayList<>();
            return arrayList;
        }
    }

    public void setArrayList(ArrayList<QuestionObject> arrayList) {
        this.arrayList = arrayList;
    }
}
