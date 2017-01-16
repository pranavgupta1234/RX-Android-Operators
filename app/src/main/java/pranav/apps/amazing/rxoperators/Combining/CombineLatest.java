package pranav.apps.amazing.rxoperators.Combining;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Pranav Gupta on 1/16/2017.
 */

public class CombineLatest extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("CombineLsit");
        mLButton.setOnClickListener(view -> combineListObservables().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("CombineList :" +integer);
            }
        }));
        
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
        });
    }
    List<Observable<Integer>> list = new ArrayList<>();
    /** Combines a list of source Observables by emitting an item that aggregates the latest values of each of the
     * source Observables each time an item is received
     *  from any of the source Observables, where this aggregation is defined by a specified function called aggregate function
     * */
    private Observable<Integer> combineListObservables() {
         for(int i=1;i<5;i++){
             list.add(createObserver(i));
         }
         return Observable.combineLatest(list,args ->{
             int temp = 0;
             for (Object i : args) {
                 log(i);
                 temp += (Integer) i;
             }
             return temp;
         });

    }
}
