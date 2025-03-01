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

import java.util.List;

import java.lang.constant.ClassDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;

import java.util.Optional;

import org.microbean.constant.Constables;

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_List;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;

/**
 * A {@link Value} holding other {@link Value}s.
 *
 * @param <T> the type of the {@link Value}s
 *
 * @param value a non-{@code null} {@link List} of {@link Value}s
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record ListValue<T extends Value<T>>(List<T> value) implements Value<ListValue<T>> {

  /**
   * Creates a new {@link ListValue}.
   *
   * @param value a non-{@code null} {@link List} of {@link Value}s
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  public ListValue {
    value = List.copyOf(value);
  }

  @Override // Comparable<ListValue<T>>
  public final int compareTo(final ListValue<T> other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    }
    return this.toString().compareTo(other.toString());
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<ListValue<T>>> describeConstable() {
    final ClassDesc me = ClassDesc.of(this.getClass().getName());
    return Constables.describeConstable(this.value())
      .map(valueDesc -> DynamicConstantDesc.of(BSM_INVOKE,
                                               MethodHandleDesc.ofMethod(STATIC,
                                                                         me,
                                                                         "of",
                                                                         MethodTypeDesc.of(me,
                                                                                           CD_List)),
                                               valueDesc));
  }

  @Override // Record
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements.
      other != null && other.getClass() == this.getClass() && this.value().equals(((ListValue<?>)other).value());
  }

  @Override // Record
  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    return this.value().hashCode();
  }

  @Override // Record
  public final String toString() {
    return this.value().toString();
  }

  /**
   * Returns a {@link ListValue} suitable for the supplied argument.
   *
   * @param <T> the type of {@link Value} held by the returned {@link ListValue}
   *
   * @param value a non-{@code null} {@link List} of suitably-typed {@link Value}s
   *
   * @return a non-{@code null} {@link ListValue}
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  @SuppressWarnings("unchecked")
  // Called by #describeConstable()
  public static final <T extends Value<T>> ListValue<T> of(final List<T> value) {
    return new ListValue<>(value);
  }

}
