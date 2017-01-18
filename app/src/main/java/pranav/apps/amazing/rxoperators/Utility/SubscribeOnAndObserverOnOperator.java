package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribeOnAndObserverOnOperator extends BaseActivity {

    /**
     *This executes the Observable on a new thread, which emits results through onNext on the main UI thread.
     *i.e all observing work id done on a new thread which will not block our UI main thread and when onNext method
     * of observer will be called then changes are done on main UI thread (UI thread is the main thread)
     *
     * Observable.from("one", "two", "three", "four", "five")
     .subscribeOn(Schedulers.newThread())
     .observeOn(AndroidSchedulers.mainThread())
     .subscribe(Action1)
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("observerOn");
        mLButton.setOnClickListener(e -> observerOnObserver().subscribe(i -> log("observerOn:" + Thread.currentThread().getName())));
        mRButton.setText("subscribeOn");
        mRButton.setOnClickListener(e -> subscribeOnObserver().subscribe(i -> log("subscribeOn:" + Thread.currentThread().getName()+" value "+i)));
    }

    private Observable<Integer> observerOnObserver() {
        return createObserver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    private Observable<Integer> subscribeOnObserver() {
        return createObserver()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.immediate());
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("on subscrib:" + Thread.currentThread().getName());
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        });
    }
}
