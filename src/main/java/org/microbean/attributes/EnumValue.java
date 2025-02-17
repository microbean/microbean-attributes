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

import java.util.Objects;
import java.util.Optional;

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_Enum;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;

/**
 * A {@link Value} whose value is an {@code Enum}.
 *
 * @param <E> the type of the {@link Enum}
 *
 * @param value the value
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record EnumValue<E extends Enum<E>>(E value) implements Value<EnumValue<E>> {

  /**
   * Creates a new {@link EnumValue}.
   *
   * @param value the value; must not be {@code null}
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  public EnumValue {
    Objects.requireNonNull(value, "value");
  }

  @Override // Comparable<EnumValue>
  public final int compareTo(final EnumValue<E> other) {
    return
      other == null ? -1 :
      this.equals(other) ? 0 :
      this.value().compareTo(other.value());
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<EnumValue<E>>> describeConstable() {
    final ClassDesc cd = ClassDesc.of(this.getClass().getName());
    return
      Optional.of(DynamicConstantDesc.of(BSM_INVOKE,
                                         MethodHandleDesc.ofMethod(STATIC,
                                                                   cd,
                                                                   "of",
                                                                   MethodTypeDesc.of(cd,
                                                                                     CD_Enum)),
                                         this.value().describeConstable().orElseThrow()));
  }

  @Override // Record
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements.
      other != null && other.getClass() == this.getClass() && this.value().equals(((EnumValue<?>)other).value());
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
   * Returns an {@link EnumValue} suitable for the supplied arguments.
   *
   * @param <E> the type of the {@link Enum}
   *
   * @param e a non-{@code null} {@code Enum}
   *
   * @return a non-{@code null} {@link EnumValue}
   *
   * @exception NullPointerException if {@code e} is {@code null}
   */
  public static final <E extends Enum<E>> EnumValue<E> of(final E e) {
    return new EnumValue<>(e);
  }

}
