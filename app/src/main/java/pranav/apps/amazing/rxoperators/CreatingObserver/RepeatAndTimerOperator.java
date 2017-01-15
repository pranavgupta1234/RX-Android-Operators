package pranav.apps.amazing.rxoperators.CreatingObserver;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class RepeatAndTimerOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("repeat");
        mLButton.setOnClickListener(e -> repeatObserver().subscribe(i -> log("repeat:" + i)));
        mRButton.setText("timer");
        mRButton.setOnClickListener(e -> timerObserver().subscribe(i -> log("timer:" + i)));
    }

   /**just makes an observable that emits 1 and then repeat(n) transforms it into an observable which emits the items emitted
    * by source observable n times
    * */
    private Observable<Integer> repeatObserver() {
        return Observable.just(1).repeat(5);
    }
    /** timer return an observable which emits an item after the specified delay
     * */
    private Observable<Long> timerObserver() {
        //timer by default operates on the computation Scheduler
        return Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }


}
