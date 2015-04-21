package pl.krzysiek.server.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.krzysiek.server.JMX.*;

@Path("/")
public class HomeController {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String main() {
		RequestCounter.getInstance().increaseTI();
		return "Main";
	}
	
	@GET
	@Path("rest")
	@Produces(MediaType.TEXT_PLAIN)
	public String rest() {
		RequestCounter.getInstance().increaseTI();
		return "Rest";
	}
}
