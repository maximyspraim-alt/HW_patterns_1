package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.OrderData;
import ru.netology.page.OrderPage;

import static com.codeborne.selenide.Selenide.open;

class OrderReplanTest {

    private OrderPage orderPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        orderPage = new OrderPage();
    }

    @Test
    void shouldReplanMeeting() {
        // Генерируем данные: первая дата +3 дня, вторая +7 дней (гарантированно разные)
        OrderData firstOrder = DataGenerator.generateOrder(3);
        OrderData secondOrder = DataGenerator.generateOrder(7);


        // Первая отправка — успешное планирование
        orderPage.fillForm(firstOrder);
        orderPage.waitForSuccess(firstOrder.getDate());

        // Вторая отправка — те же данные, но другая дата → предложение перепланировать
        OrderData replanOrder = OrderData.builder()
                .city(firstOrder.getCity())
                .date(secondOrder.getDate())
                .name(firstOrder.getName())
                .phone(firstOrder.getPhone())
                .build();

        orderPage.fillForm(replanOrder);
        orderPage.waitForReplan();
        orderPage.clickReplan();

        // Финальное уведомление — уже с новой датой
        orderPage.waitForSuccess(replanOrder.getDate());
    }
}