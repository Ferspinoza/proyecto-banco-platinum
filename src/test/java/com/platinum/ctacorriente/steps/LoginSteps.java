package com.platinum.ctacorriente.steps;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Dado("que el ejecutivo está en la página de inicio de sesión")
    public void que_el_ejecutivo_esta_en_la_pagina_de_inicio_de_sesion() {
        driver.get("http://localhost:8083/CtaCorriente/");
    }

    @Cuando("ingresa el RUT del ejecutivo {string}")
    public void ingresa_el_rut_del_ejecutivo(String rut) {
        WebElement rutInput = driver.findElement(By.name("rutEjecutivo"));
        rutInput.sendKeys(rut);
    }

    @Y("hace clic en el botón de {string}")
    public void hace_clic_en_el_boton_de(String buttonText) {
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), '" + buttonText + "')]"));
        loginButton.click();
    }

    @Entonces("debería ver el mensaje de bienvenida {string}")
    public void deberia_ver_el_mensaje_de_bienvenida(String expectedMessage) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginMessage")));
        assertTrue(messageElement.getText().contains(expectedMessage));
    }

    @Entonces("debería ver el mensaje de error {string}")
    public void deberia_ver_el_mensaje_de_error(String expectedMessage) {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginMessage")));
        assertTrue(messageElement.getText().contains(expectedMessage));
    }
}

