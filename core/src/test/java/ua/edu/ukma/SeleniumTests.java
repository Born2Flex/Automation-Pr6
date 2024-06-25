package ua.edu.ukma;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void testSearchFunctionalityOnGoogle() {
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Spring Boot 3");
        searchBox.submit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3")));
        List<WebElement> searchResults = driver.findElements(By.cssSelector("h3"));
        assertFalse(searchResults.isEmpty(), "Expected multiple search results");
    }

    @Test
    void testNavigateToSpringBootWebsite() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement datalist = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("my-datalist")));
        datalist.click();
        WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//datalist/option[2]")));
        String optionValue = option.getAttribute("value");
        datalist.sendKeys(optionValue);
        assertThat(optionValue).isEqualTo("New York");
    }

    @Test
    void testExplicitWait() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        WebElement compass = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("compass")));
        assertTrue(compass.getAttribute("src").contains("img/compass.png"));
    }
}
