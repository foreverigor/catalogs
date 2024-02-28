package build.gradle.catalogs.api;

public interface Dependency<T> {

    public T getReference();

    public void setReference(T reference);
}
