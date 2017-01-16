package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class ElementAtAndFilterOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("ElementAt");
        mLButton.setOnClickListener(e -> elementAtObserver().subscribe(i -> log("elementAt:" + i)));
        mRButton.setText("Filter");
        mRButton.setOnClickListener(e -> filterObserver().subscribe(i -> log("Filter:" + i)));
    }
    /**observable made by just emits the item instantaneously so element at 2 i.e 2 (0th indexing) is immediately logged after
     * button click
     * */
    private Observable<Integer> elementAtObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).elementAt(2);
    }
    /** the filtered values are immediately passed to the observer and all values are logged instantaneously
     * */
    private Observable<Integer> filterObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).filter(i -> i < 3);
    }


}
