package com.example.mora.animationexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
    public void OnClickLogin(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
        i.setType("text/plain");
        i.setPackage("com.whatsapp");
        startActivity(i);
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
        }else if(animation == animationFade){
            //Toast.makeText(this,"Finalizado el fade",Toast.LENGTH_SHORT).show();
            ivLogo.setAnimation(animationTranslateDown);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
