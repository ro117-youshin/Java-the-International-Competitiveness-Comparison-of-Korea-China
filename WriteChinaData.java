package competitiveness;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteChinaData {
	
	Calculate cal = new Calculate();

	public void start() {
		readCSV();
	}
	
	public void readCSV() {
		File csv = new File("C:\\Users\\liveb\\Desktop\\data02\\Data02.csv");
		BufferedReader br = null;
		int item = 5;
		int year = 2010;
		String[] HScode;
		String[] TSI_Array = new String[item];
		String[] RCA_Array = new String[item];
		String[] column;
		String TSIData;
		String RCAData;
		
		List<String> itemData = new ArrayList<String>();
		List<String> TSI_China = new ArrayList<String>();
		List<String> RCA_China = new ArrayList<String>();
		
		
		try {
			br = new BufferedReader(new FileReader(csv));
			String newLine;
			while((newLine = br.readLine()) != null) {
				itemData.add(newLine);
			}
			
			for(int index = 0; index < itemData.size(); index++) {
				
				if(index < 2) {
					if (index == 0) {
						HScode = new String[item];
						HScode = itemData.get(1).split(",");
						TSIData = "China TSI" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "수출특화품목" + "," + "수입특화품목"; 
						TSI_China.add(TSIData);
						RCAData = "China RCA" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "A등급" + "," + "B등급" + "," + "C등급" + "," + "D등급"; 
						RCA_China.add(RCAData);
						column = new String[itemData.size()];
					}
				} else {
					column = itemData.get(index).split(",");
					
					for(int indexOfCal = 0; indexOfCal < item; indexOfCal++) {
						TSI_Array[indexOfCal] = cal.calculate_TSI(column[(indexOfCal * 2) + 8], column[(indexOfCal * 2) + 9]); //18
						RCA_Array[indexOfCal] = cal.calculate_RCA(column[(indexOfCal * 2) + 8], column[7], column[indexOfCal + 2], column[1]);
					
					}
					
					int[] TSI_Rating = cal.TSI_rating(TSI_Array);
					TSIData = Integer.toString(year)  + "," + TSI_Array[0] + "," + TSI_Array[1] + "," + TSI_Array[2] + "," + TSI_Array[3] + "," + TSI_Array[4] + "," + " " + "," + TSI_Rating[0] + "," + TSI_Rating[1];
					TSI_China.add(TSIData);
					
					int[] RCA_Rating = cal.RCA_rating(RCA_Array);
					RCAData =  Integer.toString(year)  + "," + RCA_Array[0] + "," + RCA_Array[1] + "," + RCA_Array[2] + "," + RCA_Array[3] + "," + RCA_Array[4] + "," + " " + "," 
					+ RCA_Rating[0] + "," + RCA_Rating[1] + "," + RCA_Rating[2] + "," + RCA_Rating[3];
					RCA_China.add(RCAData);
					year++;
					
				}
				
			}
			
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int index = 0; index < TSI_China.size();index++) {
			System.out.println(TSI_China.get(index));
//			make_CSV_raw(TSI_China.get(index));
		}
		
		for (int index = 0; index < TSI_China.size();index++) {
			System.out.println(RCA_China.get(index));
//			make_CSV_raw(TSI_China.get(index));
		}
		
		
	}
	
	public void make_CSV_raw(String data) { // CSV 생성

		File csv = new File("C:\\Users\\liveb\\Desktop\\data02\\china.csv");
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
	
}
