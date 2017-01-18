package pranav.apps.amazing.rxoperators.ConditionalBoolean;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class DefaultIfEmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("empty");
        mLButton.setOnClickListener(e -> emptyObserver().subscribe(i -> log("empty:" + i)));
        mRButton.setText("notEmpty");
        mRButton.setOnClickListener(e -> notEmptyObserver().subscribe(i -> log("notEmpty:" + i)));
    }

    /** (calling onNext method of subscriber can be called emission)
     *  as in this case observable has nothing to emit so as specified it emits 10
     * */
    private Observable<Integer> emptyObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(10);
    }
    /** here i is emitted so default condition is not used
     * */
    private Observable<Integer> notEmptyObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(10);
    }
}


