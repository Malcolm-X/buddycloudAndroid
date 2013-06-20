package tub.iosp.budcloand.framework.model;

import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Interface BCJSONObject. 
 * All Classes implementing this Interface can be parsed into JSONObjects.
 * Implementing Classes should provide a Constructor or Factory for JSON Objects and Strings.
 */
public interface BCJSONObject {
	
	/**
	 * Gets the JSON object. 
	 * Only parses non-null values.
	 * ignores values that are not known to the server
	 *
	 * @return the JSON object representing this entity.
	 * parseable by the buddycloudserver
	 */
	public JSONObject getJSONObject();
	
	/**
	 * Gets the jSON string.
	 *
	 * @return the jSON string representing this entity.
	 * parseable by the buddycloudserver
	 */
	public String getJSONString();

}
