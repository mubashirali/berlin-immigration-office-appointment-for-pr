package com.appointment.search.driverutil

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.opera.OperaDriver
import org.openqa.selenium.remote.RemoteWebDriver
import java.util.concurrent.TimeUnit

object DriverFactory {

    val browser: WebDriver
        get() {
            val driver: WebDriver
            val browserName = System.getProperty("browser", DriverType.CHROME.toString()).toUpperCase()
            val driverType = DriverType.valueOf(browserName)

            when (driverType) {
                DriverType.CHROME -> {
                    WebDriverManager.chromedriver().setup()
                    val options = ChromeOptions()
                    options.addArguments("--disable-blink-features=AutomationControlled")
                    options.addArguments("--headless")
                    driver = ChromeDriver(options)
                }

                DriverType.FIREFOX -> {
                    WebDriverManager.firefoxdriver().setup()
                    driver = FirefoxDriver()
                }

                DriverType.EDGE -> {
                    WebDriverManager.edgedriver().setup()
                    driver = EdgeDriver()
                }

                DriverType.IE -> {
                    WebDriverManager.iedriver().setup()
                    driver = InternetExplorerDriver()
                }

                DriverType.OPERA -> {
                    WebDriverManager.operadriver().setup()
                    driver = OperaDriver()
                }
            }

            driver.manage().window().maximize()
            driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
            (driver as RemoteWebDriver).executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")

            return driver
        }
}
