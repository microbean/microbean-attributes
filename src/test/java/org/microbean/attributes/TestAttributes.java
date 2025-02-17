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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TestAttributes {

  private TestAttributes() {
    super();
  }

  @Test
  final void testAttributes() {
    final Attributes documented = Attributes.of("Documented");
    final Attributes qualifier = Attributes.of("Qualifier", documented);
    final Attributes named = Attributes.of("Named", qualifier);
    assertFalse(named.isa(named));
    assertTrue(named.isa(qualifier));
    assertTrue(qualifier.isa(documented));
    assertTrue(named.isa(documented));
  }

}
