package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class SkipAndTakeOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Skip");
        mLButton.setOnClickListener(e -> skipObserver().subscribe(i -> log("Skip:" + i)));
        mRButton.setText("Take");
        mRButton.setOnClickListener(e -> takeObserver().subscribe(i -> log("Take:" + i)));
    }

    private Observable<Integer> skipObserver() {
        /**Skips the first two items
         * As far as I had concluded that when an observable is subscribed with out any operator like debounce , delay , interval ,
         * as in this case then the items are emitted at the clock rate of processor so in this case one can see that just after
         * click on skip button all values( 2,3,4,5) are instantaneously printed
         * */
        return Observable.just(0, 1, 2, 3, 4, 5).skip(2);
    }
    /** turns it into an observable that only reports only first given n items to the observer to process and after that
     * calls the observer's onCompleted method
     * */
    private Observable<Integer> takeObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).take(2);
    }


}
