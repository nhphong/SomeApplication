package com.eezy.someapplication.fragment;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eezy.someapplication.MainActivity;
import com.eezy.someapplication.MyApplication;
import com.eezy.someapplication.R;
import com.eezy.someapplication.model.Pane;
import com.eezy.someapplication.util.Util;
import com.eezy.someapplication.view.PageTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HappyFacialFragment extends BaseFacialFragment {

    public static final String TAG = HappyFacialFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new HappyFacialFragment(), 2, 3, TAG);
    }

    @Override
    protected void setTitle(PageTitleView pageTitleView) {
        pageTitleView.setPageTitleColor(getResources().getColor(R.color.happy_title));
        pageTitleView.setPageSubtitleColor(getResources().getColor(R.color.happy_subtitle));
        pageTitleView.setPageTitle("Title");
        pageTitleView.setPageSubTitle("Subtitle");
    }

    @Override
    protected void setBackground(ImageView background) {
        background.setImageResource(R.drawable.bk_happy);
    }

    @Override
    protected void setBackgroundOverlay(RelativeLayout layout) {

    }

    @Override
    protected List<Integer> prepareFacialParts() {
        Random r = MyApplication.random;
        List<Integer> eyesBrowns = Arrays.asList(
                R.drawable.eyes_brown_happy,
                Util.pickRandomly(new int[] {R.drawable.eyes_brown_sad, R.drawable.eyes_brown_annoying}, r)
        );

        List<Integer> eyes = Arrays.asList(
                R.drawable.eyes_happy,
                Util.pickRandomly(new int[] {R.drawable.eyes_sad, R.drawable.eyes_annoying}, r)
        );

        List<Integer> mouths = Arrays.asList(
                R.drawable.mouth_happy,
                Util.pickRandomly(new int[] {R.drawable.mouth_sad, R.drawable.mouth_annoying}, r)
        );

        Collections.shuffle(eyesBrowns, r);
        Collections.shuffle(eyes, r);
        Collections.shuffle(mouths, r);

        List<Integer> result = new ArrayList<>();
        result.addAll(eyesBrowns);
        result.addAll(eyes);
        result.addAll(mouths);
        return result;
    }

    @Override
    protected boolean isCorrectFace(Pane eyesBrown, Pane eyes, Pane mouth) {
        return eyesBrown.getName().equals(String.valueOf(R.drawable.eyes_brown_happy)) &&
                eyes.getName().equals(String.valueOf(R.drawable.eyes_happy)) &&
                mouth.getName().equals(String.valueOf(R.drawable.mouth_happy));
    }

    @Override
    protected void moveOnToNextStep() {
        //Not decided yet
    }
}
