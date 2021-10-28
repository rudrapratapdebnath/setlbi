package practice;

import helper.Methods;

public class FileMerge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String baseName = "Census_Data";
		String baseFileName = "C:\\Users\\Amrit\\Documents\\1\\thesis\\aboxes\\" + baseName + ".ttl";
		
		String basePath = "C:\\Users\\Amrit\\Documents\\Census\\";
		String filePathOne = basePath + "Census_C03.ttl";
		
//		Methods.deleteAndCreateFile(baseFileName);
		
		String text = Methods.readStringFromFileWithNewLine(filePathOne);
		Methods.appendToFile(text, baseFileName);
		
		System.out.println("Done");
	}
}
