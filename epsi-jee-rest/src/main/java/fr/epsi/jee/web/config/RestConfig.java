package fr.epsi.jee.web;

import io.swagger.v3.jaxrs2.SwaggerSerializers;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
@ApplicationPath("/v1")
public class RestConfig extends ResourceConfig {

    @Autowired
    private ServletConfig servletConfig;

    public RestConfig(){
        this.packages("fr.epsi.jee.web.v1");
   }

   @PostConstruct
    public void init(){
       this.register(WadlResource.class);
       property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

       var openApiResource = new OpenApiResource();
       OpenAPI oas = new OpenAPI();
       Info info = new Info()
               .title("Gestionnaire de commandes")
               .contact(new Contact().email("epsi.fr"));
       oas.info(info);
       SwaggerConfiguration oasConfig = new SwaggerConfiguration()
               .prettyPrint(true)
               .openAPI(oas)
               .resourcePackages(Stream.of("fr.epsi.jee.web.v1.controller").collect(Collectors.toSet()));

       openApiResource.setOpenApiConfiguration(oasConfig);
       register(openApiResource);
   }
}
