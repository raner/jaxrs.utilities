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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
* A simple JAX-RS application for testing JAX-RS Extensions with RESTful web services.
*
* @author Mirko Raner
**/
@Path("/addressbook")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface AddressBookPrimary
{
    @PUT
    @Path("/{contact}")
    void createOrUpdate(@HeaderParam("ticket") String ticket, @PathParam("contact") String contact, Address address);

    @GET
    @Path("/{contact}")
    Address getAddress(@HeaderParam("ticket") String ticket, @PathParam("contact") String contact);
}
