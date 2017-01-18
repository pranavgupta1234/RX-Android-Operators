package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;

import java.util.ArrayList;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.functions.Func1;

public class FlatMapOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("flatMap");
        mLButton.setOnClickListener(e -> flatMapObserver().subscribe(i -> log(i)));
        mRButton.setText("flatMapIterable");
        mRButton.setOnClickListener(e -> flatMapIterableObserver().subscribe(i -> log("flatMapIterable:" + i)));
    }
    /**Returns an Observable that emits items based on applying a function that you supply to each item emitted by the source
     *  Observable, where that function returns an Observable, and then merging those resulting Observables and emitting the
     *  results of this merger.
     *  Remember that it does not emit the observables as emissions but it emits items from the observable formed from source
     *  observable's items.As here as 1 is emitted now that is converted into an observable which emits flatmap:1 and yoyo as
     *  emissions ans these values are logged
     * */
    private Observable<String> flatMapObserver() {
        return Observable.just(1,2,3,4,5,6,7,8,9).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.just("flatmap"+integer);
            }
        });

        //return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).flatMap(integer -> Observable.just("flat map:" + integer,"yoyo"));
    }
    /** in this case an item is received from the source observable and is processed inside the function
     * and that single item is used to make an iterable and then that iterable is emitted
     *
     * */
    private Observable<? extends Integer> flatMapIterableObserver() {

        return Observable.just(1,2,3,4,5,6,7,8,9).flatMapIterable(new Func1<Integer, Iterable<? extends Integer>>() {
            @Override
            public Iterable<? extends Integer> call(Integer integer) {
                ArrayList<Integer> s = new ArrayList<Integer>();
                for(int i=0;i<integer;i++){
                    s.add(integer);
                }
                return s;
            }
        });

        /*return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .flatMapIterable(
                        integer -> {
                            ArrayList<Integer> s = new ArrayList<>();
                            for (int i = 0; i < integer; i++) {
                                s.add(integer);
                            }
                            return s;
                        }
                );*/
    }


}