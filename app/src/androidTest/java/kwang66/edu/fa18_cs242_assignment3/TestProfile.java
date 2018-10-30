package kwang66.edu.fa18_cs242_assignment3;

import org.junit.Rule;
import org.junit.Test;

import kwang66.edu.fa18_cs242_assignment3.FragmentTestRule;
import kwang66.edu.fa18_cs242_assignment3.ProfileFrag;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;


public class TestProfile {

    @Rule
    public FragmentTestRule<ProfileFrag> mFragmentTestRule = new FragmentTestRule<>(ProfileFrag.class);

    @Test
    public void testElements() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.avatar)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).check(matches(isDisplayed()));
        onView(withId(R.id.grid)).check(matches(isDisplayed()));
        onView(withId(R.id.grid)).check(matches(hasChildCount(4)));
    }


}
