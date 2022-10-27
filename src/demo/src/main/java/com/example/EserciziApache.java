package com.example;

import java.io.File;
// import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
// import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EserciziApache{

    public static void main(String[] args) throws Exception {

    // excel file ha 2 versioni nuova xlsx import XSSF mentre la vecchia xls import HSSF
    //excel è strutturato da woorkbook(dove si lavora) poi ci sono le sheet (pagine)
    //dentro le pagine abbiamo le row (righe) che al loro interno hanno cell (le celle)
    
/*    //qui abbiamo la versione OLD di excel xls
    HSSFWorkbook workbook = new HSSFWorkbook(); //tutte queste righe sono dati temporanei, vanno scritti in un file!
    HSSFSheet sheet = workbook.createSheet("Test porco"); //senza nome (string) all'interno crea una nuova pagina in automatico, se all'interno ce n'è già una allora la mette per ultima (quella nuova)
    
    sheet.createRow(0); //index parte da zero sia per row che per columns
    sheet.getRow(0).createCell(0).setCellValue("hello");
    sheet.getRow(0).createCell(1).setCellValue("world");

    sheet.createRow(1); //index parte da zero sia per row che per columns
    sheet.getRow(1).createCell(0).setCellValue("HYR");
    sheet.getRow(1).createCell(1).setCellValue("tutorials");
    
//scriviamo i dati in un file
    
    //attenzione se non cambi nome al file lo sostituisce!
    File file = new File("/Users/liggio/Desktop/Progetto Instagram Scraper/excel file/Test3.xls");
    FileOutputStream fos = new FileOutputStream(file);//non ho capito a cosa serva, "nulla è cambiato" - puoi usare anche senza fos e mettere direttamente file nel write

    workbook.write(fos);
    workbook.close();

   */  

 //qui abbiamo la versione NEW di excel xlsx
 
  XSSFWorkbook workbook = new XSSFWorkbook(); //tutte queste righe sono dati temporanei, vanno scritti in un file!

  XSSFSheet sheet = workbook.createSheet("Test porco");
  XSSFSheet shee1 = workbook.createSheet("teschio"); //senza nome (string) all'interno crea una nuova pagina in automatico, se all'interno ce n'è già una allora la mette per ultima (quella nuova)
  
  sheet.createRow(0); //index parte da zero sia per row che per columns
  sheet.getRow(0).createCell(0).setCellValue("cazz");
  sheet.getRow(0).createCell(1).setCellValue("world");

  shee1.createRow(0);
  shee1.getRow(0).createCell(0).setCellValue("culooo");

  sheet.createRow(1); //index parte da zero sia per row che per columns
 // sheet.getRow(1).createCell(0).setCellValue("HYR");
  //sheet.getRow(1).createCell(1).setCellValue("tutorials");
  
//scriviamo i dati in un file
  
  //attenzione se non cambi nome al file lo sostituisce!
  String nome = "lucapezzolo";
  File file = new File("/Users/liggio/Desktop/Progetto Instagram Scraper/excel file/"+nome+".xlsx");
  FileOutputStream fos = new FileOutputStream(file);
  workbook.write(fos);
  workbook.close();
 



//  //read data from excel
// File file2 = new File("/Users/liggio/Desktop/Progetto Instagram Scraper/excel file/Test.xlsx");
// FileInputStream fis2 = new FileInputStream(file2); //forse ho capito che trasforma i dati xlsx in dati workbook leggibili
// XSSFWorkbook workbook2 = new XSSFWorkbook(fis2);
// //XSSFSheet sheet2 = workbook2.getSheetAt(0); //scegli la pagina 0 (cioè la prima), dalla pagina prendiamo la riga e poi la cella
// XSSFSheet sheet3 = workbook2.getSheetAt(1); //scegli la pagina 0 (cioè la prima), dalla pagina prendiamo la riga e poi la cella
// //String cellValue = sheet2.getRow(0).getCell(0).getStringCellValue(); // scenario statico, leggi solo qualche cella specifica
// //System.out.println(cellValue);

// int rowCount = sheet3.getPhysicalNumberOfRows(); // conta quante righe ci sono

// //fai un doppio ciclo for per stampare i valori all'interno del file excel
// for(int i = 0; i< rowCount; i++)
// {
//   XSSFRow row3 = sheet3.getRow(i);
//   int cellCount = row3.getPhysicalNumberOfCells();//conta le celle per la riga i
 
//   for(int j = 0; j< cellCount; j++) //ripeti fino a che le celle siano "non definite"
//   {
//     XSSFCell  cell2 = row3.getCell(j);
//     String cellValue = getCellValue(cell2);
//     System.out.print("|| "+cellValue + " "); 
//   }

//   System.out.println();

}


// workbook2.close();
// fis2.close();

//}

//questo metodo serve per identificare che tipo di dato è nel file excel (nel mio caso testo-numero e boolean)
public static String getCellValue(XSSFCell cell)
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



}