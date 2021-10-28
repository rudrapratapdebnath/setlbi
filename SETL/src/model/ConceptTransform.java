package model;

import java.util.LinkedHashMap;

public class ConceptTransform {
	private String concept;
	private String sourceType;
	private String targetType;
	private String iriValue;
	private String iriValueType;
	private String sourceCommonProperty;
	private String targetCommonProperty;
	private String sourceABoxLocationString;
	private String operationName;
	private String targetFileLocation;
	private boolean isMappingFileDependent;
	private String tempInputMapFilePath;
	private String tempOutputMapFilePath;
	private LinkedHashMap<String, MapperTransform> mapperTransformMap;
	private LinkedHashMap<String, String> propertyMap;
	private String rangeString;
	private String namespaceTypeString;
	private String namespaceValueString;

	public ConceptTransform() {
		super();
		
		this.concept = "";
		this.sourceType = "";
		this.targetType = "";
		this.iriValue = "";
		this.iriValueType = "";
		this.sourceCommonProperty = "";
		this.targetCommonProperty = "";
		this.sourceABoxLocationString = "";
		this.rangeString = "";
		this.operationName = "";
		this.targetFileLocation = "";
		this.setMappingFileDependent(false);
		this.setTempInputMapFilePath("");
		this.setTempOutputMapFilePath("");
		this.mapperTransformMap = new LinkedHashMap<String, MapperTransform>();
		this.setPropertyMap(new LinkedHashMap<String, String>());
		this.namespaceTypeString = "";
		this.namespaceValueString = "";
	}

	public ConceptTransform(String concept, String sourceType, String targetType, String iriValue, String iriValueType,
			String sourceCommonProperty, String targetCommonProperty, String sourceABoxLocationString,
			LinkedHashMap<String, MapperTransform> mapperTransformMap) {
		super();
		this.concept = concept;
		this.sourceType = sourceType;
		this.targetType = targetType;
		this.iriValue = iriValue;
		this.iriValueType = iriValueType;
		this.sourceCommonProperty = sourceCommonProperty;
		this.targetCommonProperty = targetCommonProperty;
		this.sourceABoxLocationString = sourceABoxLocationString;
		this.mapperTransformMap = mapperTransformMap;
		this.setMappingFileDependent(false);
		this.setTempInputMapFilePath("");
		this.setTempOutputMapFilePath("");
	}

	public String getSourceABoxLocationString() {
		return sourceABoxLocationString;
	}

	public void setSourceABoxLocationString(String sourceABoxLocationString) {
		this.sourceABoxLocationString = sourceABoxLocationString;
	}

	public String getIriValue() {
		return iriValue;
	}

	public void setIriValue(String iriValue) {
		this.iriValue = iriValue;
	}

	public String getIriValueType() {
		return iriValueType;
	}

	public void setIriValueType(String iriValueType) {
		this.iriValueType = iriValueType;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getSourceCommonProperty() {
		return sourceCommonProperty;
	}

	public void setSourceCommonProperty(String sourceCommonProperty) {
		this.sourceCommonProperty = sourceCommonProperty;
	}

	public String getTargetCommonProperty() {
		return targetCommonProperty;
	}

	public void setTargetCommonProperty(String targetCommonProperty) {
		this.targetCommonProperty = targetCommonProperty;
	}

	public LinkedHashMap<String, MapperTransform> getMapperTransformMap() {
		return mapperTransformMap;
	}

	public void setMapperTransformMap(LinkedHashMap<String, MapperTransform> mapperTransformMap) {
		this.mapperTransformMap = mapperTransformMap;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return concept;
	}

	public LinkedHashMap<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(LinkedHashMap<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getRangeString() {
		return rangeString;
	}

	public void setRangeString(String rangeString) {
		this.rangeString = rangeString;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getTargetFileLocation() {
		return targetFileLocation;
	}

	public void setTargetFileLocation(String targetFileLocation) {
		this.targetFileLocation = targetFileLocation;
	}

	public boolean isMappingFileDependent() {
		return isMappingFileDependent;
	}

	public void setMappingFileDependent(boolean isMappingFileDependent) {
		this.isMappingFileDependent = isMappingFileDependent;
	}

	public String getTempInputMapFilePath() {
		return tempInputMapFilePath;
	}

	public void setTempInputMapFilePath(String tempInputMapFilePath) {
		this.tempInputMapFilePath = tempInputMapFilePath;
	}

	public String getTempOutputMapFilePath() {
		return tempOutputMapFilePath;
	}

	public void setTempOutputMapFilePath(String tempOutputMapFilePath) {
		this.tempOutputMapFilePath = tempOutputMapFilePath;
	}

	public String getNamespaceTypeString() {
		return namespaceTypeString;
	}

	public void setNamespaceTypeString(String namespaceTypeString) {
		this.namespaceTypeString = namespaceTypeString;
	}

	public String getNamespaceValueString() {
		return namespaceValueString;
	}

	public void setNamespaceValueString(String namespaceValueString) {
		this.namespaceValueString = namespaceValueString;
	}
}
