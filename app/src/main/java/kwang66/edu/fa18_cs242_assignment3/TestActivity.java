package kwang66.edu.fa18_cs242_assignment3;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

    @VisibleForTesting
    public class TestActivity extends AppCompatActivity {
        @Override
        protected void onCreate(@Nullable final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setId(R.id.container);
            setContentView(frameLayout);
        }

}
