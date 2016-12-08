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
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.JsonParser;
import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.response.RestResponse;
import static com.jcabi.http.Request.GET;
import static com.jcabi.http.Request.PUT;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
* {@link AddressBookIntegrationTest} is an embedded-Tomcat-based integration test for verifying the integration between
* Jersey and top.java.jaxrs.utilities.
*
* @author Mirko Raner
**/
public class AddressBookIntegrationTest
{
    private static final String SERVLET_NAME = "jersey-container-servlet";

    private static Tomcat server;

    /**
    * Starts a stand-alone Tomcat server with a Jersey container running the {@link AddressBook} service.
    *
    * @param arguments command line arguments (currently ignored)
    * @throws ServletException if there was a problem with the Jersey servlet container
    * @throws LifecycleException if there was a problem with the Tomcat server
    **/
    public static void main(String[] arguments) throws ServletException, LifecycleException, IOException
    {
        startServer();
        server.getServer().await();
    }

    @BeforeClass
    public static void startServer() throws ServletException, LifecycleException, IOException
    {
        Path webAppDirectory = Files.createTempDirectory(null);
        server = new Tomcat();
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty())
        {
            webPort = "8080";
        }
        server.setPort(Integer.valueOf(webPort));
        Context context = server.addWebapp("", webAppDirectory.toString());
        Tomcat.addServlet(context, SERVLET_NAME, new ServletContainer(new AddressBookConfiguration()));
        context.addServletMappingDecoded("/*", SERVLET_NAME);
        server.start();
    }

    @AfterClass
    public static void stopServer() throws LifecycleException
    {
        server.stop();
    }

    @Test
    public void testRequestAndResponse() throws Exception
    {
        String json = "{\"street\":\"1 Main Street\", \"city\":\"Smalltown\", \"state\":\"CA\", \"zipCode\":\"99999\"}";
        url("addressbookForwarding/John%20Doe").method(PUT)
            .header("ticket", "VALID_TICKET")
            .body().set(json).back()
            .fetch().as(RestResponse.class)
            .assertStatus(HTTP_NO_CONTENT);
        url("addressbookForwarding/John%20Doe").method(GET)
            .header("ticket", "VALID_TICKET")
            .fetch().as(RestResponse.class)
            .assertStatus(HTTP_OK)
            .assertBody(matches(json));
    }

    private Request url(String path)
    {
        return new ApacheRequest("http://localhost:8080/" + path).header(CONTENT_TYPE, APPLICATION_JSON);
    }

    private Matcher<String> matches(String expected)
    {
        return new TypeSafeMatcher<String>()
        {
            private JsonParser parser = new JsonParser();

            @Override
            protected boolean matchesSafely(String actual)
            {
                return parser.parse(expected).equals(parser.parse(actual));
            }

            @Override
            public void describeTo(Description description)
            {
                description.appendValue(expected);
            }
        };
    }
}
