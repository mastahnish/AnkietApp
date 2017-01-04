package com.solutions.myo.ankietapp.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.solutions.myo.ankietapp.R;

public abstract class BaseStateManager {

    public static final int EVENT_NEXT = 1;

    public static final int EVENT_PREVIOUS = 2;

    public static final int EVENT_FINISH = 3;

    public static final int EVENT_NEXT_ALTERNATIVE = 4;

    private FragmentManager mFragmentManager;

    public Activity mActivity;

    protected FinishProcessState mFinishProcessState;

    protected BaseStateManager(FragmentManager fragmentManager, Activity activity) {
        mFragmentManager = fragmentManager;

        if (mFinishProcessState == null) {
            mFinishProcessState = new FinishProcessState();
        }

        mActivity = activity;
    }

    protected void addNewFragment(Fragment fragment) {
        addFragment(fragment, new Bundle());
    }

    public void addNewFragment(Fragment fragment, Bundle bundle) {
        addFragment(fragment, bundle == null ? new Bundle() : bundle);
    }

    private void addFragment(Fragment fragment, Bundle bundle) {
        Fragment oldFragment = getCurrentFragment();
        if (oldFragment == null) {
            fragment.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        } else {
            fragment.setArguments(bundle);
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    protected Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(R.id.fragmentContainer);
    }

    protected abstract BaseState getInitialState();

    public abstract class BaseState {

        public abstract void enter();

        public abstract void exit(int event);

        public abstract BaseState nextState(int event);

        public BaseState processState(int event) {
            BaseState newState = nextState(event);
            exit(event);
            newState.enter();
            return newState;
        }

    }

    protected class FinishProcessState extends BaseState {

        @Override
        public void enter() {
            Fragment fragment = getCurrentFragment();
            fragment.getActivity().finish();
        }

        @Override
        public void exit(int event) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BaseState nextState(int event) {
            throw new UnsupportedOperationException();
        }

    }

    public interface IStateChangeListener {
        BaseStateManager.BaseState processState(int event);
    }

}
