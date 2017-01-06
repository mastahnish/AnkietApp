/*
 * Copyright 2016 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.solutions.myo.ankietapp.ui.breadcrumb;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;


public class BreadcrumbsView extends LinearLayout {
  int visitedStepBorderDotColor;
  int visitedStepFillDotColor;
  int nextStepBorderDotColor;
  int nextStepFillDotColor;
  int visitedStepSeparatorColor;
  int nextStepSeparatorColor;
  int radius;
  int sizeDotBorder;
  int heightSeparator;
  int nSteps;
  int currentStep = 0;
  List<Step> steps;
  boolean animIsRunning;

  public BreadcrumbsView(Context context, int nSteps) {
    super(context);
    this.nSteps = nSteps;

    PropertiesHelper.init(this);

    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        createSteps();
      }
    });
  }

  public BreadcrumbsView(Context context, AttributeSet attrs) {
    super(context, attrs);

    PropertiesHelper.init(this, attrs);

    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        createSteps();
      }
    });
  }

  /**
   * Start counting from 0.
   *
   * @return the index of the current step.
   */
  public int getCurrentStep() {
    return currentStep;
  }

  public boolean isAnimIsRunning() {
    return animIsRunning;
  }

  /**
   * Move to the next step. Throw if not steps are left to move forward
   *
   * @throws IndexOutOfBoundsException
   */
  android.os.Handler mHandlerNext;
  public void nextStep(final IAnimationListener listener) throws IndexOutOfBoundsException {
    if (animIsRunning) return;
    animIsRunning = true;

    if (currentStep == nSteps - 1) {
      throw new IndexOutOfBoundsException(
          "nextStep() called but there is not steps left to move forward.");
    }

   final SeparatorView separatorView = steps.get(currentStep).separatorView;
    final DotView dotView = steps.get(currentStep + 1).dotView;

    mHandlerNext= new android.os.Handler();
    mHandlerNext.post(new Runnable() {
      @Override
      public void run() {
        separatorView.animateFromNextStepToVisitedStep(new Runnable() {
          @Override public void run() {
            dotView.animateFromNextStepToVisitedStep(new Runnable() {
              @Override public void run() {
                currentStep++;
                animIsRunning = false;
                listener.onAnimationFinished();
              }
            });
          }
        });
      }
    });

  }

  /**
   * Move to the previous step. Throw if not steps are left to go back.
   *
   * @throws IndexOutOfBoundsException
   */
  public void prevStep(final IAnimationListener listener) throws IndexOutOfBoundsException {
   if (animIsRunning) return;
    animIsRunning = true;

    if (currentStep == 0) {
      throw new IndexOutOfBoundsException(
          "prevStep() called but there is not steps left to go bak.");
    }

    final DotView dotView = steps.get(currentStep).dotView;
    final SeparatorView separatorView = steps.get(currentStep - 1).separatorView;

    android.os.Handler mHandler = new android.os.Handler();
    mHandler.post(new Runnable() {
      @Override
      public void run() {
        dotView.animateFromVisitedStepToNextStep(new Runnable() {
          @Override public void run() {
            separatorView.animateFromVisitedStepToNextStep(new Runnable() {
              @Override public void run() {
                currentStep--;
                animIsRunning = false;
                listener.onAnimationFinished();
              }
            });
          }
        });
      }
    });


  }

  /**
   * Should be called before this view is measured. Otherwise throw an IllegalStateException.
   *
   * @param currentStep the desired step
   */
  public void setCurrentStep(int currentStep) throws IllegalStateException {
    if (steps != null) {
      throw new IllegalStateException(
          "Illegal attempt to set the value of the current step once the view has been measured");
    }
    this.currentStep = currentStep;
  }

  private void createSteps() {
    setOrientation(LinearLayout.HORIZONTAL);

    if (nSteps < 2) throw new IllegalArgumentException("Number of steps must be greater than 1");

    int nSeparators = nSteps - 1;
    int widthDot = radius * 2;
    int widthStep = ((getWidth() - widthDot) / nSeparators) - widthDot;

    steps = new ArrayList<>(nSeparators);

    for (int i = 0; i < nSteps; i++) {
      boolean visited = i <= currentStep;

      DotView dotView =
          new DotView(getContext(), visited, visitedStepBorderDotColor,
              visitedStepFillDotColor,
              nextStepBorderDotColor, nextStepFillDotColor, radius,
              sizeDotBorder);
      addView(dotView);

      //Prevent drawing a separator after the last dot.
      if (i == nSteps - 1) {
        steps.add(new Step(null, dotView));
        break;
      }

      boolean visitedSeparator = i < currentStep;
      SeparatorView separatorView =
          new SeparatorView(getContext(), visitedSeparator, visitedStepSeparatorColor,
              nextStepSeparatorColor, widthStep,
              heightSeparator);
      addView(separatorView);

      steps.add(new Step(separatorView, dotView));
    }
  }

  private static class Step {
    private final SeparatorView separatorView;
    private final DotView dotView;

    public Step(SeparatorView separatorView, DotView dotView) {
      this.separatorView = separatorView;
      this.dotView = dotView;
    }
  }
}
