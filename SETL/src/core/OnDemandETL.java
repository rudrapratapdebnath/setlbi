package core;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JList;

import org.apache.jena.base.Sys;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.sparql.resultset.RDFOutput;
import org.apache.jena.vocabulary.RDF;

import controller.Definition;
import helper.Methods;
import model.ConceptTransform;
import model.MapperTransform;

public class OnDemandETL {
	public Definition definition;
	public PrefixExtraction prefixExtraction;
	private LinkedHashMap<Integer, ConceptTransform> conceptMap;
	private JList levelList;
	private JList datasetList;

	Methods methods;
	public String datasetString = "";

	String targetABoxString = "";
	String targetTBoxString = "";
	String mapString = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String sparqlQueryString =
//				  "PREFIX qb: <http://purl.org/linked-data/cube#>\r\n" + 
//				  "PREFIX qb4o: <http://purl.org/qb4olap/cubes#>\r\n" + 
//				  "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + 
//				  "SELECT ?admGeographyDim_administrativeUnitName ?residenceDim_residenceType ?housingTenancyDim_housingTenancyAll (SUM(<http://www.w3.org/2001/XMLSchema#long>(?m1)) as ?numberOfHousehold_sum) \r\n" + 
//				  "WHERE {\r\n" + 
//				  "?o a qb:Observation .\r\n" + 
//				  "?o qb:dataSet <http://linked-statistics-bd.org/2011/data#HouseholdByAdm5ResHousingTenancy> .\r\n" + 
//				  "?o <http://linked-statistics-bd.org/2011/mdProperty#numberOfHousehold> ?m1 .\r\n" + 
//				  "?o <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> ?admGeographyDim_admUnitFive .\r\n" + 
//				  "?admGeographyDim_admUnitFive qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> .\r\n" + 
//				  "?admGeographyDim_admUnitFive <http://linked-statistics-bd.org/2011/mdAttribute#inAdmFour> ?admGeographyDim_admUnitFour .\r\n" + 
//				  "?admGeographyDim_admUnitFour qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFour> .\r\n" + 
//				  "?admGeographyDim_admUnitFour <http://linked-statistics-bd.org/2011/mdAttribute#administrativeUnitName> ?admGeographyDim_administrativeUnitName .\r\n" + 
//				  "?o <http://linked-statistics-bd.org/2011/mdProperty#residence> ?residenceDim_residence .\r\n" + 
//				  "?residenceDim_residence qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#residence> .\r\n" + 
//				  "?residenceDim_residence <http://linked-statistics-bd.org/2011/mdAttribute#inResidenceType> ?residenceDim_residenceType .\r\n" + 
//				  "?residenceDim_residenceType qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#residenceType> .\r\n" + 
//				  "?o <http://linked-statistics-bd.org/2011/mdProperty#housingTenancy> ?housingTenancyDim_housingTenancy .\r\n" + 
//				  "?housingTenancyDim_housingTenancy qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#housingTenancy> .\r\n" + 
//				  "?housingTenancyDim_housingTenancy <http://linked-statistics-bd.org/2011/mdAttribute#inHousingTenancyAll> ?housingTenancyDim_housingTenancyAll .\r\n" + 
//				  "?housingTenancyDim_housingTenancyAll qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#housingTenancyAll> .\r\n" + 
//				  "}\r\n" + 
//				  "GROUP BY ?admGeographyDim_administrativeUnitName ?residenceDim_residenceType ?housingTenancyDim_housingTenancyAll\r\n" + 
//				  "ORDER BY ?admGeographyDim_administrativeUnitName ?residenceDim_residenceType ?housingTenancyDim_housingTenancyAll";

		String sparqlQueryString = "SELECT * WHERE {" + "?s a <http://example.com/sdw/Company>."
				+ "?x <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://extbi.lab.aau.dk/ontology/sdw/Accomodation>."
				+ "?y <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://extbi.lab.aau.dk/ontology/sdw/Belief>."
				+ "?s ?p ?o." + "} LIMIT 5";

		String basePath = "C:\\Users\\Amrit\\Documents\\1\\New_ODE_ETL\\";

		String targetABoxString = basePath + "target_abox.ttl";
		String targetTBoxString = basePath + "bd_tbox.ttl";
		String mapString = basePath + "map_just_to_check.ttl";

		OnDemandETL demandETL = new OnDemandETL(mapString, targetTBoxString, targetABoxString);
		String result = demandETL.performOnDemandETL(sparqlQueryString, mapString, targetTBoxString, targetABoxString);
		System.out.println(result);
	}

	public OnDemandETL(String mapFile, String targetTBoxFile, String targetABoxString) {
		// TODO Auto-generated constructor stub
		definition = new Definition();
		prefixExtraction = new PrefixExtraction();
		conceptMap = new LinkedHashMap<Integer, ConceptTransform>();

		definition.setMapPath(mapFile);
		prefixExtraction.extractPrefix(mapFile);

		definition.setTboxPath(targetTBoxFile);
		prefixExtraction.extractPrefix(targetTBoxFile);
	}

	private String performOnDemandETL(String sparqlString, String mapString, String targetTBoxString,
			String targetABoxString) {
		// TODO Auto-generated method stub
//		methods = new Methods();
//		Model targetABoxModel = methods.readModelFromPath(targetABoxString);
//		
//		ArrayList<String> queryConceptList = extractConcepts(sparqlString);
//		
//		System.out.println("*** Concepts ***");
//		for (String string : queryConceptList) {
//			System.out.println(string);
//		}
//		
//		ArrayList<String> requiredConceptList = checkRequiredConcepts(targetABoxModel, queryConceptList);
//		
//		System.out.println("*** Concepts ***");
//		for (String string : requiredConceptList) {
//			System.out.println(string);
//		}
//		
//		LinkedHashMap<String, ArrayList<ConceptTransform>> dependencyMap = definition.extractDependency(requiredConceptList, prefixExtraction);
//		
//		System.out.println("*** HashMap ***");
//		for (String string : dependencyMap.keySet()) {
//			System.out.println("String: " + string);
//			
//			ArrayList<ConceptTransform> conceptList = dependencyMap.get(string);
//			System.out.println("ArrayList Size: " + conceptList.size());
//			
//			ConceptTransform conceptTransform1 = conceptList.get(conceptList.size() - 1);
//			conceptTransform1.setTargetFileLocation("concept.ttl");
//			
//			for (ConceptTransform conceptTransform : conceptList) {
//				System.out.println("Concept: " + conceptTransform.getOperationName());
//				definition.performOperation(conceptTransform);
//			}
//		}
//		
//		Model conceptModel = methods.readModelFromPath("concept.ttl");
//		targetABoxModel.add(conceptModel);
//		
//		
//		
//		ResultSet finalResultSet = Methods.executeQuery(targetABoxModel, sparqlString);
//		Methods.print(finalResultSet);

		extractKeywords(sparqlString);

		return "Done";
	}

	public void mergeAllFiles(String filePath, int numOfLevelFiles, boolean b) {
		// TODO Auto-generated method stub
		Methods methods = new Methods();
		methods.createNewFile(filePath);

		for (int i = 1; i < numOfLevelFiles; i++) {
			String fileNameString = "level" + i + ".ttl";

			Model model = methods.readModelFromPath(fileNameString);
			String textString = methods.modelToString(model, "ttl");

			methods.appendToFile(textString, filePath);

			new File(fileNameString).delete();
		}
	}

	public void mergeAllLevelFiles(String filePath, int numOfLevelFiles, boolean b) {
		// TODO Auto-generated method stub
		Methods methods = new Methods();
		methods.createNewFile(filePath);

		for (int i = 1; i < numOfLevelFiles; i++) {
			String fileNameString = "level" + i + ".ttl";

			if (methods.checkFile(fileNameString)) {
				Model model = methods.readModelFromPath(fileNameString);
				String textString = methods.modelToString(model, "ttl");

				methods.appendToFile(textString, filePath);

				new File(fileNameString).delete();
			}
		}
	}

	public void generateLevelData(String datasetString, Model mapModel, Model targetTBoxModel,
			LinkedHashMap<String, String> prefixMap, ArrayList<String> requiredPropertyArrayList, int numOfLevelFiles) {
		// TODO Auto-generated method stub
		Model targetModel = ModelFactory.createDefaultModel();
		Methods methods = new Methods();

		/*
		 * System.out.println("Level: " + datasetString);
		 * 
		 * System.out.println("Required Properties:");
		 * methods.print(requiredPropertyArrayList);
		 */

		String sparql = "\nPREFIX map: <http://www.map.org/example#>\n" + "SELECT * WHERE {"
				+ "?concept a map:ConceptMapper." + "?concept map:targetConcept " + datasetString + "."
				+ "?concept map:sourceConcept ?sourcetype." + "?concept map:iriValue ?iri."
				+ "?concept map:iriValueType ?iritype." + "?concept map:sourceABoxLocation ?location."
				+ "?mapper a map:PropertyMapper." + "?mapper map:ConceptMapper ?concept."
				+ "?mapper map:sourceProperty ?source." + "?mapper map:sourcePropertyType ?propertytype."
				+ "?mapper map:targetProperty ?target." + "}";

		ResultSet resultSet = Methods.executeQuery(mapModel, sparql);
		// Methods.print(resultSet);

		String targetTypeString = datasetString.substring(1, datasetString.length() - 1);
		LinkedHashMap<String, ConceptTransform> conceptMap = new LinkedHashMap<String, ConceptTransform>();

		int resultCount = 0;
		while (resultSet.hasNext()) {
			resultCount++;
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String conceptString = querySolution.get("concept").toString();
			String sourceTypeString = querySolution.get("sourcetype").toString();
			String iriValueString = querySolution.get("iri").toString();
			String iriValueTypeString = querySolution.get("iritype").toString();
			String locationString = querySolution.get("location").toString();

			String mapperString = querySolution.get("mapper").toString();
			String targetPropertyString = querySolution.get("target").toString();
			String sourcePropertyString = querySolution.get("source").toString();
			String sourcePropertyTypeString = querySolution.get("propertytype").toString();

			MapperTransform mapperTransform = new MapperTransform(sourcePropertyString, sourcePropertyTypeString,
					targetPropertyString);

			if (conceptMap.containsKey(conceptString)) {
				ConceptTransform conceptTransform = conceptMap.get(conceptString);
				conceptTransform.getMapperTransformMap().put(mapperString, mapperTransform);
				conceptMap.replace(conceptString, conceptTransform);
			} else {
				ConceptTransform conceptTransform = new ConceptTransform();
				conceptTransform.setConcept(conceptString);
				conceptTransform.setTargetType(targetTypeString);
				conceptTransform.setSourceType(sourceTypeString);
				conceptTransform.setIriValue(iriValueString);
				conceptTransform.setIriValueType(iriValueTypeString);
				conceptTransform.setSourceABoxLocationString(locationString);
				conceptTransform.getMapperTransformMap().put(mapperString, mapperTransform);
				conceptMap.put(conceptString, conceptTransform);
			}
		}

		if (resultCount == 0) {
			System.out.println("No map data for: " + datasetString);
		}

		int count = 0, numOfFiles = 1;
		for (String conceptString : conceptMap.keySet()) {
			ConceptTransform conceptTransform = conceptMap.get(conceptString);
			String typeString = Methods.bracketString(conceptTransform.getSourceType());

			String sparqlString = "SELECT * WHERE {" + "?s a " + typeString + "." + "?s ?p ?o.}";

			Model sourceABoxModel = methods.readModelFromPath(conceptTransform.getSourceABoxLocationString());
			ResultSet set = Methods.executeQuery(sourceABoxModel, sparqlString);

			String currentSubjectString = "";
			LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();

			Model provModel = ModelFactory.createDefaultModel();

			while (set.hasNext()) {
				count++;

				QuerySolution querySolution = (QuerySolution) set.next();
				String resourceString = querySolution.get("s").toString();
				String predicateString = querySolution.get("p").toString();
				RDFNode object = querySolution.get("o");

				// System.out.println(predicateString);

				predicateString = Methods.assignPrefix(prefixMap, predicateString);

				if (currentSubjectString.equals(resourceString)) {
					valueMap.put(predicateString, methods.getRDFNodeValue(object));
				} else {
					if (currentSubjectString.equals("")) {
						currentSubjectString = resourceString;
						valueMap.put(predicateString, methods.getRDFNodeValue(object));
					} else {

						IRIGenerator generator = new IRIGenerator();

						String iriValue = generator.getIRIValue(conceptTransform.getIriValueType(),
								methods.assignPrefix(prefixMap, conceptTransform.getIriValue()), mapModel, valueMap,
								provModel, prefixMap);
						String provIRI = "";

						String rangeValue = generator.getRangeValue(targetTypeString, targetTBoxModel);

						iriValue = iriValue.replaceAll("\\s+", "_").toLowerCase();
						if (rangeValue == null) {
							provIRI = targetTypeString + "#" + iriValue;
						} else {
							provIRI = rangeValue + "#" + iriValue;
						}

						Resource resource = targetModel.createResource(provIRI);
						resource.addProperty(targetModel.createProperty("http://purl.org/qb4olap/cubes#memberOf"),
								targetModel.createResource(targetTypeString));

						resource.addProperty(RDF.type,
								ResourceFactory.createResource("http://purl.org/qb4olap/cubes#LevelMember"));

						for (String mapperString : conceptTransform.getMapperTransformMap().keySet()) {
							MapperTransform mapperTransform = conceptTransform.getMapperTransformMap()
									.get(mapperString);

							String bracketPropertyString = Methods.bracketString(mapperTransform.getTargetProperty());
							// System.out.println("Bracket: " + bracketPropertyString);

							if (requiredPropertyArrayList.contains(bracketPropertyString)) {
								Property property = targetModel.createProperty(mapperTransform.getTargetProperty());

								// System.out.println("Found: " + mapperTransform.getTargetProperty());

								String propertyType = mapperTransform.getSourcePropertyType();

								Object valueObject = null;

								if (propertyType.contains("Expression")) {
									EquationHandler equationHandler = new EquationHandler();
									valueObject = equationHandler.handleExpression(mapperTransform.getSourceProperty(),
											valueMap);
								} else {
									valueObject = valueMap
											.get(Methods.assignPrefix(prefixMap, mapperTransform.getSourceProperty()));
								}

								String rangeValueTarget = generator.getRangeValue(mapperTransform.getTargetProperty(),
										targetTBoxModel);

								/*
								 * System.out.println("Target Prop: " + mapperTransform.getTargetProperty());
								 * System.out.println("Range: " + rangeValueTarget);
								 */

								if (valueObject != null) {
									if (rangeValueTarget.contains("http://www.w3.org/2001/XMLSchema#")) {
										Literal literal = targetModel.createTypedLiteral(valueObject);
										resource.addLiteral(property, literal);
									} else {
										valueObject = valueObject.toString().replaceAll("\\s+", "_").toLowerCase();
										String propertyValueIRI = rangeValueTarget + "#" + valueObject;
										resource.addProperty(property, targetModel.createResource(propertyValueIRI));
									}
								} else {
									System.out.println("Value Object null");
								}
							}
						}

						currentSubjectString = resourceString;
						valueMap = new LinkedHashMap<String, Object>();
						valueMap.put(predicateString, methods.getRDFNodeValue(object));
					}
				}

				if (count % 100000 == 0) {
					String tempPath = numOfFiles + ".ttl";
					methods.saveModel(targetModel, tempPath);

					targetModel = ModelFactory.createDefaultModel();
					numOfFiles++;
				}
			}

			if (count == 0) {
				System.out.println("No abox data for: " + datasetString);
			}

			if (!currentSubjectString.equals("")) {
				IRIGenerator generator = new IRIGenerator();

				String iriValue = generator.getIRIValue(conceptTransform.getIriValueType(),
						methods.assignPrefix(prefixMap, conceptTransform.getIriValue()), mapModel, valueMap, provModel,
						prefixMap);
				String provIRI = "";

				String rangeValue = generator.getRangeValue(targetTypeString, targetTBoxModel);

				iriValue = iriValue.replaceAll("\\s+", "_").toLowerCase();
				if (rangeValue == null) {
					provIRI = targetTypeString + "#" + iriValue;
				} else {
					provIRI = rangeValue + "#" + iriValue;
				}

				Resource resource = targetModel.createResource(provIRI);
				Property property2 = targetModel.createProperty("http://purl.org/qb4olap/cubes#memberOf");
				resource.addProperty(property2, targetModel.createResource(targetTypeString));

				resource.addProperty(RDF.type,
						ResourceFactory.createResource("http://purl.org/qb4olap/cubes#LevelMember"));

				for (String mapperString : conceptTransform.getMapperTransformMap().keySet()) {
					MapperTransform mapperTransform = conceptTransform.getMapperTransformMap().get(mapperString);

					if (requiredPropertyArrayList
							.contains(Methods.bracketString(mapperTransform.getTargetProperty()))) {
						Property property = targetModel.createProperty(mapperTransform.getTargetProperty());

						String propertyType = mapperTransform.getSourcePropertyType();

						Object valueObject = null;

						if (propertyType.contains("Expression")) {
							EquationHandler equationHandler = new EquationHandler();
							valueObject = equationHandler.handleExpression(mapperTransform.getSourceProperty(),
									valueMap);
						} else {
							valueObject = valueMap
									.get(Methods.assignPrefix(prefixMap, mapperTransform.getSourceProperty()));
						}

						String rangeValueTarget = generator.getRangeValue(mapperTransform.getTargetProperty(),
								targetTBoxModel);

						// System.out.println("Target Property: " +
						// mapperTransform.getTargetProperty());
						// System.out.println("Range: " + rangeValueTarget);

						if (valueObject != null) {
							if (rangeValueTarget.contains("http://www.w3.org/2001/XMLSchema#")) {
								Literal literal = targetModel.createTypedLiteral(valueObject);
								resource.addLiteral(property, literal);
							} else {
								valueObject = valueObject.toString().replaceAll("\\s+", "_").toLowerCase();
								String propertyValueIRI = rangeValueTarget + "#" + valueObject;
								resource.addProperty(property, targetModel.createResource(propertyValueIRI));
							}
						} else {
							System.out.println("Value Object null");
						}
					}
				}
			}

			if (targetModel.size() > 0) {
				String tempPath = numOfFiles + ".ttl";
				methods.saveModel(targetModel, tempPath);

				targetModel = ModelFactory.createDefaultModel();
				numOfFiles++;
			}
		}

		mergeAllFiles("level" + numOfLevelFiles + ".ttl", numOfFiles);
	}

	public void generateFactData(String datasetString, Model mapModel, Model targetTBoxModel,
			LinkedHashMap<String, String> prefixMap, ArrayList<String> requiredFactArrayList) {
		// TODO Auto-generated method stub

		Model targetModel = ModelFactory.createDefaultModel();
		Methods methods = new Methods();

		String sparql = "\nPREFIX map: <http://www.map.org/example#>\n" + "SELECT * WHERE {"
				+ "?concept a map:ConceptMapper." + "?concept map:targetConcept " + datasetString + "."
				+ "?concept map:sourceConcept ?sourcetype." + "?concept map:iriValue ?iri."
				+ "?concept map:iriValueType ?iritype." + "?concept map:sourceABoxLocation ?location."
				+ "?mapper a map:PropertyMapper." + "?mapper map:ConceptMapper ?concept."
				+ "?mapper map:sourceProperty ?source." + "?mapper map:sourcePropertyType ?propertytype."
				+ "?mapper map:targetProperty ?target." + "}";

		ResultSet resultSet = Methods.executeQuery(mapModel, sparql);
		// Methods.print(resultSet);

		String targetTypeString = datasetString.substring(1, datasetString.length() - 1);
		LinkedHashMap<String, ConceptTransform> conceptMap = new LinkedHashMap<String, ConceptTransform>();

		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String conceptString = querySolution.get("concept").toString();
			String sourceTypeString = querySolution.get("sourcetype").toString();
			String iriValueString = querySolution.get("iri").toString();
			String iriValueTypeString = querySolution.get("iritype").toString();
			String locationString = querySolution.get("location").toString();

			String mapperString = querySolution.get("mapper").toString();
			String targetPropertyString = querySolution.get("target").toString();
			String sourcePropertyString = querySolution.get("source").toString();
			String sourcePropertyTypeString = querySolution.get("propertytype").toString();

			MapperTransform mapperTransform = new MapperTransform(sourcePropertyString, sourcePropertyTypeString,
					targetPropertyString);

			if (conceptMap.containsKey(conceptString)) {
				ConceptTransform conceptTransform = conceptMap.get(conceptString);
				conceptTransform.getMapperTransformMap().put(mapperString, mapperTransform);
				conceptMap.replace(conceptString, conceptTransform);
			} else {
				ConceptTransform conceptTransform = new ConceptTransform();
				conceptTransform.setConcept(conceptString);
				conceptTransform.setTargetType(targetTypeString);
				conceptTransform.setSourceType(sourceTypeString);
				conceptTransform.setIriValue(iriValueString);
				conceptTransform.setIriValueType(iriValueTypeString);
				conceptTransform.setSourceABoxLocationString(locationString);
				conceptTransform.getMapperTransformMap().put(mapperString, mapperTransform);
				conceptMap.put(conceptString, conceptTransform);
			}
		}

		int count = 0, numOfFiles = 1;
		for (String conceptString : conceptMap.keySet()) {
			ConceptTransform conceptTransform = conceptMap.get(conceptString);
			String typeString = Methods.bracketString(conceptTransform.getSourceType());

			String sparqlString = "SELECT * WHERE {" + "?s a " + typeString + "." + "?s ?p ?o.}";

			Model sourceABoxModel = methods.readModelFromPath(conceptTransform.getSourceABoxLocationString());
			ResultSet set = Methods.executeQuery(sourceABoxModel, sparqlString);
			// Methods.print(set);

			String currentSubjectString = "";
			LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();

			Model provModel = ModelFactory.createDefaultModel();

			while (set.hasNext()) {
				count++;

				QuerySolution querySolution = (QuerySolution) set.next();
				String resourceString = querySolution.get("s").toString();
				String predicateString = querySolution.get("p").toString();
				RDFNode object = querySolution.get("o");

				predicateString = Methods.assignPrefix(prefixMap, predicateString);

				if (currentSubjectString.equals(resourceString)) {
					valueMap.put(predicateString, methods.getRDFNodeValue(object));
				} else {
					if (currentSubjectString.equals("")) {
						currentSubjectString = resourceString;
						valueMap.put(predicateString, methods.getRDFNodeValue(object));
					} else {
						IRIGenerator generator = new IRIGenerator();

						String iriValue = generator.getIRIValue(conceptTransform.getIriValueType(),
								conceptTransform.getIriValue(), mapModel, valueMap, provModel, prefixMap);
						String provIRI = "";

						String rangeValue = generator.getRangeValue(targetTypeString, targetTBoxModel);

						if (rangeValue == null) {
							provIRI = targetTypeString + "#" + iriValue;
						} else {
							provIRI = rangeValue + "#" + iriValue;
						}

						boolean isAdded = false;
						Resource resource = null;
						for (String mapperString : conceptTransform.getMapperTransformMap().keySet()) {
							MapperTransform mapperTransform = conceptTransform.getMapperTransformMap()
									.get(mapperString);

							if (requiredFactArrayList.contains(mapperTransform.getTargetProperty())) {
								Property property = targetModel.createProperty(mapperTransform.getTargetProperty());

								String propertyType = mapperTransform.getSourcePropertyType();

								Object valueObject = null;

								if (propertyType.contains("Expression")) {
									EquationHandler equationHandler = new EquationHandler();
									valueObject = equationHandler.handleExpression(mapperTransform.getSourceProperty(),
											valueMap);
								} else {
									valueObject = valueMap
											.get(Methods.assignPrefix(prefixMap, mapperTransform.getSourceProperty()));
								}

								String rangeValueTarget = generator.getRangeValue(mapperTransform.getTargetProperty(),
										targetTBoxModel);

								if (valueObject != null) {
									if (!isAdded) {
										resource = targetModel.createResource(provIRI);

										Property property2 = targetModel
												.createProperty("http://purl.org/linked-data/cube#dataSet");
										resource.addProperty(property2, targetModel.createResource(targetTypeString));

										resource.addProperty(RDF.type, ResourceFactory
												.createResource("http://purl.org/linked-data/cube#Observation"));

										isAdded = true;
									}

									if (rangeValueTarget.contains("http://www.w3.org/2001/XMLSchema#")) {
										Literal literal = targetModel.createTypedLiteral(valueObject);
										resource.addLiteral(property, literal);
									} else {
										valueObject = valueObject.toString().replaceAll("\\s+", "_").toLowerCase();
										String propertyValueIRI = rangeValueTarget + "#" + valueObject;
										resource.addProperty(property, targetModel.createResource(propertyValueIRI));
									}
								} else {
									System.out.println("Value Object null");
								}
							}
						}

						currentSubjectString = resourceString;
						valueMap = new LinkedHashMap<String, Object>();
						valueMap.put(predicateString, methods.getRDFNodeValue(object));
					}
				}

				if (count % 100000 == 0) {
					String tempPath = numOfFiles + ".ttl";
					methods.saveModel(targetModel, tempPath);

					targetModel = ModelFactory.createDefaultModel();
					numOfFiles++;
				}
			}

			if (!currentSubjectString.equals("")) {
				IRIGenerator generator = new IRIGenerator();

				String iriValue = generator.getIRIValue(conceptTransform.getIriValueType(),
						conceptTransform.getIriValue(), mapModel, valueMap, provModel, prefixMap);
				String provIRI = "";

				String rangeValue = generator.getRangeValue(targetTypeString, targetTBoxModel);

				if (rangeValue == null) {
					provIRI = targetTypeString + "#" + iriValue;
				} else {
					provIRI = rangeValue + "#" + iriValue;
				}

				for (String mapperString : conceptTransform.getMapperTransformMap().keySet()) {
					MapperTransform mapperTransform = conceptTransform.getMapperTransformMap().get(mapperString);

					if (requiredFactArrayList.contains(mapperTransform.getTargetProperty())) {
						Property property = targetModel.createProperty(mapperTransform.getTargetProperty());

						String propertyType = mapperTransform.getSourcePropertyType();

						Object valueObject = null;

						if (propertyType.contains("Expression")) {
							EquationHandler equationHandler = new EquationHandler();
							valueObject = equationHandler.handleExpression(mapperTransform.getSourceProperty(),
									valueMap);
						} else {
							valueObject = valueMap
									.get(Methods.assignPrefix(prefixMap, mapperTransform.getSourceProperty()));
						}

						String rangeValueTarget = generator.getRangeValue(mapperTransform.getTargetProperty(),
								targetTBoxModel);

						if (valueObject != null) {
							Resource resource = targetModel.createResource(provIRI);

							Property property2 = targetModel.createProperty("http://purl.org/linked-data/cube#dataSet");
							resource.addProperty(property2, targetModel.createResource(targetTypeString));

							resource.addProperty(RDF.type,
									ResourceFactory.createResource("http://purl.org/linked-data/cube#Observation"));

							if (rangeValueTarget.contains("http://www.w3.org/2001/XMLSchema#")) {
								Literal literal = targetModel.createTypedLiteral(valueObject);
								resource.addLiteral(property, literal);
							} else {
								valueObject = valueObject.toString().replaceAll("\\s+", "_").toLowerCase();
								String propertyValueIRI = rangeValueTarget + "#" + valueObject;
								resource.addProperty(property, targetModel.createResource(propertyValueIRI));
							}
						} else {
							System.out.println("Value Object null");
						}
					}
				}
			}

			if (targetModel.size() > 0) {
				String tempPath = numOfFiles + ".ttl";
				methods.saveModel(targetModel, tempPath);

				targetModel = ModelFactory.createDefaultModel();
				numOfFiles++;
			}
		}

		methods.createNewFile("fact.ttl");

		mergeAllFiles("fact.ttl", numOfFiles);
	}

	private void mergeAllFiles(String filePath, int numOfFiles) {
		// TODO Auto-generated method stub
		Methods methods = new Methods();
		methods.createNewFile(filePath);

		for (int i = 1; i < numOfFiles; i++) {
			String fileNameString = i + ".ttl";

			Model model = methods.readModelFromPath(fileNameString);
			String textString = methods.modelToString(model, "ttl");

			methods.appendToFile(textString, filePath);

			new File(fileNameString).delete();
		}
	}

	public ArrayList<String> checkRequiredFacts(Model targetABoxModel, ArrayList<String> queryFactArrayList) {
		// TODO Auto-generated method stub
		ArrayList<String> requiredArrayList = new ArrayList<String>();

		for (String factString : queryFactArrayList) {
			String sparql = "SELECT * WHERE {" + "?s a <http://purl.org/linked-data/cube#Observation>." + " ?s "
					+ factString + " ?o.} LIMIT 1";

			ResultSet resultSet = Methods.executeQuery(targetABoxModel, sparql);
			// Methods.print(resultSet);

			int count = 0;
			while (resultSet.hasNext()) {
				QuerySolution querySolution = (QuerySolution) resultSet.next();
				String subjectString = querySolution.get("s").toString();

				count++;
			}

			if (count == 0) {
				// System.out.println(factString);
				requiredArrayList.add(factString.substring(1, factString.length() - 1));
			}
		}

		return requiredArrayList;
	}

	public LinkedHashMap<String, ArrayList<String>> checkRequiredLevels(Model targetABoxModel,
			LinkedHashMap<String, ArrayList<String>> queryLevelsArrayList) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, ArrayList<String>> requiredArrayList = new LinkedHashMap();

		for (String levelString : queryLevelsArrayList.keySet()) {
			String sparql = "SELECT DISTINCT ?s WHERE {" + "?s <http://purl.org/qb4olap/cubes#memberOf> <" + levelString
					+ ">." + " ?s ?p ?o.}  LIMIT 1";

			ResultSet resultSet = Methods.executeQuery(targetABoxModel, sparql);
			// Methods.print(resultSet);

			int count = 0;
			while (resultSet.hasNext()) {
				QuerySolution querySolution = (QuerySolution) resultSet.next();
				String subjectString = querySolution.get("s").toString();

				count++;
			}

			if (count == 0) {
				ArrayList<String> arrayList = queryLevelsArrayList.get(levelString);
				requiredArrayList.put(levelString, arrayList);
			} else {
				ArrayList<String> propertyList = queryLevelsArrayList.get(levelString);

				String sparqlSecond = "SELECT DISTINCT ?p WHERE {" + "?s <http://purl.org/qb4olap/cubes#memberOf> <"
						+ levelString + ">." + " ?s ?p ?o.}";

				ResultSet resultSetSecond = Methods.executeQuery(targetABoxModel, sparqlSecond);
				// Methods.print(resultSet);

				while (resultSetSecond.hasNext()) {
					QuerySolution querySolution = (QuerySolution) resultSetSecond.next();
					String predicateString = querySolution.get("p").toString();

					if (propertyList.contains(Methods.bracketString(predicateString))) {
						propertyList.remove(Methods.bracketString(predicateString));
					}
				}

				if (propertyList.size() > 0) {
					requiredArrayList.put(levelString, propertyList);
				}
			}
		}

		return requiredArrayList;
	}

	public ArrayList<String> extractRequiredFacts(String sparqlQueryString, String observationString) {
		// TODO Auto-generated method stub
		ArrayList<String> propertyList = new ArrayList<String>();

		if (observationString != null) {
			String regEx = "(\\" + observationString + ")(\\s+)(\\S+)(\\s+)(\\S+)";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(sparqlQueryString);

			while (matcher.find()) {
				String propertyString = matcher.group(3).trim();

				// System.out.println(propertyString);

				if (propertyString.contains("http://www.w3.org/1999/02/22-rdf-syntax-ns#")
						|| propertyString.equals("a")) {
					continue;
				} else if (propertyString.contains("dataSet")) {
					datasetString = matcher.group(5).trim();
				} else {
					// System.out.println("added");
					propertyList.add(propertyString);
				}
			}
		}
		return propertyList;
	}

	public LinkedHashMap<String, ArrayList<String>> extractRequiredLevels(String sparqlQueryString) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, ArrayList<String>> requiredLevelsArrayList = new LinkedHashMap();

		String regEx = "(\\?\\S+)(\\s+)(qb4o:memberOf)(\\s+)(<)([^>]+)(>)";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(sparqlQueryString);

		while (matcher.find()) {
//			System.out.println(matcher.group(0));

			String firstMatchString = matcher.group(1).trim();
			String observationString = matcher.group(6).trim();

			ArrayList<String> queryRequiredProperties = extractRequiredProperties(firstMatchString, sparqlQueryString);
			requiredLevelsArrayList.put(observationString, queryRequiredProperties);

//			System.out.println("\nObservationString: " + observationString);
//			
//			for (String string : queryRequiredProperties) {
//				System.out.println("**** " + string + "\n");
//			}
		}

		return requiredLevelsArrayList;
	}

	public ArrayList<String> extractRequiredProperties(String firstMatchString, String sparqlQueryString) {
		// TODO Auto-generated method stub
		String matchString = firstMatchString.substring(1, firstMatchString.length());
		ArrayList<String> requiredLevelsArrayList = new ArrayList<String>();

		String regEx = "(\\?" + matchString + ")(\\s+)([^.]\\S+)(\\s+)(\\S+)";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(sparqlQueryString);

		while (matcher.find()) {

			String observationString = matcher.group(3).trim();
			if (!observationString.equals("qb4o:memberOf")) {
				requiredLevelsArrayList.add(observationString);
			}
		}

		return requiredLevelsArrayList;
	}

	public String extractObservation(String sparqlQueryString) {
		// TODO Auto-generated method stub
		// System.out.println(sparqlQueryString);
		String regEx = "(\\{)(\\s*)(\\S+)(\\s+)(a)(\\s+)(qb:Observation)";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(sparqlQueryString);

		while (matcher.find()) {
			String observationString = matcher.group(3).trim();
			return observationString;
		}
		return null;
	}

	public ArrayList<String> extractKeywords(String sparqlQueryString) {
		ArrayList<String> keywordList = new ArrayList<String>();

		String parts[] = sparqlQueryString.split("SELECT");
		String sparqlString = parts[1];

		String segments[] = sparqlString.split("WHERE");
		sparqlString = segments[0];

		if (!sparqlString.trim().equals("*")) {
			String regEx = "(\\?[^\\)\\s]+)";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(sparqlString);

			while (matcher.find()) {
				String observationString = matcher.group(0).trim();
				keywordList.add(observationString);
			}

			ArrayList<String> arrayList = new ArrayList<String>();

			String partsOne[] = sparqlString.split("as");
			for (int i = partsOne.length - 1; i > 0; i--) {
				String lastKeyString = keywordList.get(keywordList.size() - 1);
				keywordList.remove(keywordList.size() - 1);
				keywordList.remove(keywordList.size() - 1);
				arrayList.add(lastKeyString);
			}

			for (int i = keywordList.size() - 1; i >= 0; i--) {
				String lastKeyString = keywordList.get(i);
				arrayList.add(lastKeyString);
			}

			keywordList = new ArrayList<String>();

			for (int i = arrayList.size() - 1; i >= 0; i--) {
				String lastKeyString = arrayList.get(i);
				keywordList.add(lastKeyString);
			}
		} else {
			String regEx = "(\\?[a-zA-Z1-9])";
			Pattern pattern = Pattern.compile(regEx);
			Matcher matcher = pattern.matcher(sparqlQueryString);

			while (matcher.find()) {
				String observationString = matcher.group(0).trim();
				if (!keywordList.contains(observationString)) {
					keywordList.add(observationString);
				}
			}
		}

		return keywordList;
	}

	public ArrayList<String> extractConcepts(String sparqlQueryString) {
		// TODO Auto-generated method stub
		ArrayList<String> conceptList = new ArrayList<>();

		String regEx2 = "(\\?\\S+)(\\s+)(<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>)(\\s+)(<)([^>]+)(>)";
		Pattern pattern2 = Pattern.compile(regEx2);
		Matcher matcher2 = pattern2.matcher(sparqlQueryString);

		while (matcher2.find()) {
//			System.out.println(matcher2.group(0));
//			System.out.println(matcher2.group(6));

			String conceptString = matcher2.group(6);
			if (!conceptList.contains(conceptString)) {
				conceptList.add(conceptString);
			}
		}

		String regEx = "(\\?\\S+)(\\s+)(a)(\\s+)(<)([^>]+)(>)";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(sparqlQueryString);

		while (matcher.find()) {
//			System.out.println(matcher.group(0));
//			System.out.println(matcher.group(6));

			String conceptString = matcher.group(6);
			if (!conceptList.contains(conceptString)) {
				conceptList.add(conceptString);
			}
		}

		return conceptList;
	}

	public ArrayList<String> checkRequiredConcepts(Model targetABoxModel, ArrayList<String> queryConceptList) {
		// TODO Auto-generated method stub
		ArrayList<String> requiredArrayList = new ArrayList<String>();

		for (String conceptString : queryConceptList) {
			String sparql = "SELECT * WHERE {" + "?s a <" + conceptString + ">." + " ?s ?p ?o.} LIMIT 1";

			ResultSet resultSet = Methods.executeQuery(targetABoxModel, sparql);
//			Methods.print(resultSet);

			int count = 0;
			while (resultSet.hasNext()) {
				QuerySolution querySolution = (QuerySolution) resultSet.next();
				String subjectString = querySolution.get("s").toString();

				count++;
			}

			if (count == 0) {
				requiredArrayList.add(conceptString);
			}
		}

		return requiredArrayList;
	}

	public LinkedHashMap<String, ArrayList<ConceptTransform>> extractDependency(ArrayList<String> requiredConceptList) {
		// TODO Auto-generated method stub
		return definition.extractDependency(requiredConceptList, prefixExtraction);
	}

	public String performOperation(ConceptTransform conceptTransform) {
		// TODO Auto-generated method stub
		return definition.performOperation(conceptTransform);
	}

	public void generateFactData2(String datasetString, String mapFile, String targetTBoxFile,
			LinkedHashMap<String, String> prefixMap, ArrayList<String> requiredFactArrayList) {
		// TODO Auto-generated method stub
		String sparql = "\nPREFIX map: <http://www.map.org/example#>\n" + "SELECT * WHERE {"
				+ "?concept a map:ConceptMapper." + "?concept map:targetConcept " + datasetString + "."
				+ "?concept map:sourceConcept ?sourcetype." + "?concept map:sourceABoxLocation ?location." + "}";

		Model mapModel = Methods.readModelFromPath(mapFile);
		ResultSet resultSet = Methods.executeQuery(mapModel, sparql);

		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String conceptString = querySolution.get("concept").toString();
			String sourceTypeString = querySolution.get("sourcetype").toString();
			String locationString = querySolution.get("location").toString();

			FactEntryGeneration entryGeneration = new FactEntryGeneration();

			if (locationString.contains(".csv")) {
				entryGeneration.generateFactEntryFromCSV(locationString, mapFile, targetTBoxFile, "fact.ttl",
						"Space ()");
			} else {
				entryGeneration.generateFactEntryFromRDF(locationString, mapFile, targetTBoxFile, "fact.ttl",
						"prov.ttl");
			}
		}
	}

	public void generateLevelData2(String bracketString, String mapFileString, String targetTBoxString,
			LinkedHashMap<String, String> prefixMap, int numOfLevelFiles) {
		// TODO Auto-generated method stub
		Model mapModel = Methods.readModelFromPath(mapFileString);

		String sparql = "\nPREFIX map: <http://www.map.org/example#>\n" + "SELECT * WHERE {"
				+ "?concept a map:ConceptMapper." + "?concept map:targetConcept " + datasetString + "."
				+ "?concept map:sourceConcept ?sourcetype." + "?concept map:sourceABoxLocation ?location." + "}";

		ResultSet resultSet = Methods.executeQuery(mapModel, sparql);

		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String conceptString = querySolution.get("concept").toString();
			String sourceTypeString = querySolution.get("sourcetype").toString();
			String locationString = querySolution.get("location").toString();

			LevelEntryGenerator entryGeneration = new LevelEntryGenerator();

			String targetABoxPath = "level" + numOfLevelFiles + ".ttl";
			if (locationString.contains(".csv")) {
				entryGeneration.generateLevelEntryFromCSV(locationString, mapFileString, targetTBoxString,
						targetABoxPath, "Space ()");
			} else {
				entryGeneration.generateLevelEntryFromRDF(locationString, mapFileString, targetTBoxString,
						targetABoxPath, "prov.ttl");
			}
		}
	}

	public static ArrayList<String> extractSameAsResources(Model targetModel) {
		// TODO Auto-generated method stub
		ArrayList<String> sameAsResources = new ArrayList<>();

		String sparql = "\nPREFIX map: <http://www.map.org/example#>\n"
				+ "\nPREFIX owl: <http://www.w3.org/2002/07/owl#>\n" + "SELECT * WHERE {"
				+ "?level owl:sameAs ?concept." + "}";

		ResultSet resultSet = Methods.executeQuery(targetModel, sparql);

		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String conceptString = querySolution.get("concept").toString();

			if (!sameAsResources.contains(conceptString)) {
				sameAsResources.add(conceptString);
			}
		}

		return sameAsResources;
	}

	public static void fetchModelFromDbpedia(String resource, String dbpediaPath) {
		// TODO Auto-generated method stub
		resource = Methods.bracketString(resource);

		String sparql = "PREFIX dbo:	<http://dbpedia.org/ontology/>\r\n"
				+ "SELECT * WHERE {\r\n" + resource
				+ " ?p ?x.\r\n"
				+ "FILTER (langMatches(lang(?x),\"en\"))\r\n"
				+ "}";
		
//		System.out.println(sparql);

		ParameterizedSparqlString qs = new ParameterizedSparqlString(sparql);
		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());
		ResultSet resultSet = ResultSetFactory.copyResults(exec.execSelect());
		
		StringBuilder stringBuilder = new StringBuilder("");
		while (resultSet.hasNext()) {
			QuerySolution querySolution = (QuerySolution) resultSet.next();
			String property = querySolution.get("p").toString();
			String value = querySolution.get("x").toString();
			
			if (Methods.containsWWW(value.toString())) {
				value = Methods.bracketString(value);
			} else {
				value = "\""+ Methods.encodeString(value) +"\"";
			}

			String line = resource + " " + Methods.bracketString(property) + " " + value + ".\n";
			stringBuilder.append(line);
		}
		
		Methods.appendToFile(stringBuilder.toString(), dbpediaPath);
	}
}
