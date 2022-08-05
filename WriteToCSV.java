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
		List<String> itemData = new ArrayList<String>();
		List<String> TSI_List = new ArrayList<String>();
		String dataList = "";
		File csv = new File("C:\\Users\\YOUSHIN KIM\\Desktop\\DataAnalysisCrawling\\test.csv");
		BufferedReader br = null;
		int item = 5;
		int year = 2010;
		
		try {
			br = new BufferedReader(new FileReader(csv));
			String newLine;
			String[] field;
			String[] column;
			String[] TSI_Array = new String[item];
			while((newLine = br.readLine()) != null) {
				itemData.add(newLine);
			}
			
			for(int index = 0; index < itemData.size(); index++) {
				
				if (index < 2) {
					field = new String[item * 2];
					field = itemData.get(0).split(",");
					
					// item HS code 기재하기
					dataList = field[0] + "," + field[1] + "," + field[3] + "," + field[5] + "," + field[7] + "," + field[9] + "," + " " + "," + "수출특화품목" + "," + "수입특화품목";
					column = new String[itemData.size()];
					
				} else {
					
					column = itemData.get(index).split(",");
					for (int index2 = 0; index2 < item; index2++) {
						TSI_Array[index2] = cal.calculate_TSI(column[(index2 * 2) + 1], column[(index2 * 2) + 2]);		
					}

					int[] TSI_Rating = cal.TSI_rating(TSI_Array);
					dataList = Integer.toString(year) + "," + TSI_Array[0] + "," + TSI_Array[1] + "," + TSI_Array[2] + "," + TSI_Array[3] + "," + TSI_Array[4] + "," + " " + "," + TSI_Rating[0] + "," + TSI_Rating[1];
					TSI_List.add(dataList);
					year++;
					
				}
								
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int index = 0; index < TSI_List.size();index++) {
			System.out.println(TSI_List.get(index));
			make_CSV_raw(TSI_List.get(index));
		}
		
	}

	public void make_CSV_raw(String data) { // CSV 생성

		File csv = new File("C:\\Users\\YOUSHIN KIM\\Desktop\\DataAnalysisCrawling\\TSIresult.csv");
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
