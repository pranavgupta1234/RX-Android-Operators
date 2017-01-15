package pranav.apps.amazing.rxoperators.CreatingObserver;

import android.os.Bundle;

import java.util.Random;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Pranav Gupta on 1/16/2017.
 */

public class CreateAndRangeOperators extends BaseActivity{

    private Subscription _subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("CreateOperator");
        /**In all the cases when we create a model of an observer in form of Action1<T> (is basically onNext of an actual observer)
         * and an observer should take care of all three actions which also include onError and onCompleted
         * So this type of observer can throw an error like onErrorNotImplemented Error so should instantiate the whole
         * observer with all its methods to be safe
         * */
        mLButton.setOnClickListener(view -> {
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            _subscription = createObservable().subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    log("onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log("on Error" + e.getMessage());
                }

                @Override
                public void onNext(Integer integer) {
                    log("onNext : "+integer);
                }
            });
        });

        mRButton.setText("RangeOperator");
        mRButton.setOnClickListener(view ->{
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            mResultView.setText("");
            _subscription= rangeObservable().subscribe(integer -> {
            log(String.valueOf(integer));
            });
        });
    }

    /** range(n,m) operator returns a observable which starts from n and emits consecutive number till m-1
     * i.e m values are emitted
     * */
    private Observable<Integer> rangeObservable() {
        return Observable.range(10,5);
    }

    private Observable<Integer> createObservable() {
        /** create returns an observable which will execute the specified function when any observer subscribes to it
         *  We can control the flow of emitted items before being processed by observer
         * */
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    for(int i=0;i<10;i++){
                        int temp = new Random().nextInt(10);
                        if(temp>8){
                            subscriber.onError(new Throwable("value>8"));
                        }
                        else {
                            //call subscriber's onNext method with this temp value and then it will process it
                            subscriber.onNext(temp);
                        }

                        if(temp == 9){
                        //calling subscriber's onCompleted method and its not the order given by observable
                            subscriber.onCompleted();
                        }
                    }
                }

            }
        });
    }
}
