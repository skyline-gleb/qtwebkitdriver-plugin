qtwebkitdriverplugin
====================

plugin for selenium-server-standalone to run with qtwebkitdriver

# build

```bash
./build <path to selenium-server-standalone.jar>
```

# run

```bash
java -cp selenium-server-standalone-2.43.1.jar:qtwebkitdriverplugin.jar org.openqa.grid.selenium.GridLauncher -Dwebdriver.chrome.driver=qtwebkitdriver -Dwebdriver.qtwebkit.args="--log-path=qtwebkitdriver.log"
```
