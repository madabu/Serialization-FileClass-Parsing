package files;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParsingClass {

	private String xml;
	private String artist;
	private String name;
	private List<String> tracks;

	public ParsingClass(String xml) {
		this.xml = xml;
		this.artist = null;
		this.name = null;
		this.tracks = new ArrayList<>();
	}

	public void parse() {
		try {
			//from Java XML Parsing library:
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//configure aspects of parsing process.
			DocumentBuilder builder = factory.newDocumentBuilder();
			//parse XML document; navigate and manipulate.
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			//InputSource wraps xml in a format that can be parsed by DocumentBuilder

			Element albumElement = document.getDocumentElement();
			//retrieve the root element of the parsed XML document (<album>).

			NodeList artistNodeList = albumElement.getElementsByTagName("artist");
			//finds all elements with the tag name <artist>
			//that are descendants of the <album> element.
			if (artistNodeList.getLength() > 0) {
				//were any artist elements found?
				artist = artistNodeList.item(0).getTextContent();
				//extract text content of the first artist element and assigns it to var.
			}

			NodeList nameNodeList = albumElement.getElementsByTagName("name");
			if (nameNodeList.getLength() > 0) {
				name = nameNodeList.item(0).getTextContent();
			}

			NodeList trackNodeList = albumElement.getElementsByTagName("track");
			for (int i = 0; i < trackNodeList.getLength(); i++) {
				Node trackNode = trackNodeList.item(i);
				tracks.add(trackNode.getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getArtist() {
		return artist;
	}

	public String getName() {
		return name;
	}

	public List<String> getTracks() {
		return tracks;
	}
}
