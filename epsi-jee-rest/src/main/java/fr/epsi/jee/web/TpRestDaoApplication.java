package fr.epsi.jee.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class TpRestDaoApplication extends SpringBootServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("contextConfigLocation", "fr.epsi.jee.web.config");
		WebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
		if (rootAppContext != null) {
			servletContext.addListener(new ContextLoaderListener(rootAppContext));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(TpRestDaoApplication.class, args);
	}

}
