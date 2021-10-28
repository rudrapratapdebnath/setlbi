package helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonMethods {
	public static Boolean checkFiles(String...strings) {
		for (String string : strings) {
			if (!checkFile(string)) {
				return false;
			}
		}
		
		return true;
	}

    public static boolean checkFile(String filePath) {
        // TODO Auto-generated method stub
        File file = new File(filePath);
        return file.exists();
    }
    
    public static String parseResult(String resultString) {
    	JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(resultString);
			return json.get(Variables.RESULT_KEY).toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Result couldn't be parsed";
	}
        
    public static void setResult(JSONObject jsonObject, String resultString) {
    	jsonObject.put(Variables.RESULT_KEY, resultString);
	}
    
	public static void checkOrCreateFile(String filePath) {
		// TODO Auto-generated method stub
		File file = new File(filePath);
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    public static boolean isJenaAccessible(String filePath) {
        return getFileSizeMegaBytes(filePath) < Variables.JENA_MB_LIMIT;
    }

    public static Double getFileSizeMegaBytes(String filePath) {
        File file = new File(filePath);
        return (double) file.length() / (1024 * 1024);
    }
    
    public static Model readModelFromPath(String filePath, String name, JSONObject jsonObject) {
        // TODO Auto-generated method stub
        Model model = ModelFactory.createDefaultModel();
        try {
            model.read(filePath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jsonObject.put(Variables.RESULT_KEY, "Check " + name + " file");
        }
        return model;
    }

    public static void print(Object object) {
        // TODO Auto-generated method stub
        if (object instanceof LinkedHashMap) {
            for (Map.Entry<Object, Object> map : ((LinkedHashMap<Object, Object>) object).entrySet()) {
                Object key = map.getKey();
                Object value = map.getValue();

                print(key);
                print(value);
            }
        } else if (object instanceof String) {
            System.out.println(object);
        } else if (object instanceof ResultSet) {
            ResultSetFormatter.out(ResultSetFactory.copyResults((ResultSet) object));
        } else if (object instanceof Model) {
            ((Model) object).write(System.out, "TTL");
        } else if (object instanceof Boolean) {

        } else if (object instanceof ArrayList) {
            for (int i = 0; i < ((ArrayList<String>) object).size(); i++) {
                String string = ((ArrayList<String>) object).get(i);
                System.out.println(string);
            }
        }
    }
}
