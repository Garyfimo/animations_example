package com.example.mora.animationexample;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Bind(R.id.ll_login_box)
    LinearLayout llLoginBox;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;

    Animation animationTranslateUp;
    Animation animationTranslateDown;
    Animation animationFade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        llLoginBox.setVisibility(View.GONE);
        animationTranslateUp = AnimationUtils.loadAnimation(this, R.anim.translateup);
        animationTranslateUp.setAnimationListener(this);
        animationTranslateDown = AnimationUtils.loadAnimation(this, R.anim.translatedown);
        animationTranslateDown.setAnimationListener(this);
        ivLogo.startAnimation(animationTranslateUp);
        Glide.with(this).load("http://i.giphy.com/oenruB2DKC7p6.gif")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivLogo);
    }

    @OnClick(R.id.btn_login)
    public void OnClickLogin() {

        if (!isInstalled("com.blogspot.newapphorizons.fakegps")) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
            i.setType("text/plain");
            i.setPackage("com.whatsapp");
            startActivity(i);
        } else {
            Log.v(this.getClass().getSimpleName(), "It's installed");
            redirectPlayStore();
        }
    }

    private void redirectPlayStore() {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.blogspot.newapphorizons.fakegps"));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isInstalled(String uri) {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (animation == animationTranslateUp) {
            animationFade = AnimationUtils.loadAnimation(this, R.anim.fade);
            animationFade.setAnimationListener(this);
            llLoginBox.startAnimation(animationFade);
            llLoginBox.setVisibility(View.VISIBLE);
        } else if (animation == animationFade) {
            //Toast.makeText(this,"Finalizado el fade",Toast.LENGTH_SHORT).show();
            ivLogo.setAnimation(animationTranslateDown);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
