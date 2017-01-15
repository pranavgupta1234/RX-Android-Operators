package pranav.apps.amazing.rxoperators.Connectable;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by Pranav Gupta on 1/15/2017.
 */

public class Connect extends BaseActivity {
    private Subscription _subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**ConnectableObservable is one which emits items only #after observable is called with connect method
         * one can see that nothing was printed on screen even when observable was subscribed
         *
         * This example also shows that how we can subscribe more than one observables to same observable and can start
         * one after sone action is completed
         * */
        ConnectableObservable<Long> connectableObservable = publishObservable();
        Action1 action1 = o -> log("action1 :"+ o);
        Action1 action2 = o -> {
            log("action2 :"+o);
            if((long)o==3){
                connectableObservable.subscribe(action1);
            }
        };
        connectableObservable.subscribe(action2);
        mLButton.setText("START");
        mLButton.setOnClickListener(view -> _subscription = connectableObservable.connect());
        mRButton.setText("STOP N CLEAR");
        mRButton.setOnClickListener(view -> {mResultView.setText("");
                                            if(!_subscription.isUnsubscribed()){
                                                _subscription.unsubscribe();
                                            }
        });
    }

    private ConnectableObservable<Long> publishObservable() {
        /** interval operator emits sequential numbers starting from zero and as this can go quite large so data type used here
         * is Long and also
         * */
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread());
        /**publish makes an observable into connectable observable ie which emits items when connected to and not just after
         * subscription
         * */
        return observable.publish();
    }
}
