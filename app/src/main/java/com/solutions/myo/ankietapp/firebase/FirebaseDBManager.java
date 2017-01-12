package com.solutions.myo.ankietapp.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jacek on 2017-01-12.
 */
@Deprecated
public class FirebaseDBManager {

    private static final String TAG = FirebaseDBManager.class.getSimpleName();

    private static volatile DatabaseReference mDatabase;

    private static volatile FirebaseDBManager mFDBManager;

    private FirebaseDBManager() {
        createNewDatabase();
    }

    private void createNewDatabase() {
        if(mDatabase==null){
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
    }

    public static FirebaseDBManager getInstance() {
        if (mFDBManager == null) {
            synchronized (FirebaseDBManager.class) {
                if (mFDBManager == null) {
                    mFDBManager = new FirebaseDBManager();
                }
            }
        }
        return mFDBManager;
    }

    public DatabaseReference getDatabase(){
        return mDatabase;
    }
}