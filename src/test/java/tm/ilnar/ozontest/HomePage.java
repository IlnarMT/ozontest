package tm.ilnar.ozontest;

/**
 * Класс хранит Xpath ссылки главной страницы
 */
public class HomePage {
    /**
     * Xpath ссылка на элемент Все разделы
     */
    public static final String VSE_RAZDELY="//div [@class=\"context-chip m-all-contexts\"]";
    /**
     * Xpath ссылка на элемент Музыка
     */
    public static final String MUZYKA="//div[contains(text(),'Музыка')]";
    /**
     * Xpath ссылка на кнопку поиска
     */
    public static final String SEARC_BUTTON="//button[@data-test-id=\"header-search-go\"]";

}
