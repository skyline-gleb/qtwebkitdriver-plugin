qtwebkitdriverplugin
====================

plugin for selenium-server-standalone to run with custom selenium-based driver

# build

```bash
./build <driver name> <path to selenium-server-standalone.jar>
```

```powershell
.\build.ps1 <driver name> <path to selenium-server-standalone.jar>
```

# run

```bash
java -cp selenium-server-standalone-2.43.1.jar:qtwebkitdriver-plugin.jar org.openqa.grid.selenium.GridLauncher -Dwebdriver.qtwebkit.driver=qtwebkitdriver -Dwebdriver.qtwebkit.args="--log-path=qtwebkitdriver.log"
```
