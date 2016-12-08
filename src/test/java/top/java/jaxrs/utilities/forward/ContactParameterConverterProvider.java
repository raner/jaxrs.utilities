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

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class ContactParameterConverterProvider implements ParamConverterProvider
{
    @Override
    public <_Type_> ParamConverter<_Type_> getConverter(Class<_Type_> rawType, Type genericType, Annotation[] annotations)
    {
        final char SEPARATOR = '_';

        if (Contact.class.equals(rawType))
        {
            @SuppressWarnings("unchecked")
            ParamConverter<_Type_> converter = (ParamConverter<_Type_>)new ParamConverter<Contact>()
            {
                @Override
                public Contact fromString(String value)
                {
                    Contact contact = new Contact();
                    int separator = value.indexOf(SEPARATOR);
                    String firstName = value.substring(0, separator);
                    String lastName = value.substring(separator+1);
                    contact.setFirstName(firstName);
                    contact.setLastName(lastName);
                    return contact;
                }

                @Override
                public String toString(Contact contact)
                {
                    return contact.getFirstName() + SEPARATOR + contact.getLastName();
                }
            };
            return converter;
        }
        return null;
    }
}
