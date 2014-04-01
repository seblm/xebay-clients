package fr.xebia.xebay;

public interface Consumer<T> {
    public void accept(T input);
}
