package tm.ilnar.ozontest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс хранит некоторые свойста и методы страницы Товара
 */
public class ProductPage {
    WebDriver driver;
    WebDriverSettings webDriverSettings;
    /**
     * Xpath ссылка на название товара
     */
    final static String PRODUCT_NAME_XPATH="//div/h1[@class=\"_718dda\"]";
    /**
     * Xpath ссылка на кнопку "Добавить в корзину"
     */
    final static String BUTTON_ADD_TO_BASKET_XPATH="//button[@class=\"_652bc6\"]";
    /**
     * Xpath ссылка на цифру рядом с пиктограммой корзины, показывающий на число товаров в корзине
     */
    final static String COUNT_PRODUCT_IN_PICTOGR_BASKET="//span[@class=\"f-caption--bold ef9580\"]";
    /**
     * Xpath ссылка на пиктограмму корзины в правом верхнем углу страницы
     */
    final static String BASKET_PICTORG="//a[@href=\"/cart\"]";

    /**
     * Создаваемый объект ссылается на текущий driver
     * @param driver
     */
    public ProductPage(WebDriver driver) {
        this.driver=driver;
        webDriverSettings = new WebDriverSettings();
    }

    /**
     * Возвращает название товара из текущей страницы товра
     * @return  название товара
     */
    public String getProductName() {
        WebElement tovarNameEl = driver.findElement(By.xpath(PRODUCT_NAME_XPATH));
        return tovarNameEl.getText();
    }

    /**
     * Возвращает цену товара
     * @return цена товара
     */
    public int getProductPrice() {
        WebElement tovarPriceEl;
        try {
            tovarPriceEl = driver.findElement(By.xpath("//span[@class=\"b3411b\" or @class=\"b3411b _04b877\"]"));
            return webDriverSettings.parsPriceToInt(tovarPriceEl);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    /**
     * По пиктограммме корзины находит число товаров в корзине
     * @return  число товаров в корзине
     * @throws InterruptedException
     */
    public int getCountProductInPiktogrBasket() throws InterruptedException {
        WebElement countInPiktogr;
            try {
                Thread.sleep(2000);
                countInPiktogr=driver.findElement(By.xpath(COUNT_PRODUCT_IN_PICTOGR_BASKET));
                return Integer.parseInt(countInPiktogr.getText());
            } catch (NoSuchElementException e){
                return 0;
            }

        }
}
