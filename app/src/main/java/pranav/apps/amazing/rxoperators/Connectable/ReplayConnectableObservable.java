package pranav.apps.amazing.rxoperators.Connectable;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by Pranav Gupta on 1/15/2017.
 */

public class ReplayConnectableObservable extends BaseActivity{
    private Subscription _subscription;
    private ConnectableObservable<Long> connectableObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**ensure that all observers see the same sequence of emitted items,
         *  even if they subscribe after the Observable has begun emitting items
         * */
        Action1 action2 = new Action1() {
            @Override
            public void call(Object o) {
                log("Action2 : "+ o);
            }
        };
        Action1 action1 = new Action1() {
            @Override
            public void call(Object o) {
                log("Action 1: "+ o);
                if((long)o== 5){
                    connectableObservable.subscribe(action2);
                }
            }
        };

        mLButton.setText("Relay Count");
        mLButton.setOnClickListener(view -> {
            mResultView.setText("");
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            connectableObservable = relayConnectableCount();
            connectableObservable.subscribe(action1);
            log("Relay Count");
            _subscription = connectableObservable.connect();
            
        });
        mRButton.setText("Relay Timer");
        mRButton.setOnClickListener(view -> {
            mResultView.setText("");
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            connectableObservable = relayConnectableTimer();
            connectableObservable.subscribe(action1);
            log("Relay Timer");
            _subscription = connectableObservable.connect();
        });
    }
    /**If you apply the Replay operator to an Observable before you convert it into a connectable Observable,
     *  the resulting connectable Observable will always emit the same complete sequence to any future observers,
     *  even those observers that subscribe after the connectable Observable has begun to emit items to other
     *  subscribed observers.
     * */

    /** For example in this case as replay is passed 5 so as we click RelayCount action1 observer will receive 1,2,3,4,5 and as soon
     * it receives 5 it gets subscribed to action 2 also and replay makes sure that last emitted 5 things should also be received by
     * action two observer so he will receive all of them and from m=next second they will synchronise to get the further emitted
     * events
     * */
    private ConnectableObservable<Long> relayConnectableCount() {
        Observable<Long> obs= Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread());
        return obs.replay(5);
    }
    /**In this below case replay will make sure that last things in a time window of 3 seconds are also received by action 2
     * and from there onwards they will synchronise
     * */

    private ConnectableObservable<Long> relayConnectableTimer(){
        Observable<Long> obs = Observable.interval(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread());
        return obs.replay(3,TimeUnit.SECONDS);
    }
}
