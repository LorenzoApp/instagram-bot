package src;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Point;
import java.util.Set;

import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.stream.Collectors;
import com.beust.ah.A;

import io.opentelemetry.semconv.trace.attributes.SemanticAttributes.FaasDocumentOperationValues;


import org.openqa.selenium.WebElement;

import java.sql.Driver;
import java.time.Duration;
import java.util.List;

public class Main {

// Headless Browser -- da applicare successivamente perchè è utile e più veloce per lo scraping

public static void main(String[] args) throws Exception {

String username = "gruppomallarc";
String password = "supereroi1234567";

System.setProperty("webdriver.chrome.driver", "/Users/liggio/Desktop/Progetto Instagram Scraper/driver/chromedriver"); //indica posizione chromedriver

WebDriver driver_insta = new ChromeDriver(); //apri finestra chrome

driver_insta.manage().window().maximize(); //full screen

driver_insta.get("https://www.instagram.com/"); // vai al seguente URL 
Thread.sleep(2000);


try {
 
driver_insta.findElement(By.xpath("//button[normalize-space()='Consenti cookie essenziali e facoltativi']")).click(); //accetta cockies

//username - password
Thread.sleep(2000);
driver_insta.findElement(By.xpath("//input[@name='username']")).sendKeys(username);;
Thread.sleep(2000);
driver_insta.findElement(By.cssSelector("input[name='password']")).sendKeys(password);;
System.out.println("Username e password");

//button - accedi
Thread.sleep(2000);
driver_insta.findElement(By.xpath("//div[contains(text(),'Accedi')]")).click();
System.out.println("accedi");

// non funziona da qui
//button - non ora notifiche
Thread.sleep(10000);
driver_insta.findElement(By.xpath("//div[contains(text(),'Non ora')]")).click();
System.out.println("rifiuta notifiche");
/*
//vai al profilo
Thread.sleep(10000);
driver_insta.findElement(By.xpath("//a[normalize-space()='gruppomallarc']")).click();
System.out.println("vai al profilo");

//followers
Thread.sleep(10000);
driver_insta.findElement(By.xpath("//a[contains(@href,'/gruppomallarc/followers/')]")).click();
System.out.println("apri followers");

//List<String> lista = new WebDriverWait(driver_insta, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@class='nav-item']//ul[@class='add-menu collapse']//li/span"))).stream().map(element->element.getAttribute("innerHTML")).collect(Collectors.toList());
 */
} catch (Exception e) {
    e.printStackTrace();
}


Thread.sleep(2000);
//driver_insta.quit();

}

}


