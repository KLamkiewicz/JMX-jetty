package pl.krzysiek.server.JMX;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class RequestCounter extends NotificationBroadcasterSupport implements RequestCounterMBean {

	private static RequestCounter rq = new RequestCounter();
	private static int timesInvokedCounter = 0;
	private static int sequence = 0;
	private static final Object lock = new Object();
	
	public static RequestCounter getInstance(){
		return rq;
	}

	@Override
	public void increaseTI() {	
		synchronized (lock) {
			Notification notification = new AttributeChangeNotification(rq, sequence++, System.currentTimeMillis(),
										"New request", "timesInvokedCounter", "int", 
										timesInvokedCounter, ++timesInvokedCounter);
			
			sendNotification(notification);
		}
	}
	
	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
		String name = AttributeChangeNotification.class.getName();
		String description = "Request count increased";
		MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);

		return new MBeanNotificationInfo[] { info };
	}

	@Override
	public int getTI() {
		synchronized (lock) {
			return timesInvokedCounter;
		}
	}

}
