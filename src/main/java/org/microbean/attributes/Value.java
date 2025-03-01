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

import java.lang.constant.Constable;

/**
 * A value of a particular type suitable for representing as an annotation value or similar.
 *
 * @param <T> a {@link Value} subtype
 *
 * <p>{@link Value}s are <a
 * href="https://docs.oracle.com/en/java/javase/23/docs/api/java.base/java/lang/doc-files/ValueBased.html#Value-basedClasses">value-based
 * classes</a>.</p>
 *
 * @author <a href="https://about.me/lairdnelson" target="_top">Laird Nelson</a>
 *
 * @see ArrayValue
 *
 * @see Attributes
 *
 * @see BooleanValue
 *
 * @see ByteValue
 *
 * @see CharValue
 *
 * @see ClassValue
 *
 * @see DoubleValue
 *
 * @see EnumValue
 *
 * @see FloatValue
 *
 * @see IntValue
 *
 * @see ListValue
 *
 * @see LongValue
 *
 * @see ShortValue
 *
 * @see StringValue
 */
public sealed interface Value<T extends Value<T>> extends Comparable<T>, Constable
  permits ArrayValue,
          Attributes,
          BooleanValue,
          ByteValue,
          CharValue,
          ClassValue,
          DoubleValue,
          EnumValue,
          FloatValue,
          IntValue,
          ListValue,
          LongValue,
          ShortValue,
          StringValue {

}
