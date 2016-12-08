//                                                                          //
// top.java.jaxrs.utilities - Enhancements and utilities for JAX-RS         //
//                                                                          //
// Written in 2016 by Mirko Raner (mirko@raner.ws)                          //
//                                                                          //
// To the extent possible under law, the author(s) have dedicated all       //
// copyright and related and neighboring rights to this software to the     //
// public domain worldwide. This software is distributed without any        //
// warranty.                                                                //
// You should have received a copy of the CC0 Public Domain Dedication      //
// along with this software. If not, see                                    //
//                                                                          //
// http://creativecommons.org/publicdomain/zero/1.0/                        //
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
    /**
    * The singleton {@link ForwardHeaderMap} instance.
    *
    * TODO: this should be solved by injecting a shared singleton, but that doesn't seem to work currently...
    **/
    public static ForwardHeaderMap INSTANCE = new ForwardHeaderMap();

    private ForwardHeaderMap()
    {
        super();
    }

    @Override
    protected Map<String, Object> initialValue()
    {
        return new HashMap<>();
    }

    public void addHeader(String name, Object value)
    {
        if (value != null)
        {
            get().put(name, value);
        }
    }
}
