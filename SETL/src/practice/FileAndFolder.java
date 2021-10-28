package practice;

import java.awt.EventQueue;
import java.io.File;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.jena.rdf.model.Model;

import core.FactEntryGeneration;
import core.InstanceGenerator;
import core.LevelEntryNew;
import core.RDFWrapper;
import helper.Methods;

public class FileAndFolder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String basePath = "C:\\Users\\Amrit\\Documents\\Census";
		File folder = new File(basePath);
		File[] listOfFiles = folder.listFiles();
		String baseName = "Census_C07";
		String baseFileName = basePath + "\\" + baseName + ".csv";

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				for (int i = 0; i < listOfFiles.length; i++) {
				// for (int i = 0; i < 1; i++) {
					if (listOfFiles[i].isFile()) {
						File baseFile = new File(baseFileName);
						if (baseFile.exists()) {
							String tempString = basePath + "\\" + baseName + "_" + i + ".csv";
							File tempFile = new File(tempString);
							baseFile.renameTo(tempFile);
							baseFile.deleteOnExit();
						}

						System.out.println(listOfFiles[i].getPath());
						listOfFiles[i].renameTo(baseFile);
						System.out.println(baseFile.getPath());
//						
						String tempBase = "C:\\Users\\Amrit\\Documents\\1\\thesis\\";
						String mapFile = tempBase + "map.ttl";
						String targetTBoxFile = tempBase + "bd_tbox.ttl";
						String targetABoxFile = tempBase + "files\\" + baseName + ".ttl";
						String provFile = tempBase + "prov.ttl";
						
//						CSVABox csvaBox = new CSVABox();
//						String resultString = csvaBox.parseCSV(baseFileName,
//								"http://linked-statistics-bd.org/2011/data",
//								"Space ( )",
//								"Expression",
//								"CONCAT(admUnitFiveId,residence,electricityConnection)",
//								"TTL",
//								"C:\\Users\\Amrit\\Documents\\1\\thesis\\files\\" + baseName + ".ttl");
						
						FactEntryGeneration factEntryGeneration = new FactEntryGeneration();
						String resultString = factEntryGeneration.generateFactEntryFromCSV(baseFileName, mapFile, targetTBoxFile, targetABoxFile, "Space ( )");
						
//						InstanceGenerator instanceGenerator = new InstanceGenerator();
//						String resultString = instanceGenerator.generateInstanceEntry(baseFileName, "Space ( )", mapFile, provFile, targetTBoxFile, targetABoxFile);
//						
						System.out.println(resultString);
					}
				}

				System.out.println("Finish");
//				playSound();
//				new Methods().showDialog("Complete");
			}
		});
	}

	public static synchronized void playSound() {
		try {
			File file = new File("C:\\Users\\Amrit\\Downloads\\alert_mesage.wav");
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
