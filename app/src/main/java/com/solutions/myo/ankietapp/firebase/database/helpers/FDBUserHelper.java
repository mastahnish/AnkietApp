package com.solutions.myo.ankietapp.firebase.database.helpers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.solutions.myo.ankietapp.logging.LogHelper;
import com.solutions.myo.ankietapp.firebase.database.IFirebaseStorage;
import com.solutions.myo.ankietapp.firebase.database.data.User;

/**
 * All methods to work with users will be here
 */

public class FDBUserHelper {

    private static final String TAG = FDBUserHelper.class.getSimpleName();

    public FDBUserHelper() {

    }

    public static Task<Void> addUser(FirebaseUser user){

        LogHelper.log(TAG, "::addUser::", true);

        String email = user.getEmail();
        String username = getUsernameFromEmail(email);

        User newUser = new User(username, email);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = user.getUid();

        Task<Void> result = mDatabase.child(IFirebaseStorage.IRootChild.USERS).child(userId).setValue(newUser);
        return result;
    }

    private static String getUsernameFromEmail(String email){
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
