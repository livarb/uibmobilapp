package com.example.fadderukeappen;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	private final static String[] EVENT_TAGS = {"date","start", "duration", "title", "location"};

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	public static ArrayList<Event> getEventsInfoFromURL(String url) throws Exception {
		Document doc = getDocument(url);
		NodeList nodes = doc.getElementsByTagName("event");

		ArrayList<Event> allEvents = new ArrayList<Event>();

		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String date = getValue(EVENT_TAGS[0], element);
				String start = getValue(EVENT_TAGS[1], element);
				String duration = getValue(EVENT_TAGS[2], element);
				String title = getValue(EVENT_TAGS[3], element);
				String location = getValue(EVENT_TAGS[4], element);
				
				Event event = makeEvent(title, location, date, start, duration);
				allEvents.add(event);
			}

		}
		return allEvents;
	}

	private static Event makeEvent(String title, String location, String date,
			String startTime, String durationTime) {
		Date d = new Date(date);
		Time t = new Time(startTime, durationTime);
		
		return new Event(title, location, d, t);
	}

	private static Document getDocument(String url1) throws Exception {
		URL url = new URL(url1);

		URLConnection conn = url.openConnection();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(conn.getInputStream());

		doc.getDocumentElement().normalize();

		return doc;
	}

}
