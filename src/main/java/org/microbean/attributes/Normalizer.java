/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2026 microBean™.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.microbean.attributes;

import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple, mutable, concurrent, {@link Attributed}-aware cache of objects.
 *
 * @param <T> the type of object to be normalized
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 *
 * @see #normalize(Object)
 */
public final class Normalizer<T> {

  private final Map<Key<? extends T>, T> cache;

  /**
   * Creates a new {@link Normalizer}.
   */
  public Normalizer() {
    super();
    this.cache = new ConcurrentHashMap<>();
  }

  /**
   * <dfn>Normalizes</dfn> the supplied {@code element} such that if this {@link Normalizer} already has an {@linkplain
   * Object#equals(Object) equivalent} cached element, the cached element is returned, and, if it does not, the supplied
   * {@code element} is cached indefinitely and returned.
   *
   * <p><strong>Note:</strong> if the supplied {@code element} is an {@link Attributed} implementation, then, in
   * accordance with the {@link Attributed} contract, it must not include its {@linkplain Attributed#attributes()
   * attributes} in its equality or {@linkplain Attributed#hashCode() hashcode} computations, or undefined behavior may
   * result.</p>
   *
   * @param element the element to normalize; may be {@code null} in which case {@code null} is returned
   *
   * @return the supplied {@code element} or a previously cached {@linkplain Object#equals(Object) equivalent} element
   */
  public final T normalize(final T element) {
    return element instanceof Attributed a ? this.cache.computeIfAbsent(new Key<>(a.attributes(), element), k -> k.element()) : element;
  }

  private static final record Key<T>(List<Attributes> attributes, T element) implements Attributed {}
  
}
