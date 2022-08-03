package Crawling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CrawlingForKorea {

   public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
   public static final String WEB_DRIVER_PATH = "C:\\Users\\YOUSHIN KIM\\Desktop\\DataAnalysisCrawling\\chromedriver.exe"; // 드라이버 경로
   public static String passWD = "PW";
   public static ArrayList<String> arr = new ArrayList<String>();
   public static String dataList = "";
   public static int year = 2010;   
//   public static int month = 1;
   public static DecimalFormat df = new DecimalFormat("00");
   public static DecimalFormat tradeValue = new DecimalFormat("#.###");
   
   public static void main(String[] args) {
      processing();
   }
   
   static void processing() {
      // 드라이버 설정
            try {
               System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
            } catch (Exception e) {
               e.printStackTrace();
            }

            // 크롬 설정을 담은 객체 생성
            ChromeOptions options = new ChromeOptions();
            // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
            // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.
            options.addArguments("headless");

            // 위에서 설정한 옵션은 담은 드라이버 객체 생성
            // 옵션을 설정하지 않았을 때에는 생략 가능하다.
            // WebDriver객체가 곧 하나의 브라우저 창이라 생각한다.
            WebDriver driver = new ChromeDriver(/* options */);

            // 이동을 원하는 url
            String url = "https://stat.kita.net/newMain.screen";

            // WebDriver을 해당 url로 이동한다.
            driver.get(url);

            // 브라우저 이동시 생기는 로드시간을 기다린다.
            // HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
            try {
               Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
            }
            // 로그인
            driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/ul/li[2]/a")).click();
            try {
               Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
            }
            driver.findElement(By.xpath("//*[@id=\"userId\"]")).sendKeys("ID");
            try {
               Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
            }
            driver.findElement(By.xpath("//*[@id=\"pwd\"]")).sendKeys(passWD);
            try {
               Thread.sleep((int) (Math.random() * 5000));
            } catch (InterruptedException e) {
            }
            driver.findElement(By.xpath("//*[@id=\"loginBtn\"]"))
                  .click();
            try {
               Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            // 검색
            
            driver.findElement(By.xpath("//*[@id=\"gnb\"]/ul/li[1]/a")).click();
            driver.findElement(By.xpath("//*[@id=\"gnb\"]/ul/li[1]/div/div/ul/li[1]/ul/li[3]/a")).click();
            // 아이템 HS코드 입력
            driver.findElement(By.xpath("//*[@id=\"s_cond_unit_num\"]")).sendKeys("HS code");
            // 교역 선택 //*[@id="contents"]/div[2]/form/fieldset/div[1]/div[2]/select
            driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/form/fieldset/div[1]/div[2]/select/option[2]")).click();
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            // 조회 클릭
            driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/form/fieldset/div[3]/a/img")).click();
            try {
               Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
      
            // 년도 옵션, 2022년도가 option[1]
            for (int index = 12; index >= 1; index--) { 
               driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/form/fieldset/div[2]/div[1]/select/option["+ index +"]")).click();
                  try {
                     Thread.sleep(1000);
                  } catch (InterruptedException e) {
                  }
                  // 조회 클릭
                  driver.findElement(By.xpath("//*[@id=\"contents\"]/div[2]/form/fieldset/div[3]/a/img")).click();
                  try {
                     Thread.sleep(1000);
                  } catch (InterruptedException e) {
                  }
                  // 맨 처음 컬럼 이름을 가져오기 위한 조건문 (아이템의 HS코드 가져오기)
                  if (index == 12) {
                     
                     String[] column = new String[5]; // 컬럼을 위한 배열 생성 - 반도체 5개 아이템
                     for(int indexOfColumn = 3; indexOfColumn <= 7; indexOfColumn++) {
                     column[indexOfColumn - 3] = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/form/div[2]/div/div/div/div/table/tbody/tr[3]/td/div/div[1]/table/tbody/tr["+ indexOfColumn +"]/td[3]")).getText();
                     }
                     // 리스트에 컬럼 먼저 쓰기
                     String field = "HS code" + "," + column[0] + "," + column[1] + "," + column[2] + "," + column[3] + "," + column[4] + "," + " " + "," + "수출특화품목" + "," + "수입특화품목";
                     arr.add(field);
                     
                  }
                  
                     String[] TSI = new String[5];
                     for(int indexOfTSI = 3; indexOfTSI <= 7; indexOfTSI++) {
                        TSI[indexOfTSI - 3] = calculate_value(driver.findElement(By.xpath("//*[@id=\"mySheet1\"]/tbody/tr[3]/td/div/div[1]/table/tbody/tr["+ indexOfTSI +"]/td[6]")).getText().replace(",", ""),
                              driver.findElement(By.xpath("//*[@id=\"mySheet1\"]/tbody/tr[3]/td/div/div[1]/table/tbody/tr["+ indexOfTSI +"]/td[8]")).getText().replace(",", ""));
                     }
                     // 수출특화품목과 수입특화품목 개수 구하기
                     int[] Tsirating = TSI_rating(TSI);
                     dataList = Integer.toString(year) + "," + TSI[0] + "," + TSI[1] + "," + TSI[2] + "," + TSI[3] + "," + TSI[4] + "," + " " + "," + Tsirating[0] + "," + Tsirating[1];
                     arr.add(dataList);
                     
                  try {
                     Thread.sleep(1000);
                  } catch (InterruptedException e) {
                  }
                  
               if (year == 2021) {
                  break;
               }
               year++;
            }
            
            // CSV파일 생성
            for (int index = 0; index < arr.size();index++) {
               System.out.println(arr.get(index));
               make_CSV_raw(arr.get(index));
            }

            try {
               // 드라이버가 null이 아니라면
               if (driver != null) {
//                           // 드라이버 연결 종료
//                           driver.close(); // 드라이버 연결 해제
                  //
//                           // 프로세스 종료
//                           driver.quit();
               }
            } catch (Exception e) {
               throw new RuntimeException(e.getMessage());
            }
   }
   
   static void make_CSV_raw(String data) { //CSV 생성
      File csv = new File("C:\\Users\\YOUSHIN KIM\\Desktop\\DataAnalysisCrawling\\testforArray1.csv");
      BufferedWriter bw = null;

      try {
         bw = new BufferedWriter(new FileWriter(csv, true));
         bw.write(data);
         bw.newLine();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            if (bw != null) {
               bw.flush();
               bw.close();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   
   static String calculate_value (String exportV, String importV) { //무역특화지수 계산
      
       double exportValue = Double.parseDouble(exportV);
      double importValue = Double.parseDouble(importV);
      
      double calculateTSI = (exportValue - importValue) / (exportValue + importValue);
      String result = tradeValue.format(calculateTSI);
      return result;
   } 
   
   static int[] TSI_rating (String[] TSI) {
      int[] TSIArray = new int[2];
      
      for(int index = 0; index < 5; index++) {
         
         double rating = Double.parseDouble(TSI[index]);
         
         // TSI[0] = 수출특화품목, TSI[1] = 수입특화품목 
         if (rating <= 1 && rating >= 0.5) {
            TSIArray[0]++;
         } else if(rating < -0.5) {
            TSIArray[1]++;
         }
         
      }
      return TSIArray;
   }
      
}
