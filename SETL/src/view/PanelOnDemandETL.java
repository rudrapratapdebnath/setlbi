package view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListModel;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import core.FactEntryGeneration;
import core.OnDemandETL;
import helper.Methods;
import model.ConceptTransform;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PanelOnDemandETL extends JPanel {
	private JTextPane textPaneStatus;
	private JList listFlow;
	private JDialog dialog;
	
	String basePath = "C:\\Users\\Amrit\\Documents\\1\\thesis\\";

	private String mapFileString = basePath + "map.ttl";
	private String targetTBoxString = basePath + "bd_tbox.ttl";;
	private String targetABoxString = basePath + "target_abox.ttl";
	private JTextArea textAreaQuery;

	private DefaultListModel flowModel;
	static String tempString = "C:\\Users\\Amrit\\Documents\\1\\thesis\\aboxes\\temp_on_demand_etl.ttl";
	
	String sparqlQueryString = "PREFIX qb: <http://purl.org/linked-data/cube#>\r\n" + 
			"PREFIX qb4o: <http://purl.org/qb4olap/cubes#>\r\n" + 
			"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + 
			"SELECT ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?abstract (SUM(<http://www.w3.org/2001/XMLSchema#long>(?m1)) as ?numberOfHousehold_count) \r\n" + 
			"WHERE {\r\n" + 
			"?o a qb:Observation .\r\n" + 
			"?o qb:dataSet <http://linked-statistics-bd.org/2011/data#HouseholdByAdm5ResidenceHouseholdSize> .\r\n" + 
			"?o <http://linked-statistics-bd.org/2011/mdProperty#numberOfHousehold> ?m1 .\r\n" + 
			"?o <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> ?admGeographyDim_admUnitFive .\r\n" + 
			"?admGeographyDim_admUnitFive qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> .\r\n" + 
			"?admGeographyDim_admUnitFive <http://linked-statistics-bd.org/2011/mdAttribute#inAdmFour> ?admGeographyDim_admUnitFour .\r\n" + 
			"?admGeographyDim_admUnitFour qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFour> .\r\n" + 
			"?admGeographyDim_admUnitFour <http://linked-statistics-bd.org/2011/mdAttribute#inAdmThree> ?admGeographyDim_admUnitThree .\r\n" + 
			"?admGeographyDim_admUnitThree qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitThree> .\r\n" + 
			"?admGeographyDim_admUnitThree <http://linked-statistics-bd.org/2011/mdAttribute#inAdmTwo> ?admGeographyDim_AdmUnitTwo .\r\n" + 
			"?admGeographyDim_AdmUnitTwo qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#AdmUnitTwo> .\r\n" + 
			"?admGeographyDim_AdmUnitTwo <http://linked-statistics-bd.org/2011/mdAttribute#inAdmOne> ?admGeographyDim_admUnitOne .\r\n" + 
			"?admGeographyDim_admUnitOne qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitOne> .\r\n" + 
			"?admGeographyDim_admUnitOne <http://linked-statistics-bd.org/2011/mdAttribute#administrativeUnitName> ?admUnitOne_administrativeUnitName .\r\n" + 
			"?admGeographyDim_admUnitOne <http://www.w3.org/2002/07/owl#sameAs> ?resource .\r\n" + 
			"?resource <http://dbpedia.org/ontology/abstract> ?abstract.\r\n" + 
			"?admGeographyDim_admUnitOne <http://linked-statistics-bd.org/2011/mdAttribute#administrativeUnitName> ?admGeographyDim_administrativeUnitName .\r\n" + 
			"?o <http://linked-statistics-bd.org/2011/mdProperty#householdSize> ?householdSizeDim_householdSize .\r\n" + 
			"?householdSizeDim_householdSize qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#householdSize> .\r\n" + 
			"?householdSizeDim_householdSize <http://linked-statistics-bd.org/2011/mdAttribute#householdSizeName> ?householdSizeDim_householdSizeName .\r\n" + 
			"FILTER (REGEX (?admUnitOne_administrativeUnitName, \"CHITTAGONG DIVISION\", \"i\"))}\r\n" + 
			"GROUP BY ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?abstract\r\n" + 
			"ORDER BY ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?abstract\r\n" + 
			"";

	/**
	 * Create the panel.
	 */
	public PanelOnDemandETL() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, BorderLayout.NORTH);

		JButton btnMappingFile = new JButton("Mapping File");
		btnMappingFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				
				/*
				 * Methods methods = new Methods(); String filePath =
				 * methods.chooseFile("Select Mapping File");
				 * 
				 * if (methods.checkString(filePath)) { mapFileString = filePath;
				 * 
				 * setStatus("Mapping file selected. Filepath: " + mapFileString); }
				 */
				 
				  setStatus("Mapping file selected. Filepath: " + mapFileString);
				 

			}
		});
		btnMappingFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonPanel.add(btnMappingFile);

		JButton btnTargetTbox = new JButton("Target TBox");
		btnTargetTbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				/*
				 * Methods methods = new Methods(); String filePath =
				 * methods.chooseFile("Select Target TBox File");
				 * 
				 * if (methods.checkString(filePath)) { targetTBoxString = filePath;
				 * 
				 * setStatus("Target TBox file selected. Filepath: " + targetTBoxString); }
				 */
				 		
				  
				  setStatus("Target TBox file selected. Filepath: " + targetTBoxString);
				 
			}
		});
		btnTargetTbox.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonPanel.add(btnTargetTbox);

		JButton btnTargetAbox = new JButton("Target ABox");
		btnTargetAbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				/*
				 * Methods methods = new Methods(); String filePath =
				 * methods.chooseFile("Select Target ABox File");
				 * 
				 * if (methods.checkString(filePath)) { targetABoxString = filePath;
				 * 
				 * setStatus("Target ABox file selected. Filepath: " + targetABoxString); }
				 */
				
				  setStatus("Target ABox file selected. Filepath: " + targetABoxString);
				 
			}
		});
		btnTargetAbox.setFont(new Font("Tahoma", Font.BOLD, 12));
		buttonPanel.add(btnTargetAbox);

		JPanel panelFirst = new JPanel();
		panelFirst.setBackground(Color.WHITE);
		add(panelFirst, BorderLayout.CENTER);
		panelFirst.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPaneFirst = new JSplitPane();
		splitPaneFirst.setResizeWeight(0.9);
		splitPaneFirst.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panelFirst.add(splitPaneFirst, "cell 0 0,grow");

		JPanel panelHolder = new JPanel();
		splitPaneFirst.setLeftComponent(panelHolder);
		panelHolder.setBackground(Color.WHITE);
		panelHolder.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JSplitPane splitPaneSecond = new JSplitPane();
		splitPaneSecond.setResizeWeight(0.4);
		panelHolder.add(splitPaneSecond, "cell 0 0,grow");

		JPanel panelQuery = new JPanel();
		splitPaneSecond.setLeftComponent(panelQuery);
		panelQuery.setBackground(Color.WHITE);
		panelQuery.setLayout(new MigLayout("", "[grow]", "[][grow][]"));

		JLabel lblQuery = new JLabel("Query:");
		lblQuery.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelQuery.add(lblQuery, "cell 0 0");

		JScrollPane scrollPaneQuery = new JScrollPane();
		panelQuery.add(scrollPaneQuery, "cell 0 1,grow");

		textAreaQuery = new JTextArea();
		textAreaQuery.setText(sparqlQueryString);
		textAreaQuery.setLineWrap(true);
		scrollPaneQuery.setViewportView(textAreaQuery);

		JButton btnPerformEtlAnd = new JButton("Perform ETL And Show Result");

		btnPerformEtlAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flowModel = new DefaultListModel<String>();

				String sparqlString = textAreaQuery.getText().toString().trim();
				Methods methods = new Methods();

				if (methods.checkString(mapFileString)) {
					if (methods.checkString(targetTBoxString)) {
						if (methods.checkString(targetABoxString)) {
							if (methods.checkString(sparqlString)) {
								dialog = methods.getProgressDialog(getParent());
								
								String dbpediaPath = "dbpedia.nt";
								String conceptPath = "concept.ttl";
								String factPath = "fact.ttl";
								String levelPath = "level.ttl";
								
								Methods.deleteAndCreateFile(dbpediaPath);
								Methods.deleteAndCreateFile(conceptPath);
								Methods.deleteAndCreateFile(factPath);
								Methods.deleteAndCreateFile(levelPath);
								
								EventQueue.invokeLater(new Runnable() {

									@Override
									public void run() { // TODO Auto-generated method stub Long
										Long totalDifference = 0L;

										OnDemandETL demandETL = new OnDemandETL(mapFileString, targetTBoxString, targetABoxString);

										Long startTimeLong = methods.getTime();
										
										Model targetABoxModel = methods.readModelFromPath(targetABoxString);
										Model targetModel = ModelFactory.createDefaultModel();
										targetModel.add(targetABoxModel);
										
										ArrayList<String> queryConceptList = demandETL.extractConcepts(sparqlString);
										
//										showInList(queryConceptList, "Extracted Concepts");
										
										ArrayList<String> requiredConceptList = demandETL.checkRequiredConcepts(targetModel, queryConceptList);
										
//										showInList(requiredConceptList, "Required Concepts");
										
										LinkedHashMap<String, ArrayList<ConceptTransform>> dependencyMap = demandETL.extractDependency(requiredConceptList);
										
//										System.out.println("*** HashMap ***");
										for (String string : dependencyMap.keySet()) {
//											System.out.println("String: " + string);
											
											ArrayList<ConceptTransform> conceptList = dependencyMap.get(string);
//											System.out.println("ArrayList Size: " + conceptList.size());
											
											ConceptTransform conceptTransform1 = conceptList.get(conceptList.size() - 1);
											conceptTransform1.setTargetFileLocation(conceptPath);
											
											for (ConceptTransform conceptTransform : conceptList) {
//												System.out.println("Concept: " + conceptTransform.getOperationName());
												demandETL.performOperation(conceptTransform);
											}
										}
										
										if (requiredConceptList.size() > 0) {
											Model conceptModel = methods.readModelFromPath(conceptPath);
											targetModel.add(conceptModel);
										}
										
										LinkedHashMap<String, ArrayList<String>> queryLevelsArrayList = demandETL
												.extractRequiredLevels(sparqlString);

//										showInList(queryLevelsArrayList, "Extracted Levels and Properties");

										String observationString = demandETL.extractObservation(sparqlString);

										if (observationString == null && queryConceptList.size() == 0) {
											showInList(null, "No observation. Check Query.");
											return;
										}

										ArrayList<String> queryFactArrayList = demandETL
												.extractRequiredFacts(sparqlString, observationString);

//										showInList(queryFactArrayList, "Extracted Facts");

										LinkedHashMap<String, ArrayList<String>> requiredLevelArrayList = demandETL
												.checkRequiredLevels(targetABoxModel, queryLevelsArrayList);

//										showInList(requiredLevelArrayList, "Required Levels and Properties");
										
										ArrayList<String> requiredFactArrayList = demandETL
												.checkRequiredFacts(targetABoxModel, queryFactArrayList);

//										showInList(requiredFactArrayList, "Required Facts");

										Model mapModel = methods.readModelFromPath(mapFileString);
										Model targetTBoxModel = methods.readModelFromPath(targetTBoxString);

										LinkedHashMap<String, String> prefixMap = Methods
												.extractPrefixes(mapFileString);
										
										if (!demandETL.datasetString.isEmpty()) {
											 demandETL.generateFactData2(demandETL.datasetString, mapFileString, targetTBoxString, prefixMap, requiredFactArrayList);

											int numOfLevelFiles = 1;
											for (String levelString : requiredLevelArrayList.keySet()) {
//												ArrayList<String> propertyList = requiredLevelArrayList.get(levelString);
//												demandETL.generateLevelData(Methods.bracketString(levelString), mapModel, targetTBoxModel, prefixMap,
//														propertyList, numOfLevelFiles);
												
												demandETL.generateLevelData2(Methods.bracketString(levelString), mapFileString, targetTBoxString, prefixMap, numOfLevelFiles);
												numOfLevelFiles++;
											}

									
											if (numOfLevelFiles > 1) {
												demandETL.mergeAllLevelFiles(levelPath, numOfLevelFiles, true);
											}

											Model factModel = methods.readModelFromPath(factPath);
											Model levelModel = methods.readModelFromPath(levelPath);
											
											targetModel.add(factModel);
											targetModel.add(levelModel);
										}
										
										if (sparqlString.contains("sameAs")) {
											ArrayList<String> sameAsResources = OnDemandETL.extractSameAsResources(targetModel);
											
											for (String resource : sameAsResources) {
												OnDemandETL.fetchModelFromDbpedia(resource, dbpediaPath);
											}
											
											Model dbpediaModel = Methods.readModelFromPath(dbpediaPath);
											targetModel.add(dbpediaModel);
										}

										ArrayList<String> selectedColumnsList = demandETL.extractKeywords(sparqlString);

										Long endTimeLong = methods.getTime();
										totalDifference += endTimeLong - startTimeLong;

										String timeStringOne = "Required Time for processing: "
												+ methods.getTimeInSeconds(totalDifference);
										setStatus(timeStringOne);

										setStatus("Query Start Time: " + methods.getCurrentTime());
										ResultSet resultSet = Methods.executeQuery(targetModel, sparqlString);
//										methods.printResultSet(resultSet);
										setStatus("Query End Time: " + methods.getCurrentTime());
										
										showInList(queryConceptList, "Extracted Concepts");
										showInList(requiredConceptList, "Required Concepts");
										showInList(queryLevelsArrayList, "Extracted Levels and Properties");
										showInList(queryFactArrayList, "Extracted Facts");
										showInList(requiredLevelArrayList, "Required Levels and Properties");
										showInList(requiredFactArrayList, "Required Facts");
										
										Methods.deleteAndCreateFile(tempString);
										Methods.saveModel(targetModel, tempString);

										Object[][] valueArray = methods.runSparqlQuery(targetModel, sparqlString,
												selectedColumnsList);

										dialog.dispose();

										JScrollPane scrollPaneTable = new JScrollPane();
										scrollPaneTable.setPreferredSize(new Dimension(1200, 600));

										JTable tableResult = new JTable(valueArray, selectedColumnsList.toArray());
										Font banglaFont = new Font("Arial Unicode MS", Font.BOLD,14);
										// tableResult.setFont(new Font("Tahoma", Font.PLAIN, 14));
										tableResult.setFont(banglaFont);
										tableResult.setBackground(Color.WHITE);
										scrollPaneTable.setViewportView(tableResult);

										JOptionPane.showMessageDialog(null, scrollPaneTable, "OLAP Result",
												JOptionPane.INFORMATION_MESSAGE);
									}
								});
							} else {
								setStatus("Check Query");
							}
						} else {
							setStatus("Check Target ABox Path");
						}
					} else {
						setStatus("Check Target TBox Path");
					}
				} else {
					setStatus("Check Mapping Path");
				}
			}
		});

		btnPerformEtlAnd.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelQuery.add(btnPerformEtlAnd, "cell 0 2,alignx center");

		JPanel panelFlow = new JPanel();
		splitPaneSecond.setRightComponent(panelFlow);
		panelFlow.setBackground(Color.WHITE);
		panelFlow.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollPaneFlow = new JScrollPane();
		panelFlow.add(scrollPaneFlow, "cell 0 0,grow");

		listFlow = new JList();
		scrollPaneFlow.setViewportView(listFlow);
		listFlow.setFont(new Font("Tahoma", Font.BOLD, 12));

		JScrollPane scrollPaneFirst = new JScrollPane();
		splitPaneFirst.setRightComponent(scrollPaneFirst);

		JPanel panelStatus = new JPanel();
		scrollPaneFirst.setViewportView(panelStatus);
		panelStatus.setBackground(Color.WHITE);
		panelStatus.setLayout(new MigLayout("", "[grow]", "[grow]"));

		textPaneStatus = new JTextPane();
		textPaneStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelStatus.add(textPaneStatus, "cell 0 0,grow");
	}

	protected void showInList(Object valueObject, String message) {
		// TODO Auto-generated method stub
		flowModel.addElement(message);

		if (valueObject != null) {
			if (valueObject instanceof LinkedHashMap) {
				LinkedHashMap<String, ArrayList<String>> hashMap = (LinkedHashMap<String, ArrayList<String>>) valueObject;

				for (String keyString : hashMap.keySet()) {
					ArrayList<String> list = hashMap.get(keyString);

					if (list.size() > 0) {
						for (String propertyString : list) {
							String listValueString = "Level: " + keyString + ", Property: " + propertyString;
							flowModel.addElement(listValueString);
						}
					} else {
						String listValueString = "Level: " + keyString;
						flowModel.addElement(listValueString);
					}
				}
			} else if (valueObject instanceof ArrayList) {
				ArrayList<String> arrayList = (ArrayList<String>) valueObject;
				
				if (message.contains("Required")) {
					if (message.toLowerCase().contains("fact")) {
						for (String factString : arrayList) {
							String listValueString = "Fact: " + factString;
							flowModel.addElement(listValueString);
						}
					} else {
						for (String factString : arrayList) {
							String listValueString = "Concept: " + factString;
							flowModel.addElement(listValueString);
						}
					}
				} else {
					if (message.toLowerCase().contains("fact")) {
						for (String factString : arrayList) {
							String listValueString = "Fact: " + factString.substring(1, factString.length() - 1);
							flowModel.addElement(listValueString);
						}
					} else {
						for (String factString : arrayList) {
							String listValueString = "Concept: " + factString;
							flowModel.addElement(listValueString);
						}
					}
				}
			}
		}

		listFlow.setModel(flowModel);
		listFlow.repaint();
		listFlow.revalidate();
	}

	protected void setStatus(String messageString) {
		// TODO Auto-generated method stub
		String string = textPaneStatus.getText().toString().trim();
		string += "\n" + messageString + "\n";
		textPaneStatus.setText(string);
	}
}
