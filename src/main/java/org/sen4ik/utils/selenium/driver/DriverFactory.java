package org.sen4ik.utils.selenium.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DriverFactory {

	private static List<DriverThread> threadPool = Collections.synchronizedList(new ArrayList<>());
	private static ThreadLocal<DriverThread> thread;

	public static void instantiateDriver() {
		thread = ThreadLocal.withInitial(() -> {
			DriverThread driverThread = new DriverThread();
			threadPool.add(driverThread);
			return driverThread;
		});
	}

	public static WebDriver getDriver() {
		if (thread == null) {
			instantiateDriver();
		}
		return thread.get().getDriver();
	}

	public static void quitDriver() {
		thread.get().quitDriver();
	}

	public static void quitAllDrivers() {
		for (DriverThread driverThread : threadPool) {
			driverThread.quitDriver();
		}
	}

	public static boolean hasThreads() {
		return !threadPool.isEmpty();
	}

}
