package tm.ilnar.ozontest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.Random;

/**
 * Класс содержит глобальные переменные и настройки, задаваемый перед запуском теста и после завершения тестов,
 * вспомогательные методы
 */
public class WebDriverSettings {
    WebDriver driver;
    WebDriverWait wait;
    /**
     * Общее количество виниловых пластинок на сайте
     */
    int productsNumbInVinilPage;
    /**
     * Случайное число, которое выступает в роли номера товара
     */
    int randomNumb;
    /**
     * Объект инкапсулирующий некоторые  свойства и методы страницы Виниловой пластинки
     */
    VinilCategoryPage vinilCategoryPage;
    /**
     * Объект инкапсулирующий некоторые  ствойства и методы страницы выбранного товара
     */
    ProductPage productPage;
    /**
     * Объект инкапсулирующий некоторые свойства и методы страницы Корзины
     */
    BasketPage basketPage;
    /**
     * Первый товар
     */
    Product product1;
    /**
     * Второй товар
     */
    Product product2;

    /**
     * Метод выполняется перед запуском тестов и содержит начальные настройки драйвера и времени ожидания
     */
    @BeforeTest
    public void setUp(){
        driver=new FirefoxDriver();
        wait=new WebDriverWait(driver,20);
    }

    /**
     * Метод выполняется после завершения всех тестови, закрывает драйвер
     */
    @AfterTest
    public void tearDown(){
        driver.quit();
    }

    /**
     * Метод принимает на вход webElement, содержащий цену товара, удаляет пробелы и денежную единицу, возвращает целочисленное значение
     * @param webElement цена товара со страницы сайта
     * @return  цена товара
     */
    public int parsPriceToInt(WebElement webElement){
        String totalPrice=webElement.getText();
        String[] totalPriceArr=totalPrice.split(" ");
        String totalPriceStr=totalPriceArr[0]+totalPriceArr[1];
        return Integer.parseInt(totalPriceStr);
    }

    /**
     * Генерирует случайное число в диапазоне от 0 до integer
     * @param integer   верхняя граница, в пределах которого генерируется случайное число
     * @return    случайное число
     */
    public int generateRandomNum(Integer integer){
        Random random = new Random();
        return random.nextInt(integer);
    }

}
