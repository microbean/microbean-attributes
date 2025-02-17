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
import java.lang.constant.ConstantDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;

import java.util.Optional;

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_boolean;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;

/**
 * A {@link Value} whose value is a {@code boolean}.
 *
 * @param value the value
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record BooleanValue(boolean value) implements Value<BooleanValue> {

  /**
   * The {@link BooleanValue} representing {@code true}.
   */
  public static final BooleanValue TRUE = new BooleanValue(true);

  /**
   * The {@link BooleanValue} representing {@code false}.
   */
  public static final BooleanValue FALSE = new BooleanValue(false);

  @Override // Comparable<Value>
  public final int compareTo(BooleanValue other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    } else if (this.value()) {
      return 1; // false comes first
    } else {
      return -1;
    }
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<BooleanValue>> describeConstable() {
    final ClassDesc cd = ClassDesc.of(this.getClass().getName());
    return
      Optional.of(DynamicConstantDesc.of(BSM_INVOKE,
                                         MethodHandleDesc.ofMethod(STATIC,
                                                                   cd,
                                                                   "of",
                                                                   MethodTypeDesc.of(cd,
                                                                                     CD_boolean)),
                                         this.value() ? ConstantDescs.TRUE : ConstantDescs.FALSE));
  }

  @Override // Record
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements
      other != null && other.getClass() == this.getClass() && this.value() == ((BooleanValue)other).value();
  }

  @Override // Record
  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    return Boolean.valueOf(this.value()).hashCode();
  }

  @Override // Record
  public final String toString() {
    return String.valueOf(this.value());
  }

  /**
   * Returns a {@link BooleanValue} suitable for the supplied arguments.
   *
   * @param b a {@code boolean}
   *
   * @return {@link TRUE} if {@code b} is {@code true}; {@link FALSE} otherwise
   */
  public static final BooleanValue of(final boolean b) {
    return b ? TRUE : FALSE;
  }

}
