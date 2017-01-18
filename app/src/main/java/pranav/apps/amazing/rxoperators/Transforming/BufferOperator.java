package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;

import java.util.List;
import java.util.concurrent.TimeUnit;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class BufferOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("buffer");
        mLButton.setOnClickListener(e -> bufferObserver().subscribe(i -> log("buffer:" + i)));
        mRButton.setText("bufferTime");
        mRButton.setOnClickListener(e -> bufferTimeObserver().subscribe(i -> log("bufferTime:" + i)));
    }
    /** buffer(n,m) operator makes buffer of sizes n and skips every mth item , so here is makes group of (1,2) and emits (after
     * emission buffer is emptied)and skips 3 then buffer is filled with 4,5 and then emptied with 6 skipped and so on....
     * Note that in case n=m the just acts as buffer(n) i.e it makes buffer of n (= m) items and emits them.
     * */
    private Observable<List<Integer>> bufferObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8,9).buffer(2, 3);
    }
    /**Returns an Observable that emits buffers of items it collects from the source Observable. The resulting Observable
     *  emits connected, non-overlapping buffers, each of a fixed duration specified by the timespan argument. When the
     *  source Observable completes or encounters an error, the resulting Observable emits the current buffer and propagates
     *  the notification from the source Observable.
     * */
    /**one important result to follow from the case below is that the first buffer emitted by this will contain 0,1 because
     * interval operator emits 0 after 1 sec and not immediately
     * */

    private Observable<List<Long>> bufferTimeObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).buffer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }

}
