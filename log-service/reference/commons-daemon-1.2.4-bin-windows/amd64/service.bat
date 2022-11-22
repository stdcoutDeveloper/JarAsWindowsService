@echo off

setlocal

if "%1"=="install" (
	goto INSTALL
)
if "%1"=="uninstall" (
	goto UNINSTALL
)

:INSTALL
%~dp0\prunsrv.exe //IS//LogDaemonService ^
    --DisplayName="Log Daemon Service" ^
	--Description="A log service that runs 3s periodically" ^
    --Install=@@PATH_TO_JAR@@\log-service\reference\commons-daemon-1.2.4-bin-windows\amd64\prunsrv.exe ^
    --Startup=manual ^
    --Jvm=auto ^
    --Classpath=@@PATH_TO_JAR@@\log-service\reference\commons-daemon-1.2.4-bin-windows\amd64\log-service-0.0.1-SNAPSHOT.jar;@@PATH_TO_JAR@@\log-service\reference\commons-daemon-1.2.4-bin-windows\amd64\lib\* ^
    --StartMode=jvm ^
    --StartClass=com.dev.LogDaemon ^
    --StartParams="" ^
    --StartMethod=start ^
    --StopMode=jvm ^
    --StopClass=com.dev.LogDaemon ^
    --StopMethod=stop ^
	--StdOutput "daemon-out.log" ^
	--StdError "daemon-err.log" 
echo LogDaemonService is installed.
exit /b 0

:UNINSTALL
%~dp0\prunsrv.exe //DS//LogDaemonService
echo LogDaemonService is uninstalled!
exit /b 0