package pranav.apps.amazing.rxoperators;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pranav.apps.amazing.rxoperators.custom.CustomActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Pranav Gupta on 1/14/2017.
 */

public class BaseActivity extends AppCompatActivity{
    protected Button mLButton, mRButton;
    protected TextView mResultView;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mLButton = (Button) findViewById(R.id.left);
        mRButton = (Button) findViewById(R.id.right);
        mResultView = (TextView) findViewById(R.id.result);
        TAG = getLocalClassName();
    }
    protected void log(Object s) {
        Log.d(TAG, String.valueOf(s));
        Observable.just(s).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override                                //this format assures every new item is appended with a \n (no listview here)
            public void call(Object o) {
               mResultView.setText(mResultView.getText()+ "\n" + o);
            }
        });

        //java 8 lambda version of above thing
        /*Observable.just(s).observeOn(AndroidSchedulers.mainThread()).subscribe(i -> {
            mResultView.setText(mResultView.getText() + "\n" + i);
        });*/

    }

}
