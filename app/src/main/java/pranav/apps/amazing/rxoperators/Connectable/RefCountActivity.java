package pranav.apps.amazing.rxoperators.Connectable;

import android.os.Bundle;
import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RefCountActivity extends BaseActivity {
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectableObservable<Long> obs = publishObserver();

        mLButton.setText("RefCount");
        /**Here we can see that we didn't used connect
         * The reason is that obs was a connectable observable but after application of refcount it returned an ordinary observable
         * The work of it is that as soon as user subscribes to this observable it connects to the underlying observable and it remains
         * connected until the last observer has not unsubscribed. In this way it keeps track of the other observers which have
         * subscribed to observable amd disconnects as soon as the last observer do so
         * */
        mLButton.setOnClickListener(e -> subscription = obs.refCount().subscribe(aLong -> {
            log("refCount:" + aLong);
        }));
        mRButton.setText("Stop");
        mRButton.setOnClickListener(e -> subscription.unsubscribe());
    }

    private ConnectableObservable<Long> publishObserver() {
        Observable<Long> obser = Observable.interval(1, TimeUnit.SECONDS);
        obser.observeOn(Schedulers.newThread());
        return obser.publish();
    }

}
