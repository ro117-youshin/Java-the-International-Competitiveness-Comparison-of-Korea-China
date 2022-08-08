package competitiveness;

public class Output {
	public void system() {
//		Crawling crawl = new Crawling();
//		crawl.processing();
		WriteKoreaData korea = new WriteKoreaData();
		korea.processing();
		WriteChinaData china = new WriteChinaData();
		china.processing();
		WriteToCSV write = new WriteToCSV();
		write.output();
	}
}
