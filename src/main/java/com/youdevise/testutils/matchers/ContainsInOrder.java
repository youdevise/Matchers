package com.youdevise.testutils.matchers;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class ContainsInOrder<T> extends TypeSafeDiagnosingMatcher<Iterable<T>> {
    
    private final Matcher<Iterable<T>> contains;
    private final Matcher<T>[] expected;
    
    public ContainsInOrder(Matcher<T>[] expected) {
        this.expected = expected;
        contains = (expected == null || expected.length == 0) ? Matchers.<T>emptyIterable() : Matchers.<T>contains(expected);
    }

    @Override
    public void describeTo(Description description) {
        contains.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(Iterable<T> actual, Description mismatchDescription) {
        List<T> actualList = new ArrayList<T>();
        for (T t : actual) {
            actualList.add(t);
        }
        diagnoseFailures(actual, mismatchDescription);
        mismatchDescription.appendText("\n\tComplete actual iterable: ").appendValue(actualList);
        return contains.matches(actual);
    }

    private void diagnoseFailures(Iterable<T> actual, Description mismatchDescription) {
        List<T> actualList = new ArrayList<T>();
        for (T t : actual) {
            actualList.add(t);
        }
        if (actualList.isEmpty()) {
            mismatchDescription.appendText("the actual collection was empty ");
            return;
        } 
        if (Matchers.containsInAnyOrder(expected).matches(actual)) {
            mismatchDescription.appendText("actual list had the right items but in the wrong order! ");
        }
        if (actualList.size() < expected.length)  {
            mismatchDescription.appendText(String.format("expected size %d, actual size %d; ", expected.length, actualList.size()));
            mismatchDescription.appendText("\n\tItems that were expected, but not present: ");
            for (int i = actualList.size(); i < expected.length; i++) {
                mismatchDescription.appendText("\n\t  ").appendValue(i + 1).appendText(" ").appendValue(expected[i]);
            }
        } 
        if (actualList.size() > expected.length)  {
            mismatchDescription.appendText(String.format("expected size %d, actual size %d; ", expected.length, actualList.size()));
            mismatchDescription.appendText("\n\tUnexpected items: ");
            for (int i = expected.length; i < actualList.size(); i++) {
                mismatchDescription.appendText("\n\t  ").appendValue(i + 1).appendText(" ").appendValue(actualList.get(i));
            }
        } 
        describeNonCorrespondances(mismatchDescription, actualList);
    }

    private void describeNonCorrespondances(Description mismatchDescription, List<T> actualList) {
        boolean first = true;
        for (int i = 0; i < Math.min(expected.length, actualList.size()); i++) {
            if (itemsDontCorrespond(actualList.get(i), expected[i])) {
                if (first) {
                    mismatchDescription.appendText("\n\tItems that did not match their corresponding expectations: ");
                    first = false;
                }
                mismatchDescription.appendText("\n\t  ").appendValue(i + 1)
                    .appendText(" Expected ").appendValue(expected[i])
                    .appendText(" but was ").appendValue(actualList.get(i));
            }
        }
    }

    private boolean itemsDontCorrespond(T actual, Matcher<T> matcher) {
        return !matcher.matches(actual);
    }
}