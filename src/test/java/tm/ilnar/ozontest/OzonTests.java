package tm.ilnar.ozontest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Класс содержит все тесты
 */

public class OzonTests extends WebDriverSettings {
    /**
     * Открывается сайт.
     * Ожидается пока станет видимой элемент кнопки поиска.
     * Если заголовок страницы совпадает с "OZON — интернет-магазин. Миллионы товаров по выгодным ценам", то тест успешно пройден
     */
    @Test(description  = "1. Открыть в браузере сайт https://www.ozon.ru/. Если откроется всплывающее окно – закрыть\n" +
                            "его.")
    public void siteOpenTest(){
        driver.get("https://www.ozon.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(HomePage.SEARC_BUTTON)));
        Assert.assertEquals(driver.getTitle(),"OZON — интернет-магазин. Миллионы товаров по выгодным ценам");
    }

    /**
     * В меню " Все разделы" выбрается категория "Музыка".
     * Если заголово страницы, к торой перешли равен "Музыка", то тест пройден.
     */
    @Test (dependsOnMethods = { "siteOpenTest" },
            description = "2. В меню \" Все разделы\" выбрать категорию \"Музыка\".")
    public void selectKategMuzykaTest() {
        driver.findElement(By.xpath(HomePage.VSE_RAZDELY)).click();
        driver.findElement(By.xpath(HomePage.MUZYKA)).click();
        wait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath(HomePage.SEARC_BUTTON))));

        driver.findElement(By.xpath(HomePage.SEARC_BUTTON)).click();
        wait.until((ExpectedConditions.visibilityOfElementLocated(By.xpath(MusicPage.TITLE_MUZYKA))));
        WebElement textMuzyka = driver.findElement(By.xpath(MusicPage.TITLE_MUZYKA));
        Assert.assertEquals(textMuzyka.getText(), "Музыка");
    }

    /**
     *Переходим на странцу Виниловые пластинки. Если название открывшейся страницы "Виниловые пластинки купить в интернет-магазине OZON.ru",
     * то тест пройден.
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest" },
            description =   "3. С открывшейся страницы перейти на страницу \"Виниловые пластинки\","+
                            "4. Проверить, что открылся список товаров.")
    public void goToVinilPageTest() {
        driver.findElement(By.xpath(MusicPage.VINIL_DISC_KATEG)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VinilCategoryPage.NUMBER_PRODUCT)));
        Assert.assertEquals(driver.getTitle(), "Виниловые пластинки купить в интернет-магазине OZON.ru");
    }

    /**
     * На странице происходит поиск элемента отображающего количество товаров. Если оно числовое, то тест пройден.
     */
    @Test(dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest" },
            description = "5. Получить количество товаров на странице.")
    public void getNumbProductInPageTest() {
        vinilCategoryPage=new VinilCategoryPage(driver);
        productsNumbInVinilPage=vinilCategoryPage.getTotalNumberOfVinilDisk();
        String typeOfProductsNumbInVinilPage=((Object)productsNumbInVinilPage).getClass().getName();
        Assert.assertEquals(typeOfProductsNumbInVinilPage,"java.lang.Integer");
    }

    /**
     * Генериуется случайное число от 0 до числа соответсущему числу товаров на странице. Если число в пределах данного диапазона, то тест пройден.
     */
    @Test(dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest" },
            description =   "6. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5")
    public void generRandomValueTest(){
        randomNumb = generateRandomNum(productsNumbInVinilPage);
        Assert.assertEquals(randomNumb>=0 & randomNumb<productsNumbInVinilPage,true);
    }

    /**
     * Происходит поиск товара под номером, полученным в предыдущем тесте. Происходит переход на страницу товара.
     * Если название товара отобразилось, то тест пройден.
     * @throws InterruptedException
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                        "getNumbProductInPageTest"},
           description =      "7. Выбрать товар под номером, полученным в п.6. ( Перейти на страницу товара ),")
    public void selectProductTest() throws InterruptedException {
        VinilCategoryPage vinilCategoryPage=new VinilCategoryPage(driver);
        vinilCategoryPage.getProductPage(randomNumb).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.PRODUCT_NAME_XPATH)));
        Assert.assertNotNull(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.PRODUCT_NAME_XPATH)));
    }

    /**
     * Создается объект product1. В его поля записываются название и стоимость товара, которые находятся на странице товара.
     * По пиктограмме определяется количество товаров в корзине. Если он равен 1, то значит товар успешно добавился в корзину и
     * тест успешно пройден.
     * @throws InterruptedException
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                            "getNumbProductInPageTest","selectProductTest"},
            description =   "8. Запомнить стоимость и название данного товара."+
                            "9. Добавить товар в корзину.")
    public void rememberNamePriceProduct1AndAddToBasketTest() throws InterruptedException {
        product1 = new Product();
        ProductPage productPage = new ProductPage(driver);
        product1.name = productPage.getProductName();
        product1.price = productPage.getProductPrice();

        driver.findElement(By.xpath(ProductPage.BUTTON_ADD_TO_BASKET_XPATH)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.COUNT_PRODUCT_IN_PICTOGR_BASKET)));
        Assert.assertEquals(productPage.getCountProductInPiktogrBasket(),1);
    }

    /**
     * Происходит переход на страницу товара. На странице корзины находится элемент отвечающий за название товара.
     * Если его значение совпадает с названием сохраненного товара, то тест пройден.
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                            "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest"},
            description =   "10. Проверить то, что в корзине появился добавленный в п.9 товар. ( Проверка данных" +
                            "определенного товара. Необходим переход в корзину для этого.)")
    public void checkProductIsInBasketTest(){
        driver.findElement(By.xpath(ProductPage.BASKET_PICTORG)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BasketPage.BASKET_TITLE)));

        BasketPage basketPage=new BasketPage(driver);
        Assert.assertEquals(basketPage.getProductName(1),product1.name);
    }

    /**
     * Переходим на странцу Виниловые пластинки по URL. Если название открывшейся страницы "Виниловые пластинки купить в интернет-магазине OZON.ru",
     *      * то тест пройден.
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                            "checkProductIsInBasketTest"},
            description = "11. Вернуться на страницу \"Виниловые пластинки\".")
    public void backToVinilPageTest(){
        driver.get(VinilCategoryPage.URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VinilCategoryPage.NUMBER_PRODUCT)));
        Assert.assertEquals(driver.getTitle(), "Виниловые пластинки купить в интернет-магазине OZON.ru");
    }

    /**
     * Генериуется случайное число от 0 до числа соответсущему числу товаров на странице. При этом верхний предел диапазона
     * берется из переменной productsNumbInVinilPage, значение которого получен в время проведения теста {@link #generRandomValueTest()}.
     * Переходим на страницу товара, если отобразилось название товара, то тест пройден.
     * @throws InterruptedException
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                                "checkProductIsInBasketTest","backToVinilPageTest"},
            description = "12. Сгенерировать случайное число в диапазоне от 1 до количества товаров, полученного в п.5"+
                          "13. Выбрать товар под номером, полученным в п.12. ( Перейти на страницу товара )")
     public void generateRandomNumAndSelectProd2Test() throws InterruptedException {
        randomNumb = generateRandomNum(productsNumbInVinilPage);
        vinilCategoryPage.getProductPage(randomNumb).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.PRODUCT_NAME_XPATH)));

        Assert.assertNotNull(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.PRODUCT_NAME_XPATH)));
    }

    /**
     * Создается объект product2. В его поля записываются название и стоимость товара, которые находятся на странице товара.
     *      * По пиктограмме определяется количество товаров в корзине. Если он равен 2, то значит товар успешно добавился в корзину и
     *      * тест успешно пройден.
     * @throws InterruptedException
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                                "checkProductIsInBasketTest","backToVinilPageTest","generateRandomNumAndSelectProd2Test"},
            description =    "14. Запомнить стоимость и название данного товара.\n" +
                            "15. Добавить товар в корзину.\n")
    public void rememberNamePriceProduct2AndAddToBasketTest() throws InterruptedException {
        product2 = new Product();
        productPage = new ProductPage(driver);
        product2.name = productPage.getProductName();
        product2.price = productPage.getProductPrice();

        driver.findElement(By.xpath(ProductPage.BUTTON_ADD_TO_BASKET_XPATH)).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProductPage.COUNT_PRODUCT_IN_PICTOGR_BASKET)));
        int currentProductInBasket=productPage.getCountProductInPiktogrBasket();
        Assert.assertEquals(productPage.getCountProductInPiktogrBasket(),2);
    }

    /**
     * По числу возле значка корзины определяется число товаров в корзине. Если в корзине 2 товара, то тест пройден.
     * @throws InterruptedException
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                                "checkProductIsInBasketTest","backToVinilPageTest","generateRandomNumAndSelectProd2Test",
                                "rememberNamePriceProduct2AndAddToBasketTest"},
            description =    "16. Проверить то, что в корзине два товара. ( Проверка количества товаров в корзине. Может\n" +
                            "быть произведена без открытия корзины, а проверяя значение в header сайта, где указано\n" +
                             "количество товаров в корзине )\n")
    public void isInBasket2ProductTest() throws InterruptedException {
        int currentValue=productPage.getCountProductInPiktogrBasket();
        Assert.assertEquals(currentValue,2);
    }

    /**
     * Поисходи переход в корзину. Проверяется, совпадают ли название и стоимость отображающиеся в корзине, с теми значениями, которые были
     * сохранены ранее. Также проверяется отображаемая сумма стоимости товаров содержащихся в корзине, с вычисленным значением стоимости товаров.
     * Если 3 сравнения успешно пройдены, то тест пройден.
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                                "checkProductIsInBasketTest","backToVinilPageTest","generateRandomNumAndSelectProd2Test",
                                "rememberNamePriceProduct2AndAddToBasketTest","isInBasket2ProductTest"},
            description =    "17. Открыть корзину.\n" +
                            "18. Проверить то, что в корзине раннее выбранные товары и итоговая стоимость по двум\n" +
                            "товарам рассчитана верно.")
    public void openBasketAndCheckNamePriceSumTest(){
        driver.findElement(By.xpath(ProductPage.BASKET_PICTORG)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BasketPage.DELETE_ALL)));
        basketPage=new BasketPage(driver);
        Assert.assertEquals(basketPage.getProductName(1),product1.name);
        Assert.assertEquals(basketPage.getProductName(2),product2.name);
        int expectedSum=product1.price+product2.price;
        Assert.assertEquals(basketPage.getSumPriceProductInBasket(),expectedSum);
    }

    /**
     * Нажимается кнопка удалить все товары, кнопка подтверждения. Если в итоге появляется страница, на которой надпись
     * "Корзина пуста", то тест пройден.
     */
    @Test (dependsOnMethods = {"siteOpenTest","selectKategMuzykaTest","goToVinilPageTest","getNumbProductInPageTest",
                                "selectProductTest","rememberNamePriceProduct1AndAddToBasketTest",
                                "checkProductIsInBasketTest","backToVinilPageTest","generateRandomNumAndSelectProd2Test",
                                "rememberNamePriceProduct2AndAddToBasketTest","isInBasket2ProductTest",
                                "openBasketAndCheckNamePriceSumTest"},
            description =       "19. Удалить из корзины все товары."+
                                "20. Проверить, что корзина пуста.")
    public void deleteProductFromBasketTest(){
        driver.findElement(By.xpath(BasketPage.DELETE_ALL)).click();
        driver.findElement(By.xpath(BasketPage.CONFIRM_DELETE)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BasketPage.BASKET_EMPTY_TITLE)));
        WebElement testBasketIsEmpty=driver.findElement(By.xpath(BasketPage.BASKET_EMPTY_TITLE));
        Assert.assertEquals(testBasketIsEmpty.getText(),"Корзина пуста");
    }
}
