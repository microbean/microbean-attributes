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

import java.lang.annotation.RetentionPolicy;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static java.lang.invoke.MethodHandles.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TestConstableSemantics {

  private TestConstableSemantics() {
    super();
  }

  @Test
  final void testArrayValue() throws ReflectiveOperationException {
    final ArrayValue<StringValue> v = ArrayValue.of("a", "b", "c");
    final ArrayValue<StringValue> v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testAttributes() throws ReflectiveOperationException {
    final Attributes v = Attributes.of("Qualifier");
    final Attributes v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testBooleanValue() throws ReflectiveOperationException {
    final BooleanValue v = BooleanValue.of(false);
    final BooleanValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertSame(v, v0); // note
  }

  @Test
  final void testByteValue() throws ReflectiveOperationException {
    final ByteValue v = ByteValue.of((byte)1);
    final ByteValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testCharValue() throws ReflectiveOperationException {
    final CharValue v = CharValue.of('a');
    final CharValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testClassValue() throws ReflectiveOperationException {
    final ClassValue v = ClassValue.of(Long.class);
    final ClassValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }


  @Test
  final void testDoubleValue() throws ReflectiveOperationException {
    final DoubleValue v = DoubleValue.of(2D);
    final DoubleValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testEnumValue() throws ReflectiveOperationException {
    final EnumValue<RetentionPolicy> v = EnumValue.of(RetentionPolicy.RUNTIME);
    final EnumValue<RetentionPolicy> v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testFloatValue() throws ReflectiveOperationException {
    final FloatValue v = FloatValue.of(2F);
    final FloatValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testIntValue() throws ReflectiveOperationException {
    final IntValue v = IntValue.of(2);
    final IntValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }


  @Test
  final void testLongValue() throws ReflectiveOperationException {
    final LongValue v = LongValue.of(2L);
    final LongValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testShortValue() throws ReflectiveOperationException {
    final ShortValue v = ShortValue.of((short)2);
    final ShortValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

  @Test
  final void testStringValue() throws ReflectiveOperationException {
    final StringValue v = StringValue.of("a");
    final StringValue v0 = v.describeConstable().orElseThrow().resolveConstantDesc(lookup());
    assertEquals(v, v0);
    assertNotSame(v, v0);
  }

}
