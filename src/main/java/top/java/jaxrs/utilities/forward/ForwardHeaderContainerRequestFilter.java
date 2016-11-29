//                                                                          //
// Copyright 2016 Mirko Raner                                               //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
// http://www.apache.org/licenses/LICENSE-2.0                               //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
//                                                                          //
package top.java.jaxrs.utilities.forward;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;

public class ForwardHeaderContainerRequestFilter implements ContainerRequestFilter
{
    private final static Class<Forward> FORWARD = Forward.class;

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    private ForwardHeaderMap headers;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        Method method = resourceInfo.getResourceMethod();
        Stream<Parameter> parameters = Stream.of(method.getParameters());
        Stream<Parameter> forwardedParameters = parameters.filter(parameter -> parameter.isAnnotationPresent(FORWARD));
        forwardedParameters.map(this::getForwardHeader).forEach(header -> headers.addHeader(header, header));
        // ^^^ TODO: this just puts the header name as the header value...
    }

    private String getForwardHeader(Parameter parameter)
    {
        return parameter.getAnnotation(FORWARD).value();
    }
}
