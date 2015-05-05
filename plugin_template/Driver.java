package _nameInLower_driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverCommandExecutor;

import _nameInLower_driver._name_DriverService;


public class _name_Driver extends RemoteWebDriver {
    public _name_Driver(Capabilities capabilities) {
        this(_name_DriverService.createDefaultService(), capabilities);
    }

    public _name_Driver(_name_DriverService service, Capabilities capabilities) {
        super(new DriverCommandExecutor(service), capabilities);
    }
}
