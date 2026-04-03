package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCardTest extends BaseTest {

    @Test
    void shouldSubmitFormSuccessfully() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();

        WebElement checkboxInput = driver.findElement(By.cssSelector("[data-test-id='agreement'] input"));
        assertTrue(checkboxInput.isSelected());

        driver.findElement(By.cssSelector("button.button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement successText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Ваша заявка успешно отправлена!')]")
                )
        );

        assertTrue(successText.isDisplayed());
        assertEquals(
                "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                successText.getText().trim()
        );
    }

    @Test
    void shouldShowErrorOnInvalidName() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", error.getText().trim());
    }

    @Test
    void shouldShowErrorOnEmptyName() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub"));
        assertEquals("Поле обязательно для заполнения", error.getText().trim());
    }

    @Test
    void shouldShowErrorOnInvalidPhone() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("89991234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error.getText().trim());
    }

    @Test
    void shouldShowErrorOnEmptyPhone() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement error = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub"));
        assertEquals("Поле обязательно для заполнения", error.getText().trim());
    }

    @Test
    void shouldShowErrorIfAgreementNotChecked() {
        driver.get("http://localhost:9999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input"))
                .sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input"))
                .sendKeys("+79991234567");
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement agreement = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid"));
        assertTrue(agreement.isDisplayed());
    }
}
