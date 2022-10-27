package com.example;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Collections;
import java.util.HashMap;

public class Accesso {

    //comprare un video corso su selenium su udemy
    //da implementare i profili da utilizzare con user-data-dir

    public static void main(String[] args) throws InterruptedException {

//minuto 18
        //le hashmap sono molto utili 0 ask - 1 allow - 2 block utili per esempio geolocalizzazione, notifiche e mediastream
        
        ChromeOptions options = new ChromeOptions();
        
        HashMap<String, Integer> conentSettings = new HashMap<String, Integer>();
        HashMap<String, Object> profile = new HashMap<String, Object>();
        HashMap<String, Object> prefs = new HashMap<String, Object>();
        
        //da rispettare l'ordine del grafico  - POP UP NOTIFICHE
        conentSettings.put("notifications", 2);//cambia solo questo gli altri campi uguali
        profile.put("managed_default_content_settings", conentSettings);
        prefs.put("profile", profile);
        options.setExperimentalOption("prefs", prefs);

         //da rispettare l'ordine del grafico - POP UP GEOLOCALIZZAZIONE
         conentSettings.put("geolocation", 0);//cambia solo questo gli altri campi uguali
         profile.put("managed_default_content_settings", conentSettings);
         prefs.put("profile", profile);
         options.setExperimentalOption("prefs", prefs);

         //da rispettare l'ordine del grafico - POP UP MICROFONO E FOTOCAMERA
         conentSettings.put("media_stream", 1);//cambia solo questo gli altri campi uguali
         profile.put("managed_default_content_settings", conentSettings);
         prefs.put("profile", profile);
         options.setExperimentalOption("prefs", prefs);

        //System.setProperty("webdriver.chrome.driver", "/Users/liggio/Desktop/Progetto Instagram Scraper/driver/chromedriver"); //indica posizione chromedriver
        //oppure
        WebDriverManager.chromedriver().setup(); //installando correttamente maven e la libreria bonigarcia avrai i driver chrome sempre aggiornati

        // si accettano i siti non sicuri
       // ChromeOptions options = new ChromeOptions();
       /*  options.addArguments("start-maximized");//non funziona
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        options.merge(capabilities);*/


      //  options.addArguments("--incognito");
       //options.addArguments("start-maximized");
        //options.addArguments("disable-notifications"); //non funziona usare hash map
        //options.addArguments("disable-geolocation"); //non funziona usare hashmap
        
        /*DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	    options.merge(capabilities);*/
        //capabilities.setAcceptInsecureCerts(true);

       

        // qui disabilitiamo la scritta "chrome è stato aperto da un software automatizzato"
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        
        //options.addArguments("disable-infobars"); //non funziona
        //options.merge(capabilities);

        WebDriver driver_insta = new ChromeDriver(options); //apri finestra chrome

        driver_insta.manage().window().maximize();

        //esercizio per pop up notifiche
        //driver_insta.get("https://web-push-book.gauntface.com/demos/notification-examples/");
        //driver_insta.findElement(By.cssSelector("input[type='checkbox']")).click();

        //esercizio per pop up geolocalizzazione
        //driver_insta.get("https://whatmylocation.com/");

        //esercizio per pop up microfono
        //driver_insta.get("https://mictests.com/");
        //Thread.sleep(5000);
        //driver_insta.findElement(By.id("mic-launcher")).click();
        
        //esercizio per pop up fotocamera
        driver_insta.get("https://webcamtests.com/");
       


        Thread.sleep(10000);
        driver_insta.close();

    }
    
}
