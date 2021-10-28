package practice;

public class PromptABoxGeneration {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String basePath = "C:\\Users\\Amrit\\Documents\\Census\\";
		String baseName = "PopulationType";
		String sourceString = basePath + baseName + ".csv";
		
		CSVABox csvaBox = new CSVABox();
		String resultString = csvaBox.parseCSV(
				sourceString,
				"http://linked-statistics-bd.org/2011/data",
				"Space ()",
				"",
				"populationTypeId",
				"TTL",
				"C:\\Users\\Amrit\\Documents\\1\\thesis\\sources\\" + baseName + ".ttl");
		System.out.println(resultString);
	}
}
