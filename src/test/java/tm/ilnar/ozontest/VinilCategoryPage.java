package tm.ilnar.ozontest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Класс хранит некоторые свойства и методы страницы "Виниловые пластинки"
 */
public class VinilCategoryPage {
    /**
     * Cсылку на страницу "Виниловые пластинки"
     */
    static final String URL = "https://www.ozon.ru/category/vinilovye-plastinki-31667/";
    /**
     * Число Продуктов, которые содержатся в одном контейнере страницы
     */
    int numProductsInContainer=36;
    /**
     * Объявляется локальная переменная
     */
    private WebDriver driver;
    /**
     * Xpath ссылка на текст с числом продуктов
     */
    public static final String NUMBER_PRODUCT="//div[@class=\"_3fb7c4\"]";

    /**
     * Создается объект который ссылается на передаваемый driver
     * @param driver
     */
    public VinilCategoryPage(WebDriver driver) {
        this.driver=driver;
    }

    /**
     * Метод возвращает номер контейнера в котором содержится продукт
     * @param n номер продукта
     * @return  номер контейнера
     */
    public int getProductContainerNumber(int n){
        return n/numProductsInContainer;
    }

    /**
     * Возвращает номер продукта внутри контейнера
     * @param n номер продукта на странице VinilCategoryPage
     * @return номер номер продукта внутри контейнера
     */

    public int getProductNumberInContainer(int n){
        return n%numProductsInContainer;
    }

    /**
     * Находит на странице VinilCategoryPage текст с числом товаров
     * @return  число товаров
     */
    public int getTotalNumberOfVinilDisk() {
        this.driver.get(URL);
        WebElement findColTov = driver.findElement(By.xpath("//div[@class=\"_3fb7c4\"]"));
        String findColTovText = findColTov.getText();
        return Integer.parseInt(findColTovText.split(" ")[0]);
    }

    /**
     *Находит товар на странице
     * @param randomNumb    номер товара
     * @return  WebElement товара
     * @throws InterruptedException
     */
    public WebElement getProductPage(int randomNumb) throws InterruptedException {
        driver.get(URL + "?page="+ (getProductContainerNumber(randomNumb)+1));
        Thread.sleep(4000);

        List<WebElement> containersList = driver.findElements(By.xpath("//div[@class=\"widget-search-result-container content\"]"));
        WebElement container = containersList.get(getProductContainerNumber(randomNumb)%10);
        return container.findElement(By.xpath("//div[@data-index=" + getProductNumberInContainer(randomNumb) + "]/*/div[@class=\"content m-default\"]"));
    }
}
