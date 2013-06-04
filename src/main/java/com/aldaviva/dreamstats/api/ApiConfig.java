package com.aldaviva.dreamstats.api;

import com.aldaviva.dreamstats.api.marshal.ClassNameSerializer;
import com.aldaviva.dreamstats.api.marshal.DateTimeSerializer;
import com.aldaviva.dreamstats.api.marshal.DurationSerializer;
import com.aldaviva.dreamstats.api.marshal.LocalTimeSerializer;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Provider
@Component
public class ApiConfig implements ContextResolver<ObjectMapper> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiConfig.class);

	private final ObjectMapper objectMapper;

	public ApiConfig(){
		objectMapper = new ObjectMapper();
		applyConfig(objectMapper);
	}

	@Override
	public ObjectMapper getContext(final Class<?> type) {
		return objectMapper;
	}

	public static void applyConfig(final ObjectMapper om) {
		final SimpleModule module = new SimpleModule("JodaTime JSON", new Version(1,0,0,null));

		/*
		 * May need to add @JsonSerialize(typing=Typing.STATIC) if the serialized Map is a subclass of your response entity.
		 */
		module.addSerializer(new DurationSerializer(false));
		module.addSerializer(new DateTimeSerializer());
		module.addSerializer(new LocalTimeSerializer(false));
		module.addSerializer(new ClassNameSerializer());

		module.addKeySerializer(Duration.class, new DurationSerializer(true));
		module.addKeySerializer(DateTime.class, new DateTimeSerializer());
		module.addKeySerializer(LocalTime.class, new LocalTimeSerializer(true));

		om.registerModule(module);

		LOGGER.debug("Registered custom JSON serializers");
	}

}