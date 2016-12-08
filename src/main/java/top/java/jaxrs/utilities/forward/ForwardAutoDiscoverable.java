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

import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import top.java.jaxrs.utilities.forward.internal.AnnotationCollector;

public class ForwardAutoDiscoverable implements AutoDiscoverable
{
    private Class<ForwardFeature> forwardHeaderFeature = ForwardFeature.class;

    @Override
    public void configure(FeatureContext context)
    {
        if (!context.getConfiguration().isRegistered(forwardHeaderFeature))
        {
            AbstractBinder binder = new AbstractBinder()
            {
                @Override
                protected void configure()
                {
                    bindToSelf(ForwardContainerRequestFilter.class);
                    bindToSelf(ForwardClientRequestFilter.class);
                    bindToSelf(AnnotationCollector.class);
                }

                private ServiceBindingBuilder<?> bindToSelf(Class<?> type)
                {
                    return bind(type).to(type);
                }
            };
            context.register(binder);
            context.register(forwardHeaderFeature);
        }
    }
}
