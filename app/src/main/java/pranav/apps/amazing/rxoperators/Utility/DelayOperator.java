package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DelayOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLButton.setText("delay");
        mLButton.setOnClickListener(e -> {
            log("start subscrib:" + getCurrentTime());
            delayObserver().subscribe(i -> log("delay:" + (getCurrentTime() - i)));
        });
        mRButton.setText("delaySubscription");
        mRButton.setOnClickListener(e -> {
            log("start subscrib:" + getCurrentTime());
            delaySubscriptionObserver().subscribe(i -> log("delaySubscription:" + i));
        });
    }
    /** delay converts the source observable into an observable that delays the item emitted by the source by the time amount
     * specified and this time window starts as soon as the source observable emits an item
     *
     * so in this case value 1 and 2 are emitted at 0ms and 1000ms and after delay of 2000ms they will be logged
     * after t=2000ms and t= 3000ms i.e 2nd will be logged 1 sec after Ist
     * */
    private Observable<Long> delayObserver() {
        return createObserver(2).delay(2000, TimeUnit.MILLISECONDS);
    }
    /** It delays the subscription by 2sec
     *
     * */
    private Observable<Long> delaySubscriptionObserver() {
        return createObserver(2).delaySubscription(2000, TimeUnit.MILLISECONDS);
    }
    /** in this case as we can see that as soon as we subscribe the item 1 is emitted but due to delayed subscription value of
     * even subscrib is also logged after 2 sec so this shows that function inside create is only executed after subscription
     * and then the observable behaves simply accordingly
     * */
    private Observable<Long> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                log("subscrib:" + getCurrentTime());
                for (int i = 1; i <= index; i++) {
                    subscriber.onNext(getCurrentTime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    private long getCurrentTime() {
        return System.currentTimeMillis()/1000;
    }

}
