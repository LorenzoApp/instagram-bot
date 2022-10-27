package com.example;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.io.IOException;

import io.github.bonigarcia.wdm.WebDriverManager;


public class ChromeProfileSwitch{
   
   String nomeProfilo;
   WebDriver driver_profile;
   JavascriptExecutor js;
   WebDriverWait wWait;
   List<String> followerArray;
   List<String> seguitiArray;
   List<WebElement> listaFollowers;
   List<WebElement> listaSeguiti;

   public ChromeProfileSwitch(ChromeOptions options)
   {
      this.driver_profile = new ChromeDriver(options);
      this.js = (JavascriptExecutor) this.driver_profile;
      this.wWait = new WebDriverWait(driver_profile, Duration.ofSeconds(20));
      this.followerArray = new ArrayList<>();
      this.seguitiArray = new ArrayList<>();
      this.listaFollowers = null;
      this.listaSeguiti = null;
      this.nomeProfilo= null; 
   }


    public static void main(String[] args) throws InterruptedException, IOException {

    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    
    //cambia profilo chrome
    options.addArguments("user-data-dir=/Users/liggio/Library/Application Support/Google/Chrome");
    options.addArguments("--profile-directory=Profile 2");

    //leva messaggio chrome è utilizzato da software automatizzato 
    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
    
    HashMap<String, Integer> contentSettings = new HashMap<String, Integer>();
    HashMap<String, Object> profile = new HashMap<String, Object>();
    HashMap<String, Object> prefs = new HashMap<String, Object>();
    
    contentSettings.put("cookies",2);
    profile.put("managed_default_content_settings",contentSettings);
    prefs.put("profile",profile);
    options.setExperimentalOption("prefs",prefs);

    
    ChromeProfileSwitch profilo = new ChromeProfileSwitch(options);
    
    //vai su instagram
    //profilo.vaiSuInstagram();

    //vai al profilo e segui tot followers
    profilo.iniziaSeguireFollowers(); //(c'è un errore)

    //vai al profilo personale e smetti di seguire tot seguiti
    profilo.smettiSeguireSeguiti();

      
   //  vai al profilo
   //profilo.vaiAlProfiloPersonale();


   //cattura followers
   //profilo.catturaFollower(true);

   //cattura seguiti (da testare)
   //profilo.catturaSeguiti(true);

   //cattura follower e seguiti
   //profilo.catturaFollowerSeguiti();


   Thread.sleep(3000);
   profilo.driver_profile.quit();

}


      
   private void vaiSuInstagram()
   {
      this.driver_profile.get("https://www.instagram.com/");
      this.driver_profile.manage().window().maximize();
   }

   

   private void vaiAlProfiloPersonale(){
      vaiSuInstagram();
      this.wWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='x1iyjqo2 xh8yej3'])[1]//div[6]//a[@role='link']"))).click();
      prendiNomeProfiloPersonale();
   }

   private void prendiNomeProfiloPersonale()
   {
      String nomeProfiloPersonale = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='_aacl _aacs _aact _aacx _aada']"))).getText();
      setNomeProfilo(nomeProfiloPersonale);
      System.out.println("Profilo personale: "+nomeProfiloPersonale);
   }

   private void cercaProfilo() throws InterruptedException, IOException{

      String nome = "farmaciecomunali";
      
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/explore/']"))).click();
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Cerca']"))).sendKeys(nome);
      this.wWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@role,'none')])[1]"))).click();
      
      setNomeProfilo(nome);

   }

   private void scrollDialog (int nFollower) throws InterruptedException // funziona (da vedere se riesci a mettere il wait)
   {
   
      int numeroCicli = Math.round(nFollower / 100); 
      for(int i = 0; i < numeroCicli; i++ )
      {
         Thread.sleep(2000);
         this.js.executeScript("document.querySelector('._aano').scrollBy(0,document.body.scrollHeight)");// funziona
      }

      System.out.println("numero follower diviso 100 = "+numeroCicli);
      
      //js.executeScript("document.querySelector('._aano').scrollBy(0,500)");// funziona
   }



   private void iniziaSeguireFollowers() throws InterruptedException, IOException
   {
      vaiSuInstagram();

      cercaProfilo(); // se faccio 2 funzioni non fa in tempo

      //variabile per analizzare riga per riga || parte da 2 perchè è la prima riga utile
      int i = 1;
      int cicloDialog = 0;

      int follow = 0;
      int maxFollow= 2;
      List<WebElement> listaFollowerDialog = null;
      int numeroInt;
      numeroInt = prendiNumeroFollower();

      apriDialogFollower();

      while(follow<maxFollow)
      {

         listaFollowerDialog= this.wWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'_aano')]//div[1]//div[2]//div[1]//div[1]//span[1]//a[1]//span[1]//div[1]")));
         int numeroFollowerDialog = listaFollowerDialog.size();
         System.out.println("prendo "+numeroFollowerDialog + "ciclo numero " + i);

         if(cicloDialog < numeroFollowerDialog)
         {

            String nomeDelFollower = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[2]//div[1]//div[1]//div[1]//div[1]"))).getText();
            String seguiOnonSegui = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[3]//button[1]//div[1]//div[1]"))).getText();
   
            if(seguiOnonSegui.equals("Segui già") == true)
            {
               System.out.println(nomeDelFollower + " lo " + seguiOnonSegui + " ... vai al prossimo!");
            }
            else if(seguiOnonSegui.equals("Segui") == true)
            {
               follow++;
               this.wWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[3]//button[1]"))).click();
               System.out.println(nomeDelFollower + " non lo " + seguiOnonSegui + " ... per questo lo seguo e siamo a " + follow + " follower seguito");
            }
            else if (seguiOnonSegui.equals("Richiesta effettuata") == true)
            {
               System.out.println("Chissa se accetterà!");
            }
            cicloDialog++;   
            i++;
         }
         else
         {
            scrollDialog(numeroInt);
         }

      }

      System.out.println("Perfetto! Hai seguito "+ follow + " ora il ciclo si fermerà!");
      chiudiDialogFollower();

   }

   private void smettiSeguireSeguiti() throws InterruptedException, IOException
   {
      vaiSuInstagram();


      vaiAlProfiloPersonale();
            
      //variabile per analizzare riga per riga
      int i = 1;
      int cicloDialog = 0;
      
      int unfollow = 0;
      int maxUnfollow= 2;
      List<WebElement> listaFollowerDialog = null;
      
      int numeroInt;
      numeroInt = prendiNumeroSeguiti();
      
      apriDialogSeguiti();
      
      while(unfollow<maxUnfollow)
      {
      
         Thread.sleep(500);
         listaFollowerDialog= this.wWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'_aano')]//div[1]//div[2]//div[1]//div[1]//span[1]//a[1]//span[1]//div[1]")));
         int numeroSeguitiDialog = listaFollowerDialog.size();
         System.out.println("numero dei seguiti presente "+numeroSeguitiDialog);

         if(cicloDialog < numeroSeguitiDialog)
         {
            
            String nomeDelFollower = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[2]//div[1]//div[1]//div[1]//div[1]"))).getText();
            String seguiOnonSegui = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[3]//button[1]//div[1]//div[1]"))).getText();
            System.out.println(nomeDelFollower + " lo " + seguiOnonSegui + " ora entro dentro al ciclo");

            if(seguiOnonSegui.equals("Segui già") == true)
            {
               System.out.println("entro dentro l'if");
               this.wWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[contains(@class,'_ab9- _aba8 _abcm')])[" + i + "]//div[3]//button[1]"))).click();
               this.wWait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@role='dialog'])[3]//button[1]"))).click();
               unfollow++;
               System.out.println(nomeDelFollower + " lo " + seguiOnonSegui + " ... per questo lo rimuovo e siamo a " + unfollow + " di seguiti tolti");
            }
            else
            {
               System.out.println("Errore!");
            }
            
            cicloDialog++;   
            i++;
            
         }
         else
         {
         scrollDialog(numeroInt);
         System.out.println("scroll");
         }
                  
         
      }

      System.out.println("Perfetto! Hai smesso di seguire "+ unfollow + " ora il ciclo si fermerà!");

      chiudiDialogSeguiti();
      
   }
 


   private void catturaFollower (boolean veroFalso) throws InterruptedException, IOException
   {

   //primo step prendi numero dei followers
   int numeroInt;
   numeroInt = prendiNumeroFollower();

   //secondo step apri dialog
   apriDialogFollower();

   //variabili di controllo -- EVITARE CICLO INFINITO NEL CASO INSTAGRAM DECISEDDE DI NON FORNIRE TUTTI I DATI
   int contatore = 0;
   int differenza = 0;
   int numeroOperazioniPrecedenti=0;
   int numeroOperazioniAttuali=0;

   while((this.followerArray.size() + differenza) < numeroInt) {

      if( (numeroOperazioniAttuali == numeroOperazioniPrecedenti) & (numeroOperazioniPrecedenti != 0)) // se fossero lo stesso numero  significa che c'è un errore, dopo 3 volte fa finire il ciclo
      {
         contatore++;
         if (contatore >= 3)
         {
            differenza = numeroInt - this.followerArray.size();
            System.out.println("L'algoritmo di instagram da problemi, mancano ancora "+differenza+" follower... FINE");
         }
         else
         {
            System.out.println("Errore: contatore numero = " +contatore);
         }
      } 
      else
      {

         //funziona ma sarebbe meglio aggiungere un wait    
         scrollDialog(numeroInt);

         //xpath axes - prendi _aano e prendi tutti i suoi discendenti (per ora funziona ma va studiato meglio)
         Thread.sleep(500);
         this.listaFollowers = this.driver_profile.findElements(By.xpath("//div[contains(@class,'_aano')]//div[1]//div[2]//div[1]//div[1]//span[1]//a[1]//span[1]//div[1]"));
         System.out.println(this.listaFollowers.size()); //per capire il numero di righe presenti
      
         numeroOperazioniPrecedenti = numeroOperazioniAttuali;// var prende il valore prima che si aggiorni con il ciclo

         List<WebElement>  listaAccorciata = this.listaFollowers.subList(numeroOperazioniPrecedenti, this.listaFollowers.size());
         System.out.println("numero di elementi nella lista accorciata: "+listaAccorciata.size());

         for(WebElement l : listaAccorciata)
         {
               this.followerArray.add(l.getText());
               //followerArray.remove("");//non serve, l'ho messo per rircordare della funzione
               System.out.println("ho aggiunto: " +l.getText()+ " numero "+ this.followerArray.size());
               //System.out.println("Contatore ciclo: " +contatoreCiclo);  
         }

         numeroOperazioniAttuali = this.followerArray.size(); //numero attuale aggiornato
         System.out.println("valore numero operazioni effettuate: " +numeroOperazioniAttuali);  
      
      
      } 
   }

   System.out.println(this.followerArray);
   System.out.println("Il totale dei followers è: "+this.followerArray.size());
   
   chiudiDialogFollower();
   
   if(veroFalso == true)
   {
      scriviFollowInExcel();
   }

   }

   private  void catturaSeguiti (boolean veroFalso) throws InterruptedException, IOException
   {
      //DA AGGIORNARE

   // primo step prendi numero dei seguiti
   int numeroInt;
   numeroInt = prendiNumeroSeguiti();  
   
   //secondo step apri dialog
   apriDialogSeguiti();

   //variabili di controllo -- EVITARE CICLO INFINITO NEL CASO INSTAGRAM DECISEDDE DI NON FORNIRE TUTTI I DATI
   int contatore = 0;
   int differenza = 0;
   int numeroOperazioniPrecedenti=0;
   int numeroOperazioniAttuali=0;

   while((this.seguitiArray.size() + differenza) < numeroInt)
   {

      if( (numeroOperazioniAttuali == numeroOperazioniPrecedenti) & (numeroOperazioniPrecedenti != 0) ) // se fossero lo stesso numero  significa che c'è un errore, dopo 3 volte fa finire il ciclo
      {
         contatore++;
         if (contatore >= 3)
         {
            differenza = numeroInt - this.followerArray.size();
            System.out.println("L'algoritmo di instagram da problemi, mancano ancora "+differenza+" seguiti... FINE");
         }else
         {
            System.out.println("Errore: contatore numero = " +contatore);
         }
      }
      else
      {
         scrollDialog(numeroInt); //funziona ma sarebbe meglio aggiungere un wait 

         //xpath axes - prendi _aano e prendi tutti i suoi discendenti (per ora funziona ma va studiato meglio)
         Thread.sleep(500);
         this.listaSeguiti = this.driver_profile.findElements(By.xpath("//div[contains(@class,'_aano')]//div[1]//div[2]//div[1]//div[1]//span[1]//a[1]//span[1]//div[1]"));
         System.out.println(this.listaSeguiti.size()); //per capire il numero di elementi presenti

         numeroOperazioniPrecedenti = numeroOperazioniAttuali;// var prende il valore prima che si aggiorni con il ciclo

         List<WebElement>  listaAccorciata = this.listaSeguiti.subList(numeroOperazioniPrecedenti, this.listaSeguiti.size());
         System.out.println("numero di elementi nella lista accorciata: "+listaAccorciata.size());

         for(WebElement l : listaAccorciata)
         {            
                  this.seguitiArray.add(l.getText());
                  //followerArray.remove("");//non serve, l'ho messo per rircordare della funzione
                  System.out.println("ho aggiunto: " +l.getText()+ " numero "+ this.seguitiArray.size());
         }

         numeroOperazioniAttuali = this.seguitiArray.size(); //numero attuale aggiornato
         System.out.println("valore numero operazioni effettuate: " +numeroOperazioniAttuali);  


      }
} 

System.out.println(this.seguitiArray);
System.out.println("Il totale dei seguiti stampati è: "+this.seguitiArray.size());

chiudiDialogSeguiti();

if(veroFalso == true)
{
   scriviSeguitiInExcel();
}


}

   private void catturaFollowerSeguiti () throws InterruptedException, IOException
   {
      catturaFollower(false);
      catturaSeguiti(false);
      scriviFollowerSeguitiInExcel();
   }

   

   private void apriDialogFollower()
   {
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'follower')]"))).click();
   }

   private void apriDialogSeguiti()
   {
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'profili seguiti')]"))).click();
   }



   private void chiudiDialogFollower()
   {
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > button:nth-child(1)"))).click();
      System.out.println("Ho chiuso la dialog dei follower");
   } 

   private void chiudiDialogSeguiti()
   {
      this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > button:nth-child(1)"))).click();
      System.out.println("Ho chiuso la dialog dei seguiti");
   }



   private int prendiNumeroFollower()
   {

      //prendi numero followers del profilo e stampalo (servirà per il ciclo)
      String numeroFolloweString = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[2]//a[1]//div[1]/child::*"))).getAttribute("title");
      System.out.println("numero follower string: "+numeroFolloweString);
      
      //prendi stringa ed elimina la virgola
      String replaceString=numeroFolloweString.replaceAll(",","");//replaces all occurrences of 'a' to 'e' 
      System.out.println("elimina virgola: "+replaceString); //stampa l'operazione

      //prendi numero seguiti e trasformalo in un intero
      int numeroFollowerInt = Integer.parseInt(replaceString);
      System.out.println("trasformato in int: "+numeroFollowerInt); //stampa l'operazione
   
      //da eliminare, cambiato per testing
   //int nFarlocco = 200;

      return numeroFollowerInt; 
   }

   private int prendiNumeroSeguiti()
   {
      ///prendi numero seguiti del profilo e stampalo (servirà per il ciclo)
      String numeroSeguitiString = this.wWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[3]//a[1]//div[1]/child::span"))).getText();
      System.out.println(numeroSeguitiString);
      
      //prendi stringa ed elimina la virgola
      String replaceString=numeroSeguitiString.replaceAll(",","");//replaces all occurrences of 'a' to 'e' 
      System.out.println("elimina virgola: "+replaceString); //stampa l'operazione

      //prendi numero seguiti e trasformalo in un intero
      int numeroSeguitiInt = Integer.parseInt(replaceString);
      System.out.println("trasformato in int: "+numeroSeguitiInt); //stampa l'operazione
      
      return numeroSeguitiInt;
   }



   private  void scriviFollowInExcel() throws IOException, InterruptedException
   {

      CreazioneFileExcel fileExcel = new CreazioneFileExcel("Followers");
      fileExcel.creaPaginaFollower(fileExcel.workbook, this.followerArray.size(), this.followerArray, this.nomeProfilo);
      fileExcel.creaFile(fileExcel.workbook, this.nomeProfilo);
      fileExcel.close();
   
   }

   private void scriviSeguitiInExcel() throws IOException, InterruptedException
   {
      CreazioneFileExcel fileExcel = new CreazioneFileExcel("Seguiti");
      fileExcel.creaPaginaSeguiti(fileExcel.workbook, this.seguitiArray.size(), this.seguitiArray, this.nomeProfilo);
      fileExcel.creaFile(fileExcel.workbook, this.nomeProfilo);
      fileExcel.close();
   }

   private void scriviFollowerSeguitiInExcel() throws IOException, InterruptedException
   {
      CreazioneFileExcel fileExcel = new CreazioneFileExcel("Followers", "Seguiti");
      fileExcel.creaPaginaFollower(fileExcel.workbook, this.followerArray.size(), this.followerArray, this.nomeProfilo);
      fileExcel.creaPaginaSeguiti(fileExcel.workbook, this.seguitiArray.size(), this.seguitiArray, this.nomeProfilo);
      fileExcel.creaFile(fileExcel.workbook, this.nomeProfilo);
      fileExcel.close();
   }



   public void setNomeProfilo(String nomeProfilo) {
      this.nomeProfilo = nomeProfilo;
   }


}

//fare wait con js (ancora non ho trovato il modo)
//da fare algoritmo che controlli i nomi follower, seguiti e persone da seguire
// dividi le classi
// salva cockie personalizzati
//fai cartella solo di profili da caricare instagram



//(appunti) scroll tramite la classe action (nelle dialog non funziona)
   /*
   Thread.sleep(5000);
   Actions actions = new Actions(driver_profile);
   actions.sendKeys(Keys.END).perform();*/




// per prendere il nome
//div[contains(@class,'_aano')]//div[3]//div[2]//div[1]//div[1]//span[1]//a[1]//span[1]//div[1]

 //per il bottone
 //div[contains(@class,'_aano')]//div[1]//div[3]//button[1]//div[1]//div[1]

