package com.youdevise.testutils.matchers;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class Contains {
    private Contains() { }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<T>> inOrder(final T...expected) {
        final List<Matcher<? super T>> expectedMatchers = new ArrayList<Matcher<? super T>>();
        for (final T t : expected) {
            expectedMatchers.add(Matchers.equalTo(t));
        }
        return inOrder(expectedMatchers.toArray(new Matcher[expectedMatchers.size()]));
    }

    public static <T> Matcher<Iterable<T>> inOrder(final Matcher<T>...expected) {
        return new ContainsInOrder<T>(expected);
    }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<T>> only(final T expected) {
        return inOrder(expected);
    }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<T>> only(final Matcher<T> expected) {
        return inOrder(expected);
    }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<T>> inAnyOrder(final T...expected) {
        final List<Matcher<? super T>> expectedMatchers = new ArrayList<Matcher<? super T>>();
        for (final T t : expected) {
            expectedMatchers.add(Matchers.equalTo(t));
        }
        return inAnyOrder(expectedMatchers.toArray(new Matcher[expectedMatchers.size()]));
    }

    public static <T> Matcher<Iterable<T>> inAnyOrder(final Matcher<T>...expected) {
        return new ContainsInAnyOrder<T>(expected);
    }
    @SuppressWarnings("unchecked")
    public static <T> Matcher<Iterable<T>> theItems(final T...expected) {
        final List<Matcher<? super T>> expectedMatchers = new ArrayList<Matcher<? super T>>();
        for (final T t : expected) {
            expectedMatchers.add(Matchers.equalTo(t));
        }
        return theItems(expectedMatchers.toArray(new Matcher[expectedMatchers.size()]));
    }

    public static <T> Matcher<Iterable<T>> theItems(final Matcher<T>...expected) {
        return new ContainsTheItems<T>(expected);
    }

    public static <T> Matcher<Iterable<T>> theItem(final Matcher<T> expected) {
        return new ContainsTheItem<T>(expected);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Matcher<Iterable<T>> theItem(final T expected) {
        return theItem((Matcher)Matchers.is(expected));
    }


}