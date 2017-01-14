package com.solutions.myo.ankietapp.workflow.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.solutions.myo.ankietapp.R;
import com.solutions.myo.ankietapp.common.AuthActivity;
import com.solutions.myo.ankietapp.common.IAuthAction;
import com.solutions.myo.ankietapp.databinding.ActivityLoginBinding;
import com.solutions.myo.ankietapp.firebase.analytics.FirebaseAnalyticsHelper;
import com.solutions.myo.ankietapp.logging.LogHelper;

public class LoginActivity extends AuthActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private ActivityLoginBinding binding;

    private FirebaseAnalyticsHelper mFirebaseAnalyticsHelper;
    private FirebaseAnalytics mFirebaseAnalytics;

    private View focusView;
    private String email;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelper.log(TAG, "::onCreate!", true);
        checkIfUserExist();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalyticsHelper = new FirebaseAnalyticsHelper(mFirebaseAnalytics);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        binding.emailSignInButton.setOnClickListener(this);
        binding.emailSignUpButton.setOnClickListener(this);

        createLoginDataManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.log(TAG, "::onStart!", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress(false);
    }


    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.log(TAG, "::onStop!", true);
    }

    private boolean isCredentialsWrong(){
        // Reset errors.
        binding.email.setError(null);
        binding.password.setError(null);

        // Store values at the time of the login attempt.
        email = binding.email.getText().toString().trim();
        password = binding.password.getText().toString().trim();

        boolean cancel = false;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            binding.password.setError(getString(R.string.error_invalid_password));
            focusView = binding.password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            binding.email.setError(getString(R.string.error_field_required));
            focusView = binding.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            binding.email.setError(getString(R.string.error_invalid_email));
            focusView = binding.email;
            cancel = true;
        }

        return cancel;
    }

    private void attemptLogin() {
        LogHelper.log(TAG, "attemptLogin:",true);
        if (isCredentialsWrong()) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            proceedWithLogin();
        }
    }

    private void attemptSignUp() {
        LogHelper.log(TAG, "attemptSignUp:",true);
        if (isCredentialsWrong()) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            createAccount();
        }
    }

    private void proceedWithLogin() {
        LogHelper.log(TAG, "proceedWithLogin:",true);
        setAuthAction(IAuthAction.SIGN_IN);
        getFirebaseAuth().signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, completeListener)
                .addOnFailureListener(this, failureListener);


    }

    private void createAccount(){
        LogHelper.log(TAG, "createAccount:",true);
        setAuthAction(IAuthAction.SIGN_UP);
        getFirebaseAuth().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, completeListener)
                .addOnFailureListener(this, failureListener);
    }

    //FIXME not needed for now
    @Deprecated
    OnCompleteListener completeListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            LogHelper.log(TAG, "onCompleteListener:" + task.isSuccessful(),true);

            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            if (!task.isSuccessful()) {
                Log.w(TAG, "signInWithEmail:failed", task.getException());
                Toast.makeText(LoginActivity.this, R.string.auth_failed,
                        Toast.LENGTH_SHORT).show();
                showProgress(false);
            }
        }
    };

    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            //singInWithEmailAndPassword exceptions handling
            if(e instanceof FirebaseAuthInvalidUserException){
                LogHelper.log(Log.ERROR, TAG, ((FirebaseAuthInvalidUserException) e).getErrorCode(), true);
                Toast.makeText(LoginActivity.this, ((FirebaseAuthInvalidUserException) e).getMessage(),  Toast.LENGTH_SHORT).show();
            }

            if(e instanceof FirebaseAuthInvalidCredentialsException){
                Toast.makeText(LoginActivity.this, ((FirebaseAuthInvalidCredentialsException) e).getMessage(),  Toast.LENGTH_SHORT).show();
            }


            //create account exceptions handling
            if(e instanceof FirebaseAuthWeakPasswordException){
                Toast.makeText(LoginActivity.this, ((FirebaseAuthWeakPasswordException) e).getReason(),  Toast.LENGTH_SHORT).show();
            }

            if(e instanceof FirebaseAuthInvalidCredentialsException){
                LogHelper.log(Log.ERROR, TAG, ((FirebaseAuthInvalidCredentialsException) e).getErrorCode(), true);
                Toast.makeText(LoginActivity.this, ((FirebaseAuthInvalidCredentialsException) e).getMessage(),  Toast.LENGTH_SHORT).show();
            }

            if(e instanceof FirebaseAuthUserCollisionException){
                LogHelper.log(Log.ERROR, TAG, ((FirebaseAuthUserCollisionException) e).getErrorCode(), true);
                Toast.makeText(LoginActivity.this, ((FirebaseAuthUserCollisionException) e).getMessage(),  Toast.LENGTH_SHORT).show();
            }

            showProgress(false);
        }
    };

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            binding.loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            binding.loginProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            binding.loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.email_sign_in_button:
                mFirebaseAnalyticsHelper.logLoginButtonClickedEvent();
                attemptLogin();
                break;
            case R.id.email_sign_up_button:
                mFirebaseAnalyticsHelper.logSignUpButtonClickedEvent();
                attemptSignUp();
            //TODO some other options for login like Facebook or so
        }

    }

    private void checkIfUserExist(){
        FirebaseUser firebaseUser = getFirebaseAuth().getCurrentUser();
        if(firebaseUser!=null){
            navigateNext();
            if(this!=null){
                this.finish();
            }

        }
    }

}

