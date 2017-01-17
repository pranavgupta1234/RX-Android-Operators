package pranav.apps.amazing.rxoperators.Aggregate;

import android.os.Bundle;

import java.util.ArrayList;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

public class ReduceOperator extends BaseActivity {
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            list.add(2);
        }
        mLButton.setText("reduce");
        mLButton.setOnClickListener(e -> reduceObserver().subscribe(i -> log("reduce:" + i)));
        mRButton.setText("collect");
        mRButton.setOnClickListener(e -> collectObserver().subscribe(i -> log("collect:" + i)));
    }
    /** reduce recursively passes the result into chain like here with ten 2's in list the first two picked and multiplied and
     * now x=4, y = 2 (next element) so now x = 8 and y = 2(next) ..... end result is 2^10 = 1024 and this value is logged
     * immediately after button click
     * */
    private Observable<Integer> reduceObserver() {
        return Observable.from(list).reduce((x, y) -> x * y);
    }
    /** from converts the list into an observable that emits its contents
     * collect :
     * Collects items emitted by the source Observable into a single mutable data structure and returns an Observable that emits
     * this structure.
     * */

    /** for collect :
     * @param Ist parameter is the mutable data structure in which the buffered items will be stored
     *  Note: Because these operations must wait for the source Observable to complete emitting items before they can construct
     *            their own emissions (and must usually buffer these items), these operators are dangerous to use on Observables
     *            that may have very long or infinite sequences.
     * @param IInd parameter is the function through which we bridge the working between the two observables i.e it receives
     *             the items emitted by first observable and modify them for 2nd type of mutable structure which will emit this
     *             structure
     * */
    private Observable<ArrayList<Integer>> collectObserver() {
        return Observable.from(list).collect(new Func0<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> call() {
                return new ArrayList<Integer>();
            }
        }, new Action2<ArrayList<Integer>, Integer>() {
            @Override
            public void call(ArrayList<Integer> integers, Integer integer) {
                integers.add(integer);
            }
        });
        //lambda expression for above
        // return Observable.from(list).collect(() -> new ArrayList<>(), (integers, integer) -> integers.add(integer));
    }

}

