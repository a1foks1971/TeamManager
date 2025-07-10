@echo off
rem echo Запускаю эмулятор...
rem start "" "C:\Path\To\Your\Android\Sdk\emulator\emulator.exe" -avd Your_AVD_Name

rem echo Жду 10 секунд, чтобы эмулятор успел загрузиться...
rem timeout /t 10

rem echo Настраиваю проброс порта ADB...
adb -s emulator-5554 forward tcp:8080 tcp:8080

rem echo Готово!
pause