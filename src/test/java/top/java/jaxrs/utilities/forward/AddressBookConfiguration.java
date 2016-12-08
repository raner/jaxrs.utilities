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

import org.glassfish.jersey.server.ResourceConfig;

/**
* A simple Jersey JAX-RS configuration for testing JAX-RS Extensions with RESTful web services.
*
* @author Mirko Raner
**/
public class AddressBookConfiguration extends ResourceConfig
{
    public AddressBookConfiguration()
    {
        register(new AddressBookBinder());
        register(AddressBookImpl.class);
        register(AddressBookForwardingImpl.class);
        register(ContactParameterConverterProvider.class);
    }
}
