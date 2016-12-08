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

import javax.inject.Inject;
import javax.inject.Singleton;
import top.java.jaxrs.utilities.forward.Forward;

/**
* {@link AddressBookForwardingImpl} is a simple implementation of the {@link AddressBookForwarding} interface that
* forwards all calls to an embedded JAX-RS {@link AddressBook} implementation.
*
* @author Mirko Raner
**/
@Singleton
public class AddressBookForwardingImpl implements AddressBookForwarding
{
    @Inject
    private AddressBook addressBook;

    @Override
    public void createOrUpdate(@Forward("ticket") String ticket, String contact, Address address)
    {
        try
        {
            addressBook.createOrUpdate(contact, address);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public Address getAddress(@Forward("ticket") String ticket, String contact)
    {
        return addressBook.getAddress(contact);
    }
}
