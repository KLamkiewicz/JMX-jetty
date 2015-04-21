package pl.krzysiek.server.JMX;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RequestListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName objectName = new ObjectName("pl.krzysiek.server:type=RequestCounter");
			RequestCounter mbean = RequestCounter.getInstance();
			mbs.registerMBean(mbean, objectName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName objName = new ObjectName("pl.krzysiek.server:type=RequestCounter");
			mbs.unregisterMBean(objName);
		} catch (Exception ex) {
		}
	}

}