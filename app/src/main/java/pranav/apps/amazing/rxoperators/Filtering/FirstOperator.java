package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

public class FirstOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("First");
        mLButton.setOnClickListener(e -> firstObserver().subscribe(i -> log("First:" + i)));
        mRButton.setText(" Blocking");
        mRButton.setOnClickListener(e -> {
            /** Here as observable created by filter emits 0 ,1 , 2 .. each after gap of 500ms so it receives them but and onNext method
             * of observer is called but it doesn't log the values because of the doing too much work on main thread and skipping frames
             * so if we subscribe it on a new thread then values will be logged properly and there is mo boundation that method inside onCreate
             * should complete
             * */

            log("blocking:" + filterObserver().first(i -> i > 1));
        });
    }

    /**In case of just operator only it emits those items immediately and the first value > 1 is 2 so 2 in instantaneously logged
     * after button click
     * */
    private Observable<Integer> firstObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).first(i -> i > 1);
    }

    private BlockingObservable<Integer> filterObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!subscriber.isUnsubscribed()) {
                        log("onNext:" + i);
                        subscriber.onNext(i);
                    }
                }
                // this is just to ensure that after all emiited items on Completed is called
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        }).toBlocking();
    }
}
