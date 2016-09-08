
package com.mastek.designschool.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;


/**
 * The class for mapping operations on pojo.
 * 
 */
public final class JsonUtil {
	/** The Constant UTCTIMEZONE. */
	public static final String UTCTIMEZONE = "UTC";
	
	/** The Constant LONDONTIMEZONE. */
	public static final String LONDONTIMEZONE ="Europe/London";
	public static final String YYYYMMDDHHMMSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/** The Constant OBJECT_MAPPER. */
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/** The Constant MAPPING_JSON_FACTORY. */
	private static final MappingJsonFactory MAPPING_JSON_FACTORY = new MappingJsonFactory();

	/**
	 * Instantiates a new jSON util.
	 */
	private JsonUtil() {
	}

	/**
	 * From json.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param jsonString
	 *            the json string
	 * @param clazz
	 *            the clazz
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static <T> Object fromJSON(String jsonString, Class<T> clazz)
			throws IOException {
		TimeZone.setDefault(TimeZone.getTimeZone(LONDONTIMEZONE));
		final DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSSZ);
		df.setTimeZone(TimeZone.getTimeZone(UTCTIMEZONE));
		OBJECT_MAPPER.setDateFormat(df);

		return OBJECT_MAPPER.readValue(jsonString, clazz);
	}

	/**
	 * From object.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param object
	 *            the object
	 * @param clazz
	 *            the clazz
	 * @return the object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static <T> Object fromObject(Object object, Class<T> clazz)
			throws IOException {
		return fromJSON(createJSON(object), clazz);
	}

	/**
	 * From json to list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param jsonString
	 *            the json string
	 * @param clazz
	 *            the clazz
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static <T> List<T> fromJSONToList(String jsonString, Class<T> clazz)
			throws IOException {
		TimeZone.setDefault(TimeZone.getTimeZone(LONDONTIMEZONE));
		final DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSSZ);
		df.setTimeZone(TimeZone.getTimeZone(UTCTIMEZONE));
		OBJECT_MAPPER.setDateFormat(df);

		TypeFactory typeFactory = TypeFactory.defaultInstance();
		return OBJECT_MAPPER.readValue(jsonString,
				typeFactory.constructCollectionType(List.class, clazz));
	}

	/**
	 * Creates the json.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @throws JsonGenerationException
	 *             the json generation exception
	 * @throws JsonMappingException
	 *             the json mapping exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String createJSON(Object object)
			throws JsonGenerationException, JsonMappingException, IOException {
		StringWriter stringWriter = new StringWriter();
		JsonGenerator jsonGenerator = MAPPING_JSON_FACTORY
				.createGenerator(stringWriter);
		OBJECT_MAPPER.writeValue(jsonGenerator, object);
		stringWriter.close();
		return stringWriter.getBuffer().toString();
	}

	/**
	 * Method which returns attribute value for a given attribute key from JSON
	 * String.
	 * 
	 * @param jsonString
	 *            the json string
	 * @param attributeKey
	 *            the attribute key
	 * @return corresponding attribute value.
	 */
	public static String getAttributeValueFromJSON(final String jsonString,
			final String attributeKey) {
		int attributeKeyBeginIndex = jsonString.indexOf(attributeKey);

		if (attributeKeyBeginIndex < 0)
			return null;

		int attrbutValIdentifierIndex = attributeKeyBeginIndex
				+ attributeKey.length();
		String attributeVal = jsonString.substring(
				attrbutValIdentifierIndex + 1,
				jsonString.indexOf(',', attrbutValIdentifierIndex));
		attributeVal = attributeVal.replace("\"", "").replace("}", "");
		return attributeVal.trim();
	}

	/**
	 * Converts JSON string to specified object type without throwing checked
	 * exceptions.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param jsonAsString
	 *            the json as string
	 * @param pojoClass
	 *            the object class
	 * @return the object
	 */
	public static <T> Object fromJsonUnchecked(String jsonAsString,
			Class<T> pojoClass) {
		TimeZone.setDefault(TimeZone.getTimeZone(LONDONTIMEZONE));
		
		final DateFormat df = new SimpleDateFormat(YYYYMMDDHHMMSSZ);
		df.setTimeZone(TimeZone.getTimeZone(UTCTIMEZONE));
		OBJECT_MAPPER.setDateFormat(df);
		try {
			return OBJECT_MAPPER.readValue(jsonAsString, pojoClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
