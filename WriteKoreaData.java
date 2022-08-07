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

public class WriteToCSV {
	
	Calculate cal = new Calculate();
	
	public void start() {
		readCSV();
	}
	
	public void readCSV() { // CSV 생성
		List<String> dataForKoreaItem = new ArrayList<String>();
		List<String> TSI_Korea = new ArrayList<String>();
		List<String> RCA_Korea = new ArrayList<String>();
		
		String TSIData = "";
		String RCAData = "";
		File csv = new File("");
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
						
						TSIData = "Korea TSI" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "수출특화품목" + "," + "수입특화품목"; 
						TSI_Korea.add(TSIData);
						
						RCAData = "Korea RCA" + "," +  HScode[2] + "," +  HScode[3] + "," +  HScode[4] + "," +  HScode[5] + "," +  HScode[6] + "," + " " + "," + "A등급" + "," + "B등급" + "," + "C등급" + "," + "D등급"; 
						RCA_Korea.add(RCAData);
						// item HS code 기재하기
						column = new String[dataForKoreaItem.size()];
					}
				} else {
					
					column = dataForKoreaItem.get(index).split(",");
					for (int indexOfCal = 0; indexOfCal < item; indexOfCal++) {
						TSI_Array[indexOfCal] = cal.calculate_TSI(column[(indexOfCal * 2) + 19], column[(indexOfCal * 2) + 20]);		
						RCA_Array[indexOfCal] = cal.calculate_RCA(column[(indexOfCal * 2) + 19], column[18], column[indexOfCal + 2], column[1]);
					}

					int[] TSI_Rating = cal.TSI_rating(TSI_Array);
					TSIData = Integer.toString(year) + "," + TSI_Array[0] + "," + TSI_Array[1] + "," + TSI_Array[2] + "," + TSI_Array[3] + "," + TSI_Array[4] + "," + " " + "," + TSI_Rating[0] + "," + TSI_Rating[1];
					TSI_Korea.add(TSIData);
					
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
		
		for (int index = 0; index < TSI_Korea.size();index++) {
			System.out.println(TSI_Korea.get(index));
//			make_CSV_raw(TSI_Korea.get(index));
		}
		
		for (int index = 0; index < RCA_Korea.size();index++) {
			System.out.println(RCA_Korea.get(index));
//			make_CSV_raw(RCA_Korea.get(index));
		}
		
	}

	public void make_CSV_raw(String data) { // CSV 생성

		File csv = new File("");
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
