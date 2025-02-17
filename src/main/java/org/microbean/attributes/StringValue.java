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
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;

/**
 * A {@link Value} whose value is a {@code String}.
 *
 * @param value the value
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record StringValue(String value) implements Value<StringValue> {

  public static final StringValue EMPTY = new StringValue("");

  /**
   * Creates a new {@link StringValue}.
   *
   * @param value the value; must not be {@code null}
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  public StringValue {
    Objects.requireNonNull(value, "value");
  }

  @Override // Comparable<StringValue>
  public final int compareTo(final StringValue other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    }
    return this.value().compareTo(other.value());
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<StringValue>> describeConstable() {
    final ClassDesc cd = ClassDesc.of(this.getClass().getName());
    return
      Optional.of(DynamicConstantDesc.of(BSM_INVOKE,
                                         MethodHandleDesc.ofMethod(STATIC,
                                                                   cd,
                                                                   "of",
                                                                   MethodTypeDesc.of(cd,
                                                                                     CD_String)),
                                         this.value()));
  }

  @Override
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements.
      other != null && other.getClass() == this.getClass() && this.value().equals(((StringValue)other).value());
  }

  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    return this.value().hashCode();
  }

  @Override // Record
  public final String toString() {
    return this.value();
  }

  /**
   * Returns a {@link StringValue} suitable for the supplied arguments.
   *
   * @param s a non-{@code null} {@code String}
   *
   * @return a non-{@code null} {@link StringValue}
   *
   * @exception NullPointerException if {@code s} is {@code null}
   */
  public static final StringValue of(final String s) {
    return s.isEmpty() ? EMPTY : new StringValue(s);
  }

}
