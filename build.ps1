Param([string]$driverName, [string]$seleniumServerPath)

$help = "Add driver name as first argument and path to selenium-server-standalone as second argument"
if (!$driverName -or !$seleniumServerPath) { Write-Host $help; return }

$nameInLower = $driverName.ToLower()
$nameInUpper = $driverName.ToUpper()
$packageName = $nameInLower + "driver"

New-Item META-INF\services\org.openqa.selenium.remote.server.DriverProvider -type file -force `
    -value ($nameInLower + "driver." + $driverName + "DriverProvider") | Out-Null
New-Item $packageName -type directory -force | Out-Null

$files = "Driver.java", "DriverProvider.java", "DriverService.java" 
foreach ($file in $files) 
{ 
    $outFile = Join-Path $packageName ($driverName + $file)
    Get-Content plugin_template\$file `
	    | ForEach-Object { $_ -Replace "_name_", $driverName -Replace "_nameInLower_", $nameInLower -Replace "_nameInUpper_", $nameInUpper } `
		| Set-Content $outFile
}

javac -cp $seleniumServerPath $packageName/*.java
jar cf $packageName-plugin.jar $packageName/*.class META-INF/*