package io.aifo.example.ui;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import io.aifo.api.aspect.Permission;
import io.aifo.api.aspect.SingleClick;
import io.aifo.api.javassist.Inject;
import io.aifo.example.R;
import io.aifo.example.entity.Persion;
//import io.aifo.gen0.InstanceFactory;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(R.layout.activity_main);
        View viewById = this.findViewById(R.id.tv_hello);
        viewById.setOnClickListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container,new MainFragment(),"MainFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }


    @SingleClick
    @Override
    public void onClick(View v) {
        takeAPicture();
    }

    @Permission(Manifest.permission.CAMERA)
    private void takeAPicture() {
        textInject();
    }

//    @Inject
    Persion persion;

    private void textInject() {
//        persion.school.printSchool();
    }

//    private void testInstanceFactory() throws InstantiationException, IllegalAccessException {
//        Persion persion = (Persion) InstanceFactory.create(Persion.class);
//    }


}
