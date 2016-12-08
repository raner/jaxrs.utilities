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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import top.java.jaxrs.utilities.forward.internal.AnnotationCollector;
import static java.util.stream.IntStream.range;
import static top.java.jaxrs.utilities.forward.ForwardHeaderMap.INSTANCE;

public class ForwardContainerRequestFilter implements ContainerRequestFilter
{
    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private AnnotationCollector annotationCollector;

    @Override
    public void filter(ContainerRequestContext context) throws IOException
    {
        Method method = resourceInfo.getResourceMethod();
        int parameters = method.getParameterCount();
        Stream<Set<Annotation>> annotationSets;
        annotationSets = range(0, parameters).mapToObj(parameter -> annotationCollector.collectAnnotations(method, parameter));
        annotationSets.forEach(annotationSet ->
        {
            Optional<Forward> forwarded = findAnnotation(Forward.class, annotationSet.stream());
            Optional<HeaderParam> headerParam = findAnnotation(HeaderParam.class, annotationSet.stream());
            headerParam.ifPresent(header -> forwarded.ifPresent(forward -> INSTANCE.addHeader(forward.value(), value(context, header))));
        });
    }

    private <_Annotation_> Optional<_Annotation_> findAnnotation(Class<_Annotation_> type, Stream<Annotation> annotations)
    {
        Predicate<Annotation> predicate = annotation -> type.equals(annotation.annotationType());
        @SuppressWarnings("unchecked")
        Optional<_Annotation_> annotation = (Optional<_Annotation_>)annotations.filter(predicate).findFirst();
        return annotation;
    }

    private Object value(ContainerRequestContext requestContext, HeaderParam headerParameter)
    {
        String headerName = headerParameter.value();
        return requestContext.getHeaderString(headerName);
    }
}
