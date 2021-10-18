package com.example.mareu;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class RecyclerViewItemCount implements ViewAssertion {
    private final Matcher<Integer> matcher;

    private RecyclerViewItemCount(Matcher<Integer> matcher) {
        this.matcher = matcher;
    }

    public static RecyclerViewItemCount withItemCount(int expectedCount) {
        return withItemCount( Matchers.is(expectedCount));
    }

    private static RecyclerViewItemCount withItemCount(Matcher<Integer> matcher) {
        return new RecyclerViewItemCount(matcher);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assert adapter != null;
        Assert.assertThat(adapter.getItemCount(), matcher);
    }
}