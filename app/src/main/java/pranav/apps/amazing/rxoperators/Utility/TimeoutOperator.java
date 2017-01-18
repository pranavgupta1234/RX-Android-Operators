package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TimeoutOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Timeout");
        mLButton.setOnClickListener(e -> timeoutObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                log(e);
            }

            @Override
            public void onNext(Integer integer) {
                log("timeout:" + integer);
            }
        }));
        mRButton.setText("Timeoutobserver");
        mRButton.setOnClickListener(e -> timeoutobserverObserver().subscribe(i -> log(i)));
    }
    /** timeout returns an observable that if in a particular time window as mentioned in the param of timeout if the source observable
     * does not emit an item then a timeout exception occurs and then observable terminates and onError method is called with the
     * timeout exception throwable
     * Also as one can see in app that in this case the values are logged only after timeout exception occurs
     * while in case of logcat the the values are logged each time the onNext method is called, and I think the reason behind this
     * is that due to rapid logging it skips the small changes and finally plots the result but still not sure , try to look for this
     * reason
     * */
    private Observable<Integer> timeoutObserver() {
        return createObserver().timeout(1000, TimeUnit.MILLISECONDS);
    }
    /**Returns an Observable that mirrors the source Observable but applies a timeout policy for each emitted item. If the next
     * item isn't emitted within the specified timeout duration starting from its predecessor, the resulting Observable begins
     * instead to mirror a fallback Observable.
     * Here in this case as initially 0 and 1 are logged and but now due to timeout our control falls to another callback
     * which just emits 5,6
     * Note this very very very important thing:
     * As we remove .subscribeOn(Schedulers.newthread()) we can see that nothing comes up on android screen but values are logged
     * in logcat , till now we thought the values are logged only after create function does not execute fully but this is due to
     * another mistake
     *
     * If one removes this subscribeOn and then runs and sees logcat that it says skipped 400+ frames and application is doing
     * too much work on its main thread and this is the reason why android screen does not show anything even its updated
     * This type of things cause sluggishness in UI and is bad
     * So when we subscribe it on new thread then work of application is released and values on logcat and screen are simultaneously
     * logged
     * http://stackoverflow.com/questions/14678593/the-application-may-be-doing-too-much-work-on-its-main-thread (read this for more)
     *
     * SO IN ALL CASES WHERE WE THOUGHT THAT FUNCTION INSIDE CREATE IS NEEDED TO BE COMPLETED THEN ONLY VALUES ARE LOGGED IS WRONG
     * THIS IS BECAUSE OF DOINF TOO MUCH WORK ON MAIN THREAD AND SKIPPING FRAMES
     * */
    private Observable<Integer> timeoutobserverObserver() {
        return createObserver().timeout(200, TimeUnit.MILLISECONDS, Observable.just(5, 6)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 12; i++) {
                    try {
                        Thread.sleep(i * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
    }
}
