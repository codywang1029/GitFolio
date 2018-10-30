package kwang66.edu.fa18_cs242_assignment3;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class TestFollowing {
    @Rule
    public FragmentTestRule<FollowingFrag> mFragmentTestRule = new FragmentTestRule<>(FollowingFrag.class);

    @Test
    public void testElements() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.following_recycler_list)).check(matches(isDisplayed()));
        onView(withId(R.id.following_recycler_list)).check(matches(hasChildCount(6)));
        onView(allOf(withId(R.id.following_login),withText("octocat"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.following_login),withText("codywang1029"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.following_login),withText("dimaliok"))).check(matches(isDisplayed()));
    }
}
