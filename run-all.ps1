# Run all services in separate PowerShell windows
$root = 'C:\dev\e-commerce'

Start-Process powershell "-NoExit -Command cd '$root'; ./gradlew :order:bootRun"
Start-Process powershell "-NoExit -Command cd '$root'; ./gradlew :inventory:bootRun"
Start-Process powershell "-NoExit -Command cd '$root'; ./gradlew :payment:bootRun"
Start-Process powershell "-NoExit -Command cd '$root'; ./gradlew :notification:bootRun"
