package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Notification;
import rx.Observable;

public class MeterializeOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Materialize");
        mLButton.setOnClickListener(e -> meterializeObserver().subscribe(i -> log("meterialize:" + i.getValue() + " type" + i.getKind())));
        mRButton.setText("DeMaterialize");
        mRButton.setOnClickListener(e -> deMeterializeObserver().subscribe(i->log("deMeterialize:"+i)));
    }
    /**Returns an Observable that represents all of the emissions and notifications from the source Observable into
     *  emissions marked with their original types within Notification objects
     * */
    /**emitted items like for an observable of integers are just integers but materialize operator makes the source observable
     * to emit notification objects as emissions which also contain other details of it and can be extracted and used inside
     * onNext(Action 1) of subscriber or observer
     * */
    private Observable<Notification<Integer>> meterializeObserver() {
        return Observable.just(1, 2, 3).materialize();
    }

    private Observable<Integer> deMeterializeObserver() {
        return meterializeObserver().dematerialize();
    }

}
