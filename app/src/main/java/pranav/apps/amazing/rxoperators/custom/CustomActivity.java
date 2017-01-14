package pranav.apps.amazing.rxoperators.custom;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Observable.Operator;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.internal.operators.OperatorPublish;
import rx.schedulers.Schedulers;

/**
 * Created by Pranav Gupta on 1/14/2017.
 */

public class CustomActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Lift");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //observable emits a string s
                liftObservable().subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log("lift" + s);
                    }
                });
            }
        });
    }
    //this function returns an observable
    private Observable<String> liftObservable() {
        Operator<String, String> myOperator = new Operator<String, String>() {
            @Override
            public Subscriber<? super String> call(Subscriber<? super String> subscriber) {
                return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();     //on completion of work of observable (used to notify observer)
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("Operating " + s);

                        }
                    }
                };
            }
        };
        /**Firstly an observable is created with just which converts the items into an observable which emit those items
         * Now we map these integers into 1st Map i then we apply the lift operator which makes the items of observable to pass
         * through a custom function where we can modify the observable , here we attach Operating (x) x comes from Ist map
         * Now as first map gave the string and now we map the resultant string into 2nd Map(y) and finally this
         *  is logged.
         */
        return Observable.just(1, 2, 3).map(integer -> "1st Map " + integer).lift(myOperator).map(s -> "2nd Map " + s);
    }

}
