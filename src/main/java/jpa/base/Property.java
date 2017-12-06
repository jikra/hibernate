package jpa.base;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
public interface Property<T> {

    String getKey();

    T getValue();
}
