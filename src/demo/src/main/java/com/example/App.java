package com.example;


import java.util.Set;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;


public  class App {
    
               
    public static void main(String[] args) throws InterruptedException {
        
        //inizializza();

        WebDriverManager.chromedriver().setup();//setup automatico e aggiornato dei driver di google chrome
        String nomeProfilo = "hosteria eucalipto";//da implementare in futuro inferfaccia grafica con switch case o con bottoni

        ChromeProfileSwitchCase scegliProfilo = new ChromeProfileSwitchCase(nomeProfilo);

        ProfiloInstagram profilo = new ProfiloInstagram(scegliProfilo.options);

        profilo.vaiSuInstagram();

        Set<Cookie> cookies = profilo.driver_profile.manage().getCookies();
        System.out.println("Syze of cookie: "+cookies.size());

        if(cookies.size() < 7)
        {
            profilo.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Consenti cookie essenziali e facoltativi']"))).click();
            profilo.accediConDati();
            profilo.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Non ora']"))).click();
            //profilo.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Salva le informazioni']"))).click();          // non funziona però sai già come disattivare le notifiche
        }
        else
        {
            System.out.println("Hai già accettato i cookie");//per i dati dipende dalla cache (da studiare)
        }
        //button[normalize-space()='Salva le informazioni']
        //button[normalize-space()='Non ora']
        
        

       
        /* System.out.println("Syze of cookie: "+cookies.size());

         for(Cookie cookie: cookies)
        {
            System.out.println(cookie.getName() + " : " + cookie.getValue());
        } */ 

//consenti cookie
//button[normalize-space()='Consenti cookie essenziali e facoltativi']
        Thread.sleep(2000);
        //profilo.driver_profile.quit();

       
       

        
    }


    public static void inizializza()
    {
    
        
    }

    



}


    






