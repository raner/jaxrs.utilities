//                                                                          //
// Copyright 2016 Mirko Raner                                               //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
//     http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
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
