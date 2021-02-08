# selenium-utils

Set of Java Selenium utilities.

If you want to use it, you need to do the following.
First, add jitpack repository to the pom.xml:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Second, add dependency to the pom.xml:
```xml
<dependencies>
  <dependency>
    <groupId>com.github.sen4ik</groupId>
    <artifactId>selenium-utils</artifactId>
    <version>aa229ad</version>
  </dependency>
</dependencies>
 ```

Third, let the library know where to get your driver instance:
```java
SeleniumUtils.init(DriverFactory::getDriver);
 ```

Currently, there are six utils.
* BrowserWindowUtil - helps working with browser windows.
* DriverUtil - contains methods that are related to working with a driver.
* DropdownUtil - helps working with dropdowns.
* JavaScriptUtil - contains methods that utilize JavaScript executor.
* KeyboardUtil - utility that imitates keyboard buttons being pressed.
* WaiterUtil - helps wait for elements.

The project also contains driver factory implementation, which helps to create, reuse and dismiss WebDriver instances.
