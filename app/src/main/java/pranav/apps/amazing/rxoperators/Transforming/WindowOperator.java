package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class WindowOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("window");
        mLButton.setOnClickListener(e -> windowCountObserver().subscribe(i -> {
            log(i);
            i.subscribe((j -> log("window:" + j)));
        }));
        mRButton.setText("Time");
        mRButton.setOnClickListener(e -> wondowTimeObserver().subscribe(i -> {
            log(i);
            i.observeOn(AndroidSchedulers.mainThread()).subscribe((j -> log("wondowTime:" + j)));
        }));
    }
    /** observing emitted items over a window specified by count or timer
     * */
    private Observable<Observable<Integer>> windowCountObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).window(3);
    }

    private Observable<Observable<Long>> wondowTimeObserver() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
                .window(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }


}