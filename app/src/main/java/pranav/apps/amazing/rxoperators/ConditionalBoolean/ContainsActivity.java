package pranav.apps.amazing.rxoperators.ConditionalBoolean;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class ContainsActivity extends BaseActivity {
    boolean tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("contains");
        mLButton.setOnClickListener(e -> containsObserver().subscribe(i -> log("contains:" + i)));
        mRButton.setText("isEmpty");
        mRButton.setOnClickListener(e -> defaultObserver().subscribe(i -> log("isEmpty:" + i)));
    }
   /** emits true if contained and otherwise false
    * */
    private Observable<Boolean> containsObserver() {
        if (tag) {
            return Observable.just(1, 2, 3).contains(3);
        }
        tag = true;
        return Observable.just(1, 2, 3).contains(4);
    }
    /** return true as observable does not emit anything
     * */
    private Observable<Boolean> defaultObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).isEmpty();
    }
}


