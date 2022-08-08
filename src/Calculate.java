package competitiveness;

import java.text.DecimalFormat;

public class Calculate {

	public static DecimalFormat tradeValue = new DecimalFormat("#.###");

	// 무역특화지수 계산
	public String calculate_TSI(String exportV, String importV) {

		double exportValue = Double.parseDouble(exportV);
		double importValue = Double.parseDouble(importV);

		double calculateTSI = (exportValue - importValue) / (exportValue + importValue);
		String result = tradeValue.format(calculateTSI);
		return result;
	}

	// 무역특화지수 등급 분류
	// -1 ~ 1
	public int[] TSI_rating(String[] TSI) {
		int[] TSIArray = new int[2];

		for (int index = 0; index < 5; index++) {

			double rating = Double.parseDouble(TSI[index]);

			// TSI[0] = 수출특화품목, TSI[1] = 수입특화품목
			if (rating <= 1 && rating >= 0.5) {
				TSIArray[0]++;
			} else if (rating < -0.5) {
				TSIArray[1]++;
			}

		}
		return TSIArray;
	}

	// 현시비교우위지수 계산
	public String calculate_RCA(String countrysItemExports, String countrysTotalExports, String globalsItemExports, String globalTotalExports) {

		double CIE = Double.parseDouble(countrysItemExports);
		double CTE = Double.parseDouble(countrysTotalExports);
		double GIE = Double.parseDouble(globalsItemExports);
		long GTE = Long.parseLong(globalTotalExports);
		double calculateRCA = (CIE / CTE) / (GIE / GTE);
		String result = tradeValue.format(calculateRCA);
		return result;
	}

	// 현시비교우위지수 등급분류
	// 0 ~ 2.5
	public int[] RCA_rating(String[] RCA) {
		int[] RCAArray = new int[4];
		for (int index = 0; index < 5; index++) {

			double rating = Double.parseDouble(RCA[index]);

			// RCAArray[0] = A등급, RCAArray[1] = B등급, RCAArray[2] = C등급, RCAArray[3] = D등급
			if (rating > 2.5) {
				RCAArray[0]++;
			} else if (rating > 1.25 && rating < 2.5) {
				RCAArray[1]++;
			} else if (rating > 0.8 && rating < 1.25) {
				RCAArray[2]++;
			} else {
				RCAArray[3]++;
			}
		}
		return RCAArray;
	}
}