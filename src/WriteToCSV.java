package competitiveness;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToCSV {

	WriteKoreaData korea = new WriteKoreaData();
	WriteChinaData china = new WriteChinaData();

	public void output() {
		readList();
	}

	public void readList() {

		for (int index = 0; index < korea.TSI_Korea.size(); index++) {
//			System.out.println(korea.TSI_Korea.get(index));
			make_CSV_raw(korea.TSI_Korea.get(index));
		}

		for (int index = 0; index < korea.RCA_Korea.size(); index++) {
//			System.out.println(korea.RCA_Korea.get(index));
			make_CSV_raw(korea.RCA_Korea.get(index));
		}

		for (int index = 0; index < china.TSI_China.size(); index++) {
//			System.out.println(china.TSI_China.get(index));
			make_CSV_raw(china.TSI_China.get(index));
		}

		for (int index = 0; index < china.RCA_China.size(); index++) {
//			System.out.println(china.RCA_China.get(index));
			make_CSV_raw(china.RCA_China.get(index));
		}

	}

	public void make_CSV_raw(String data) { // CSV 생성

		File csv = new File("C:\\Users\\YOUSHIN KIM\\Desktop\\데이터분석프로그래밍\\result.csv");
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