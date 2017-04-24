package me.microdog.demorotation;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    View contentView;
    ImageView imageView;
    TextView textView;
    Toast lastToast;
    float rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.container);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setClickable(true);
        imageView.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                StringBuilder log = new StringBuilder();

                log.append(String.format("Before - imageView.getPivotX: %f\n", imageView.getPivotX()));
                imageView.setPivotX(imageView.getWidth() / 2);
                log.append(String.format("After - imageView.getPivotX: %f\n", imageView.getPivotX()));

                log.append(String.format("Before - imageView.getPivotY: %f\n", imageView.getPivotY()));
                imageView.setPivotY(imageView.getHeight() / 2);
                log.append(String.format("After - imageView.getPivotY: %f\n", imageView.getPivotY()));

                log(log.toString());
            }
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        if (v.getId() == imageView.getId()) {
            String rotationTarget;
            float setValue = (rotate += 15f) % 105 - 45;
            float newValue;

            /*
             * >>>>>> BUG HERE <<<<<<
             *
             * The entire view will disappear if the value of `rotationY` or `rotationX` is not zero.
             *
             */
            View view = findViewById(R.id.imageView);
            switch (0) {
                case 0:
                    view.setRotationY(setValue);

                    rotationTarget = "RotationY";
                    newValue = view.getRotationY();
                    break;
                case 1:
                    view.setRotationX(setValue);

                    rotationTarget = "RotationX";
                    newValue = view.getRotationX();
                    break;
                case 2:
                    view.setRotation(setValue);

                    rotationTarget = "Rotation";
                    newValue = view.getRotation();
                    break;
                default:
                    rotationTarget = "N/A";
                    newValue = Float.MIN_VALUE;
                    break;
            }

            textView.setText(String.format("%s: % 06.2f", rotationTarget, newValue));
        }
    }

    private void log(String log) {
        if (lastToast != null) {
            lastToast.cancel();
        }
        lastToast = Toast.makeText(this, log, Toast.LENGTH_LONG);
        lastToast.show();

        Log.d(TAG, log);
    }
}
