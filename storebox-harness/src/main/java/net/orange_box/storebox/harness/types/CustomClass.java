/*
 * Copyright 2015 Martin Bella
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.orange_box.storebox.harness.types;

import java.util.Arrays;

public class CustomClass {
    
    private int hashCode;
    
    private final String one;
    private final String two;
    
    public CustomClass() {
        this("", "");
    }
    
    public CustomClass(String one, String two) {
        this.one = one;
        this.two = two;
    }
    
    public String getOne() {
        return one;
    }
    
    public String getTwo() {
        return two;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof CustomClass)) {
            return false;
        }
        
        final CustomClass other = (CustomClass) o;
        return (one.equals(other.one) && two.equals(other.two));
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = Arrays.hashCode(new String[] {one, two});
        }
        
        return hashCode;
    }
}
