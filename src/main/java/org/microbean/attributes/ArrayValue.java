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

import java.util.Arrays;
import java.util.List;

import java.lang.constant.ClassDesc;
import java.lang.constant.DynamicConstantDesc;
import java.lang.constant.MethodHandleDesc;
import java.lang.constant.MethodTypeDesc;

import java.util.Optional;

import org.microbean.constant.Constables;

import static java.lang.constant.ConstantDescs.BSM_INVOKE;
import static java.lang.constant.ConstantDescs.CD_List;
import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.DirectMethodHandleDesc.Kind.STATIC;
import static java.lang.constant.DirectMethodHandleDesc.Kind.VIRTUAL;

/**
 * A {@link Value} holding other {@link Value}s.
 *
 * @param <T> the type of the {@link Value}s
 *
 * @param value a non-{@code null} array of {@link Value}s
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 */
public final record ArrayValue<T extends Value<T>>(T[] value) implements Value<ArrayValue<T>> {

  @SuppressWarnings("rawtypes")
  private static final Value<?>[] EMPTY_VALUE_ARRAY = new Value[0];

  /**
   * Creates a new {@link ArrayValue}.
   *
   * @param value a non-{@code null} array of {@link Value}s
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  public ArrayValue {
    value = value.clone();
  }

  @Override // Comparable<ArrayValue<T>>
  public final int compareTo(final ArrayValue<T> other) {
    if (other == null) {
      return -1;
    } else if (this.equals(other)) {
      return 0;
    }
    return this.toString().compareTo(other.toString());
  }

  @Override // Constable
  public final Optional<DynamicConstantDesc<ArrayValue<T>>> describeConstable() {
    final ClassDesc me = ClassDesc.of(this.getClass().getName());
    return Constables.describeConstable(Arrays.asList(this.value()))
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
      other != null && other.getClass() == this.getClass() && Arrays.equals(this.value(), ((ArrayValue<?>)other).value());
  }

  @Override // Record
  public final int hashCode() {
    // Follow java.lang.annotation.Annotation requirements.
    return Arrays.hashCode(this.value());
  }

  @Override // Record
  public final String toString() {
    return Arrays.asList(this.value()).toString();
  }

  /**
   * Returns the array of suitably-typed {@link Value}s this {@link ArrayValue} holds.
   *
   * <p>No reference to the returned array is kept.</p>
   *
   * @return a non-{@code null} array of suitably-typed {@link Value}s
   */
  @Override // Record
  public final T[] value() {
    return this.value.clone();
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<BooleanValue> of(final boolean... values) {
    final var va = new BooleanValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = BooleanValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<ByteValue> of(final byte... values) {
    final var va = new ByteValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = ByteValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<CharValue> of(final char... values) {
    final var va = new CharValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = CharValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<DoubleValue> of(final double... values) {
    final var va = new DoubleValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = DoubleValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<FloatValue> of(final float... values) {
    final var va = new FloatValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = FloatValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<IntValue> of(final int... values) {
    final var va = new IntValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = IntValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<LongValue> of(final long... values) {
    final var va = new LongValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = LongValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<ShortValue> of(final short... values) {
    final var va = new ShortValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = ShortValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<ClassValue> of(final Class<?>... values) {
    final var va = new ClassValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = ClassValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param values a non-{@code null} array of values
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code values} is {@code null}
   *
   * @see #of(Value[])
   */
  public static final ArrayValue<StringValue> of(final String... values) {
    final var va = new StringValue[values.length];
    for (int i = 0; i < values.length; i++) {
      va[i] = StringValue.of(values[i]);
    }
    return new ArrayValue<>(va);
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param <T> the type of {@link Value} held by the returned {@link ArrayValue}
   *
   * @param value a non-{@code null} {@link List} of suitably-typed {@link Value}s
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code value} is {@code null}
   *
   * @see #of(Value[])
   */
  @SuppressWarnings("unchecked")
  // Called by #describeConstable()
  public static final <T extends Value<T>> ArrayValue<T> of(final List<T> value) {
    return of(value.toArray((T[])EMPTY_VALUE_ARRAY));
  }

  /**
   * Returns an {@link ArrayValue} suitable for the supplied argument.
   *
   * @param <T> the type of {@link Value} held by the returned {@link ArrayValue}
   *
   * @param value a non-{@code null} array of suitably-typed {@link Value}s
   *
   * @return a non-{@code null} {@link ArrayValue}
   *
   * @exception NullPointerException if {@code value} is {@code null}
   */
  public static final <T extends Value<T>> ArrayValue<T> of(final T[] value) {
    return new ArrayValue<>(value);
  }

}
