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
    <version>6fe86cc</version>
  </dependency>
</dependencies>
 ```

Third, let the library know where to get your driver instance. In my case, I am using driver factory, which is also part of this project.
```java
SeleniumUtils.setSupplier(DriverFactory::getDriver);
 ```
