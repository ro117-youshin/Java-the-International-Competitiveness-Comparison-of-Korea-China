package competitiveness;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Crawling c = new Crawling();
//		c.processing();
		WriteToCSV w = new WriteToCSV();
		w.start();
		Read r = new Read();
		r.start();
	}

}
