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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Forward} annotation marks method parameters that need to be forwarded as headers to outgoing JAX-RS method
* calls.
*
* @author Mirko Raner
**/
@Target(PARAMETER)
@Retention(RUNTIME) 
public @interface Forward
{
    /**
    * @return the name of the header for outgoing JAX-RS calls
    **/
    String value();
}
