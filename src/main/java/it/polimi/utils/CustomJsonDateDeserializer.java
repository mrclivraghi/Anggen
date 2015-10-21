package it.polimi.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date>
{
	public  CustomJsonDateDeserializer ()
	{
	}
    @Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String date = jsonparser.getText();
        try {
            Date returnedDate=format.parse(date);
            System.out.println(returnedDate.toString());
           // returnedDate.setDate(returnedDate.getDate()+1);
            System.out.println(returnedDate.toString());
            return returnedDate;
        } catch (ParseException e) {
        	format = new SimpleDateFormat("yyyy-MM-dd");
            try {
            	  Date returnedDate=format.parse(date);
                  returnedDate.setDate(returnedDate.getDate()+1);
                  return returnedDate;
            } catch (ParseException e2)
            {
            	try
            	{
            		return new Date(Long.valueOf(date));
            	}catch (Exception e3)
            	{
            		throw new RuntimeException(e3);
            	}
            }
            
        }

    }

}
