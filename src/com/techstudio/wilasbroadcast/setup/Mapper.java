package com.techstudio.wilasbroadcast.setup;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Mapper {
  ObjectMapper mapper;

  public Mapper() {
    mapper = new ObjectMapper();
    mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

  }

  public void setDateFormat() {
    //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
    //System.out.println("!!!!!!!!!!!!!!  time zone " + format.getTimeZone());
    mapper.getSerializationConfig().setDateFormat(format);
  }

  public String getGenericJsonRep(Object obj) {
    String result= "";
    try {
      StringWriter writer = new StringWriter();
      mapper.writeValue(writer, obj);
      writer.flush();
      result = writer.getBuffer().toString();
      writer.close();
      //System.out.println(" mapper " + writer.toString());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return result;

  }

  public CampaignConfig[] getCampaignsConfig(String jsonRep) {
    CampaignConfig[] obj = new CampaignConfig[0];
    try {
      obj = mapper.readValue(jsonRep, obj.getClass());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return obj;
  }

  private JsonNode getNode(String jsonRep) {
    JsonNode obj = null;
    try {
      //System.out.println("json " + jsonRep);
      obj = mapper.readValue(jsonRep, JsonNode.class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return obj;
  }



  public String getPrettyFormat(Object obj) {
    JsonNode rootNode = getNode(getGenericJsonRep(obj));
    StringWriter writer = new StringWriter();

    JsonFactory factory = new JsonFactory();
    JsonGenerator g = null;

    try {
      g = factory.createJsonGenerator(writer);
      g.useDefaultPrettyPrinter();
      g.setCodec(mapper);
      g.writeTree(rootNode);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    //g.setFeature();


    String result = writer.getBuffer().toString();

    //System.out.println(" result " + result);
    return result;


  }

}
