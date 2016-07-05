package comb.self.webtest;

/**
 * Created by raghavpetluru on 4/13/16.
 *
 * This is a behavior class that shows/hides the bottom toolbar layout when scrolled.
 * The toolbar appears when scrolling up and hides when scrolling down.
 */
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class BottomBarBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    private int defaultDependencyTop = -1;
    private int currentTranslation = 0;

    public BottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (defaultDependencyTop == -1) {
            defaultDependencyTop = dependency.getTop();
        }

        currentTranslation = -dependency.getTop() + defaultDependencyTop;
        child.setTranslationY(currentTranslation);

        return true;
    }

    public int getCurrentTranslation() {
        return currentTranslation;
    }

}
