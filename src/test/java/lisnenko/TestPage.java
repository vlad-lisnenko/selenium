package lisnenko;

import lisnenko.conf.ConfProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TestPage {
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductPage productPage;

    private CartPage cartPage;

    @BeforeEach
    public void init() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        WebDriver driver = new ChromeDriver();
        homePage = new HomePage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("homePage"));
    }

    @AfterEach
    public void tearDown() {homePage.getDriver().quit();}

    @Test
    public void homePageContainsPostForSales_1() {
        String text = ConfProperties.getProperty("salesPost_1");
        List<String> listSalesPost = new ArrayList<>();
        homePage.getNamesSalesPost().forEach(webElement -> listSalesPost.add(webElement.getText()));
        listSalesPost.forEach(System.out::println);
        assertTrue(listSalesPost.contains(text));
    }

    @Test
    public void homePageContainsPostForSales_2() {
        String text = ConfProperties.getProperty("salesPost_2");
        List<String> listSalesPost = new ArrayList<>();
        homePage.getNamesSalesPost().forEach(webElement -> listSalesPost.add(webElement.getText()));
        listSalesPost.forEach(System.out::println);
        assertTrue(listSalesPost.contains(text));
    }

    @Test
    public void searchProduct() {
        String text = ConfProperties.getProperty("searchProduct");
        homePage.enterTextIntoSearchBar(text);
        String actual = homePage.clickSearch().getTextOfResultSearch();
        assertEquals(text, actual);
    }

    @Test
    public void ChangeLanguage() {
        homePage.changeLanguage();
        assertEquals(ConfProperties.getProperty("sell"), homePage.getStringSellOnMainPanel());
    }

    @Test
    public void isSortByPrice() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        assertEquals(ConfProperties.getProperty("sort"), searchPage.isSortOnPrice());
    }


    @Test
    public void isListOfProductsNoEmpty() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage =  homePage.clickSearch();
        List<WebElement> products = searchPage.getListOfProduct();
        assertFalse(products.isEmpty());
    }

    @Test
    public void listOfProductsMoreThan10nPage() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        List<WebElement> products = searchPage.getListOfProduct();
        assertTrue(products.size() > 10);
    }

    @Test
    public void isProductWithNameInlist_ExpectedTrue() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        assertTrue(searchPage.isProductByName(ConfProperties.getProperty("productName")));
    }

    @Test
    public void isProductWithNameInlist_ExpectedFalse() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        assertFalse(searchPage.isProductByName("apple"));
    }

    @Test
    public void getNameOfProduct() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        productPage = searchPage.getProductByName(ConfProperties.getProperty("productName"));
        assertEquals(ConfProperties.getProperty("productName"), productPage.getNameProduct());
    }
    @Test
    public void getPriceOfProduct() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        productPage = searchPage.getProductByName(ConfProperties.getProperty("productName"));
        assertEquals(ConfProperties.getProperty("priceOfProduct"), productPage.getPriceOfProduct());
    }


    @Test
    public void getItemModelNumber() {
        homePage.enterTextIntoSearchBar(ConfProperties.getProperty("searchProduct"));
        searchPage = homePage.clickSearch();
        productPage = searchPage.getProductByName(ConfProperties.getProperty("productName"));
        assertEquals(ConfProperties.getProperty("itemModelNumber"), productPage.getItemModelNumber());
    }

    @Test
    void getStatusCart() {
        cartPage = homePage.getCartPage();
        assertEquals(ConfProperties.getProperty("statusCart"), cartPage.getStatusCart());
    }

    @Test
    void getLocatorSigIn() {
        cartPage = homePage.getCartPage();
        assertEquals(ConfProperties.getProperty("signIn"), cartPage.getLocatorSigIn());
    }

    @Test
    void getLocatorSignUp() {
        cartPage = homePage.getCartPage();
        assertEquals(ConfProperties.getProperty("signUp"), cartPage.getLocatorSignUp());
    }
}
