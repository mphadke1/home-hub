
/*********


http://www.saxproject.org/

SAX is the Simple API for XML, originally a Java-only API. 
SAX was the first widely adopted API for XML in Java, and is a �de facto� standard. 
The current version is SAX 2.0.1, and there are versions for several programming language environments other than Java. 

The following URL from Oracle is the JAVA documentation for the API

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html


*********/
import org.xml.sax.InputSource;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import  java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


////////////////////////////////////////////////////////////

/**************

SAX parser use callback function  to notify client object of the XML document structure. 
You should extend DefaultHandler and override the method when parsin the XML document

***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
	
	TV tv;
	Laptop laptop;
	Phone phone;
	SoundSystem soundSystem;
	Wearable wearable;
	Accessory accessory;
	VoiceAssistant voiceAssistant;
    static HashMap<String, TV> tvs;
    static HashMap<String, Laptop> laptops;
    static HashMap<String, Phone> phones;
    static HashMap<String, SoundSystem> soundSystems;
    static HashMap<String, Wearable> wearables;
    static HashMap<String, Accessory> accessories;
    static HashMap<String, VoiceAssistant> voiceAssistants;
    String xmlFileName;
	HashMap<String,String> accessoryHashMap;
    String elementValueRead;
	String currentElement="";
	
	public SaxParserDataStore(){
	
	}

	public SaxParserDataStore(String xmlFileName) {
		this.xmlFileName = xmlFileName;
		tvs = new HashMap<String, TV>();
		laptops = new HashMap<String, Laptop>();
		phones = new HashMap<String, Phone>();
		soundSystems = new HashMap<String, SoundSystem>();
		wearables = new HashMap<String, Wearable>();
		voiceAssistants = new HashMap<String, VoiceAssistant>();
		accessories = new HashMap<String, Accessory>();
		accessoryHashMap = new HashMap<String,String>();
		parseDocument();
    }

	// Parse the xml using sax parser to get the data
    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try  {
			SAXParser parser = factory.newSAXParser();
			parser.parse(xmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
	}

   

////////////////////////////////////////////////////////////

/*************

There are a number of methods to override in SAX handler  when parsing your XML document :

    Group 1. startDocument() and endDocument() :  Methods that are called at the start and end of an XML document. 
    Group 2. startElement() and endElement() :  Methods that are called  at the start and end of a document element.  
    Group 3. characters() : Method that is called with the text content in between the start and end tags of an XML document element.


There are few other methods that you could use for notification for different purposes, check the API at the following URL:

https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html

***************/

////////////////////////////////////////////////////////////
	
	// when xml start element is parsed store the id into respective hashmap for all products 
    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

		switch (elementName.toLowerCase()) {
			case "tv":
				currentElement="tv";
				tv = new TV();
				tv.setId(attributes.getValue("id"));
				break;
			case "soundsystem":
				currentElement="soundsystem";
				soundSystem = new SoundSystem();
				soundSystem.setId(attributes.getValue("id"));
				break;
			case "phone":
				currentElement="phone";
				phone = new Phone();
				phone.setId(attributes.getValue("id"));
				break;
			case "laptop":
				currentElement="laptop";
				laptop = new Laptop();
				laptop.setId(attributes.getValue("id"));
				break;
			case "wearable":
				currentElement="wearable";
				wearable = new Wearable();
				wearable.setId(attributes.getValue("id"));
				break;
			case "voiceassistant":
				currentElement="voiceassistant";
				voiceAssistant = new VoiceAssistant();
				voiceAssistant.setId(attributes.getValue("id"));
				break;
			case "accessory":
				if(!currentElement.equalsIgnoreCase("tv")) {
					currentElement="accessory";
					accessory = new Accessory();
					accessory.setId(attributes.getValue("id"));
					break;
				}
		}


        // if (elementName.equalsIgnoreCase("console")) 
		// {
		// 	currentElement="console";
		// 	console = new Console();
        //     console.setId(attributes.getValue("id"));
		// }
        // if (elementName.equalsIgnoreCase("tablet"))
		// {
		// 	currentElement="tablet";
		// 	tablet = new Tablet();
        //     tablet.setId(attributes.getValue("id"));
        // }
        // if (elementName.equalsIgnoreCase("game"))
		// {
		// 	currentElement="game";
		// 	game= new Game();
        //     game.setId(attributes.getValue("id"));
        // }
        // if (elementName.equals("accessory"))
		// {
		// 	currentElement="accessory";
		// 	accessory=new Accessory();
		// 	accessory.setId(attributes.getValue("id"));
	    // }
    }
	// when xml end element is parsed store the data into respective hashmap for console,games etc respectively
    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {
 
		switch (element.toLowerCase()) {
			case "tv":
				tvs.put(tv.getId(), tv);
				return;
			case "soundsystem":
				soundSystems.put(soundSystem.getId(), soundSystem);
				return;
			case "laptop":
				laptops.put(laptop.getId(), laptop);
				return;
			case "phone":
				phones.put(phone.getId(), phone);
				return;
			case "wearable":
				wearables.put(wearable.getId(), wearable);
				return;
			case "voiceassistant":
				voiceAssistants.put(voiceAssistant.getId(), voiceAssistant);
				return;
			case "accessory":
				switch (currentElement.toLowerCase()) {
					case "accessory":
						accessories.put(accessory.getId(), accessory);
						return;
					case "tv":
						accessoryHashMap.put(elementValueRead, elementValueRead);
				}
				return;
			case "image":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setImage(elementValueRead);
						return;
					case "soundsystem":
						soundSystem.setImage(elementValueRead);
						return;
					case "laptop":
						laptop.setImage(elementValueRead);
						return;
					case "phone":
						phone.setImage(elementValueRead);
						return;
					case "wearable":
						wearable.setImage(elementValueRead);
						return;
					case "voiceassistant":
						wearable.setImage(elementValueRead);
						return;
					case "accessory":
						accessory.setImage(elementValueRead);
						return;
				}
				return;
			case "discount":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "soundsystem":
						soundSystem.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "laptop":
						laptop.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "phone":
						phone.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "wearable":
						wearable.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "voiceassistant":
						wearable.setDiscount(Double.parseDouble(elementValueRead));
						return;
					case "accessory":
						accessory.setDiscount(Double.parseDouble(elementValueRead));
						return;
				}
				return;
			case "condition":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setCondition(elementValueRead);
						return;
					case "soundsystem":
						soundSystem.setCondition(elementValueRead);
						return;
					case "laptop":
						laptop.setCondition(elementValueRead);
						return;
					case "phone":
						phone.setCondition(elementValueRead);
						return;
					case "wearable":
						wearable.setCondition(elementValueRead);
						return;
					case "voiceassistant":
						wearable.setCondition(elementValueRead);
						return;
					case "accessory":
						accessory.setCondition(elementValueRead);
						return;
				}
				return;
			case "manufacturer":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setRetailer(elementValueRead);
						return;
					case "soundsystem":
						soundSystem.setRetailer(elementValueRead);
						return;
					case "laptop":
						laptop.setRetailer(elementValueRead);
						return;
					case "phone":
						phone.setRetailer(elementValueRead);
						return;
					case "wearable":
						wearable.setRetailer(elementValueRead);
						return;
					case "voiceassistant":
						wearable.setRetailer(elementValueRead);
						return;
					case "accessory":
						accessory.setRetailer(elementValueRead);
						return;
				}
				return;
			case "name":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setName(elementValueRead);
						return;
					case "soundsystem":
						soundSystem.setName(elementValueRead);
						return;
					case "laptop":
						laptop.setName(elementValueRead);
						return;
					case "phone":
						phone.setName(elementValueRead);
						return;
					case "wearable":
						wearable.setName(elementValueRead);
						return;
					case "voiceassistant":
						wearable.setName(elementValueRead);
						return;
					case "accessory":
						accessory.setName(elementValueRead);
						return;
				}
				return;
			case "price":
				switch (currentElement.toLowerCase()) {
					case "tv":
						tv.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "soundsystem":
						soundSystem.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "laptop":
						laptop.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "phone":
						phone.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "wearable":
						wearable.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "voiceassistant":
						wearable.setPrice(Double.parseDouble(elementValueRead));
						return;
					case "accessory":
						accessory.setPrice(Double.parseDouble(elementValueRead));
						return;
				}
				return;
		}

		if(element.equalsIgnoreCase("accessories")){
			if(currentElement.equalsIgnoreCase("tv")) {
				tv.setAccessories(accessoryHashMap);
				accessoryHashMap = new HashMap<String, String>();
			}
			return;
		}

        // if (element.equals("console")) {
		// 	consoles.put(console.getId(),console);
		// 	return;
        // }
 
        // if (element.equals("tablet")) {	
		// 	tablets.put(tablet.getId(),tablet);
		// 	return;
        // }
        // if (element.equals("game")) {	  
		// 	games.put(game.getId(),game);
		// 	return;
        // }
        // if (element.equals("accessory") && currentElement.equals("accessory")) {
		// 	accessories.put(accessory.getId(),accessory);       
		// 	return; 
        // }
		// if (element.equals("accessory") && currentElement.equals("console")) 
		// {
		// 	accessoryHashMap.put(elementValueRead,elementValueRead);
		// }
      	// if (element.equalsIgnoreCase("accessories") && currentElement.equals("console")) {
		// 	console.setAccessories(accessoryHashMap);
		// 	accessoryHashMap=new HashMap<String,String>();
		// 	return;
		// }
        // if (element.equalsIgnoreCase("image")) {
		//     if(currentElement.equals("console"))
		// 		console.setImage(elementValueRead);
        // 	if(currentElement.equals("game"))
		// 		game.setImage(elementValueRead);
        //     if(currentElement.equals("tablet"))
		// 		tablet.setImage(elementValueRead);
        //     if(currentElement.equals("accessory"))
		// 		accessory.setImage(elementValueRead);          
		// 	return;
        // }
        

		// if (element.equalsIgnoreCase("discount")) {
        //     if(currentElement.equals("console"))
		// 		console.setDiscount(Double.parseDouble(elementValueRead));
        // 	if(currentElement.equals("game"))
		// 		game.setDiscount(Double.parseDouble(elementValueRead));
        //     if(currentElement.equals("tablet"))
		// 		tablet.setDiscount(Double.parseDouble(elementValueRead));
        //     if(currentElement.equals("accessory"))
		// 		accessory.setDiscount(Double.parseDouble(elementValueRead));          
		// 	return;
	    // }


		// if (element.equalsIgnoreCase("condition")) {
        //     if(currentElement.equals("console"))
		// 		console.setCondition(elementValueRead);
        // 	if(currentElement.equals("game"))
		// 		game.setCondition(elementValueRead);
        //     if(currentElement.equals("tablet"))
		// 		tablet.setCondition(elementValueRead);
        //     if(currentElement.equals("accessory"))
		// 		accessory.setCondition(elementValueRead);          
		// 	return;  
		// }

		// if (element.equalsIgnoreCase("manufacturer")) {
        //     if(currentElement.equals("console"))
		// 		console.setRetailer(elementValueRead);
        // 	if(currentElement.equals("game"))
		// 		game.setRetailer(elementValueRead);
        //     if(currentElement.equals("tablet"))
		// 		tablet.setRetailer(elementValueRead);
        //     if(currentElement.equals("accessory"))
		// 		accessory.setRetailer(elementValueRead);          
		// 	return;
		// }

        // if (element.equalsIgnoreCase("name")) {
        //     if(currentElement.equals("console"))
		// 		console.setName(elementValueRead);
        // 	if(currentElement.equals("game"))
		// 		game.setName(elementValueRead);
        //     if(currentElement.equals("tablet"))
		// 		tablet.setName(elementValueRead);
        //     if(currentElement.equals("accessory"))
		// 		accessory.setName(elementValueRead);          
		// 	return;
	    // }
	
        // if(element.equalsIgnoreCase("price")){
		// 	if(currentElement.equals("console"))
		// 		console.setPrice(Double.parseDouble(elementValueRead));
        // 	if(currentElement.equals("game"))
		// 		game.setPrice(Double.parseDouble(elementValueRead));
        //     if(currentElement.equals("tablet"))
		// 		tablet.setPrice(Double.parseDouble(elementValueRead));
        //     if(currentElement.equals("accessory"))
		// 		accessory.setPrice(Double.parseDouble(elementValueRead));          
		// 	return;
        // }

		if(element.equalsIgnoreCase("TVCatalog")) {
			currentElement = "";
		}
	}
	//get each element in xml tag
    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }


    /////////////////////////////////////////
    // 	     Kick-Start SAX in main       //
    ////////////////////////////////////////
	
//call the constructor to parse the xml and get product details
 public static void addHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");	
		new SaxParserDataStore(TOMCAT_HOME+"\\webapps\\home-hub\\ProductCatalog.xml");
    } 
}
