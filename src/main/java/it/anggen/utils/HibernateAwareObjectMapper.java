package it.anggen.utils;

import java.util.Date;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final SimpleModule module = new SimpleModule("", Version.unknownVersion());
        module.addSerializer(Date.class, new CustomDateSerializer());
        registerModule(module);
        
    }
}
