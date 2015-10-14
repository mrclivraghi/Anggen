package it.polimi.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonListDeserializer extends JsonDeserializer<List>
{
	public  CustomJsonListDeserializer ()
	{
	}
    @Override
    public List deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        String text = jsonparser.getText();
        if (text.equals(""))	       
        	return new ArrayList();
        
        return new ArrayList();

    }

}
