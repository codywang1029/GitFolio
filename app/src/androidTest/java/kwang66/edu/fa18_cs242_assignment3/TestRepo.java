package kwang66.edu.fa18_cs242_assignment3;

import org.junit.Rule;
import org.junit.Test;

import network.Repo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

public class TestRepo {

    @Rule
    public FragmentTestRule<RepoFrag> mFragmentTestRule = new FragmentTestRule<>(RepoFrag.class);

    @Test
    public void testElements() {
        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);
        onView(withId(R.id.recycler_list)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.description),not(withText("Empty description")))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.text_view),(withText("test-repo")))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.text_view),(withText("test-repo2")))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.text_view),(withText("chess-game")))).check(matches(isDisplayed()));
        onView(withId(R.id.recycler_list)).check(matches(hasChildCount(3)));
    }


}