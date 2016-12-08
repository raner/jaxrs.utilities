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
package top.java.jaxrs.utilities.forward.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.inject.Singleton;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;

/**
* The {@link AnnotationCollector} collects annotations for a particular method parameter from all superclasses and
* interfaces.
*
* @author Mirko Raner
**/
@Singleton
public class AnnotationCollector
{
    /**
    * Collects annotations.
    *
    * @param method the {@link Method}
    * @param parameter the parameter index
    * @return all annotations from the method itself or from any superclass or super-interface
    **/
    public Set<Annotation> collectAnnotations(Method method, int parameter)
    {
        Set<Method> methods = new HashSet<>(singleton(method));
        Class<?> declaringClass = method.getDeclaringClass();
        class Utilities
        {
            void addMethod(Class<?> type)
            {
                try
                {
                    methods.add(type.getDeclaredMethod(method.getName(), method.getParameterTypes()));
                }
                catch (@SuppressWarnings("unused") NoSuchMethodException noSuchMethod)
                {
                    return;
                }
            }
        }
        Utilities utilities = new Utilities();
        utilities.addMethod(declaringClass.getSuperclass());
        Stream.of(declaringClass.getInterfaces()).forEach(utilities::addMethod);
        Stream<Annotation[]> annotations = methods.stream().map(annotated -> annotated.getParameters()[parameter].getAnnotations());
        return annotations.flatMap(Stream::of).collect(toSet());
    }
}
