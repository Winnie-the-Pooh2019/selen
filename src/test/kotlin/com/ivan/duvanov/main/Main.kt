package com.ivan.duvanov.main

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.io.File
import java.time.Duration

@TestMethodOrder(MethodOrderer.MethodName::class)
class Main {

    @Test
    fun firstTest() {
        driver.get("https://habr.com/ru/all")
        driver.byXpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div[2]/a[1]").click()

        val searchRow = driver.byXpath("//input[@name='q']")
        assertEquals(searchRow, driver.switchTo().activeElement())
        searchRow.sendKeys("Selenium WebDriver")
        driver.byXpath("//*[@class='tm-svg-img tm-svg-icon']").click()

        driver.findElement(By.linkText("Что такое Selenium?")).click()

        val date = driver.byXpath("//time[@datetime='2012-09-28T09:14:11.000Z']").text
        assertTrue(date.contains("28 сен 2012"))

        val js = driver as JavascriptExecutor
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)")

        driver.byXpath("//*/a[@href='/ru/articles/' and @class='footer-menu__item-link router-link-active']").click()
    }

    @Test
    fun mailRuTest() {
        driver.get("https://mail.ru/")

        driver.byXpath("/html/body/main/div[2]/div[2]/div[1]/div/div[1]/button").click()

        val frame = driver.byXpath("/html/body/div[5]/div/iframe")
        driver
            .switchTo()
            .frame(frame)
            .byXpath("//input[@name='username']")
            .clean()
            .sendKeys(email)

        driver.findElement(By.xpath("//button[@type='submit']")).click()

        driver.byXpath("//input[@name='password']").clean().sendKeys(password)
        driver.byXpath("//button[@type='submit']").click()

        driver.byXpath("/html/body/div[3]/div[1]/div/div[2]/div[2]/span").click()

        val name = driver.byXpath("/html/body/div[3]/div[1]/div/div[4]/div/div[2]/div/div/div[1]/div/div[1]/div").text
        assertTrue(name.contains("Иван Дуванов"))

        driver.byXpath("/html/body/div[3]/div[1]/div/div[4]/div/div[2]/div/div/div[3]/div[2]").click()

        val createButton = By.linkText("Создать почту")
        assertTrue(driver.findElement(createButton).isDisplayed)
    }

    private fun WebDriver.byXpath(xpath: String): WebElement = this.findElement(By.xpath(xpath))

    private fun WebElement.clean(): WebElement = this.apply { clear() }


    companion object {

        private lateinit var driver: WebDriver
        private lateinit var email: String
        private lateinit var password: String

        @JvmStatic
        @BeforeAll
        fun setUp() {
            val path = File("./.driver/chromedriver.exe").absolutePath
            System.setProperty("webdriver.chrome.driver", path)

            val chromeOptions = ChromeOptions()
                .addArguments("--window-size=1920,1080")

            System.getProperty("email")?.let { email = it } ?: throw NullPointerException("Email cannot be null")
            System.getProperty("pass")?.let { password = it } ?: throw NullPointerException("Password cannot be null")

            driver = ChromeDriver(chromeOptions).apply {
                manage().timeouts().implicitlyWait(Duration.ofSeconds(15))
            }
        }

        @JvmStatic
        @AfterAll
        fun destroy() = driver.quit()
    }
}