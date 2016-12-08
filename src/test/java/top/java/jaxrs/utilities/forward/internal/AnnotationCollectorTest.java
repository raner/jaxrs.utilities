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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.HeaderParam;
import org.junit.Test;
import top.java.jaxrs.utilities.forward.Forward;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

public class AnnotationCollectorTest
{
    static class TestClass
    {
        void methodWithDeprecatedParameter(@SuppressWarnings("unused") @Deprecated Void parameter)
        {
            return;
        }
    }

    static class TestSubclass extends TestClass
    {
        @Override
        void methodWithDeprecatedParameter(@Forward("value") Void parameter)
        {
            return;
        }
    }

    static interface TestInterface
    {
        void jaxrsEndpoint(@HeaderParam("header") String header);
    }

    static class TestImplementation implements TestInterface
    {
        @Override
        public void jaxrsEndpoint(@Forward("forward") String header)
        {
            return;
        }
    }

    private AnnotationCollector annotationCollector = new AnnotationCollector();

    @Test
    public void testAnnotationOnPrimaryMethod() throws Exception
    {
        Method methodWithUnusedParameter = TestClass.class.getDeclaredMethod("methodWithDeprecatedParameter", Void.class);
        Set<Annotation> annotations = annotationCollector.collectAnnotations(methodWithUnusedParameter, 0);
        Set<Class<? extends Annotation>> actual = annotations.stream().map(Annotation::annotationType).collect(toSet());
        Set<Class<? extends Annotation>> expected = annotations(Deprecated.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testInheritedAnnotationsFromInterfaces() throws Exception
    {
        Method jaxrsEndpoint = TestImplementation.class.getDeclaredMethod("jaxrsEndpoint", String.class);
        Set<Annotation> annotations = annotationCollector.collectAnnotations(jaxrsEndpoint, 0);
        Set<Class<? extends Annotation>> actual = annotations.stream().map(Annotation::annotationType).collect(toSet());
        Set<Class<? extends Annotation>> expected = annotations(Forward.class, HeaderParam.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testInheritedAnnotationsFromSuperclass() throws Exception
    {
        Method method = TestSubclass.class.getDeclaredMethod("methodWithDeprecatedParameter", Void.class);
        Set<Annotation> annotations = annotationCollector.collectAnnotations(method, 0);
        Set<Class<? extends Annotation>> actual = annotations.stream().map(Annotation::annotationType).collect(toSet());
        Set<Class<? extends Annotation>> expected = annotations(Forward.class, Deprecated.class);
        assertEquals(expected, actual);
    }

    @SafeVarargs
    private final Set<Class<? extends Annotation>> annotations(Class<? extends Annotation>... annotations)
    {
        return new HashSet<>(Arrays.asList(annotations));
    }
}
