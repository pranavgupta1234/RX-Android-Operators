package pranav.apps.amazing.rxoperators.Utility;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class DoOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("do");
        mLButton.setOnClickListener(e -> doOnEachObserver().subscribe(i -> log("do:" + i)));
        mRButton.setText("doOnError");
        mRButton.setOnClickListener(e -> doOnErrorObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("subscriber onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                log("subscriber onNext:" + integer);
            }
        }));
    }
    /** these do operations are useful when you want to do something whenever observable emits item, interacts with something other
     * etc. Note that this method is applied to observable and not on observer
     * */
    private Observable<Integer> doOnEachObserver() {
        return Observable.just(1,2,3)
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        log("value of notification "+notification.getValue()+ notification.getKind());
                    }
                })
                //invokes an action when the observer's onNext method is called
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("This is reaction of observable"+integer);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        log("Subscription occured");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        log("Unsubscription occured");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        log("Work Completed, just relax ;p");
                    }
                });
         //lambda version
        /*return Observable.just(1, 2, 3)
                .doOnEach(notification -> log("doOnEach send " + notification.getValue() + " type:" + notification.getKind()))
                .doOnNext(aInteger -> log("doOnNext send " + aInteger))
                .doOnSubscribe(() -> log("on subscribe"))
                .doOnUnsubscribe(() -> log("on unsubscribe\n"))
                .doOnCompleted(() -> log("onCompleted"));*/

    }

    private Observable<Integer> doOnErrorObserver() {
        return createObserver().doOnEach(new Action1<Notification<? super Integer>>() {
            @Override
            public void call(Notification<? super Integer> notification) {

            }
        })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log("Error has occured");
                    }
                })
                /**same as finally do except that this occur before any log value present in observer's onCompleted
                 * or onError method is called
                 */
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        log("Termination, called before observer's method");
                    }
                })
                //this is called when either onCompleted or onError is called i.e after completion of work
                .finallyDo(new Action0() {
                    @Override
                    public void call() {
                       log("finally do");
                    }
                });

        /*return createObserver()
                .doOnEach(notification -> log("doOnEach send " + notification.getValue() + " type:" + notification.getKind()))
                .doOnError(throwable -> log("OnError:" + throwable.getMessage()))
                .doOnTerminate(() -> log("OnTerminate"))
                .finallyDo(() -> log("finallyDo"));*/
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i <= 5; i++) {
                    if (i <= 3) {
                        subscriber.onNext(i);
                    } else {
                        subscriber.onError(new Throwable("num>3"));
                    }
                }
            }
        });
    }


}
