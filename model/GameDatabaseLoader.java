package model;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


/**
 * Loads the game data from a Board Game Geek XML file into a GameList of Game objects for use elsewhere
 */
public final class GameDatabaseLoader {

    private static String gameFilepath;
    public static GameList mainList;

    private GameDatabaseLoader() {}

    /**
     * Constructs the loader from a String containing the XML file's filepath
     */
    public static void initializeFile() {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gameFilepath = line;
        mainList = new GameList();
    }

    /**
     * Imports all game data from the XML file into a GameList object comprised of individual Games
     */
    public static void importGameData() {

        // Used to temporarily store data from the XML file until each Game object is constructed
        String thumbnail = "";
        String fullSizeImage = "";
        String name = "";
        String description = "";
        StringBuilder descriptionBuilder = new StringBuilder();
        int id = 0,
                yearPublished = 0,
                minPlayers = 0,
                maxPlayers = 0,
                playingTime = 0;
        ArrayList<String> categoryList = new ArrayList<>();
        ArrayList<String> mechanicList = new ArrayList<>();

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            // Security settings: https://rules.sonarsource.com/java/RSPEC-2755
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            // Iterates through XML elements as "events"
            // StartElement (opening <> tag, contains attritutes)
            // Characters (contains characters between <> tags
            // EndElement (closing </> tag)
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(gameFilepath));

            // Loop through the entire XML file
            while (eventReader.hasNext()) {

                XMLEvent event = eventReader.nextEvent();

                // If opening <> tag
                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();

                    // Match tag to one of the tags we are looking for
                    switch (element.getName().getLocalPart()) {

                        // <item id="######">
                        case "item" -> id = Integer.parseInt(element.getAttributeByName(new QName("id")).getValue());

                        // <thumbnail>
                        case "thumbnail" -> {
                            event = eventReader.nextEvent();
                            if (event.isCharacters())
                                thumbnail = event.asCharacters().getData().trim().replaceAll("\\s+", ""); // Remove all whitespace and linebreaks
                        }

                        // <image>
                        case "image" -> {
                            event = eventReader.nextEvent();
                            if (event.isCharacters())
                                fullSizeImage = event.asCharacters().getData().trim().replaceAll("\\s+", "");
                        }

                        // <name value="name"/>
                        case "name" -> {
                            // Make sure we're only using the "primary" name
                            if (element.getAttributeByName(new QName("type")).getValue().equals("primary"))
                                name = element.getAttributeByName(new QName("value")).getValue().trim();
                        }

                        // <description>
                        case "description" -> {
                            // A long string as character data will be split up into multiple events in StAX
                            // Loop through and append each piece to the description string until the </description> tag is reached
                            while (!eventReader.peek().isEndElement()) {
                                event = eventReader.nextEvent();
                                String tempDescription = event.asCharacters().getData();
                                tempDescription = tempDescription.replaceAll("\\R+", " "); // Replace all line breaks with a single space
                                descriptionBuilder.append(tempDescription);
                            }
                            description = descriptionBuilder.toString().replaceAll("\\s+", " "); // Replace all instances of 2+ spaces with a single space
                            descriptionBuilder.setLength(0); // Clear the descriptionBuilder
                        }

                        // <yearpublished value="####"/>
                        case "yearpublished" ->
                                yearPublished = Integer.parseInt(element.getAttributeByName(new QName("value")).getValue());

                        // <minplayers value="##"/>
                        case "minplayers" ->
                                minPlayers = Integer.parseInt(element.getAttributeByName(new QName("value")).getValue());

                        // <maxplayers value="##"/>
                        case "maxplayers" ->
                                maxPlayers = Integer.parseInt(element.getAttributeByName(new QName("value")).getValue());

                        // <playingtime value="###"/>
                        case "playingtime" ->
                                playingTime = Integer.parseInt(element.getAttributeByName(new QName("value")).getValue());

                        // <link type="boardgameXXXXXXX" value="XXXXXXXXXXXXX"/>
                        case "link" -> {
                            String type = element.getAttributeByName(new QName("type")).getValue();
                            if (type.equals("boardgamecategory"))
                                categoryList.add(element.getAttributeByName(new QName("value")).getValue());
                            if (type.equals("boardgamemechanic"))
                                mechanicList.add(element.getAttributeByName(new QName("value")).getValue());
                        }
                    }
                }

                // If closing </> tag
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();

                    // </item>
                    if (endElement.getName().getLocalPart().equals("item")) {

                        // Construct the game object
                        Game g = new Game(id, thumbnail, fullSizeImage, name, description, yearPublished, minPlayers, maxPlayers, playingTime, categoryList, mechanicList);

                        // Add it to the game list
                        mainList.addGame(g);

                        // Reset all variables for the next Game object
                        id = 0;
                        thumbnail = "";
                        fullSizeImage = "";
                        name = "";
                        description = "";
                        yearPublished = 0;
                        minPlayers = 0;
                        maxPlayers = 0;
                        playingTime = 0;
                        categoryList = new ArrayList<>();
                        mechanicList = new ArrayList<>();
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            System.out.println(e.getMessage());
        }
    }

}