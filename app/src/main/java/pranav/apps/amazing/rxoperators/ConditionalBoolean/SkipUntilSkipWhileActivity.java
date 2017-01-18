package pranav.apps.amazing.rxoperators.ConditionalBoolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class SkipUntilSkipWhileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("skipUntil");
        mLButton.setOnClickListener(e -> skipUntilObserver().subscribe(i -> log("skipUntil:" + i)));
        mRButton.setText("skipWhile");
        mRButton.setOnClickListener(e -> skipWhileObserver().subscribe(i -> log("skipWhile:" + i)));
    }
    /** skips value emitted by source observable until the specified inner observable does not emit a single item
     * */
    private Observable<Long> skipUntilObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).skipUntil(Observable.timer(3, TimeUnit.SECONDS));
    }
    /** skip all items which met the condition specified so first value logged here is 5
     * */
    private Observable<Long> skipWhileObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).skipWhile(aLong -> aLong < 5);
    }
}


