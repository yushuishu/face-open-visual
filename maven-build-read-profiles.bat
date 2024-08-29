@echo off
setlocal enabledelayedexpansion

:: 读取 application.yml 文件中的 spring.profiles.active 属性
for /f "tokens=2 delims=: " %%a in ('findstr "spring.profiles.active" src\main\resources\application.yml') do (
    set spring_profiles_active=%%a
)

:: 去除前后的空格
set spring_profiles_active=%spring_profiles_active:~1,-1%

:: 设置 Maven 属性
call mvn -Dspring.profiles.active=%spring_profiles_active% clean package

endlocal
