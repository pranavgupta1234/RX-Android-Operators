package pranav.apps.amazing.rxoperators.Transforming;

import android.os.Bundle;
import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Observable;

public class MapAndCastOperator extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Map");
        mLButton.setOnClickListener(e -> mapObserver().subscribe(i -> log("Map:" + i)));
        mRButton.setText("Cast");
        mRButton.setOnClickListener(e -> castObserver().subscribe(i -> log("Cast:" + i.getName())));
    }
    /** converts into an observable that emits the item of source observable by mapping through a function
     * */
    private Observable<Integer> mapObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).map(integer -> integer * 10);
    }
    /** getAnimal() method receives an object which is emitted by this observable then cast operator casts into dog class
     *
     * note that in onNext method we can extract information of the passed object through the functions which are present inside
     * our class to which we have casted
     *
     * This also shows that custom objects can also be emitted and handled through rx
     * */
    private Observable<Dog> castObserver() {
        return Observable.just(getAnimal())
                .cast(Dog.class);
    }

    Animal getAnimal() {
        return new Dog();
    }

    class Animal {
        protected String name = "Animal";

        Animal() {
            log("create " + name);
        }

        String getName() {
            return name;
        }
    }

    class Dog extends Animal {
        Dog() {
            name = getClass().getSimpleName();
            log("create " + name);
        }

    }
}
