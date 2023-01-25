package com.appointment.search.pageobjects

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

class SearchResultsPage(driver: WebDriver) {

    private var driver: WebDriver? = null
    init {
        this.driver = driver
        PageFactory.initElements(driver, this)
    }
}
