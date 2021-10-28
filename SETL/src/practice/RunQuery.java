package practice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.resultset.RDFOutput;
import org.apache.jena.vocabulary.RDF;

import helper.Methods;

public class RunQuery {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String filepath = "C:\\Users\\Amrit\\Documents\\1\\New_ODE_ETL\\source_abox.ttl";
//		Methods methods = new Methods();
//
//		Model model = methods.readModelFromPath(filepath);
//
////		Methods.print(model);
//
//		String sparql = "SELECT ?t WHERE {"
//				+ "?s a <http://extbi.lab.aau.dk/ontology/sdw/Company>."
//				+ "?t <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://extbi.lab.aau.dk/ontology/sdw/Company>."
//				+ "?s ?p ?o."
//				+ "} LIMIT 5";
//
//		Query query = QueryFactory.create(sparql);
//		QueryExecution execution = QueryExecutionFactory.create(query, model);
//		ResultSet resultSet = ResultSetFactory.copyResults(execution.execSelect());
//
//		methods.printResultSet(resultSet);
//		
//		ArrayList<String> conceptList = extractConcepts(sparql);
//		
//		System.out.println("Done " + conceptList.size());
		
		
//		String sparql = "PREFIX qb: <http://purl.org/linked-data/cube#>\r\n" + 
//				"PREFIX qb4o: <http://purl.org/qb4olap/cubes#>\r\n" + 
//				"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\r\n" + 
//				"SELECT ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?resource (SUM(<http://www.w3.org/2001/XMLSchema#long>(?m1)) as ?numberOfHousehold_count) \r\n" + 
//				"WHERE {\r\n" + 
//				"?o a qb:Observation .\r\n" + 
//				"?o qb:dataSet <http://linked-statistics-bd.org/2011/data#HouseholdByAdm5ResidenceHouseholdSize> .\r\n" + 
//				"?o <http://linked-statistics-bd.org/2011/mdProperty#numberOfHousehold> ?m1 .\r\n" + 
//				"?o <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> ?admGeographyDim_admUnitFive .\r\n" + 
//				"?admGeographyDim_admUnitFive qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFive> .\r\n" + 
//				"?admGeographyDim_admUnitFive <http://linked-statistics-bd.org/2011/mdAttribute#inAdmFour> ?admGeographyDim_admUnitFour .\r\n" + 
//				"?admGeographyDim_admUnitFour qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitFour> .\r\n" + 
//				"?admGeographyDim_admUnitFour <http://linked-statistics-bd.org/2011/mdAttribute#inAdmThree> ?admGeographyDim_admUnitThree .\r\n" + 
//				"?admGeographyDim_admUnitThree qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitThree> .\r\n" + 
//				"?admGeographyDim_admUnitThree <http://linked-statistics-bd.org/2011/mdAttribute#inAdmTwo> ?admGeographyDim_AdmUnitTwo .\r\n" + 
//				"?admGeographyDim_AdmUnitTwo qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#AdmUnitTwo> .\r\n" + 
//				"?admGeographyDim_AdmUnitTwo <http://linked-statistics-bd.org/2011/mdAttribute#inAdmOne> ?admGeographyDim_admUnitOne .\r\n" + 
//				"?admGeographyDim_admUnitOne qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#admUnitOne> .\r\n" + 
//				"?admGeographyDim_admUnitOne <http://linked-statistics-bd.org/2011/mdAttribute#administrativeUnitName> ?admUnitOne_administrativeUnitName .\r\n" + 
//				"?admGeographyDim_admUnitOne <http://www.w3.org/2002/07/owl#sameAs> ?resource .\r\n" + 
//				"?admGeographyDim_admUnitOne <http://linked-statistics-bd.org/2011/mdAttribute#administrativeUnitName> ?admGeographyDim_administrativeUnitName .\r\n" + 
//				"?o <http://linked-statistics-bd.org/2011/mdProperty#householdSize> ?householdSizeDim_householdSize .\r\n" + 
//				"?householdSizeDim_householdSize qb4o:memberOf <http://linked-statistics-bd.org/2011/mdProperty#householdSize> .\r\n" + 
//				"?householdSizeDim_householdSize <http://linked-statistics-bd.org/2011/mdAttribute#householdSizeName> ?householdSizeDim_householdSizeName .\r\n" + 
//				"FILTER (REGEX (?admUnitOne_administrativeUnitName, \"CHITTAGONG DIVISION\", \"i\"))}\r\n" + 
//				"GROUP BY ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?resource\r\n" + 
//				"ORDER BY ?admGeographyDim_administrativeUnitName ?householdSizeDim_householdSizeName ?resource";
//		
//		
//		if (sparql.contains("sameAs")) {
//			String dbpediaString = extractDbpediaString(sparql);
//			System.out.println(dbpediaString);
//		} else {
//			
//		}
//		
//		System.out.println("Done");
		
		
		
		
		
		
		
		
		
		
		
//		String sparql = "PREFIX dbo:	<http://dbpedia.org/ontology/>\r\n" + 
//				"SELECT * WHERE {\r\n" + 
//				"	<http://dbpedia.org/resource/Chittagong> ?p ?x.\r\n" + 
//				"	FILTER (langMatches(lang(?x),\"en\"))\r\n" + 
//				"}";
//		
//		ParameterizedSparqlString qs = new ParameterizedSparqlString(sparql);
//		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());
//		ResultSet resultSet = ResultSetFactory.copyResults(exec.execSelect());
//		
//		Methods.print(resultSet);
		
		Methods.deleteAndCreateFile("dbpedia.nt");
		String resource = Methods.bracketString("http://dbpedia.org/resource/Chittagong");

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
			
			System.out.println(querySolution.get("x"));
			
			if (Methods.containsWWW(value.toString())) {
				value = Methods.bracketString(value);
			} else {
				value = "\""+ Methods.encodeString(value) +"\"";
			}

			String line = resource + " " + Methods.bracketString(property) + " " + value + ".\n";
			stringBuilder.append(line);
		}
		
		Methods.appendToFile(stringBuilder.toString(), "dbpedia.nt");
		
		Model model = Methods.readModelFromPath("dbpedia.nt");
		Methods.print(model);
	}

	private static String extractDbpediaString(String sparql) {
		// TODO Auto-generated method stub
		String regEx2 = "(\\?\\S+)(\\s+)(<http://www.w3.org/2002/07/owl#sameAs>)(\\s+)(\\?\\S+)";
		Pattern pattern2 = Pattern.compile(regEx2);
		Matcher matcher2 = pattern2.matcher(sparql);
		
		while (matcher2.find()) {
			System.out.println(matcher2.group(5));
			
			String conceptString = matcher2.group(5);
			System.out.println(conceptString);
		}
		
		return null;
	}

	private static ArrayList<String> extractConcepts(String sparqlQueryString) {
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
}
