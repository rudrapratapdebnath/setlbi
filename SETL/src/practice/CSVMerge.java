package practice;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import core.FactEntryGeneration;
import helper.Methods;

public class CSVMerge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String basePath = "C:\\Users\\Amrit\\Documents\\Census";
		File folder = new File(basePath);
		File[] listOfFiles = folder.listFiles();
		String baseName = "Census_C15_HousingTenancy";
		String baseFileName = "C:\\Users\\Amrit\\Documents\\1\\thesis\\sources\\" + baseName + ".csv";
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Methods.deleteAndCreateFile(baseFileName);
				
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						int count = 0;
						File csvFile = listOfFiles[i];
						
						BufferedReader bufferedReader = null;

				        try {
				            bufferedReader = new BufferedReader(new FileReader(csvFile));

				            String text = "", s;
				            StringBuilder stringBuilder = new StringBuilder(text);
				            while ((s = bufferedReader.readLine()) != null) {
				                if (i == 0 && count == 0) {
				                	stringBuilder.append(s + "\n");
								} else if (count != 0) {
									stringBuilder.append(s + "\n");
								}
				                
				                count++;
				            }
				            
				            Methods.appendToFile(stringBuilder.toString(), baseFileName);
				        } catch (Exception e) {
				            // TODO: handle exception
				            System.out.println(e.getMessage());
				        }
					}
				}
				

	            System.out.println("Done");
			}
		});
	}
}
