package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

public class TimeIntervalTimeStampOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("timeInterval");
        mLButton.setOnClickListener(e -> timeIntervalObserver().subscribe(i -> log("timeInterval:" + i.getValue()+"-"+i.getIntervalInMilliseconds())));
        mRButton.setText("timeStamp");
        mRButton.setOnClickListener(e -> timeStampObserver().subscribe(i -> log("timeStamp:" + i.getValue()+"-"+i.getTimestampMillis())));
    }
    /** returns an observable which emits time record duration between various items emitted by source observable
     * values are accessible in onNext of subscriber
     * */
    private Observable<TimeInterval<Integer>> timeIntervalObserver() {
        return createObserver().timeInterval();
    }
    /** returns an observable which emits item in form of a timestamped object therefore the time stamp can be accessed from
     * the emitted object inside onNext method
     * */
    private Observable<Timestamped<Integer>> timeStampObserver() {
        return createObserver().timestamp();
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
