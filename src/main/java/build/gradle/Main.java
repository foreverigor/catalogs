package build.gradle;

import build.gradle.catalogs.Catalogs;
import groovy.lang.Closure;
import org.gradle.api.*;
import org.gradle.api.initialization.dsl.VersionCatalogBuilder;
import org.gradle.api.initialization.resolve.MutableVersionCatalogContainer;
import org.gradle.api.provider.Provider;
import org.gradle.api.specs.Spec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Main {


  public static void main(String[] args) {
    Catalogs.INSTANCE.getCatalogsConfig().accept(new MutableVersionCatalogContainer() {
      @Override
      public VersionCatalogBuilder create(String name) throws InvalidUserDataException {
        return null;
      }

      @Override
      public VersionCatalogBuilder maybeCreate(String name) {
        return null;
      }

      @Override
      public VersionCatalogBuilder create(String name, Closure configureClosure) throws InvalidUserDataException {
        return null;
      }

      @Override
      public VersionCatalogBuilder create(String name, Action<? super VersionCatalogBuilder> configureAction) throws InvalidUserDataException {
        return null;
      }

      @Override
      public NamedDomainObjectContainer<VersionCatalogBuilder> configure(Closure configureClosure) {
        return null;
      }

      @Override
      public NamedDomainObjectProvider<VersionCatalogBuilder> register(String name, Action<? super VersionCatalogBuilder> configurationAction) throws InvalidUserDataException {
        return null;
      }

      @Override
      public NamedDomainObjectProvider<VersionCatalogBuilder> register(String name) throws InvalidUserDataException {
        return null;
      }

      @Override
      public <S extends VersionCatalogBuilder> NamedDomainObjectSet<S> withType(Class<S> type) {
        return null;
      }

      @Override
      public NamedDomainObjectSet<VersionCatalogBuilder> matching(Spec<? super VersionCatalogBuilder> spec) {
        return null;
      }

      @Override
      public NamedDomainObjectSet<VersionCatalogBuilder> matching(Closure spec) {
        return null;
      }

      @Override
      public Set<VersionCatalogBuilder> findAll(Closure spec) {
        return null;
      }

      @Override
      public boolean add(VersionCatalogBuilder e) {
        return false;
      }

      @Override
      public boolean addAll(Collection<? extends VersionCatalogBuilder> c) {
        return false;
      }

      @Override
      public Namer<VersionCatalogBuilder> getNamer() {
        return null;
      }

      @Override
      public SortedMap<String, VersionCatalogBuilder> getAsMap() {
        return null;
      }

      @Override
      public SortedSet<String> getNames() {
        return null;
      }

      @Nullable
      @Override
      public VersionCatalogBuilder findByName(String name) {
        return null;
      }

      @Override
      public VersionCatalogBuilder getByName(String name) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public VersionCatalogBuilder getByName(String name, Closure configureClosure) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public VersionCatalogBuilder getByName(String name, Action<? super VersionCatalogBuilder> configureAction) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public VersionCatalogBuilder getAt(String name) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public Rule addRule(Rule rule) {
        return null;
      }

      @Override
      public Rule addRule(String description, Closure ruleAction) {
        return null;
      }

      @Override
      public Rule addRule(String description, Action<String> ruleAction) {
        return null;
      }

      @Override
      public List<Rule> getRules() {
        return null;
      }

      @Override
      public NamedDomainObjectProvider<VersionCatalogBuilder> named(String name) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public NamedDomainObjectProvider<VersionCatalogBuilder> named(String name, Action<? super VersionCatalogBuilder> configurationAction) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public <S extends VersionCatalogBuilder> NamedDomainObjectProvider<S> named(String name, Class<S> type) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public <S extends VersionCatalogBuilder> NamedDomainObjectProvider<S> named(String name, Class<S> type, Action<? super S> configurationAction) throws UnknownDomainObjectException {
        return null;
      }

      @Override
      public NamedDomainObjectCollectionSchema getCollectionSchema() {
        return null;
      }

      @Override
      public void addLater(Provider<? extends VersionCatalogBuilder> provider) {

      }

      @Override
      public void addAllLater(Provider<? extends Iterable<VersionCatalogBuilder>> provider) {

      }

      @Override
      public <S extends VersionCatalogBuilder> DomainObjectCollection<S> withType(Class<S> type, Action<? super S> configureAction) {
        return null;
      }

      @Override
      public <S extends VersionCatalogBuilder> DomainObjectCollection<S> withType(Class<S> type, Closure configureClosure) {
        return null;
      }

      @Override
      public Action<? super VersionCatalogBuilder> whenObjectAdded(Action<? super VersionCatalogBuilder> action) {
        return null;
      }

      @Override
      public void whenObjectAdded(Closure action) {

      }

      @Override
      public Action<? super VersionCatalogBuilder> whenObjectRemoved(Action<? super VersionCatalogBuilder> action) {
        return null;
      }

      @Override
      public void whenObjectRemoved(Closure action) {

      }

      @Override
      public void all(Action<? super VersionCatalogBuilder> action) {

      }

      @Override
      public void all(Closure action) {

      }

      @Override
      public void configureEach(Action<? super VersionCatalogBuilder> action) {

      }

      @Override
      public int size() {
        return 0;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public boolean contains(Object o) {
        return false;
      }

      @NotNull
      @Override
      public Iterator<VersionCatalogBuilder> iterator() {
        return null;
      }

      @NotNull
      @Override
      public Object[] toArray() {
        return new Object[0];
      }

      @NotNull
      @Override
      public <T> T[] toArray(@NotNull T[] a) {
        return null;
      }

      @Override
      public boolean remove(Object o) {
        return false;
      }

      @Override
      public boolean containsAll(@NotNull Collection<?> c) {
        return false;
      }

      @Override
      public boolean removeAll(@NotNull Collection<?> c) {
        return false;
      }

      @Override
      public boolean retainAll(@NotNull Collection<?> c) {
        return false;
      }

      @Override
      public void clear() {

      }
    });
  }

}
