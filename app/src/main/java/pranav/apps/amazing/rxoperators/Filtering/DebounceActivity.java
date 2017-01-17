package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DebounceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("throttleWithTimeout");
        mLButton.setOnClickListener(e -> throttleWithTimeoutObserver().subscribe(i -> log("throttleWithTimeout:" + i)));
        mRButton.setText("debounce");
        mRButton.setOnClickListener(e -> debounceObserver().subscribe(i -> log("debounce:" + i)));
    }
    /**debounce operator drop the items which are emitted in a specified time window
     * */
    private Observable<Integer> debounceObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).debounce(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                log(integer);
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if (integer % 2 == 0 && !subscriber.isUnsubscribed()) {
                            log("complete:" + integer);
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread());



        /*debounce(integer -> {
            log(integer);
            return Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    if (integer % 2 == 0 && !subscriber.isUnsubscribed()) {
                        log("complete:" + integer);
                        subscriber.onCompleted();
                    }
                }
            });
        })*/
             //
    }

    private Observable<Integer> throttleWithTimeoutObserver() {
        return createObserver().throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());

    }
    /** please check marble diagram for better understanding throttleWithTimeout(x)
     * In this case 0 emitted wait 300, 1 emitted wait 100 , 2 emitted wait 100 , 3 emitted wait 300 , 4 emitted wait 100 , 5 emitted
     * wait 100, 6 emitted wait 200 ..... i.e after each n%3 == 0 we have to wait 300ms otherwise 100sec , also out throttle window
     * is of 200ms.
     * Let us start at t=0;
     * Now as soon as 0 emitted window of 200ms is considered and no other item is emitted during this duration so 0 is logged
     * Now at t = 300ms 1 is passes and in timer window of 200ms extending from 300ms to 500ms we have 2 emitted at 400ms so by
     * rule of throttle timeout an other item is emitted in the specified time window so it will not log this and same happens with 2
     * because in its time window 3 is emitted but 3 gets logged because in its time window nothing is emitted
     * */
    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation());
    }
}