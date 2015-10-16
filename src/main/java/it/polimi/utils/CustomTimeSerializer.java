package it.polimi.utils;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomTimeSerializer extends JsonSerializer<Time> {    
    @Override
    public void serialize(Time value, JsonGenerator gen, SerializerProvider arg2) throws 
        IOException, JsonProcessingException {      

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String formattedTime = formatter.format(value);

        gen.writeString(formattedTime);

    }
}