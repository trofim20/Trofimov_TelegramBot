package ru.trofimov.Trofimov_TelegramBot;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

/**
 * Тесты для проверки функционала авторизации и выхода из системы.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginLogoutTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    /**
     * Настройка тестового окружения перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\chrome\\chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://localhost:" + port;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    /**
     * Тест проверяет корректность отображения страницы авторизации.
     */
    @Test
    void loginUrlTest() {
        driver.get(baseUrl + "/login");

        WebElement loginTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.tagName("h2")));
        Assertions.assertEquals("Авторизация", loginTitle.getText());

        WebElement registerLink = driver.findElement(By.xpath("//a[contains(text(),'Зарегистрируйтесь здесь')]"));
        Assertions.assertTrue(registerLink.isDisplayed());
    }

    /**
     * Тест проверяет успешную авторизацию с валидными учетными данными.
     */
    @Test
    void loginSuccessfullyTest() {
        driver.get(baseUrl + "/login");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("admin123");
        loginButton.click();

        wait.until(ExpectedConditions.urlToBe(baseUrl + "/"));

        WebElement logoutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Logout')]")));
        Assertions.assertTrue(logoutLink.isDisplayed());
    }

    /**
     * Тест проверяет обработку неверных учетных данных при авторизации.
     */
    @Test
    void loginWithInvalidCredentialsTest() {
        driver.get(baseUrl + "/login");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(),'Sign in')]"));

        usernameField.sendKeys("wronguser");
        passwordField.sendKeys("wrongpass");
        loginButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'message') and contains(text(),'Invalid username or password')]")));
        Assertions.assertTrue(errorMessage.isDisplayed());
    }

    /**
     * Тест проверяет успешный выход из системы.
     */
    @Test
    void logoutSuccessfulTest() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/"));

        WebElement logoutLink = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        logoutLink.click();

        wait.until(ExpectedConditions.urlContains("/login"));

        WebElement logoutMessage = driver.findElement(
                By.xpath("//div[contains(@class, 'message') and contains(text(),'You have been logged out')]"));
        Assertions.assertTrue(logoutMessage.isDisplayed());
    }

    /**
     * Очистка ресурсов после каждого теста.
     */
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}