package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;

import java.util.ArrayList;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class FlatMapOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("flatMap");
        mLButton.setOnClickListener(e -> flatMapObserver().subscribe(i -> log(i)));
        mRButton.setText("flatMapIterable");
        mRButton.setOnClickListener(e -> flatMapIterableObserver().subscribe(i -> log("flatMapIterable:" + i)));
    }

    private Observable<String> flatMapObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).flatMap(integer -> Observable.just("flat map:" + integer,"yoyo"));
    }

    private Observable<? extends Integer> flatMapIterableObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .flatMapIterable(
                        integer -> {
                            ArrayList<Integer> s = new ArrayList<>();
                            for (int i = 0; i < integer; i++) {
                                s.add(integer);
                            }
                            return s;
                        }
                );
    }


}