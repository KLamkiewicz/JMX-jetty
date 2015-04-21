package pl.krzysiek.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.AttributeChangeNotification;
import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import pl.krzysiek.server.JMX.RequestCounterMBean;

public class JMXClient {

	public static void main(String[] args) throws Exception{
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        ObjectName mxbeanName = new ObjectName("pl.krzysiek.server:type=RequestCounter");
        
        NotificationListener listener = new NotificationListener() {
			@Override
			public void handleNotification(Notification notification, Object obj) {
				//System.out.println(notification.getMessage());
				if (notification instanceof AttributeChangeNotification) {
					AttributeChangeNotification acn = (AttributeChangeNotification) notification;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					Date date = new Date(acn.getTimeStamp());
					sdf.format(date);
					System.out.print(notification.getMessage() + " made at " + sdf.format(date) + " ---");
					System.out.println(" Request count changed from " + acn.getOldValue() + " to " + acn.getNewValue());
				}
			}
		};
		
        mbsc.addNotificationListener(mxbeanName, listener, null, null);
		RequestCounterMBean counterProxy = JMX.newMBeanProxy(mbsc, mxbeanName, RequestCounterMBean.class);
        
        System.in.read();
        
	}
	
}
