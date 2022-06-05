package ru.nsu.lab3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Publisher<T> {
    protected List<Subscriber<T>> subscribers;

    public Publisher() {
        this.subscribers = new ArrayList<Subscriber<T>>();
    }

    public void addSubscriber(Subscriber<T> subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }

        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    public void clearSubscribers()
    {
        subscribers.clear();
    }

    public void removeSubscriber(Subscriber<T> subscriber) {
        if (!subscribers.contains(subscriber)) {
            throw new NoSuchElementException();
        }
        subscribers.remove(subscriber);
    }

    protected void notifySubscriber(T argument) {
        for (Subscriber<T> subscriber : subscribers) {
            subscriber.update(this, argument);
        }
    }

    public int countSubscribers() {
        return subscribers.size();
    }
}