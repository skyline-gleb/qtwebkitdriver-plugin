package qtwebkitdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverCommandExecutor;

import qtwebkitdriver.QtWebkitDriverService;


public class QtWebkitDriver extends RemoteWebDriver {
    public QtWebkitDriver(Capabilities capabilities) {
        this(QtWebkitDriverService.createDefaultService(), capabilities);
    }

    public QtWebkitDriver(QtWebkitDriverService service, Capabilities capabilities) {
        super(new DriverCommandExecutor(service), capabilities);
    }
}
