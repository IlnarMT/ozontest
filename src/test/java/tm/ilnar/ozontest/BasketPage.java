package tm.ilnar.ozontest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Класс хранит некоторые свойства и методы страницы Корзина
 */
public class BasketPage {
    WebDriver driver;
    WebDriverSettings webDriverSettings;
    /**
     * Хранит сумму всех товаров в корзине
     */
    WebElement sumPrice;
    /**
     * Xpath ссылка на заголовок корзины
     */
    static final String BASKET_TITLE="//div[@class=\"_2fd0f7 c07b11\"]";
    /**
     * Xpath ссылка на пиктограмму, показывающую на количество товаров в корзине
     */
    static final String COUNT_PRODUCT_IN_PICTOGR_BASKET="//span[@class=\"f-caption--bold ef9580\"]";
    /**
     * Xpath ссылка на кнопку "Удалить все товары"
     */
    static final String DELETE_ALL="//span[@class=\"dc5e23 a443c8\"]";
    /**
     * Xpath ссылка на кнопку подтверждения удаления товара
     */
    static final String CONFIRM_DELETE="//button[@class=\"button button blue\"]";
    /**
     * Xpath ссылка на надпись, что корзина пуста
     */
    static final String BASKET_EMPTY_TITLE="//h1";

    /**
     * Создается объект с ссылкой на текущий driver
     * @param driver
     */
    public BasketPage(WebDriver driver){
        this.driver=driver;
        webDriverSettings=new WebDriverSettings();
    }

    /**
     * Возвращает название товара
     * @param numProd   номер товара (1 или 2)
     * @return  название товара
     */
    public String getProductName(int numProd) {
        WebElement productInBasket;
        if (getCountProductInPiktogrBasket()<2){
            productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[1]"));
        } else {
            if(numProd==1) {
                productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[2]"));
            } else {
                productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[1]"));
            }
        }
        return productInBasket.getText();
    }

    /**
     * Возвращает цену товара
     * @param numProd   номер товара (1 или 2)
     * @return  цена товара
     */
    public int getProductPrice(int numProd) {
        WebElement productInBasket;
        if (getCountProductInPiktogrBasket()<2){
            productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[1]"));
        } else {
            if(numProd==1) {
                productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[2]"));
            } else {
                productInBasket = driver.findElement(By.xpath("(//div[@class=\"cart-item\"]/*/a[@class=\"title\"]/span)[1]"));
            }
        }
        WebElement productPriceEl = productInBasket.findElement(By.xpath("//div[@class=\"price-block-part\"]/span"));
        return webDriverSettings.parsPriceToInt(productPriceEl);
    }

    /**
     * Возвращает число в корзине
     * @return  число товаров
     */
    public int getCountProductInPiktogrBasket(){
        WebElement countInPiktogr=driver.findElement(By.xpath(COUNT_PRODUCT_IN_PICTOGR_BASKET));
        return Integer.parseInt(countInPiktogr.getText());
    }

    /**
     * Возвращает суммарную стоимость товаров в корзине
     * @return  суммарная стоимость товаров
     */
    public int getSumPriceProductInBasket(){
        sumPrice=driver.findElement(By.xpath("//span[@class=\"total-middle-footer-text\"]"));
        return webDriverSettings.parsPriceToInt(sumPrice);
    }

}
