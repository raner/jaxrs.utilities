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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

/**
* The {@link AddressBookBinder} is the HK2 binder that binds the external dependencies of the address book service.
*
* @author Mirko Raner
**/
public class AddressBookBinder extends AbstractBinder
{
    @Override
    protected void configure()
    {
        Client client = ClientBuilder.newClient();
        client.register(ForwardClientRequestFilter.class);
        WebTarget target = client.target("http://localhost:8080");
        AddressBook addressBook = WebResourceFactory.newResource(AddressBook.class, target);
        bind(addressBook).to(AddressBook.class);
    }
}
