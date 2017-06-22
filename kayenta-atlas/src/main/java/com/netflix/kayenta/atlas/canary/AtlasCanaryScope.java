/*
 * Copyright 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.kayenta.atlas.canary;

import com.netflix.kayenta.canary.CanaryScope;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AtlasCanaryScope extends CanaryScope {

  @NotNull
  private String type;

  public String cq() {
    if (type == null) {
      throw new IllegalArgumentException("Atlas canary scope requires 'type' to be application, cluster or node.");
    }

    switch (type) {
      case "application":
      case "cluster":
      case "node":
        return ":list,(,nf." + type + "," + scope + ",:eq,:cq,),:each";
      default:
        throw new IllegalArgumentException("Scope type '" + type + "' is unknown: scope=" + scope);
    }
  }
}