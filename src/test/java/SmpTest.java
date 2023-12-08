import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.List;


public class SmpTest
{
    static WebDriver driver;


    /**
     * Первичные настройки
     */
    @BeforeAll
    public static void setUp()
    {
        WebDriverManager.chromedriver().browserVersion("119").setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1148, 1040));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }


    /**
     * Тестирование добавления карточки в избранное
     * @throws InterruptedException
     */
    @Test
    public void addFavorite() throws InterruptedException
    {
        login();

        // Добавление в избранное
        driver.findElement(By.xpath("//span[@id=\'favorite\']")).click();
        Thread.sleep(2000);

        // Подтвердить добавление
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-apply\']")).click();

        // Открытие панели избранного
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9\']/div")).click();
        Thread.sleep(2000);

        // Проверки
        WebElement element = driver.findElement(By.xpath("//a[@id='gwt-debug-title']"));
        String textElement = element.getText();
        String msg = String.format("Название объекта не совпало. Ожидалось: %s, Получили: %s", "employee1 \"Федянин Михаил\"/Карточка сотрудника", textElement);
        Assertions.assertEquals("employee1 \"Федянин Михаил\"/Карточка сотрудника", textElement);

        // Очистка
        // Редактирование избранного
        driver.findElement(By.xpath("//span[@id=\'gwt-debug-editFavorites\']")).click();
        Thread.sleep(2000);

        // Удаление элемента из избранного
        driver.findElement(By.xpath("//table[@id=\'gwt-debug-favoritesEditTable\']/tbody/tr/td[6]/div/span")).click();
        Thread.sleep(2000);

        // Подтверждение удаления
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(2000);

        // Сохранение состояния
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-apply\']")).click();
        Thread.sleep(2000);

        // Закрытие панели избранного
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9\']/div")).click();

        // Выход из профиля
        driver.findElement(By.xpath("//a[@id=\'gwt-debug-logout\']")).click();
    }

    /**
     * Тестирование удаления карточки из избранного
     * @throws InterruptedException
     */
    @Test
    public void deleteFavorite() throws InterruptedException
    {
        login();
        Thread.sleep(20000);

        // Добавление в избранное
        driver.findElement(By.xpath("//span[@id=\'favorite\']")).click();
        Thread.sleep(2000);

        // Подтвердить добавление
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-apply\']")).click();

        // Открытие панели избранного
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9\']/div")).click();
        Thread.sleep(2000);

        // Редактирование избранного
        driver.findElement(By.xpath("//span[@id=\'gwt-debug-editFavorites\']")).click();
        Thread.sleep(2000);

        // Удаление элемента из избранного
        driver.findElement(By.xpath("//table[@id=\'gwt-debug-favoritesEditTable\']/tbody/tr/td[6]/div/span")).click();
        Thread.sleep(2000);

        // Подтверждение удаления
        driver.findElement(By.id("gwt-debug-YES")).click();
        Thread.sleep(2000);

        // Сохранение состояния
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-apply\']")).click();
        Thread.sleep(2000);

        // Проверки
        List<WebElement> element = driver.findElements(By.xpath("//table[@id='gwt-debug-favoritesPage_64745_cef6dff5-cf90-f40b-8f86-5b1d2cf00ad9']"));
        Assertions.assertTrue(element.isEmpty(), "Объект не удалился");

        // Закрытие панели избранного
        driver.findElement(By.xpath("//div[@id=\'gwt-debug-12c338ac-168c-348b-a88c-b9594aed4be9\']/div")).click();

        // Выход из профиля
        driver.findElement(By.xpath("//a[@id=\'gwt-debug-logout\']")).click();
    }


    /**
     * Переход на сайт и авторизация
     */
    private void login()
    {
        // Переход на сайт
        driver.get("https://test-m.sd.nau.run/sd/");

        // Вход в аккаунт
        driver.findElement(By.xpath("//label[@id=\'usernameLabel\']")).click();
        driver.findElement(By.xpath("//input[@id=\'username\']")).sendKeys("IRON66");
        driver.findElement(By.xpath("//label[@id=\'passwordLabel\']")).click();
        driver.findElement(By.xpath("//input[@id=\'password\']")).sendKeys("123");
        driver.findElement(By.xpath("//input[@id=\'submit-button\']")).click();
    }

    @AfterAll
    public static void close()
    {
        driver.close();
    }
}
