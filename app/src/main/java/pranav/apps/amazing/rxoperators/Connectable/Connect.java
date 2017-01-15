package pranav.apps.amazing.rxoperators.Connectable;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscription;
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
        ConnectableObservable<Long> connectableObservable = publishObservable();
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
