package ru.trofimov.Trofimov_TelegramBot;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import ru.trofimov.Trofimov_TelegramBot.entity.DishEntity;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.exception.DishNotFound;
import ru.trofimov.Trofimov_TelegramBot.repository.DishRepository;
import ru.trofimov.Trofimov_TelegramBot.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private DishRepository dishRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    /**
     * Тест успешного получения списка блюд по ID ресторана.
     * Проверяет:
     * - статус ответа 200 OK
     * - наличие ожидаемого блюда в ответе
     * - корректность данных блюда (id, name, price)
     */
    @Test
    void getDishesByRestaurantId() {
        DishEntity dish = new DishEntity();
        dish.setId(1L);
        dish.setName("Пицца");
        dish.setPrice(500.0);

        when(dishRepository.findByRestaurantId(1L)).thenReturn(List.of(dish));

        given()
                .contentType(ContentType.JSON)
                .queryParam("restaurantId", 1)
                .when()
                .get("/custom/dishes/findByRestaurantId")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("Пицца"))
                .body("[0].price", equalTo(500.0f));
    }

    /**
     * Тест получения пустого списка блюд для ресторана без блюд.
     * Проверяет:
     * - статус ответа 200 OK
     * - пустой массив в ответе
     */
    @Test
    void getDishesByRestaurantIdWhenNoDishes() {
        when(dishRepository.findByRestaurantId(2L)).thenReturn(Collections.emptyList());

        given()
                .contentType(ContentType.JSON)
                .queryParam("restaurantId", 2)
                .when()
                .get("/custom/dishes/findByRestaurantId")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(0));
    }

    /**
     * Тест обработки случая, когда ресторан не найден.
     * Проверяет:
     * - статус ответа 404 Not Found
     * - наличие сообщения об ошибке
     */
    @Test
    void getDishesByRestaurantIdWhenRestaurantNotFound() {
        when(dishRepository.findByRestaurantId(999L))
                .thenThrow(new DishNotFound("Ресторан с id 999 не найден"));

        given()
                .contentType(ContentType.JSON)
                .queryParam("restaurantId", 999)
                .when()
                .get("/custom/dishes/findByRestaurantId")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("error", containsString("Ресторан с id 999 не найден"));
    }

    /**
     * Тест успешного поиска пользователя по имени.
     * Проверяет:
     * - статус ответа 200 OK
     * - корректность данных пользователя (id, name)
     */
    @Test
    void findUserByName() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("admin");

        when(userRepository.findByName("admin")).thenReturn(user);

        given()
                .contentType(ContentType.JSON)
                .queryParam("name", "admin")
                .when()
                .get("/custom/users/findByName")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("name", equalTo("admin"));
    }

    /**
     * Тест обработки случая, когда пользователь не найден.
     * Проверяет:
     * - статус ответа 404 Not Found
     */
    @Test
    void findUserByNameWhenUserNotFound() {
        when(userRepository.findByName("unknown"))
                .thenReturn(null);

        given()
                .contentType(ContentType.JSON)
                .queryParam("name", "unknown")
                .when()
                .get("/custom/users/findByName")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    /**
     * Тест обработки пустого имени пользователя.
     * Проверяет:
     * - статус ответа 400 Bad Request
     */
    @Test
    void findUserByNameWhenNameIsEmpty() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("name", "")
                .when()
                .get("/custom/users/findByName")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Тест обработки запроса к несуществующему эндпоинту.
     * Проверяет:
     * - статус ответа 404 Not Found
     */
    @Test
    void shouldReturn404WhenEndpointNotFound() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/custom/nonexistent")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
