package com.anders.stemgame;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by anders on 1/27/2017.
 */

public class DBhelper extends SQLiteOpenHelper {

    private static final String TAG = "DBhelper";

    public static DBhelper mInstance;
    private int id;

    public static final String DATABASE_NAME = "StemGame.db";
    public static final Integer DATABASE_VERSION = 6;

    public static final String SCI_QUESTION_TABLE = "sci_question_table";

    public static final String SCI_QUESTION = "sci_question";
    public static final String SCI_ANSWER1 = "sci_answer1";
    public static final String SCI_ANSWER2 = "sci_answer2";
    public static final String SCI_ANSWER3 = "sci_answer3";
    public static final String SCI_ANSWER4 = "sci_answer4";
    public static final String SCI_REAL_ANSWER = "sci_real_answer";
    public static final String SCI_DIFFICULTY = "sci_difficulty";

    public static final String TECH_QUESTION_TABLE = "tech_question_table";

    public static final String TECH_QUESTION = "tech_question";
    public static final String TECH_ANSWER1 = "tech_answer1";
    public static final String TECH_ANSWER2 = "tech_answer2";
    public static final String TECH_ANSWER3 = "tech_answer3";
    public static final String TECH_ANSWER4 = "tech_answer4";
    public static final String TECH_REAL_ANSWER = "tech_real_answer";
    public static final String TECH_DIFFICULTY = "tech_difficulty";

    public static final String MATH_QUESTION_TABLE = "math_question_table";

    public static final String MATH_QUESTION = "math_question";
    public static final String MATH_ANSWER1 = "math_answer1";
    public static final String MATH_ANSWER2 = "math_answer2";
    public static final String MATH_ANSWER3 = "math_answer3";
    public static final String MATH_ANSWER4 = "math_answer4";
    public static final String MATH_REAL_ANSWER = "math_real_answer";
    public static final String MATH_DIFFICULTY = "math_difficulty";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBhelper getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBhelper(context);
        }
        return mInstance;
    }


//    make join table for getting unique id for each request for the alarm

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(
                "CREATE TABLE " + MATH_QUESTION_TABLE + " ("
                        + MATH_QUESTION + " TEXT, "
                        + MATH_ANSWER1 + " TEXT, "
                        + MATH_ANSWER2 + " TEXT, "
                        + MATH_ANSWER3 + " TEXT, "
                        + MATH_REAL_ANSWER + " TEXT, "
                        + MATH_ANSWER4 + " TEXT, "
                        + MATH_DIFFICULTY + " INTEGER )");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + TECH_QUESTION_TABLE + " ("
                        + TECH_QUESTION + " TEXT, "
                        + TECH_ANSWER1 + " TEXT, "
                        + TECH_ANSWER2 + " TEXT, "
                        + TECH_ANSWER3 + " TEXT, "
                        + TECH_REAL_ANSWER + " TEXT, "
                        + TECH_ANSWER4 + " TEXT, "
                        + TECH_DIFFICULTY + " INTEGER )");

        sqLiteDatabase.execSQL(
                "CREATE TABLE " + SCI_QUESTION_TABLE + " ("
                        + SCI_QUESTION + " TEXT, "
                        + SCI_ANSWER1 + " TEXT, "
                        + SCI_ANSWER2 + " TEXT, "
                        + SCI_ANSWER3 + " TEXT, "
                        + SCI_REAL_ANSWER + " TEXT, "
                        + SCI_ANSWER4 + " TEXT, "
                        + SCI_DIFFICULTY + " INTEGER )");

        setData(sqLiteDatabase);
        addMath(sqLiteDatabase);
        addTech(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MATH_QUESTION_TABLE);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SCI_QUESTION_TABLE);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TECH_QUESTION_TABLE);
        onCreate(sqLiteDatabase);

    }


    public ArrayList<QuestionObject> getQuestionsTech(int difficulty) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<QuestionObject> arrayList = new ArrayList<>();
        String question, ans1, ans2, ans3, ans4, real;
        int diff = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TECH_QUESTION_TABLE + " WHERE " + TECH_DIFFICULTY + " = " + difficulty, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                question = cursor.getString(cursor.getColumnIndex(TECH_QUESTION));
                ans1 = cursor.getString(cursor.getColumnIndex(TECH_ANSWER1));
                ans2 = cursor.getString(cursor.getColumnIndex(TECH_ANSWER2));
                ans3 = cursor.getString(cursor.getColumnIndex(TECH_ANSWER3));
                ans4 = cursor.getString(cursor.getColumnIndex(TECH_ANSWER4));
                diff = cursor.getInt(cursor.getColumnIndex(TECH_DIFFICULTY));
                real = cursor.getString(cursor.getColumnIndex(TECH_REAL_ANSWER));
                arrayList.add(new QuestionObject(ans1, ans2, ans3, ans4, question, diff, real));
                cursor.moveToNext();
            }
        } else {
            Log.i(TAG, "getQuestions: ");
        }
        db.close();
        cursor.close();
        return arrayList;
    }


    public ArrayList<QuestionObject> getQuestionsScience(int difficulty) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<QuestionObject> arrayList = new ArrayList<>();
        String question, ans1, ans2, ans3, ans4, real;
        int diff = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + SCI_QUESTION_TABLE + " WHERE " + SCI_DIFFICULTY + " = " + difficulty, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                question = cursor.getString(cursor.getColumnIndex(SCI_QUESTION));
                ans1 = cursor.getString(cursor.getColumnIndex(SCI_ANSWER1));
                ans2 = cursor.getString(cursor.getColumnIndex(SCI_ANSWER2));
                ans3 = cursor.getString(cursor.getColumnIndex(SCI_ANSWER3));
                ans4 = cursor.getString(cursor.getColumnIndex(SCI_ANSWER4));
                diff = cursor.getInt(cursor.getColumnIndex(SCI_DIFFICULTY));
                real = cursor.getString(cursor.getColumnIndex(SCI_REAL_ANSWER));
                arrayList.add(new QuestionObject(ans1, ans2, ans3, ans4, question, diff, real));
                cursor.moveToNext();
            }
        } else {
            Log.i(TAG, "getQuestions: ");
        }
        db.close();
        cursor.close();
        return arrayList;
    }


    public ArrayList<QuestionObject> getQuestionsMath(int difficulty) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<QuestionObject> arrayList = new ArrayList<>();
        String question, ans1, ans2, ans3, ans4, real;
        int diff = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + MATH_QUESTION_TABLE + " WHERE " + MATH_DIFFICULTY + " = " + difficulty, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                question = cursor.getString(cursor.getColumnIndex(MATH_QUESTION));
                ans1 = cursor.getString(cursor.getColumnIndex(MATH_ANSWER1));
                ans2 = cursor.getString(cursor.getColumnIndex(MATH_ANSWER2));
                ans3 = cursor.getString(cursor.getColumnIndex(MATH_ANSWER3));
                ans4 = cursor.getString(cursor.getColumnIndex(MATH_ANSWER4));
                diff = cursor.getInt(cursor.getColumnIndex(MATH_DIFFICULTY));
                real = cursor.getString(cursor.getColumnIndex(MATH_REAL_ANSWER));
                arrayList.add(new QuestionObject(ans1, ans2, ans3, ans4, question, diff, real));
                cursor.moveToNext();
            }
        } else {
            Log.i(TAG, "getQuestions: ");
        }
        db.close();
        cursor.close();
        return arrayList;
    }

    public void setData(SQLiteDatabase database) {
        ArrayList<QuestionObject> arrayList =  new ArrayList<>();

        // public QuestionObject(String mAnswer1, String mAnswer2, String mAnswer3, String mAnswer4, String mQuestion, Integer mDifficulty, String realAns) {
        arrayList.add(new QuestionObject("Brightness","Time","Distance","Weight","What does the light yaer measure",2,"Distance"));
        arrayList.add(new QuestionObject("Nitrogen","Silicon","Uranium","Titanium","Which of these elements is needed to make nuclear enery and nuclaer weapons",2,"Uranium"));
        arrayList.add(new QuestionObject("Mars","Earth","Venus","Mercury","Wchich planet is closet to sun?",1,"mercury"));
        arrayList.add(new QuestionObject("six","five","seven","three","How many continents are there in the world?",1,"seven"));
        arrayList.add(new QuestionObject("one","two","three","four","How many set of teeth does a person get in their lifetime?",1,"one"));
        arrayList.add(new QuestionObject("Germs","Not practicing good hygine","virus","all the above","What causes a person to become sick from an infection",1,"all the above"));
        arrayList.add(new QuestionObject("jupiter","saturn","earth","mercury","which planet has rings",1,"saturn"));
        arrayList.add(new QuestionObject("Kangaroo","hippopotamus","rat","kangaroo and rat","which animal never drinks water in its entire life?",1,"kangaroo and rat"));
        arrayList.add(new QuestionObject("protoplam","cytoplasm","oragelles","none of the above","what is the physical phase of life called?",1,"protoplasm"));
        arrayList.add(new QuestionObject("nerve cell","ovum","the egg of an ostrich","none of the above","the largest cell is?",1,"the egg of an ostrich"));
        arrayList.add(new QuestionObject("liver","skin","spleen","ovum","which is the largest human cell?",1,"ovum"));
        arrayList.add(new QuestionObject("fish","snake","blue whale","crocodile","which is the vertebrate that has two chambered heart ",1,"fish"));
        arrayList.add(new QuestionObject("kiwi","penguine","ostrich","rhea","Which is the smallest flightless bird",1,"kiwi"));
        arrayList.add(new QuestionObject("Mosquitoes","snakes","lizards","cockroach","Saurology is the study of",2,"lizard"));
        arrayList.add(new QuestionObject("Endocrine glands","Pituitary glands","Hypothalamus","Pancreas","Hormones are produced by",2,"Endocrine glands"));
        arrayList.add(new QuestionObject("Thymus gland","Pancreas","Pituitary gland","Pineal gland","Which of the following is the ‘master gland’?",2,"Pituitary gland"));
        arrayList.add(new QuestionObject("Anti Diuretic Hormone","Adhesive Diuretic Hormone","Acidic Diuretic Hormone","Adenosine Double Hormone","What is the full form of ADH",2,"Anti Diuretic Hormone"));
        arrayList.add(new QuestionObject("Alveoli","Artery","Aorta","Vein","Which is the largest blood vessel in the body",2,"Aorta"));
        arrayList.add(new QuestionObject("Helium","Neon","Xenon","Hydrogen","Which one of the following is not an element of Noble gases?",2,"Hydrogen"));
        arrayList.add(new QuestionObject("Water","Oxygen","Water and Oxygen","Carbon dioxide","What is needed for rusting to occur?",2,"Water and Oxygen"));
        arrayList.add(new QuestionObject("Vinegar","Saliva","Ammonia","Acid rain","Which one is an alkaline among the following?",2,"Ammonia"));
        arrayList.add(new QuestionObject("Mass number","Atomic and Mass number","WAtomic number","None of the above","In Periodic table elements are arranged according to their ",3,"Atomic number"));
        arrayList.add(new QuestionObject("A","M","X","Z","Atomic number is represented by which letter?",3,"Z"));
        arrayList.add(new QuestionObject("Physics","Chemistry","Biology","Geology","Which science is sometimes called ‘central science’?",3,"Chemistry"));
        arrayList.add(new QuestionObject("Hydrochloric Acid","Sulphuric Acid","Acetic Acid","Boric Acid","  Which acid is used in the body to help digestion?",3,"Hydrochloric Acid"));
        arrayList.add(new QuestionObject("Steel","Aluminium","Graphite","Glass","Which of the following is not a mixture?",3,"Graphite"));
        arrayList.add(new QuestionObject("Ionization","Oxidation","Reduction","GNone of the above","Rusting is a which reaction?",3,"Oxidation"));
        arrayList.add(new QuestionObject("Oxidation","Electrolysis","Ionization","Reduction","What is the name of that process in which oxygen is removed?",3,"Reduction"));
        arrayList.add(new QuestionObject("Radio waves","visible light waves","sound waves","gravity waves","Which kind of waves are used to make and receive cellphone calls?",3,"Radio waves"));
        arrayList.add(new QuestionObject("frequency","wavelength","velocity or rate of change","amplitude or height","the loudness of a sound is determined by which property of a sound wave?",3,"amplitude or height"));
        Log.i(TAG, "setData: " + arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(SCI_ANSWER1, arrayList.get(i).getmAnswer1());
            contentValues.put(SCI_ANSWER2, arrayList.get(i).getmAnswer2());
            contentValues.put(SCI_ANSWER3, arrayList.get(i).getmAnswer3());
            contentValues.put(SCI_ANSWER4, arrayList.get(i).getmAnswer4());
            contentValues.put(SCI_DIFFICULTY, arrayList.get(i).getmDifficulty());
            contentValues.put(SCI_QUESTION, arrayList.get(i).getmQuestion());
            contentValues.put(SCI_REAL_ANSWER,arrayList.get(i).getmRealAnswer());
            database.insert(SCI_QUESTION_TABLE, null, contentValues);
        }


    }

    public void addTech(SQLiteDatabase database){

        ArrayList<QuestionObject> arrayList = new ArrayList<>();

        // public QuestionObject(String mAnswer1, String mAnswer2, String mAnswer3, String mAnswer4, String mQuestion, Integer mDifficulty, String realAns) {
        arrayList.add(new QuestionObject("Floppy disk","Keyboard","Flash drive","Monitor","A computer input device that is used to enter computer instructions and data into the computer.",2,"Keyboard"));
        arrayList.add(new QuestionObject("Desktop","Modem","Monitor","Speaker","An output device which shows images and text from the computer.",1,"Monitor"));
        arrayList.add(new QuestionObject("Double click on the desktop to reveal hidden items","Use the command 'keyboard'","Click the start menu button and select the program from the menu","It is not possible to start it any other way","How do you open a program such as microsoft word when there are no icons on the desktop?",1,"Click the start menu button and select the program from the menu"));
        arrayList.add(new QuestionObject("Yahoo and google","Apple and ebay","Ebay and google","Amazon and hotmail","Which two sites offer free emails?",3,"Yahoo and google"));
        arrayList.add(new QuestionObject("Johnbrown.com","Johnbrown@com","Johnbrowngmail.com","Johnbrown@gmail.com","Which of the following is a valid email address?",2,"Johnbrown@gmail.com"));
        arrayList.add(new QuestionObject("Http://johnbrown@www.com","Www.johnbrown@yahoo.com","Ww.johnbrown.com","Www.johnbrown.com","Which of these is a valid webpage address?.",2,"Www.johnbrown.com"));
        arrayList.add(new QuestionObject("Go to the START menu button and choose log off","Press the power button on the moitor","Go to the START button menu and choose shut down","Press the power button on the system unit","How do you power off a computer properly?.",1,"Go to the START button menu and choose shut down"));
        arrayList.add(new QuestionObject("Chrome","Altavista","Msn","Netscape","Which one of the following is a search engine?",2,"Altavista"));
        arrayList.add(new QuestionObject("Hotmail","Ms word","Internet","Chrome","Which of the following is a web browser?",1,"Chrome"));
        arrayList.add(new QuestionObject("Type the address in the search bar","Type the address in the address bar","Click on internet explorer on the desktop","Type the address in the title bar","To go to a new website we",1,"Type the address in the search bar"));
        Log.i(TAG, "setData: " + arrayList.size());
        for (int k = 0; k < arrayList.size(); k++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TECH_ANSWER1, arrayList.get(k).getmAnswer1());
            contentValues.put(TECH_ANSWER2, arrayList.get(k).getmAnswer2());
            contentValues.put(TECH_ANSWER3, arrayList.get(k).getmAnswer3());
            contentValues.put(TECH_ANSWER4, arrayList.get(k).getmAnswer4());
            contentValues.put(TECH_DIFFICULTY, arrayList.get(k).getmDifficulty());
            contentValues.put(TECH_QUESTION, arrayList.get(k).getmQuestion());
            contentValues.put(TECH_REAL_ANSWER,arrayList.get(k).getmRealAnswer());
            database.insert(TECH_QUESTION_TABLE, null, contentValues);
        }
    }

    public void addMath(SQLiteDatabase database){
        ArrayList<QuestionObject> arrayList = new ArrayList<>();


        // public QuestionObject(String mAnswer1, String mAnswer2, String mAnswer3, String mAnswer4, String mQuestion, Integer mDifficulty, String realAns) {
        // public QuestionObject(String mAnswer1, String mAnswer2, String mAnswer3, String mAnswer4, String mQuestion, Integer mDifficulty, String realAns) {
        arrayList.add(new QuestionObject("one","two","three","four","what is 2 times 2",1,"4"));
        arrayList.add(new QuestionObject("one","two","three","four","what is the square root of 16", 2,"4"));
        arrayList.add(new QuestionObject("two hundred and twenty six","fourty five","3 hundrend and one","Sixty nine","72 times 3",3,"4"));
        arrayList.add(new QuestionObject("seventeen","ninety one","twenty seven","four","105 minus 78",2,"27"));
        arrayList.add(new QuestionObject("eight","one","nineteen","four","2 cubed",2,"8"));
        arrayList.add(new QuestionObject("one","fifteen","seven","fourteen","what is 47 -62",4,"15"));
        arrayList.add(new QuestionObject("seven","twenty four","two","eight","what is 80 divided by 10",2,"8"));
        arrayList.add(new QuestionObject("ten","thirty two","three","seven","what is 2 to the 5th power",3,"32"));
        arrayList.add(new QuestionObject("one","two","three","four","what is x if 4 minus x is two",4,"2"));
        arrayList.add(new QuestionObject("one","two","three","four","what is x if 39 - x equals 38",4,"2"));
        arrayList.add(new QuestionObject("One Hundred","Seventy one","Ninety two","One Hundrend and twenty four","what is 12 times 12",3,"124"));
        arrayList.add(new QuestionObject("negative four","one","thirty three","fourty seven","6 minus 10 2",3,"-4"));
        arrayList.add(new QuestionObject("six","nine","three","seven","what is 3 factorial",4,"6"));
        arrayList.add(new QuestionObject("Negative one","thrity two","thrity six","one","19 plus 17",1,"36"));
        arrayList.add(new QuestionObject("10","21","8","6","what is 4 times 2",1,"8"));
        arrayList.add(new QuestionObject("52","47","42","49","what is 7 times 7",1,"49"));
        arrayList.add(new QuestionObject("18","16","12","24","what is 48 divided by 3",1,"16"));
        arrayList.add(new QuestionObject("52","47","35","45","what is 9 times 5",1,"45"));
        arrayList.add(new QuestionObject("55","70","65","60","what is 440 divided by 8",2,"55"));
        Log.i(TAG, "setData: " + arrayList.size());
        for (int j = 0; j < arrayList.size(); j++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MATH_ANSWER1, arrayList.get(j).getmAnswer1());
            contentValues.put(MATH_ANSWER2, arrayList.get(j).getmAnswer2());
            contentValues.put(MATH_ANSWER3, arrayList.get(j).getmAnswer3());
            contentValues.put(MATH_ANSWER4, arrayList.get(j).getmAnswer4());
            contentValues.put(MATH_DIFFICULTY, arrayList.get(j).getmDifficulty());
            contentValues.put(MATH_QUESTION, arrayList.get(j).getmQuestion());
            contentValues.put(MATH_REAL_ANSWER,arrayList.get(j).getmRealAnswer());
            database.insert(MATH_QUESTION_TABLE, null, contentValues);
        }
    }


}
