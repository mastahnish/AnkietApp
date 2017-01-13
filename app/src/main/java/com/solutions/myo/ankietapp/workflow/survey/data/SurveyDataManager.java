package com.solutions.myo.ankietapp.workflow.survey.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.solutions.myo.ankietapp.firebase.database.IFirebaseStorage;
import com.solutions.myo.ankietapp.firebase.database.models.User;
import com.solutions.myo.ankietapp.firebase.database.helpers.FDBSurveyHelper;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.objects.Survey;

//User for sending data to Firebase
public class SurveyDataManager implements ValueEventListener{
    private static final String TAG = SurveyDataManager.class.getSimpleName();

    private Survey mSurvey;
    private DatabaseReference mDatabase;
    private ISurveyUpdateListener mUpdateListener;
    private String uid;

    //TODO define argument dependning db structure
    public void sendSurvey(Survey survey, ISurveyUpdateListener listener){
        //TODO sendSurvey to Firebase
        LogHelper.log(TAG, "::sendSurvey::", true);
        mSurvey = survey;
        mUpdateListener = listener;
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        addListenerForSingleValueEvent();
    }


    private void addListenerForSingleValueEvent(){
        mDatabase.child(IFirebaseStorage.IRootChild.USERS).child(uid).addListenerForSingleValueEvent(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        LogHelper.log(TAG, "::onDataChange::", true);
        User user = dataSnapshot.getValue(User.class);

        if(user!=null){
            FDBSurveyHelper.insertNewSurvey(uid, user.username, mSurvey, onInsertFailureListener, onInsertCompleteListener);
        }else{
            mUpdateListener.onUnexpectedFailure();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
            LogHelper.log(TAG, "::onCancelled::", true);
            mUpdateListener.onFailure(databaseError);
    }

    OnFailureListener onInsertFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            mUpdateListener.onFailure(e);
        }
    };

    OnCompleteListener onInsertCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            mUpdateListener.onSuccess();
        }
    };

}
