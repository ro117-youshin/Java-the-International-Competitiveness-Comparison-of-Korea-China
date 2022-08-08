package competitiveness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteKoreaData {
	
	Calculate cal = new Calculate();
	static List<String> dataForKoreaItem = new ArrayList<String>();
	static List<String> TSI_Korea = new ArrayList<String>();
	static List<String> RCA_Korea = new ArrayList<String>();
	
	public void processing() {
		readCSV();
	}
	
	public void readCSV() { // CSV 생성
		String TSIData = "";
		String RCAData = "";
		File csv = new File("C:\\Users\\YOUSHIN KIM\\Desktop\\데이터분석프로그래밍\\Data.csv");
		BufferedReader br = null;
		int item = 5;
		int year = 2010;
		
		try {
			br = new BufferedReader(new FileReader(csv));
			String newLine;
			String[] HScode;
			String[] column;
			String[] TSI_Array = new String[item];
			String[] RCA_Array = new String[item];
			
			while((newLine = br.readLine()) != null) {
				dataForKoreaItem.add(newLine);
			}
			
			for(int index = 0; index < dataForKoreaItem.size(); index++) {
				
				if (index < 2) {
					
					if (index == 0) {
						HScode = new String[item];
						HScode = dataForKoreaItem.get(1).split(",");
						// add index
						TSIData = "Korea TSI" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "수출특화품목" + "," + "수입특화품목"; 
						TSI_Korea.add(TSIData);
						
						RCAData = "Korea RCA" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "A등급" + "," + "B등급" + "," + "C등급" + "," + "D등급"; 
						RCA_Korea.add(RCAData);
						column = new String[dataForKoreaItem.size()];
					}
					
				} else {
					
					column = dataForKoreaItem.get(index).split(",");
					for (int indexOfCal = 0; indexOfCal < item; indexOfCal++) {
						TSI_Array[indexOfCal] = cal.calculate_TSI(column[(indexOfCal * 2) + 19], column[(indexOfCal * 2) + 20]);		
						RCA_Array[indexOfCal] = cal.calculate_RCA(column[(indexOfCal * 2) + 19], column[18], column[indexOfCal + 2], column[1]);
					}
					// TSI Rating
					int[] TSI_Rating = cal.TSI_rating(TSI_Array);
					TSIData = Integer.toString(year) + "," + TSI_Array[0] + "," + TSI_Array[1] + "," + TSI_Array[2] + "," + TSI_Array[3] + "," + TSI_Array[4] + "," + " " + "," + TSI_Rating[0] + "," + TSI_Rating[1];
					TSI_Korea.add(TSIData);
					// RCA Rating
					int[] RCA_Rating = cal.RCA_rating(RCA_Array);
					RCAData =  Integer.toString(year)  + "," + RCA_Array[0] + "," + RCA_Array[1] + "," + RCA_Array[2] + "," + RCA_Array[3] + "," + RCA_Array[4] + "," + " " + "," 
							+ RCA_Rating[0] + "," + RCA_Rating[1] + "," + RCA_Rating[2] + "," + RCA_Rating[3];
					RCA_Korea.add(RCAData);
					year++;
				}
								
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}