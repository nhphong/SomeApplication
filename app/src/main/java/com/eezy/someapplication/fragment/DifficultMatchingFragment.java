package com.eezy.someapplication.fragment;

import android.widget.ImageView;

import com.eezy.someapplication.MainActivity;
import com.eezy.someapplication.MyApplication;
import com.eezy.someapplication.R;
import com.eezy.someapplication.content.Content;
import com.eezy.someapplication.model.Case;
import com.eezy.someapplication.model.Pane;
import com.eezy.someapplication.util.Util;
import com.eezy.someapplication.view.PageTitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DifficultMatchingFragment extends BaseMatchingFragment {

    public static final String TAG = DifficultMatchingFragment.class.getSimpleName();

    public static void launch(MainActivity activity) {
        Util.launchFragment(activity, new DifficultMatchingFragment(), 2, 3, TAG, true);
    }

    private List<Integer> caseIndices;
    private int index;

    @Override
    protected void setupResources() {
        int n = Content.getCases().size();
        caseIndices = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            caseIndices.add(i);
        }
        Collections.shuffle(caseIndices, MyApplication.random);
        index = 0;
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
    protected List<Pane> fillInResources(List<Integer> resIds, Pane cause1, Pane cause2, Pane result) {
        List<Pane> targetPanes = new ArrayList<>();
        int id;

        switch (resIds.remove(0)) {
            case 0:
                id = resIds.remove(0);
                setPaneBackgroundAndDescription(cause1, id, Content.getCaseDescription(id));
                targetPanes.add(cause2);
                targetPanes.add(result);
                break;
            case 1:
                id = resIds.remove(0);
                targetPanes.add(cause1);
                setPaneBackgroundAndDescription(cause2, id, Content.getCaseDescription(id));
                targetPanes.add(result);
                break;
            default:
                id = resIds.remove(0);
                targetPanes.add(cause1);
                targetPanes.add(cause2);
                setPaneBackgroundAndDescription(result, id, Content.getCaseDescription(id));
                break;
        }

        return targetPanes;
    }

    @Override
    protected List<Integer> retrieveResources() {
        List<Integer> result = new ArrayList<>();
        Random r = MyApplication.random;
        List<Case> cases = Content.getCases();

        Case currentCase = cases.remove((int) (caseIndices.get(index)));
        index = (index + 1) % caseIndices.size();
        int index = r.nextInt(3);

        List<Integer> items = currentCase.split();
        int visibleItem = items.remove(index);

        result.add(index);
        result.add(visibleItem);

        List<Integer> candidateItems = new ArrayList<>();
        candidateItems.addAll(items);

        List<Integer> temp = new ArrayList<>();
        for (Case c : cases) {
            temp.addAll(c.split());
        }
        Collections.shuffle(temp, r);

        candidateItems.addAll(temp.subList(0, 3));
        Collections.shuffle(candidateItems, r);

        result.addAll(candidateItems);
        return result;
    }
}
