package com.appointment.search.tests

import com.appointment.search.bot.TelegramBot
import com.appointment.search.driverutil.DriverFactory
import com.appointment.search.pageobjects.BerlinImmigrationOffice
import com.appointment.search.pageobjects.SearchResultsPage
import org.openqa.selenium.WebDriver
import org.testng.annotations.AfterTest
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test


class RunTest {

    private var driver: WebDriver? = null
    private lateinit var berlinImmigrationOffice: BerlinImmigrationOffice
    private lateinit var searchResultsPage: SearchResultsPage

    @BeforeTest
    fun setUp() {
        driver = DriverFactory.browser
        berlinImmigrationOffice = BerlinImmigrationOffice(driver!!)
        searchResultsPage = SearchResultsPage(driver!!)
    }

    @Test
    fun runAppointmentSearch() {
        println("Checking for appointments...")
        berlinImmigrationOffice.openURL()
        berlinImmigrationOffice.acknowledge()
        berlinImmigrationOffice.clickNext()
        berlinImmigrationOffice.selectCitizenship()
        berlinImmigrationOffice.selectNumberOfApplicants()
        berlinImmigrationOffice.selectFamilyMemberLiving()
        berlinImmigrationOffice.selectService()
        berlinImmigrationOffice.checkMarkService()
        berlinImmigrationOffice.clickNext()
        val available = berlinImmigrationOffice.isAppointmentAvailable()

        if (available) {
            TelegramBot.sendMsgToTelegram(berlinImmigrationOffice.getCurrentURL())
            Thread.sleep(1000)
            TelegramBot.sendMsgToTelegram("Book the appointment!!!")
            Thread.sleep(1000)
            TelegramBot.sendMsgToTelegram("Book the appointment!!!")
        }
    }
    @AfterTest
    fun tearDown() {
        driver!!.quit()
    }
}
