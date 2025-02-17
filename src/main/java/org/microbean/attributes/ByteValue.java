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

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_Integer;
import static java.lang.constant.ConstantDescs.CD_byte;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;
import static java.lang.constant.DirectMethodHandleDesc.Kind.VIRTUAL;

/**
 * A {@link Value} whose value is a {@code byte}.
 *
 * @param value the value
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record ByteValue(byte value) implements Value<ByteValue> {

  @Override // Comparable<ByteValue>
  public final int compareTo(ByteValue other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    }
    return this.value() > other.value() ? 1 : -1;
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<ByteValue>> describeConstable() {
    final ClassDesc cd = ClassDesc.of(this.getClass().getName());
    return
      Optional.of(DynamicConstantDesc.of(BSM_INVOKE,
                                         MethodHandleDesc.ofMethod(STATIC,
                                                                   cd,
                                                                   "of",
                                                                   MethodTypeDesc.of(cd,
                                                                                     CD_byte)),
                                         DynamicConstantDesc.of(BSM_INVOKE,
                                                                MethodHandleDesc.ofMethod(VIRTUAL,
                                                                                          CD_Integer,
                                                                                          "byteValue",
                                                                                          MethodTypeDesc.of(CD_byte)),
                                                                Integer.valueOf(this.value()))));
  }

  @Override // Record
  public final boolean equals(final Object other) {
    return
      other == this ||
      // Follow java.lang.annotation.Annotation requirements.
      other != null && other.getClass() == this.getClass() && this.value() == ((ByteValue)other).value();
  }

  @Override // Record
  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    return Byte.valueOf(this.value()).hashCode();
  }

  @Override // Record
  public final String toString() {
    return String.valueOf(this.value());
  }

  /**
   * Returns a {@link ByteValue} suitable for the supplied arguments.
   *
   * @param b a {@code byte}
   *
   * @return a non-{@code null} {@link ByteValue}
   */
  public static final ByteValue of(final byte b) {
    return new ByteValue(b);
  }

}
