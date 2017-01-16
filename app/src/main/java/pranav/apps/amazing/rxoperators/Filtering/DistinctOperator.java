package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class DistinctOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("distinct");
        mLButton.setOnClickListener(e -> distinctObserver().subscribe(i -> log("distinct:" + i)));
        mRButton.setText("UntilChanged");
        mRButton.setOnClickListener(e -> distinctUntilChangedObserver().subscribe(i -> log("UntilChanged:" + i)));
    }
    /** all distinct values are immediately logged on button click without any lag
     * */
    private Observable<Integer> distinctObserver() {
        return Observable.just(1, 2, 3, 4, 5, 4, 3, 2, 1).distinct();

    }
    /** All values different from their previous emitted value are immediately logged
     * */
    private Observable<Integer> distinctUntilChangedObserver() {
        return Observable.just(1, 2, 3, 3, 3, 1, 2, 3, 3).distinctUntilChanged();

    }


}
