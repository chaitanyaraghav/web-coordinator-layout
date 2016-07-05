package comb.self.webtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.test_image)
    ImageButton imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.webbutton)
    public void onLaunchClick(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*@OnClick(R.id.red)
    public void onRedClick(){



    }

    @OnClick(R.id.green)
    public void onGreenClick(){

    }

    @OnClick(R.id.blue)
    public void onBlueClick(){

    }*/
}
