package comb.self.webtest;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    NestedWebView webView;
    @BindView(R.id.bottom_sheet_layout)
    RelativeLayout bottomSheetLayout;
    @BindView(R.id.bottom_bar)
    LinearLayout bottomBarLayout;
    @BindView(R.id.save_button)
    FloatingActionButton saveButton;

    int peekHeight = 0;
    int defaultBottomMargin = 0;
    int rightMargin = 0;
    boolean elevationFlat = true;

    private BottomSheetBehavior<RelativeLayout> mBottomSheet;
    private BottomBarBehavior bottomBarBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Web browser");

        peekHeight = getResources().getDimensionPixelSize(R.dimen.peek_height_normal);
        defaultBottomMargin = getResources().getDimensionPixelSize(R.dimen.fab_bottom_margin);
        rightMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin_right);

        mBottomSheet = BottomSheetBehavior.from(bottomSheetLayout);
        mBottomSheet.setPeekHeight(peekHeight);

        bottomBarBehavior = (BottomBarBehavior) ((CoordinatorLayout.LayoutParams)bottomBarLayout.getLayoutParams()).getBehavior();

        webView.setOnScrollChangedCallback(new NestedWebView.OnScrollChangedCallback() {
            @Override
            public void onScrollDown(int l, int t) {

                recalculatePeekHeight();

                calculateBottomMargin();

                int height = (int) Math.floor(webView.getContentHeight() * webView.getScale());
                int webViewHeight = webView.getHeight();
                int cutoff = height - webViewHeight - 10; // Don't be too strict on the cutoff point
                if (t >= cutoff) {
                    //handle edge detection scrolling
                    expandBottomSheet();
                }
            }

            @Override
            public void onScrollUp(int l, int t) {

                recalculatePeekHeight();

                calculateBottomMargin();
            }
        });

        bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mBottomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                //sheet collapsed.. reset peek height
                if(newState == BottomSheetBehavior.STATE_COLLAPSED){
                    mBottomSheet.setPeekHeight(peekHeight);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl("http://www.theverge.com");
    }

    private void recalculatePeekHeight(){

        int currentTranslation = bottomBarBehavior.getCurrentTranslation();
        currentTranslation = peekHeight - currentTranslation;
        mBottomSheet.setPeekHeight(currentTranslation);
        bottomBarLayout.requestLayout();
    }

    private void calculateBottomMargin(){

        int currentTranslation = bottomBarBehavior.getCurrentTranslation();
        currentTranslation = defaultBottomMargin - currentTranslation;

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)saveButton.getLayoutParams();
        params.setMargins(0,0,rightMargin,currentTranslation);

        saveButton.setLayoutParams(params);
    }

    private void expandBottomSheet(){

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        };

        handler.postDelayed(runnable, 200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int id = item.getItemId();

        if(id == R.id.toggle){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if(elevationFlat){
                    int elevation = getResources().getDimensionPixelSize(R.dimen.elevation_high);
                    saveButton.setElevation(elevation);
                    elevationFlat = false;
                }else{
                    saveButton.setElevation(0);
                    elevationFlat = true;
                }

            }

            return true;
        }

        return false;
    }
}
