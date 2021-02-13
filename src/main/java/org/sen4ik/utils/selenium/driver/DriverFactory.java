package org.sen4ik.utils.selenium.driver;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class DriverFactory {

	private static List<DriverThread> threadPool = Collections.synchronizedList(new ArrayList<>());
	private static ThreadLocal<DriverThread> thread;

	public static void instantiateDriverThread() {
		log.debug("CALLED: instantiateDriverThread()");
		thread = new ThreadLocal<DriverThread>() {
			@Override
			protected DriverThread initialValue() {
				DriverThread driverThread = new DriverThread();
				threadPool.add(driverThread);
				return driverThread;
			}
		};
	}

	public static WebDriver getDriver() {
		log.debug("CALLED: getDriver()");
		if (thread == null) {
			instantiateDriverThread();
		}
		return thread.get().getDriver();
	}

	public static void quitDriver() {
		log.debug("CALLED: quitDriver()");
		thread.get().quitDriver();
	}

	public static void quitAllDrivers() {
		log.debug("CALLED: quitAllDrivers()");
		for (DriverThread driverThread : threadPool) {
			driverThread.quitDriver();
		}
	}

	public static boolean hasThreads() {
		log.debug("CALLED: hasThreads()");
		return !threadPool.isEmpty();
	}

}
