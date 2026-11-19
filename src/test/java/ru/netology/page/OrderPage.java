package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.OrderData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class OrderPage {

    private final SelenideElement cityInput = $("[data-test-id='city'] input");
    private final SelenideElement dateInput = $("[data-test-id='date'] input");
    private final SelenideElement nameInput = $("[data-test-id='name'] input");
    private final SelenideElement phoneInput = $("[data-test-id='phone'] input");
    private final SelenideElement agreementLabel = $("[data-test-id='agreement']");
    private final SelenideElement agreementInput = $("[data-test-id='agreement'] input");
    private final SelenideElement submitButton = $("button.button");
    private final SelenideElement successNotification = $("[data-test-id='success-notification']");
    private final SelenideElement replanNotification = $("[data-test-id='replan-notification']");

    public void fillForm(OrderData data) {
        cityInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        cityInput.setValue(data.getCity());
        dateInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        dateInput.setValue(data.getDate());
        nameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        nameInput.setValue(data.getName());
        phoneInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        phoneInput.setValue(data.getPhone());
        if (!agreementInput.isSelected()) {
            agreementLabel.click();
            agreementInput.shouldBe(Condition.checked);
        }
        submitButton.click();
    }

    public void waitForSuccess(String expectedDate) {
        successNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно запланирована на " + expectedDate), Duration.ofSeconds(15));
    }

    public void waitForReplan() {
        replanNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
    }

    public void clickReplan() {
        replanNotification.$("button").click();
    }
}