package tm.ilnar.ozontest;

/**
 * Класс хранит Xpath ссылки на некоторые элементы страницы Музыка
 */
public class MusicPage {
    /**
     * Xpath ссылка на заколовок страницы
     */
    public static final String TITLE_MUZYKA="//h1[@class=\"category-title-text\" and contains(text(),\"Музыка\")]";
    /**
     * Xpath ссылка на элемент Виниловые пластинки
     */
    public static final String VINIL_DISC_KATEG="//a[@href=\"/category/vinilovye-plastinki-31667/\"]";
}
