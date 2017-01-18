package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class GroupbyOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("groupBy");
        mLButton.setOnClickListener(e -> groupByObserver().subscribe(new Subscriber<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, Integer> groupedObservable) {
                groupedObservable.count().subscribe(integer -> log("key" + groupedObservable.getKey() + " contains:" + integer + " numbers"));
            }
        }));
        mRButton.setText("groupByKeyValue");
        mRButton.setOnClickListener(e -> groupByKeyValueObserver().subscribe(new Subscriber<GroupedObservable<Integer, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, String> integerIntegerGroupedObservable) {
                if (integerIntegerGroupedObservable.getKey() == 0) {
                    integerIntegerGroupedObservable.subscribe(integer -> log(integer));
                }
            }
        }));
    }
    /**Note: A GroupedObservable will cache the items it is to emit until such time as it is subscribed to. For this reason,
     *  in order to avoid memory leaks, you should not simply ignore those GroupedObservables that do not concern you.
     *  Instead, you can signal to them that they may discard their buffers by applying an operator like take(0) to them.
     *
     * this function present inside is used to assign keys as those which are divisible by 2 are grouped with key 0 and
     * others are grouped with key 1
     *
     */
    private Observable<GroupedObservable<Integer, Integer>> groupByObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer%2;
            }
        });
        //return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(integer -> integer % 2);
    }
    /**a function that extracts the return element for each item is also present in this below case
     * i.e along with each key the element is also emitted while in previous case it was not so we were only able to calculate
     * the count of items in observable
     * */
    private Observable<GroupedObservable<Integer, String>> groupByKeyValueObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer%2;
            }
        }, new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "groupByKeyValue"+integer;
            }
        });
        /*return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(integer -> integer % 2, integer -> "groupByKeyValue:" + integer);*/
    }
}