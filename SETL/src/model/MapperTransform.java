package model;

public class MapperTransform {
	private String sourceProperty;
	private String sourcePropertyType;
	private String targetProperty;
	private String mapperName;
	private String namespaceTypeString;
	private String namespaceValueString;
	
	public MapperTransform(String sourceProperty, String sourcePropertyType, String targetProperty) {
		super();
		this.sourceProperty = sourceProperty;
		this.sourcePropertyType = sourcePropertyType;
		this.targetProperty = targetProperty;
		this.setMapperName("");
	}

	public MapperTransform() {
		super();
		this.sourceProperty = "";
		this.sourcePropertyType = "";
		this.targetProperty = "";
		this.setMapperName("");
		this.namespaceTypeString = "";
		this.namespaceValueString = "";
	}

	public String getSourceProperty() {
		return sourceProperty;
	}

	public void setSourceProperty(String sourceProperty) {
		this.sourceProperty = sourceProperty;
	}

	public String getSourcePropertyType() {
		return sourcePropertyType;
	}

	public void setSourcePropertyType(String sourcePropertyType) {
		this.sourcePropertyType = sourcePropertyType;
	}

	public String getTargetProperty() {
		return targetProperty;
	}

	public void setTargetProperty(String targetProperty) {
		this.targetProperty = targetProperty;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return sourceProperty;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
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
