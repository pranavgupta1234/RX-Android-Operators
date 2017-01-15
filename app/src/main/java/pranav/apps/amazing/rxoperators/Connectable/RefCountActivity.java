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
