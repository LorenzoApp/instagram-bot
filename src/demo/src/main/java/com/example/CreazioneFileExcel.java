package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreazioneFileExcel extends XSSFWorkbook{

    protected XSSFWorkbook workbook;
    private XSSFSheet sheetFollow;
    private XSSFSheet sheetSeguiti;

    
    public CreazioneFileExcel(String nome) throws  InterruptedException
    {
         this.workbook = new XSSFWorkbook(); //tutte queste operazioni lavorano con dati temporanei, vanno scritti in un file!        
         
         if(nome.equals("Followers")){
            this.sheetFollow = workbook.createSheet(nome);
            this.sheetSeguiti = null;
        }
        else if(nome.equals("Seguiti"))
        {
            this.sheetSeguiti = workbook.createSheet(nome);
            this.sheetFollow = null;
        }

    }

    public CreazioneFileExcel(String nFollower, String nSeguiti) throws  InterruptedException
    {
         this.workbook = new XSSFWorkbook(); //tutte queste operazioni lavorano con dati temporanei, vanno scritti in un file!        
         this.sheetFollow = workbook.createSheet(nFollower);
         this.sheetSeguiti = workbook.createSheet(nSeguiti);
    }


    private String creaCartella(String nomeProfilo){
        
        String cartella ="/Users/liggio/Desktop/Progetto Instagram Scraper/excel file/" +nomeProfilo;
        File crea = new File(cartella);
        if (!crea.exists()){
            crea.mkdir();
        }
        
        return cartella;
    }

    protected void creaFile(XSSFWorkbook workbook, String nomeProfilo) {

        String cartella = creaCartella(nomeProfilo);

        String giorno = prendiGiorno();
        String doveCrearePath = cartella + "/" + giorno + "-" +nomeProfilo+ ".xlsx";
        File file = new File(doveCrearePath);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            System.out.println("Ho creato con successo il file "+nomeProfilo+ ".xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    protected void creaPaginaFollower( XSSFWorkbook workbook, int follower, List<String> followArr, String nomeProfiloString) throws InterruptedException {
       
        System.out.println("creo pagina follower");
        int j = 0;
        int numeroRighe = follower;

        for(int i = 0; i < numeroRighe; i++)
        {
            Thread.sleep(500);
            this.sheetFollow.createRow(i);
            System.out.println("creo "+i+ "riga");
            
            String variabileAppoggio = followArr.get(j);

            if(!variabileAppoggio.isEmpty())
            {
            
                this.sheetFollow.getRow(i).createCell(0).setCellValue(variabileAppoggio);
                j++;
                System.out.println("creo cella e metto il valore: "+variabileAppoggio);
            }
            else
            {
                System.out.println("è vuotaaaa!");
            }
        }    
   
    }

    protected void creaPaginaSeguiti( XSSFWorkbook workbook, int seguiti, List<String> seguiArr, String nomeProfiloString) throws InterruptedException {
       
        System.out.println("creo pagina seguiti");
        int j = 0;
        int numeroRighe = seguiti;

        for(int i = 0; i < numeroRighe; i++)
        {
            Thread.sleep(500);
            this.sheetSeguiti.createRow(i);
            System.out.println("creo "+i+ "riga");
            
            String variabileAppoggio = seguiArr.get(j);
            
            if(!variabileAppoggio.isEmpty())
            {
                this.sheetSeguiti.getRow(i).createCell(0).setCellValue(variabileAppoggio);
                j++;
                System.out.println("creo cella e metto il valore: "+variabileAppoggio);
            }
            else
            {
                System.out.println("è vuotaaaa!");
            }
            
                
        }              
   
    }
    



    private String prendiGiorno()
    {
        String date = FastDateFormat.getInstance("dd-MM-yyyy").format(System.currentTimeMillis( ));
        System.out.println("oggi è: "+date);
        
        return date;

    }


    
    //da implementare ancora
    private static String getCellValue(XSSFCell cell)
    {
        switch (cell.getCellType())
        {
            case NUMERIC:
            return String.valueOf(cell.getNumericCellValue());

            case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());

            case STRING:
            return String.valueOf(cell.getStringCellValue());
        
            default:
            return cell.getStringCellValue(); 
        }

    }

    

    // private void controlloCartella (String nomeCartella)
    // {
        
            
    //     // Definiamo il path della cartella da creare
    //     String pathFolderName = "/Users/liggio/Desktop/Progetto Instagram Scraper/excel file";
        
    //     // Creiamo l'oggetto File
    //     File folder = new File(nomeCartella);
        
    //     // Verifichiamo che non sia già esistente come cartella
    //     if(!folder.isDirectory()){
    //         // In caso non sia già presente, la creiamo
    //         folder.mkdir();
    //     }

    // }

    
}

    





