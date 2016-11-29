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
        WebTarget target = ClientBuilder.newClient().target("http://localhost:8080");
        AddressBook addressBook = WebResourceFactory.newResource(AddressBook.class, target);
        bind(addressBook).to(AddressBook.class);
    }
}
