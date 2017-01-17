package pranav.apps.amazing.rxoperators.ErrorHandlingOperators;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class OnExceptionOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("onException-true");
        mLButton.setOnClickListener(e -> onExceptionResumeObserver(true).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onException-true-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onException-true-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onException-true-onNext:" + s);
            }
        }));
        mRButton.setText("onException-false");
        mRButton.setOnClickListener(e -> onExceptionResumeObserver(false).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onException-false-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onException-false-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onException-false-onNext:" + s);
            }
        }));
    }

    private Observable<String> onExceptionResumeObserver(boolean isException) {
        return createObserver(isException).onExceptionResumeNext(Observable.just("7", "8", "9"));
    }


    private Observable<String> createObserver(Boolean createExcetion) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= 6; i++) {
                    if (i < 3) {
                        subscriber.onNext("onNext:" + i);
                    }
                    /**Throwable is super class of Exception as well as Error. In normal cases we should
                     * always catch sub-classes of Exception
                     *
                     * Error is programmatically unrecoverable in any way and is usually not to be caught,
                     * except for logging purposes (which passes it through again). Exception is programmatically recoverable.
                     * Its subclass RuntimeException indicates a programming error and is usually not to be caught as well.
                     * */
                    else if (createExcetion) {
                        subscriber.onError(new Exception("Exception"));
                    } else {
                        subscriber.onError(new Throwable("Throw error"));

                    }
                }
            }
        });
    }
}
