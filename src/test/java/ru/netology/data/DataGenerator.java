package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@UtilityClass
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    public static String generateCity() {
        return faker.address().city();
    }

    public static String generateName() {
        return faker.name().fullName();
    }

    public static String generatePhone() {
        return "+7" + faker.number().digits(10);
    }

    public static String generateDate(int daysFromNow) {
        return LocalDate.now().plusDays(daysFromNow)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static OrderData generateOrder(int daysFromNow) {
        return OrderData.builder()
                .city(generateCity())
                .date(generateDate(daysFromNow))
                .name(generateName())
                .phone(generatePhone())
                .build();
    }
}