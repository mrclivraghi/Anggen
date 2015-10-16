package it.polimi.utils;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonTimeDeserializer extends JsonDeserializer<Time>
{
	public  CustomJsonTimeDeserializer ()
	{
	}
    @Override
    public Time deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = jsonparser.getText();
        try {
            Date date=format.parse(time);
            Time returnedTime = new Time(date.getTime());
            return returnedTime;
        } catch (ParseException e) {
        	
					try {
						SimpleDateFormat format2= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						Date date;
						date = format2.parse(time);
						Time returnedTime = new Time(date.getTime());
						returnedTime.setHours(returnedTime.getHours()+1);
						return returnedTime;
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						throw new RuntimeException(e);
					}
        	
            
        }

    }

}
