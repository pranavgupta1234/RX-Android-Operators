package pranav.apps.amazing.rxoperators.Connectable;

import android.os.Bundle;

import pranav.apps.amazing.rxoperators.BaseActivity;
import rx.Subscription;

/**
 * Created by Pranav Gupta on 1/15/2017.
 */

public class ReplayConnectableObservable extends BaseActivity{
    private Subscription _subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
