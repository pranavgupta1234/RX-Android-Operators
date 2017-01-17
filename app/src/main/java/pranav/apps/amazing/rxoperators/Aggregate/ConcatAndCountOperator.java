package pranav.apps.amazing.rxoperators.Aggregate;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;


public class ConcatAndCountOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Concat");
        mLButton.setOnClickListener(e -> concatObserver().subscribe(i -> log("concat:" + i)));
        mRButton.setText("Count");
        mRButton.setOnClickListener(e -> countObserver().subscribe(i -> log("count:" + i)));
    }
    /**
     an Observable that emits items emitted by the three source Observables, one after the other, without interleaving them
     so in this case it will log 4,5,6,1,2,3,7,8,9 immediately after the button click
     * */
    private Observable<Integer> concatObserver() {
        Observable<Integer> obser1 = Observable.just(1, 2, 3);
        Observable<Integer> obser2 = Observable.just(4, 5, 6);
        Observable<Integer> obser3 = Observable.just(7, 8, 9);
        return Observable.concat(obser2, obser1, obser3);
    }
    /**emits the number of items emitted by source observable
     * here it will give 3
     * */
    private Observable<Integer> countObserver() {
        return Observable.just(1, 2, 3).count();
    }
}


