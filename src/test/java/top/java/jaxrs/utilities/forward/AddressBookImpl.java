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
* {@link AddressBookImpl} is a simple in-memory implementation of the {@link AddressBook} JAX-RS interface.
*
* @author Mirko Raner
**/
@Singleton
public class AddressBookImpl implements AddressBookPrimary
{
    private Map<String, Address> map = new HashMap<>();

    @Override
    public void createOrUpdate(String ticket, String contact, Address address)
    {
        validate(ticket);
        map.put(contact, address);
    }

    @Override
    public Address getAddress(String ticket, String contact)
    {
        validate(ticket);
        return map.get(contact);
    }

    private void validate(String ticket)
    {
        if (!"VALID_TICKET".equals(ticket))
        {
            throw new IllegalAccessError("Invalid ticket: " + ticket);
        }
    }
}
