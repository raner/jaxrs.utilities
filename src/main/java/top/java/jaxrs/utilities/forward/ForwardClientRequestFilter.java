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

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
* The {@link ForwardClientRequestFilter} transfers header values from the shared {@link ForwardHeaderMap}
* to the request headers of an outgoing JAX-RS call.
*
* @author Mirko Raner
**/
public class ForwardClientRequestFilter implements ClientRequestFilter
{
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException
    {
        MultivaluedMap<String, Object> requestHeaders = requestContext.getHeaders();
        ForwardHeaderMap.INSTANCE.get().forEach((key, value) -> requestHeaders.add(key, value));
    }
}
