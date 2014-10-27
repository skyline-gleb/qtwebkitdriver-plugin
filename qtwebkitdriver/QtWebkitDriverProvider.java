package qtwebkitdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.server.DriverProvider;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Platform;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;
import java.util.logging.Level;


public class QtWebkitDriverProvider implements DriverProvider {
    private static final Logger log = Logger.getLogger(QtWebkitDriverProvider.class.getName());

    private final String browser = "qtwebkit";

    private Capabilities capabilities;
    private Class<? extends WebDriver> implementation;

    public QtWebkitDriverProvider() {
        this.capabilities = new DesiredCapabilities(browser, "", Platform.ANY);
        String className = "qtwebkitdriver.QtWebkitDriver";
        try {
            this.implementation = Class.forName(className).asSubclass(WebDriver.class);
        } catch (ClassNotFoundException e) {
            log.log(Level.INFO, "Unable to register driver with className " + className + " due to ClassNotFoundException");
        } catch (NoClassDefFoundError e) {
            log.log(Level.WARNING, "Unable to register driver with className " + className + " due to NoClassDefFoundError");
        }
    }

    public QtWebkitDriverProvider(Capabilities capabilities, Class<? extends WebDriver> implementation) {
        this.capabilities = capabilities;
        this.implementation = implementation;
    }

    @Override
    public Capabilities getProvidedCapabilities() {
        return capabilities;
      }

    @Override
    public Class<? extends WebDriver> getDriverClass() {
        return implementation;
      }

    @Override
    public WebDriver newInstance(Capabilities capabilities) {
        log.info("Creating a new session for " + capabilities);
        // Try and call the single arg constructor that takes a capabilities first
        return callConstructor(implementation, capabilities);
    }

    private WebDriver callConstructor(Class<? extends WebDriver> from, Capabilities capabilities) {
        try {
            Constructor<? extends WebDriver> constructor = from.getConstructor(Capabilities.class);
            return constructor.newInstance(capabilities);
        } catch (NoSuchMethodException e) {
            try {
                return from.newInstance();
            } catch (InstantiationException e1) {
                throw new WebDriverException(e);
            } catch (IllegalAccessException e1) {
                throw new WebDriverException(e);
            }
        } catch (InvocationTargetException e) {
            throw new WebDriverException(e);
        } catch (InstantiationException e) {
            throw new WebDriverException(e);
        } catch (IllegalAccessException e) {
            throw new WebDriverException(e);
        }
    }
}
