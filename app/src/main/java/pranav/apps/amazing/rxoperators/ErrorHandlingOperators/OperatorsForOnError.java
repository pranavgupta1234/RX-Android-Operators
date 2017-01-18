package pranav.apps.amazing.rxoperators.ErrorHandlingOperators;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class OperatorsForOnError extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("onErrorReturn");
        mLButton.setOnClickListener(e -> onErrorReturnObserver().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onErrorReturn-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onErrorReturn-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onErrorReturn-onNext:" + s);
            }
        }));
        mRButton.setText("onErrorResume");
        mRButton.setOnClickListener(e -> onErrorResumeNextObserver().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onErrorResume-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onErrorResume-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onErrorResume-onNext:" + s);
            }
        }));
    }
    /**Instructs an Observable to emit an item (returned by a specified function)
     * rather than invoking onError if it encounters an error.
     * Here in our example firstly the values 1 and 2 will ne logged and then as it encounters error it will pass the item
     * specifies in function (in this case it is "onErrorReturn" to the **onNext** method and remember onError method is not invoked
     * and after it it calls onCompleted
     * */
    private Observable<String> onErrorReturnObserver() {
        return createObserver().onErrorReturn(throwable -> "onErrorReturn");
    }
    /**
     * By default, when an Observable encounters an error that prevents it from emitting the expected item to its Observer,
     * the Observable invokes its Observer's onError method, and then quits without invoking any more of its Observer's methods.
     * The onErrorResumeNext method changes this behavior. If you pass another Observable (resumeSequence) to an Observable's
     * onErrorResumeNext method, if the original Observable encounters an error, instead of invoking its Observer's onError
     * method, it will instead relinquish control to resumeSequence which will invoke the Observer's onNext method if it is
     * able to do so. In such a case, because no Observable necessarily invokes onError, the Observer may never know that
     * an error happened.
     * so in this case values 1 ,2 will be passes and then 7,8,9 will be passes in onNext method of observer (it doesn't know that
     * error has occured and then onCompleted will be called
     * */
    private Observable<String> onErrorResumeNextObserver() {
        return createObserver().onErrorResumeNext(Observable.just("7", "8", "9"));
    }

    private Observable<String> createObserver() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= 6; i++) {
                    if (i < 3) {
                        subscriber.onNext("onNext:" + i);
                    } else {
                        subscriber.onError(new Throwable("Throw error"));
                    }
                }
            }
        });
    }
}
