package com.trufla.androidtruforms;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TruBottomNavigation extends FrameLayout {
    //0 is the starting index of pages
    int pageIdx = 0;
    int sizeOfPages = 1;
    TextView stepCounterTextView;
    ImageView nextStepIcon;
    ImageView preStepIcon;
    private TextView nextStepText;
    private TruNavigationListener mListener;


    public TruBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.bottom_sections_navigation, null);
        nextStepIcon = view.findViewById(R.id.next_step_icon);
        preStepIcon = view.findViewById(R.id.previous_step_icon);
        stepCounterTextView = view.findViewById(R.id.page_counter_tv);
        nextStepText = view.findViewById(R.id.next_step_text);
        nextStepText.setOnClickListener((v) -> onNextClicked());
        nextStepIcon.setOnClickListener((V) -> onNextClicked());
        preStepIcon.setOnClickListener((v) -> onPreClicked());
        updateView();
        addView(view);
    }

    private void onPreClicked() {
        if (pageIdx == 0) {
            onExitClicked();
            return;
        }
        pageIdx--;
        updateView();
        if (mListener != null)
            mListener.onPrePageClicked();
    }

    private void onExitClicked() {
        if (mListener != null)
            mListener.onExitClicked();
    }

    private void onNextClicked() {
        if (pageIdx + 1 == sizeOfPages) {
            onSubmitClicked();
            return;
        }
        pageIdx++;
        updateView();
        if (mListener != null)
            mListener.onNextPageClicked();
    }

    private void onSubmitClicked() {
        if (mListener != null)
            mListener.onSubmitClicked();
    }

    private void updateView() {
        stepCounterTextView.setText(getResources().getString(R.string.page_no_of_pages, String.valueOf(pageIdx + 1), String.valueOf(sizeOfPages)));
        if (pageIdx + 1 == sizeOfPages) {
            nextStepText.setText(getResources().getString(R.string.submit_btm_nav_text));
            nextStepIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_white_24dp));
        } else {
            nextStepText.setText(getResources().getString(R.string.next_step_btm_nav_text));
            nextStepIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_forward_white_24dp));
        }

    }

    public int getCurrentPageIndex() {
        return pageIdx;
    }

    public void setSizeOfPages(int size) {
        this.sizeOfPages = size;
        updateView();
    }

    public interface TruNavigationListener {
        void onNextPageClicked();

        void onPrePageClicked();

        void onSubmitClicked();

        void onExitClicked();
    }

}
