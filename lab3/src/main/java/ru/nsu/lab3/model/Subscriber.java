package ru.nsu.lab3.model;

public interface Subscriber<T> {
    public abstract void update(Publisher<T> sender, T argument);
}
