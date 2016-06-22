package com.eezy.someapplication.fragment;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.eezy.someapplication.MainActivity;
import com.eezy.someapplication.R;
import com.eezy.someapplication.model.MovableRect;
import com.eezy.someapplication.model.Pane;
import com.eezy.someapplication.util.DeviceUtil;
import com.eezy.someapplication.util.GeoUtil;
import com.eezy.someapplication.view.Character;
import com.eezy.someapplication.view.DragablePaneView;
import com.eezy.someapplication.view.MovingHand;
import com.eezy.someapplication.view.PageTitleView;

import java.util.List;

public abstract class BaseFacialFragment extends Fragment {

    public static final String TAG = BaseFacialFragment.class.getSimpleName();

    private View mView;
    private DragablePaneView mDragablePaneView;
    protected MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();
        mView = inflater.inflate(R.layout.fragment_facial, container, false);
        mDragablePaneView = (DragablePaneView) mView.findViewById(R.id.dragable_pane_view);

        setBackground((ImageView) mView.findViewById(R.id.background));
        setBackgroundOverlay((RelativeLayout) mView.findViewById(R.id.background_overlay));
        setTitle((PageTitleView) mView.findViewById(R.id.page_title_view));
        setUpPaneView(mDragablePaneView);
        return mView;
    }

    private void setUpPaneView(final DragablePaneView paneView) {
        Point screenSize = DeviceUtil.getScreenSize(mActivity);
        List<Integer> facialPartIds = prepareFacialParts();
        MovableRect rect = new MovableRect();

        int eyesBrownTop = (int) (screenSize.y * 0.261);
        int eyesBrownHeight = (int) (screenSize.y * 0.15);
        int eyesBrownBottom = eyesBrownTop + eyesBrownHeight;

        int eyesTopMargin = 0;
        int eyesSideMargin = (int) (screenSize.x * 0.029);
        int eyesTop = eyesBrownBottom + eyesTopMargin;
        int eyesHeight = (int) (screenSize.y * 0.2);
        int eyesBottom = eyesTop + eyesHeight;

        int mouthTopMargin = 0;
        int mouthSideMargin = (int) (screenSize.x * 0.029);
        int mouthTop = eyesBottom + mouthTopMargin;
        int mouthBottom = screenSize.y - eyesBrownTop;
        int sideLength = (int) (screenSize.x * 0.33);
        int sideMargin = (int) (screenSize.x * 0.055);

        int resId = facialPartIds.remove(0);
        Pane eyesBrownLeft = new Pane(String.valueOf(resId));
        eyesBrownLeft.addVertex(sideMargin, eyesBrownTop);
        eyesBrownLeft.addVertex(sideLength - sideMargin, eyesBrownTop);
        eyesBrownLeft.addVertex(sideLength - sideMargin, eyesBrownBottom);
        eyesBrownLeft.addVertex(sideMargin, eyesBrownBottom);
        eyesBrownLeft.setBackground(mActivity, resId);
        eyesBrownLeft.setMovable(true);

        resId = facialPartIds.remove(0);
        rect.set(eyesBrownLeft.getBoundingRect());
        Pane eyesBrownRight = new Pane(String.valueOf(resId), rect.moveTo(screenSize.x - sideMargin - rect.width()));
        eyesBrownRight.setBackground(mActivity, resId);
        eyesBrownRight.setMovable(true);

        resId = facialPartIds.remove(0);
        Pane eyesLeft = new Pane(String.valueOf(resId));
        eyesLeft.addVertex(sideMargin + eyesSideMargin, eyesTop);
        eyesLeft.addVertex(sideLength - sideMargin - eyesSideMargin, eyesTop);
        eyesLeft.addVertex(sideLength - sideMargin - eyesSideMargin, eyesBottom);
        eyesLeft.addVertex(sideMargin + eyesSideMargin, eyesBottom);
        eyesLeft.setBackground(mActivity, resId);
        eyesLeft.setMovable(true);

        resId = facialPartIds.remove(0);
        rect.set(eyesLeft.getBoundingRect());
        Pane eyesRight = new Pane(String.valueOf(resId), rect.moveTo(screenSize.x - sideMargin - rect.width() - eyesSideMargin));
        eyesRight.setBackground(mActivity, resId);
        eyesRight.setMovable(true);

        resId = facialPartIds.remove(0);
        Pane mouthLeft = new Pane(String.valueOf(resId));
        mouthLeft.addVertex(sideMargin + mouthSideMargin, mouthTop);
        mouthLeft.addVertex(sideLength - sideMargin - mouthSideMargin, mouthTop);
        mouthLeft.addVertex(sideLength - sideMargin - mouthSideMargin, mouthBottom);
        mouthLeft.addVertex(sideMargin + mouthSideMargin, mouthBottom);
        mouthLeft.setBackground(mActivity, resId);
        mouthLeft.setMovable(true);

        resId = facialPartIds.remove(0);
        rect.set(mouthLeft.getBoundingRect());
        Pane mouthRight = new Pane(String.valueOf(resId), rect.moveTo(screenSize.x - sideMargin - rect.width() - mouthSideMargin));
        mouthRight.setBackground(mActivity, resId);
        mouthRight.setMovable(true);

        Pane emptyFace = new Pane("Empty Face");
        emptyFace.addVertex(eyesBrownLeft.rightTop(sideMargin, -sideMargin));
        emptyFace.addVertex(eyesBrownRight.leftTop(-sideMargin, -sideMargin));
        emptyFace.addVertex(mouthRight.leftBottom(-sideMargin - mouthSideMargin, (int) (sideMargin * 1.6)));
        emptyFace.addVertex(mouthLeft.rightBottom(sideMargin + mouthSideMargin, (int) (sideMargin * 1.6)));
        emptyFace.setBackground(mActivity, R.drawable.face);

        final Pane eyesBrownMiddle = eyesBrownLeft.cloneWithoutBackground();
        eyesBrownMiddle.move(screenSize.x / 2 - eyesBrownMiddle.width() / 2 - sideMargin, (int) (sideMargin * 0.7));
        final Pane eyesMiddle = eyesLeft.cloneWithoutBackground();
        eyesMiddle.move(screenSize.x / 2 - eyesLeft.width() / 2 - sideMargin - eyesSideMargin, (int) (sideMargin * 0.7));
        final Pane mouthMiddle = mouthLeft.cloneWithoutBackground();
        mouthMiddle.move(screenSize.x / 2 - mouthLeft.width() / 2 - sideMargin - mouthSideMargin, (int) (sideMargin * 0.45));

        addFrame(paneView, sideMargin, eyesBrownLeft, eyesLeft, mouthLeft, -5);
        addFrame(paneView, sideMargin, eyesBrownRight, eyesRight, mouthRight, 5);

        paneView.addPane(emptyFace);
        paneView.addPane(eyesBrownMiddle);
        paneView.addPane(eyesMiddle);
        paneView.addPane(mouthMiddle);
        paneView.addPane(eyesBrownLeft);
        paneView.addPane(eyesLeft);
        paneView.addPane(mouthLeft);
        paneView.addPane(eyesBrownRight);
        paneView.addPane(eyesRight);
        paneView.addPane(mouthRight);
        paneView.setGhostMode(true);
        paneView.invalidate();

        DragablePaneView.TargetPaneReachListener listener = new DragablePaneView.TargetPaneReachListener() {
            @Override
            public void onTargetReach(Pane targetPane, Pane draggingPane) {
                targetPane.setBackground(draggingPane.getBackground());
                targetPane.setName(draggingPane.getName());
                verifyFace(eyesBrownMiddle, eyesMiddle, mouthMiddle);
            }
        };

        paneView.addTargetPaneFor(eyesBrownMiddle, eyesBrownLeft, listener);
        paneView.addTargetPaneFor(eyesBrownMiddle, eyesBrownRight, listener);
        paneView.addTargetPaneFor(eyesMiddle, eyesLeft, listener);
        paneView.addTargetPaneFor(eyesMiddle, eyesRight, listener);
        paneView.addTargetPaneFor(mouthMiddle, mouthLeft, listener);
        paneView.addTargetPaneFor(mouthMiddle, mouthRight, listener);

        Point startPos = mouthLeft.center(0, 0);
        Point endPos = mouthMiddle.center(0, 0);
        MovingHand.show(this, (ViewGroup) mView, startPos, endPos);
    }

    private void addFrame(DragablePaneView paneView, int sideMargin, Pane eyesBrown, Pane eyes, Pane mouth, float rotation) {
        Rect boundingRect = GeoUtil.getBoundingRect(eyesBrown, eyes, mouth);
        boundingRect = GeoUtil.inflate(boundingRect, sideMargin, (int) (sideMargin * 0.45));
        Pane frame = new Pane("Frame", boundingRect);
        //frame.setBackground(mActivity, bkResId);
        frame.setRotation(rotation);

        Rect frameBounding = frame.getBoundingRect();
        int w = frameBounding.width();
        int h = frameBounding.height();
        Pane frameInner = new Pane("Frame Inner", GeoUtil.deflate(frameBounding, (int) (0.08 * w),
                (int) (0.165 * h), (int) (0.08 * w), (int) (0.055 * h)));
        //frameInner.setBackground(mActivity, R.drawable.frame_inner_area);
        frameInner.setRotation(rotation);

        paneView.addPane(frameInner);
        paneView.addPane(frame);
    }

    private void verifyFace(Pane eyesBrown, Pane eyes, Pane mouth) {
        if (eyesBrown.getBackground() != null && eyes.getBackground() != null && mouth.getBackground() != null) {
            if (isCorrectFace(eyesBrown, eyes, mouth)) {
                Character.sayYay2(mActivity);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Character.remove(mActivity);
                        moveOnToNextStep();
                    }
                }, 1000);
            } else {
                Character.sayUhOh2(mActivity);
            }
        }
    }

    protected abstract void setTitle(PageTitleView pageTitleView);
    protected abstract void setBackground(ImageView background);
    protected abstract void setBackgroundOverlay(RelativeLayout layout);
    protected abstract List<Integer> prepareFacialParts();
    protected abstract boolean isCorrectFace(Pane eyesBrown, Pane eyes, Pane mouth);
    protected abstract void moveOnToNextStep();
}
