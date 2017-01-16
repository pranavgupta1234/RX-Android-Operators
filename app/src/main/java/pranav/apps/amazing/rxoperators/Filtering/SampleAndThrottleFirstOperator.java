package pranav.apps.amazing.rxoperators.Filtering;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Pranav Gupta on 1/16/2017.
 */

public class SampleAndThrottleFirstOperator extends BaseActivity {

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Sample");
        /** sample operator turns our observable into an observable which emits the most recent item emitted by the original observable
         * in a given time window
         * */
        mLButton.setOnClickListener(view ->  createObserver().sample(1000, TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("sample : "+ integer);
            }
        }));
        mRButton.setText("throttleFirst");
        /**Returns an Observable that emits only the first item emitted by the source
         * Observable during sequential time windows of a specified duration.
         * */
        mRButton.setOnClickListener(view -> createObserver().throttleFirst(1000,TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("throttle first : "+ integer);
            }
        }));
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<20;i++){
                    try{
                        Thread.sleep(200);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }

            }
        });
    }
}
