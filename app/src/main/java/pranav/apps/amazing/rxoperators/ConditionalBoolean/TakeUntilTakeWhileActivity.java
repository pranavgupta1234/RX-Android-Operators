package pranav.apps.amazing.rxoperators.ConditionalBoolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class TakeUntilTakeWhileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("takeUntil");
        mLButton.setOnClickListener(e -> takeUntilObserver().subscribe(i -> log("takeUntil:" + i)));
        mRButton.setText("takeWhile");
        mRButton.setOnClickListener(e -> takeWhileObserver().subscribe(i -> log("takeWhile:" + i)));
    }
    /** take values emitted by source observable until first observer does not emit item but once it does its get completed
     * */
    private Observable<Long> takeUntilObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.timer(3, TimeUnit.SECONDS));
    }
    /** its takes value until the emitted values by source observable met the condition specified
     * */
    private Observable<Long> takeWhileObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).takeWhile(aLong -> aLong < 5);
    }
}


