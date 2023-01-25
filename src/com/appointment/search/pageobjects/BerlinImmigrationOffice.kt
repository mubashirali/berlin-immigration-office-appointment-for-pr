package com.appointment.search.pageobjects

import org.apache.commons.io.FileUtils
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class BerlinImmigrationOffice(driver: WebDriver) {

    private var driver: WebDriver? = null

    @FindBy(name = "applicationForm:managedForm:proceed")
    internal var next: WebElement? = null

    @FindBy(name = "anton-remote-back")
    internal var back: WebElement? = null

    init {
        this.driver = driver
        PageFactory.initElements(driver, this)
    }

    fun openURL() {
        driver!!.get("https://otv.verwalt-berlin.de/ams/TerminBuchen/wizardng?lang=en")
    }

    fun acknowledge() {
        val ackInputCheckbox = "gelesen"
        driver!!.findElement(By.name(ackInputCheckbox)).click()
    }

    fun clickNext() {
        WebDriverWait(driver, 300).until(
            ExpectedConditions.elementToBeClickable(next)
        )
        next!!.click()
    }

    fun selectCitizenship() {
        WebDriverWait(driver, 300).until(
            ExpectedConditions.elementToBeClickable(back)
        )
        Thread.sleep(2000)
        val findElement = driver!!.findElement(By.name("sel_staat"))
        val select = Select(findElement)
        select.selectByVisibleText("Pakistan")
    }

    fun selectNumberOfApplicants() {
        val findElement = driver!!.findElement(By.name("personenAnzahl_normal"))
        val select = Select(findElement)
        select.selectByVisibleText("one person")

    }

    fun selectFamilyMemberLiving() {
        val findElement = driver!!.findElement(By.name("lebnBrMitFmly"))
        val select = Select(findElement)
        select.selectByVisibleText("no")
    }

    fun selectService() {
        WebDriverWait(driver, 100).until(
            ExpectedConditions.presenceOfElementLocated(By.id("SERVICEWAHL_EN3461-0-3"))
        )
        driver!!.findElement(By.className("kachel-461-0-3")).click()
    }

    fun checkMarkService() {
        WebDriverWait(driver, 100).until(
            ExpectedConditions.presenceOfElementLocated(By.id("SERVICEWAHL_EN461-0-3-99-324280"))
        )
        driver!!.findElement(By.id("SERVICEWAHL_EN461-0-3-99-324280")).click()
    }

    fun isAppointmentAvailable(): Boolean {
        driver!!.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
        try {
            driver?.findElement(By.className("errorMessage"))
        } catch (ex: Exception) {
            try {
                driver?.switchTo()?.alert()
            } catch (ex: Exception) {
                println("*********Sucker!!!")
                return false
            }
            println("*********Party!!!")
            Thread.sleep(5000)
            captureScreen()
            clickBack()
            return true
        }

        println("*********Sucker!!!")
        return false
    }

    private fun clickBack() {
        WebDriverWait(driver, 300).until(
            ExpectedConditions.elementToBeClickable(back)
        )
        back!!.click()
    }

    private fun captureScreen() {
        try {
            val augmentedDriver = Augmenter().augment(driver)
            val source = (augmentedDriver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
            FileUtils.copyFile(source, File("screenshot${Math.random()}.png"))
        } catch (e: IOException) {
            println("Failed to capture screenshot: " + e.message)
        }
    }

    fun getCurrentURL(): String = driver!!.currentUrl

}
