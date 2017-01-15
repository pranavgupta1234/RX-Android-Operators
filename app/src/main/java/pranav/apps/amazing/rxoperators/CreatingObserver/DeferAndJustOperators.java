package pranav.apps.amazing.rxoperators.CreatingObserver;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscription;

public class DeferAndJustOperators extends BaseActivity {

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable<Long> deferObservable = DeferObserver();
        Observable<Long> justObservable = JustObserver();
        mLButton.setText("Defer");
        mRButton.setText("Just");
        mLButton.setOnClickListener(e -> {
            mResultView.setText("");
            if(subscription!=null){
                subscription.unsubscribe();
            }
            subscription = deferObservable.subscribe(time -> log("defer:" + time));
        });
        mRButton.setOnClickListener(e -> {
            mResultView.setText("");
            if(subscription!=null){
                subscription.unsubscribe();
            }
            subscription = justObservable.subscribe(time -> log("just:" + time));
        });
    }
    /**Returns an Observable that calls an Observable factory to create an Observable for each new Observer that subscribes. That is,
     * for each subscriber, the actual Observable that subscriber observes is determined by the factory function
     *
     * The defer Observer allows you to defer or delay emitting items from an Observable until such time as an Observer
     * subscribes to the Observable. This allows an Observer to easily obtain updates or a refreshed version of the sequence
     * */

    private Observable<Long> DeferObserver() {
        return Observable.defer(() -> Observable.just(System.currentTimeMillis()));
    }

    /**returns an observable which emits a single item and then completes
     * */
    private Observable<Long> JustObserver() {
        return Observable.just(System.currentTimeMillis());
    }


}
