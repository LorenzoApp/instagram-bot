package com.example;

import java.util.Collections;
import java.util.HashMap;

import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeProfileSwitchCase{
   
   protected ChromeOptions options;

   public ChromeProfileSwitchCase(String nomeScelto)
   {
      this.options = new ChromeOptions();

      HashMap<String, Integer> conentSettings = new HashMap<String, Integer>();
      HashMap<String, Object> profile = new HashMap<String, Object>();
      HashMap<String, Object> prefs = new HashMap<String, Object>();
     
      options.addArguments("user-data-dir=/Users/liggio/Library/Application Support/Google/Chrome");

// da implementare i cookie per ogni profilo
      switch (nomeScelto) {
         case "hosteria eucalipto":
         options.addArguments("--profile-directory=Profile 1");
         break;

         case "--- vuoto1":
         options.addArguments("--profile-directory=Profile 2");
            break;
      
         case "--- vuoto":
         options.addArguments("--profile-directory=Profile 3");
            break;
      
         case "gatto miele":
         options.addArguments("--profile-directory=Profile 4");   
            break; 
            
         case "--- vuoto2":
         options.addArguments("--profile-directory=Profile 5");
            break;

         default:
         System.out.println("Ci deve esssere un erore in Chrome Profilo Switch case");
            break;
      }

      
      
      //da rispettare l'ordine del grafico  - POP UP NOTIFICHE
      conentSettings.put("notifications", 2);//cambia solo questo gli altri campi uguali
      profile.put("managed_default_content_settings", conentSettings);
      prefs.put("profile", profile);
      options.setExperimentalOption("prefs", prefs);
      options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
      
   }
}

