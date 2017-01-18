package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class UsingOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable<Long> observable = usingObserver();
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("onError");
            }

            @Override
            public void onNext(Object o) {
                log("onNext"+o);
            }
        };
        mLButton.setText("using");
        mLButton.setOnClickListener(e -> observable.subscribe(subscriber));
        mRButton.setText("unSubscrib");
        mRButton.setOnClickListener(e -> subscriber.unsubscribe());
    }
    /** tom create a dependent resource object
     *
     Parameters:
     resourceFactory - the factory function to create a resource object that depends on the Observable
     observableFactory - the factory function to create an Observable
     disposeAction - the function that will dispose of the resource
     Returns:
     the Observable whose lifetime controls the lifetime of the dependent resource object
     *
     * As soon as animal is created then is starts emitting items so animal eat is logged and as soon as 5 sec timer is up
     * it gets released
     * */
    private Observable<Long> usingObserver() {
        return Observable.using(() -> new Animal(), i -> Observable.timer(5000, TimeUnit.MILLISECONDS), o -> o.relase());
    }

    private class Animal {
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                log("animal eat");
            }
        };

        public Animal() {
            log("create animal");
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(subscriber);
        }

        public void relase() {
            log("animal released");
            subscriber.unsubscribe();
        }
    }
}
