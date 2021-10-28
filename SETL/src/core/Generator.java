package core;

import java.util.LinkedHashMap;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonObject;

import helper.CommonMethods;
import helper.Methods;
import helper.Variables;
import model.ConceptTransform;

public class Generator {
	// This is still temporary. Don't use it anywhere.
	
	private LinkedHashMap<String, String> keyAttributesMap;
	private PrefixExtraction prefixExtraction;
	private IRIGenerator iriGenerator;
	
	public Generator() {
		// TODO Auto-generated constructor stub
		prefixExtraction = new PrefixExtraction();
		iriGenerator = new IRIGenerator();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String basePath = "C:\\Users\\Amrit\\Documents\\1\\LevelEntry\\";
		
		String sourceABoxFile = basePath + "source_abox.ttl";
		String mappingFile = basePath + "map.ttl";
		String targetTBoxFile = basePath + "bd_tbox.ttl";
		String provGraphFile = basePath + "prov.ttl";
		String targetABoxFile = basePath + "new_level_entry_output.ttl";
		
		
		Generator generator = new Generator();
		String resultString = generator.generateLevelEntry(sourceABoxFile, mappingFile, targetTBoxFile, provGraphFile, targetABoxFile);
		
		System.out.println(CommonMethods.parseResult(resultString));
	}

	private String generateLevelEntry(String sourceABoxFile, String mappingFile, String targetTBoxFile,
			String provGraphFile, String targetABoxFile) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		
		CommonMethods.checkOrCreateFile(provGraphFile);
		boolean filesExists = CommonMethods.checkFiles(sourceABoxFile, mappingFile, targetTBoxFile);
		
		if (filesExists) {
			if (Methods.isJenaAccessible(sourceABoxFile)) {
				generateLevelEntryFromTinyRDF(sourceABoxFile, mappingFile, targetTBoxFile, provGraphFile, targetABoxFile, jsonObject);
			} else {
				generateLevelEntryFromLargeRDF(sourceABoxFile, mappingFile, targetTBoxFile, provGraphFile, targetABoxFile, jsonObject);
			}
		} else {
			CommonMethods.setResult(jsonObject, Variables.CHECK_FILES_PATH_MSG);
		}
		
		return jsonObject.toString();
	}

	private void generateLevelEntryFromLargeRDF(String sourceABoxFile, String mappingFile, String targetTBoxFile,
			String provGraphFile, String targetABoxFile, JSONObject jsonObject) {
		// TODO Auto-generated method stub
		
	}

	private void generateLevelEntryFromTinyRDF(String sourceABoxFile, String mappingFile, String targetTBoxFile,
			String provGraphFile, String targetABoxFile, JSONObject jsonObject) {
		// TODO Auto-generated method stub
Model provModel = CommonMethods.readModelFromPath(provGraphFile, "Prov graph", jsonObject);
		
		if (jsonObject.containsKey(Variables.RESULT_KEY)) {
			return;
		}
		
		Model mapModel = CommonMethods.readModelFromPath(mappingFile, "Mapping file", jsonObject);
		
		if (jsonObject.containsKey(Variables.RESULT_KEY)) {
			return;
		}
		
		Model targetTBoxModel = CommonMethods.readModelFromPath(targetTBoxFile, "Target Tbox file", jsonObject);
		
		if (jsonObject.containsKey(Variables.RESULT_KEY)) {
			return;
		}
		
		Model sourceModel = CommonMethods.readModelFromPath(sourceABoxFile, "Source Abox file", jsonObject);
		
		if (jsonObject.containsKey(Variables.RESULT_KEY)) {
			return;
		}
		
		Model completeModel = ModelFactory.createDefaultModel();
		completeModel.add(mapModel);
		completeModel.add(targetTBoxModel);
		
		prefixExtraction.extractPrefix(mappingFile);
		prefixExtraction.extractPrefix(sourceABoxFile);
		
		String sparql = "PREFIX map: <http://www.map.org/example#>\r\n"
				+ "PREFIX	qb:	<http://purl.org/linked-data/cube#>\r\n"
				+ "PREFIX   qb4o:   <http://purl.org/qb4olap/cubes#>\r\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
				+ "SELECT * WHERE { \r\n"
				+ "?concept a map:ConceptMapper. \r\n"
				+ "?concept map:sourceConcept ?stype. \r\n"
				+ "?concept map:targetConcept ?ttype. \r\n"
				+ "?concept map:iriValueType ?iritype. \r\n"
				+ "?concept map:iriValue ?irivalue. \r\n"
				+ "?ttype a qb4o:LevelProperty. \r\n"
				+ "OPTIONAL {?ttype rdfs:range ?range.}"
				+ "?mapper a map:PropertyMapper. \r\n"
				+ "?mapper map:ConceptMapper ?concept. \r\n"
				+ "?mapper map:sourceProperty ?sprop. \r\n"
				+ "?mapper map:targetProperty ?tprop. \r\n" + "}";
		
		ResultSet resultSet = Methods.executeQuery(completeModel, sparql);
		completeModel.close();
		
		LinkedHashMap<String, ConceptTransform> conceptMap = new LinkedHashMap<>();
		
		while (resultSet.hasNext()) {
			QuerySolution querySolution = resultSet.next();
			String sourceType = querySolution.get("stype").toString();

			String concept = querySolution.get("concept").toString();
			String targetType = querySolution.get("ttype").toString();
			String iriValue = querySolution.get("irivalue").toString();
			String iriType = querySolution.get("iritype").toString();

			String rangeString = "";
			if (querySolution.get("range") != null) {
				rangeString = querySolution.get("range").toString();
			}

			String sourceProperty = querySolution.get("sprop").toString();
			String targetProperty = querySolution.get("tprop").toString();

			if (conceptMap.containsKey(concept)) {
				ConceptTransform conceptTransform = conceptMap.get(concept);
				conceptTransform.getPropertyMap().put(sourceProperty, targetProperty);
				conceptMap.replace(concept, conceptTransform);
			} else {
				ConceptTransform conceptTransform = new ConceptTransform();
				conceptTransform.setConcept(concept);
				conceptTransform.setSourceType(sourceType);
				conceptTransform.setTargetType(targetType);
				conceptTransform.setIriValue(iriValue);
				conceptTransform.setIriValueType(iriType);
				conceptTransform.setRangeString(rangeString);
				conceptTransform.getPropertyMap().put(sourceProperty, targetProperty);
				conceptMap.put(concept, conceptTransform);
			}
		}
	}
	


	private Model extractLevelEntryData(String targetABoxFile, Model sourceModel, Model targetTBoxModel,
			Model provModel, LinkedHashMap<String, ConceptTransform> conceptMap, Model model) {
		for (String concept : conceptMap.keySet()) {
			LinkedHashMap<String, String> rangeMap = new LinkedHashMap<>();
			ConceptTransform conceptTransform = conceptMap.get(concept);

			String sparqlString = "SELECT DISTINCT ?s "
								+ "WHERE " + "{"
								+ "?s a <" + conceptTransform.getSourceType() + ">."
								+ "?s ?p ?o."
								+ "}";

			ResultSet set = Methods.executeQuery(sourceModel, sparqlString);

			int count = 0;
			while (set.hasNext()) {
				QuerySolution querySolution = set.next();
				String subjectString = querySolution.get("s").toString();

				Resource sourceResource = sourceModel.createResource(subjectString);

				String iriValue = "";

				StringBuilder stringBuilder = new StringBuilder(iriValue);
				if (conceptTransform.getIriValueType().contains("SourceAttribute")) {
					String iriPropertyName = conceptTransform.getIriValue();

					Property tempProperty = sourceModel.createProperty(iriPropertyName);
					StmtIterator stmtIterator = sourceResource.listProperties(tempProperty);

					while (stmtIterator.hasNext()) {
						Statement statement = stmtIterator.nextStatement();

						stringBuilder.append(statement.getObject());
					}
					stmtIterator.close();

					iriValue = stringBuilder.toString();
				} else if (conceptTransform.getIriValueType().contains("Expression")) {
					String expressionString = conceptTransform.getIriValue();
					LinkedHashMap<String, Object> valueMap = new LinkedHashMap<>();
					
					StmtIterator stmtIterator = sourceResource.listProperties();
					while (stmtIterator.hasNext()) {
						Statement statement = stmtIterator.nextStatement();
						
						valueMap.put(prefixExtraction.assignPrefix(statement.getPredicate().toString()), Methods.getRDFNodeValue(statement.getObject()));
					}
					stmtIterator.close();
					
					EquationHandler equationHandler = new EquationHandler();
					Object valueObject = equationHandler.handleExpression(expressionString,
							valueMap);
					
					iriValue = valueObject.toString();
				}

				iriValue = Methods.formatURL(iriValue);
				String iriString = "";

				if (conceptTransform.getRangeString().equals("")) {
					iriString = Methods.createSlashTypeString(conceptTransform.getTargetType()) + "#" + iriValue;
				} else {
					iriString = conceptTransform.getRangeString() + "#" + iriValue;
				}

				UrlValidator urlValidator = new UrlValidator();
				if (!urlValidator.isValid(iriString)) {
					iriString = Methods.validateIRI(iriString);
					System.out.println("No valid URL for " + subjectString);
				}

				Resource targetResource = model.createResource(iriString);

				Resource provResource = provModel.createResource(subjectString);
				Property owlProperty = provModel.createProperty("http://www.w3.org/2002/07/owl#sameAs");
				provResource.addProperty(owlProperty, provModel.createResource(iriString));

				for (String sourcePropertyString : conceptTransform.getPropertyMap().keySet()) {
					Property sourceProperty = sourceModel.createProperty(sourcePropertyString);
					String targetPropertyString = conceptTransform.getPropertyMap().get(sourcePropertyString);
					Property targetProperty = model.createProperty(targetPropertyString);

					StmtIterator stmtIterator = sourceResource.listProperties(sourceProperty);

					while (stmtIterator.hasNext()) {
						Statement statement = stmtIterator.nextStatement();

						String rangeValue;

						if (rangeMap.containsKey(targetPropertyString)) {
							rangeValue = rangeMap.get(targetPropertyString);
						} else {
							rangeValue = iriGenerator.getRangeValue(targetPropertyString, targetTBoxModel);
							rangeMap.put(targetPropertyString, rangeValue);
						}

						if (rangeValue.contains("http://www.w3.org/2001/XMLSchema#")) {
							targetResource.addProperty(targetProperty, statement.getObject());
						} else {
							String value = Methods.formatURL(statement.getObject().toString());
							String propertyValueIRI = rangeValue + "#" + value;
							targetResource.addProperty(targetProperty, model.createResource(propertyValueIRI));
						}
					}
					stmtIterator.close();

					Property memberProperty = model.createProperty("http://purl.org/qb4olap/cubes#memberOf");
					targetResource.addProperty(memberProperty, model.createResource(conceptTransform.getTargetType()));

					Resource typeResource = model.createResource("http://purl.org/qb4olap/cubes#LevelMember");
					targetResource.addProperty(RDF.type, typeResource);
				}

				count++;

				if (Methods.checkToSaveModel(count, targetABoxFile, model, "rdf")) {
					model = ModelFactory.createDefaultModel();
				}
			}
		}
		return model;
	}
}
