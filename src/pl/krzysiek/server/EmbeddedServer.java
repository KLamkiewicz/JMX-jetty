package pl.krzysiek.server;

import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import pl.krzysiek.server.JMX.RequestListener;
import pl.krzysiek.server.controllers.HomeController;

public class EmbeddedServer {

	public static void main(String[] args) throws Exception {
		System.out.println("TEST");
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		Server server = new Server(8080);
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", HomeController.class.getCanonicalName());

		context.addEventListener(new RequestListener());
		
		server.start();
		server.join();
	}
}
