package com.aldaviva.dreamstats.api.marshal;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;


@Provider
@Component
public class ApiConfig implements ContextResolver<ObjectMapper> {

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

		module.addSerializer(new DurationSerializer(false));
		module.addSerializer(new DateTimeSerializer());
		module.addSerializer(new LocalTimeSerializer(false));

		module.addKeySerializer(Duration.class, new DurationSerializer(true));
		module.addKeySerializer(DateTime.class, new DateTimeSerializer());
		module.addKeySerializer(LocalTime.class, new LocalTimeSerializer(true));

		om.registerModule(module);
	}

}