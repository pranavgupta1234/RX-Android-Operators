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
        /**For the case of sample we see that after setting up the observable as soon as we subscribe method inside create is called
         * remember that time window for sample is of 1 sec so thread sleeps for 200 sec no item is emitted just after subscription
         * and after 200 ms 0 is emitted and then thread sleeps and so on and hence the last item it receives is at 800ms which is 3 and this
         * is passed to the observer but remember one can also see in the app that the value are logged onto the screen only after
         * (200/1000)*20 = 4 sec but one can see that in the logcat the values are logged immediately but not on app and our app UI is
         * sluggish . The logcat says that application in doing too much work on its main thread so if we apply subscribeOn method to
         * subscribe to a new scheduler thread than this error and inconsistency will be gone and values will be logged simultaneously
         * And also the though that we were thinking that maybe method inside create is needed to be completed totally is wrong
         * */

        mLButton.setOnClickListener(view ->{
            if(subscription!=null){
                subscription.unsubscribe();
            }
            subscription = createObserver().sample(1000, TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("sample : "+ integer);
            }
        });});
        mRButton.setText("throttleFirst");
        /**Returns an Observable that emits only the first item emitted by the source
         * Observable during sequential time windows of a specified duration.
         * */
        /**In the case of throttle with time window of 1000ms first items in 4 windows of 1 sec each over a time span of 4 sec
         * as mentioned in the method inside create method the values are logged as 0,5,10,15 and are logged all at a same time after
         * 4 sec have passes and observer's onCompleted method is called
         * */

        mRButton.setOnClickListener(view -> {
            if(subscription!=null){
                subscription.unsubscribe();
            }
            subscription = createObserver().throttleFirst(1000,TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("throttle first : "+ integer);
            }
        });});
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<20;i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription!=null){
            subscription.unsubscribe();
        }
    }
}
