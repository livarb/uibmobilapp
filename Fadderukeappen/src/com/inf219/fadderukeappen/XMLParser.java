package com.inf219.fadderukeappen;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Copyright 2013 Marianne Grov and Johan Rusvik
 * 
 * This file is part of Fadderukeappen.
 * Fadderukeappen is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Fadderukeappen is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Fadderukeappen. If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class XMLParser {
	private final static String[] EVENT_TAGS = { "date", "start", "duration", "title", "location" };

	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	public static ArrayList<Event> getEventsInfoFromURL(String url) {
		Document doc = null;
		try {
			doc = getDocument(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

				Event event = new Event(title, location, date, start, duration);
				allEvents.add(event);
			}
		}

		return allEvents;
	}

	private static Document getDocument(String url1) throws Exception {
		URL url = new URL(url1);
		URLConnection conn = url.openConnection();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		InputStream is = null;
		try {
			is = conn.getInputStream();
			is = new BufferedInputStream(is);
			Document doc = builder.parse(is);
			doc.getDocumentElement().normalize();

			return doc;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
