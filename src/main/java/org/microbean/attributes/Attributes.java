/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2025 microBean™.
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

import java.lang.constant.ClassDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;

import java.util.Optional;

import java.util.function.Predicate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

import org.microbean.constant.Constables;

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_Map;
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;

import static java.util.Collections.unmodifiableSortedMap;

/**
 * An {@linkplain Attributed attributed} {@link Value} with a {@linkplain #name() name}, {@linkplain #values() named
 * values}, and {@linkplain #notes() non-normative named values}.
 *
 * @param name a non-{@code null} name of this {@link Attributes}
 *
 * @param values a non-{@code null} {@link Map} of named {@linkplain Value values} associated with this {@link Attributes}
 *
 * @param notes a non-{@code null} {@link Map} of non-normative named {@linkplain Value values} associated with this
 * {@link Attributes}
 *
 * @param attributesMap a non-{@code null} {@link Map} of named metadata associated with this {@link Attributes}
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record Attributes(String name,
                               Map<String, Value<?>> values,
                               Map<String, Value<?>> notes,
                               Map<String, List<Attributes>> attributesMap)
  implements Attributed, Value<Attributes> {

  /**
   * Creates a new {@link Attributes}.
   *
   * @param name a non-{@code null} name of this {@link Attributes}
   *
   * @param values a non-{@code null} {@link Map} of named {@linkplain Value values} associated with this {@link Attributes}
   *
   * @param notes a non-{@code null} {@link Map} of non-normative named {@linkplain Value values} associated with this
   * {@link Attributes}
   *
   * @param attributesMap a non-{@code null} {@link Map} of named metadata associated with this {@link Attributes}
   *
   * @exception NullPointerException if any argument is {@code null}
   */
  public Attributes {
    Objects.requireNonNull(name, "name");
    switch (values.size()) {
    case 0:
      values = Map.of();
      break;
    case 1:
      values = Map.copyOf(values);
      break;
    default:
      final TreeMap<String, Value<?>> sortedValues = new TreeMap<>();
      sortedValues.putAll(values);
      values = unmodifiableSortedMap(sortedValues);
    }
    if (values.containsKey("")) {
      throw new IllegalArgumentException("values: " + values);
    }
    switch (notes.size()) {
    case 0:
      notes = Map.of();
      break;
    case 1:
      notes = Map.copyOf(notes);
      break;
    default:
      final TreeMap<String, Value<?>> sortedNotes = new TreeMap<>();
      sortedNotes.putAll(notes);
      notes = unmodifiableSortedMap(sortedNotes);
    }
    if (notes.containsKey("")) {
      throw new IllegalArgumentException("notes: " + notes);
    }
    switch (attributesMap.size()) {
    case 0:
      attributesMap = Map.of();
      break;
    case 1:
      attributesMap = Map.copyOf(attributesMap);
      break;
    default:
      final TreeMap<String, List<Attributes>> sortedAttributesMap = new TreeMap<>();
      for (final Entry<String, List<Attributes>> e : attributesMap.entrySet()) {
        sortedAttributesMap.put(e.getKey(), List.copyOf(e.getValue()));
      }
      attributesMap = unmodifiableSortedMap(sortedAttributesMap);
      break;
    }
    if (attributesMap.containsKey("")) {
      throw new IllegalArgumentException("attributesMap: " + attributesMap);
    }
  }

  /**
   * Returns a {@link List} of {@link Attributes} associated with this {@link Attributes} directly, or an {@linkplain
   * List#isEmpty() empty <code>List</code>} if there is no such {@link List}.
   *
   * @return a non-{@code null} {@link List} of {@link Attributes}
   *
   * @see #attributes(String)
   *
   * @see #name()
   */
  @Override // Attributed
  public final List<Attributes> attributes() {
    return this.attributes(this.name());
  }

  /**
   * Returns a {@link List} of {@link Attributes} associated with the supplied {@code key}, or an {@linkplain
   * List#isEmpty() empty <code>List</code>} if there is no such {@link List}.
   *
   * @param key a {@link String}; must not be {@code null}
   *
   * @return a non-{@code null} {@link List} of {@link Attributes}
   *
   * @exception NullPointerException if {@code key} is {@code null}
   */
  public final List<Attributes> attributes(final String key) {
    return this.attributesMap().getOrDefault(key, List.of());
  }

  @Override // Comparable<Attributes>
  public final int compareTo(final Attributes other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    }
    final int c = (this.name() + this.values()).compareTo(other.name() + other.values());
    // (Possible? that c simply cannot be 0 here?)
    return c != 0 ? c : (this.notes().toString() + this.attributesMap()).compareTo(other.notes().toString() + other.attributesMap());
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<Attributes>> describeConstable() {
    final ClassDesc me = ClassDesc.of(this.getClass().getName());
    return Constables.describeConstable(this.values())
      .flatMap(valuesDesc -> Constables.describeConstable(this.notes())
               .flatMap(notesDesc -> Constables.describeConstable(this.attributesMap())
                        .map(attributesDesc -> DynamicConstantDesc.of(BSM_INVOKE,
                                                                      MethodHandleDesc.ofMethod(STATIC,
                                                                                                me,
                                                                                                "of",
                                                                                                MethodTypeDesc.of(me,
                                                                                                                  CD_String,
                                                                                                                  CD_Map,
                                                                                                                  CD_Map,
                                                                                                                  CD_Map)),
                                                                      this.name(),
                                                                      valuesDesc,
                                                                      notesDesc,
                                                                      attributesDesc))));
  }

  /**
   * Returns {@code true} if this {@link Attributes} equals the supplied {@link Object}.
   *
   * <p>If the supplied {@link Object} is also an {@link Attributes} and has a {@linkplain #name() name} equal to this
   * {@link Attributes}' {@linkplain #name() name} and a {@linkplain #values() values} {@link Map} {@linkplain
   * Map#equals(Object) equal to} this {@link Attributes}' {@linkplain #values() values} {@link Map}, this method
   * returns {@code true}.</p>
   *
   * <p>This method returns {@code false} in all other cases.</p>
   *
   * @param other an {@link Object}; may be {@code null}
   *
   * @return {@code true} if this {@link Attributes} equals the supplied {@link Object}
   *
   * @see #hashCode()
   *
   * @see #name()
   *
   * @see #values()
   */
  @Override // Record
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements.
      other instanceof Attributes a && this.name().equals(a.name()) && this.values().equals(a.values());
  }

  /**
   * Returns a hash code value for this {@link Attributes} derived solely from its {@linkplain #values() values}.
   *
   * @return a hash code value
   *
   * @see #values()
   *
   * @see #equals(Object)
   */
  @Override // Record
  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    int hashCode = 0;
    for (final Entry<String, Value<?>> e : this.values().entrySet()) {
      hashCode += (127 * e.getKey().hashCode()) ^ e.getValue().hashCode();
    }
    return hashCode;
  }

  /**
   * Returns a suitably-typed {@link Value} indexed under the supplied {@code name}, or {@code null} if no such {@link
   * Value} exists.
   *
   * @param <T> the type of the {@link Value}
   *
   * @param name the name; must not be {@code null}
   *
   * @return a suitably-typed {@link Value} indexed under the supplied {@code name}, or {@code null} if no such {@link
   * Value} exists
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @exception ClassCastException if {@code <T>} does not match the actual type of the {@link Value} indexed under the
   * supplied {@code name}
   */
  @SuppressWarnings("unchecked")
  public final <T extends Value<T>> T value(final String name) {
    return (T)this.values().get(name);
  }

  /**
   * Returns an {@link Attributes} comprising the supplied arguments.
   *
   * @param name the name; must not be {@code null}
   *
   * @return a non-{@code null} {@link Attributes}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @see #of(String, Map, Map, Map)
   */
  public static final Attributes of(final String name) {
    return of(name, Map.of(), Map.of(), Map.of());
  }

  /**
   * Returns an {@link Attributes} comprising the supplied arguments.
   *
   * @param name the name; must not be {@code null}
   *
   * @param valueValue a {@link String} that will be indexed under the key "{@code value}"; must not be {@code null}
   *
   * @return a non-{@code null} {@link Attributes}
   *
   * @exception NullPointerException if {@code name} or {@code valueValue} is {@code null}
   *
   * @see #of(String, Map, Map, Map)
   */
  public static final Attributes of(final String name, final String valueValue) {
    return of(name, Map.of("value", new StringValue(valueValue)), Map.of(), Map.of());
  }

  /**
   * Returns an {@link Attributes} comprising the supplied arguments.
   *
   * @param name the name; must not be {@code null}
   *
   * @param attributes an array of {@link Attributes}; may be {@code null}
   *
   * @return a non-{@code null} {@link Attributes}
   *
   * @exception NullPointerException if {@code name} is {@code null}
   *
   * @see #of(String, List)
   */
  public static final Attributes of(final String name, final Attributes... attributes) {
    return of(name, attributes == null || attributes.length == 0 ? List.of() : Arrays.asList(attributes));
  }

  /**
   * Returns an {@link Attributes} comprising the supplied arguments.
   *
   * @param name the name; must not be {@code null}
   *
   * @param attributes a non-{@code null} {@link List} of {@link Attributes}
   *
   * @return a non-{@code null} {@link Attributes}
   *
   * @exception NullPointerException if any argument is {@code null}
   *
   * @see #of(String, Map, Map, Map)
   */
  public static final Attributes of(final String name, final List<Attributes> attributes) {
    return of(name, Map.of(), Map.of(), Map.of(name, attributes));
  }

  /**
   * Returns an {@link Attributes} comprising the supplied arguments.
   *
   * @param name the name; must not be {@code null}
   *
   * @param values a {@link Map} of {@link Value}s indexed by {@link String} keys; must not be {@code null}
   *
   * @param notes a {@link Map} of {@link Value}s indexed by {@link String} keys containing descriptive information
   * only; must not be {@code null}; not incorporated into equality calculations
   *
   * @param attributes a {@link Map} of {@link List}s of {@link Attributes} instances denoting metadata for a given
   * value in {@code values} (or for this {@link Attributes} as a whole if the key in question is equal to {@code
   * name}); must not be {@code null}
   *
   * @return a non-{@code null} {@link Attributes}
   *
   * @exception NullPointerException if any argument is {@code null}
   */
  public static final Attributes of(final String name,
                                    final Map<String, Value<?>> values,
                                    final Map<String, Value<?>> notes,
                                    final Map<String, List<Attributes>> attributes) {
    return new Attributes(name, values, notes, attributes);
  }

}
