package pranav.apps.amazing.rxoperators.Combining;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Pranav Gupta on 1/16/2017.
 */

public class CombineLatest extends BaseActivity{
    private Subscription _subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("CombineList");
        mLButton.setOnClickListener(view -> {
            mResultView.setText("");
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            _subscription = combineListObservables().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("CombineList :" +integer);
            }
        });});
        mRButton.setText("CombineLatest");
        mLButton.setOnClickListener(view -> {
            mResultView.setText("");
            if(_subscription!=null){
                _subscription.unsubscribe();
            }
            _subscription = combineLatest().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("Combine Latest: "+integer);
            }
        });});
    }

    private Observable<Integer> createObserver(int index){
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=1;i<6;i++){
                    subscriber.onNext(i*index);
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
    /** Combines two source Observables by emitting an item that aggregates the latest values of each of the source Observables
     *  each time an item is received from either of the source Observables, where this aggregation is defined by a specified function.
     * called aggregate function
     * */
    private Observable<Integer> combineLatest(){
        return Observable.combineLatest(createObserver(1), createObserver(2), (integer, integer2) -> {
            log("left:" + integer + " right:" + integer2);
            return integer + integer2;
        });
    }

    List<Observable<Integer>> list = new ArrayList<>();
    /** Combines a list of source Observables by emitting an item that aggregates the latest values of each of the
     * source Observables each time an item is received
     *  from any of the source Observables, where this aggregation is defined by a specified function called aggregate function
     * */
    private Observable<Integer> combineListObservables() {

        /** Creates a list of integer observables
         * */
         for(int i=1;i<5;i++){
             list.add(createObserver(i));
         }
        /** Combining all observables in list and here before combining into single observable it gets and sums all the items in
         * the list and in this it adds to 10 i.e temp =10*/
         return Observable.combineLatest(list,args ->{
             int temp = 0;
             for (Object i : args) {
                 log(i);
                 temp += (Integer) i;
             }
             return temp;
         });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!_subscription.isUnsubscribed()) {
            _subscription.unsubscribe();
        }
    }
}
