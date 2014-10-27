package qtwebkitdriver;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.net.PortProber;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;


public class QtWebkitDriverService extends DriverService {
    public static final String QTWEBKIT_DRIVER_EXE_PROPERTY = "webdriver.qtwebkit.driver";
    public static final String QTWEBKIT_DRIVER_ARGS_PROPERTY = "webdriver.qtwebkit.args";

    public QtWebkitDriverService(File executable, int port, ImmutableList<String> args,
                                 ImmutableMap<String, String> environment) throws IOException {
        super(executable, port, args, environment);
    }

    public static QtWebkitDriverService createDefaultService() {
        File exe = findExecutable("qtwebkitdriver", QTWEBKIT_DRIVER_EXE_PROPERTY, "", "");
        String args = getArguments(QTWEBKIT_DRIVER_ARGS_PROPERTY, "");
        return new Builder().usingDriverExecutable(exe).usingAnyFreePort().usingArgs(args).build();
    }

    public static String getArguments(String argsProperty, String defaultArguments) {
        return System.getProperty(argsProperty, defaultArguments);
    }

    public static class Builder {
        private int port = 0;
        private File exe = null;
        private ImmutableList.Builder<String> args = ImmutableList.builder();
        private ImmutableMap<String, String> environment = ImmutableMap.of();

        public Builder usingDriverExecutable(File file) {
            checkNotNull(file);
            checkExecutable(file);
            this.exe = file;
            return this;
        }

        public Builder usingArgs(String Arguments) {
            for(String arg : Arguments.trim().split("\\s+")) {
                this.args.add(arg);
            }
            return this;
        }

        public Builder usingPort(int port) {
            checkArgument(port >= 0, "Invalid port number: %d", port);
            this.port = port;
            return this;
        }

        public Builder usingAnyFreePort() {
            this.port = 0;
            return this;
        }

        public QtWebkitDriverService build() {
            if (port == 0) {
                port = PortProber.findFreePort();
            }

            checkState(exe != null, "Path to the driver executable not specified");

            try {
                this.args.add(String.format("--port=%d", port));

                return new QtWebkitDriverService(exe, port, this.args.build(), environment);

            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }
    }
}
