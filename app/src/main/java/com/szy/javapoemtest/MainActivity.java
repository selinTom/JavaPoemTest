package com.szy.javapoemtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.szy.annotations.BeginGenerate;

import org.jetbrains.annotations.NotNull;

/**
 * Created by devov on 2021/2/4.
 */

public class MainActivity extends AppCompatActivity {
    @BeginGenerate(value1 = 10,value2 = 18)
    public int a;

    private AnimatorSet animatorSet = new AnimatorSet();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
        View main = findViewById(R.id.main);
        View rect = findViewById(R.id.rect);
        View target = findViewById(R.id.target);
        View target2 = findViewById(R.id.target2);
         View delete=   ((TextView)findViewById(R.id.delete));
         ThreadTest tt = new ThreadTest();

        DragLayout drag = findViewById(R.id.drag);
        drag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("RRORE","drag  click!");
                target.requestLayout();
                target.invalidate();
                tt.test();
            }
        });



        rect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("RRORE","bottom  click!");
                target.offsetLeftAndRight(600);
                target.offsetTopAndBottom(600);
                tt.test2();

            }
        });

        drag.setUseLongClickMode(false);
        drag.setConstraintRect(new Rect(20,0,20,100));
        drag.setActionListener(new DragLayout.ActionListener() {
            @Override
            public void onDragInActionView(@NotNull View view, @NotNull View actionView) {
//                ((TextView)actionView.findViewById(R.id.delete)).setText("删除");
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(delete,"scaleX",1f,1.2f);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(delete,"scaleY",1f,1.2f);
                animatorX.setDuration(100);
                animatorY.setDuration(100);
                animatorSet.play(animatorX).with(animatorY);
                animatorSet.start();


            }

            @Override
            public void onDragOutActionView(@NotNull View view, @NotNull View actionView) {
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(delete,"scaleX",1.2f,1f);
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(delete,"scaleY",1.2f,1f);
                animatorX.setDuration(100);
                animatorY.setDuration(100);
                animatorSet.play(animatorX).with(animatorY);
                animatorSet.start();
            }

            @Override
            public void startAction(@NotNull View view, @NotNull View actionView, boolean viewInActionView,int left,int right) {
                if(viewInActionView){
                    drag.removeView(view);
                }
            }
        });
        drag.addDragView(target);
        drag.addDragView(target2);





    }
}
