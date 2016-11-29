//                                                                          //
// Copyright 2016 Mirko Raner                                               //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
// http://www.apache.org/licenses/LICENSE-2.0                               //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
//                                                                          //
package top.java.jaxrs.utilities.forward;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

/**
* The {@link ForwardHeaderMap} provides shared storage between filters, mainly used to communicate header
* values.
*
* @author Mirko Raner
**/
@Singleton
public class ForwardHeaderMap extends ThreadLocal<Map<String, Object>>
{
    @Override
    protected Map<String, Object> initialValue()
    {
        return new HashMap<>();
    }

    public void addHeader(String name, Object value)
    {
        get().put(name, value);
    }

    public Object getHeader(String name)
    {
        return get().get(name);
    }
}
