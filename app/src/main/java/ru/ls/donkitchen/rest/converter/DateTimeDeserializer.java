package ru.ls.donkitchen.rest.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

/**
 * @author Lord (Kuleshov M.V.)
 * @since 10.09.15
 */
public final class DateTimeDeserializer implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {
	@Override
	public DateTime deserialize(final JsonElement json, final Type type,
	                            final JsonDeserializationContext jdc) throws JsonParseException {

		String jsonDate = json.getAsJsonPrimitive().getAsString();

		try {
			return DateTime.parse(jsonDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (IllegalArgumentException e) {
			return DateTime.parse(jsonDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
		}
	}

	@Override
	public JsonElement serialize(final DateTime src, final Type typeOfSrc,
	                             final JsonSerializationContext context) {
		String result = "";

		if (src == null) {
			result = StringUtils.EMPTY;
		} else {
			if (src.getSecondOfDay() == 0) {
				result = src.toString("yyyy-MM-dd");
			} else {
				result = src.toString("yyyy-MM-dd HH:mm:ss");
			}
		}

		return new JsonPrimitive(result);
	}
}
