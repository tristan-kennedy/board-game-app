package model;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

public final class UserDataManager {

    private static String userFilepath;
    public static User currentUser;

    private UserDataManager() {}

    /**
     * Initializes the userFilePath based upon config.txt
     */
    public static void initializeFile() {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
            line = reader.readLine();
            line = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        userFilepath = line;
        currentUser = new User("Guest", "");

        //This code only executes if the UserData file dictated by the config.txt doesn't exist or has no data
        File userDoc = new File(userFilepath);
        try {
            if (userDoc.length() == 0) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("BoardGameAppUserData");
                doc.appendChild(rootElement);

                Element userList = doc.createElement("userList");
                rootElement.appendChild(userList);
                Element reviewList = doc.createElement("reviewList");
                rootElement.appendChild(reviewList);

                writeXML(doc, userFilepath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Login lets a user login based upon a userName and password. Checks to ensure the username/password are correct, populates collections of user.
     *
     * @param userName String userName
     * @param password String password
     * @return Boolean true if login was successful, false otherwise
     */
    public static Boolean login(String userName, String password) {
        //Call to private class which initializes an XML Doc
        Document doc = initializeXMLDoc(userFilepath);

        //Checking if login matches XML saved userNames/passwords
        NodeList userList = doc.getElementsByTagName("user");
        //This loops through every single user element in the XML document.
        for (int i = 0; i < userList.getLength(); i++) {
            Node user = userList.item(i);
            String testName = user.getAttributes().getNamedItem("userName").getTextContent();
            //If the username is a match to what the user entered
            if (userName.equals(testName)) {
                String testPassword = user.getAttributes().getNamedItem("password").getTextContent();
                //If the password is a match to what the user entered
                if (password.equals(testPassword)) {
                    //Creating a new user to be the currentUser with the credentials entered if they match
                    currentUser = new User(userName, password);

                    //Initializing the users collections to the collections that they had saved in the XML.
                    NodeList gameCollectionList = ((Element) user).getElementsByTagName("gameCollection");
                    for (int j = 0; j < gameCollectionList.getLength(); j++) {
                        Node gameCollection = gameCollectionList.item(j);
                        String collectionName = gameCollection.getAttributes().getNamedItem("name").getTextContent();
                        GameCollection newGameCollection = new GameCollection(collectionName);
                        NodeList gameList = ((Element) gameCollection).getElementsByTagName("game");
                        for (int k = 0; k < gameList.getLength(); k++) {
                            Node game = gameList.item(k);
                            int gameId = Integer.parseInt(game.getAttributes().getNamedItem("id").getTextContent());
                            newGameCollection.addGame(GameDatabaseLoader.mainList.getGame(gameId));
                        }
                        currentUser.addCollection(newGameCollection);
                    }

                    //Returns true if there is a matching userName and password
                    return true;
                } else {
                    //Returns false if a matching userName is found but password is wrong
                    return false;
                }
            }
        }
        //Returns false if no matching userName is found in whole list
        return false;
    }

    /**
     * Logout sets the currentUser to a Guest user, used to disable collections.
     */
    public static void logout() {
        currentUser = new User("Guest", "");
    }

    /**
     * Populates all reviews in a GameList via an XML document, uses GameID.
     */
    public static void loadReviews() {
        Document doc = initializeXMLDoc(userFilepath);

        NodeList reviewList = doc.getElementsByTagName("review");
        for (int i = 0; i < reviewList.getLength(); i++) {
            Node review = reviewList.item(i);
            int gameId = Integer.parseInt(review.getAttributes().getNamedItem("id").getTextContent());
            int rating = Integer.parseInt(review.getAttributes().getNamedItem("rating").getTextContent());
            String userName = review.getAttributes().getNamedItem("userName").getTextContent();

            NodeList reviewTextList = ((Element) review).getElementsByTagName("reviewText");
            String reviewText = reviewTextList.item(0).getTextContent();

            Review newReview = new Review(rating, reviewText, gameId, userName);
            GameDatabaseLoader.mainList.getGame(gameId).addReview(newReview);
        }
    }

    /**
     * Creates an account based upon entered Strings, will not allow creation of duplicate usernames, returns false if fails.
     *
     * @param userName String userName
     * @param password String password
     * @return Boolean true if account creation success, false otherwise
     */
    public static Boolean createAccount(String userName, String password) {
        if(userName == "Guest")
            return  false;
        //Call to private class which initializes an XML Doc
        Document doc = initializeXMLDoc(userFilepath);

        //Verifying that the userName entered by the user is not a duplicate. If it is a duplicate returns false.
        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node user = userList.item(i);
            String testName = user.getAttributes().getNamedItem("userName").getTextContent();
            if (userName.equals(testName.trim()))
                return false;
        }

        //Creating an XML DOM element which contains the userName and password entered.
        Element user = doc.createElement("user");
        user.setAttribute("password", password);
        user.setAttribute("userName", userName);

        //Appending the user element to the userList DOM element as a child.
        NodeList list = doc.getElementsByTagName("userList");
        list.item(0).appendChild(user);

        //Write the current DOM to the filepath
        writeXML(doc, userFilepath);
        return true;
    }

    /**
     * Saves a review to the XML document
     *
     * @param review Review
     */
    public static void saveReview(Review review) {
        Document doc = initializeXMLDoc(userFilepath);

        Element xmlReview = doc.createElement("review");
        xmlReview.setAttribute("userName", review.getUserName());
        xmlReview.setAttribute("id", review.getGameId().toString());
        xmlReview.setAttribute("rating", review.getRating().toString());
        Element reviewText = doc.createElement("reviewText");
        reviewText.setTextContent(review.getReviewText());
        xmlReview.appendChild(reviewText);

        NodeList list = doc.getElementsByTagName("reviewList");
        list.item(0).appendChild(xmlReview);

        writeXML(doc, userFilepath);
    }

    /**
     * Saves the current users collections to the XML document
     */
    public static void saveGameCollections() {
        Document doc = initializeXMLDoc(userFilepath);

        NodeList userList = doc.getElementsByTagName("user");
        for (int i = 0; i < userList.getLength(); i++) {
            Node user = userList.item(i);
            String testName = user.getAttributes().getNamedItem("userName").getTextContent();
            if (currentUser.getUserName().equals(testName)) {
                //Clearing the list before adding new things to it
                while(user.hasChildNodes())
                    user.removeChild(user.getFirstChild());

                for (GameCollection c : currentUser.getCollectionList()) {
                    Element gameCollection = doc.createElement("gameCollection");
                    gameCollection.setAttribute("name", c.getName());
                    for (Game g : c) {
                        Element game = doc.createElement("game");
                        game.setAttribute("id", Integer.toString(g.getID()));
                        gameCollection.appendChild(game);
                    }
                    user.appendChild(gameCollection);
                }
                writeXML(doc, userFilepath);
                return;
            }
        }
    }

    /**
     * Private helper function which writes an XML DOM to a document
     *
     * @param doc    the current Document DOM object you want to write to a file
     * @param output the filepath of the file to write it to
     */
    private static void writeXML(Document doc, String output) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            // pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

            //Removes all white space nodes to make the xml prettier
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);
            for (int i = 0; i < nl.getLength(); ++i) {
                Node node = nl.item(i);
                node.getParentNode().removeChild(node);
            }

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(output);

            transformer.transform(source, result);
        } catch (TransformerException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private helper function which initializes an XML Document DOM object
     *
     * @param userFilepath String filepath
     * @return a XML Document DOM that has parsed the XML at filepath
     */
    private static Document initializeXMLDoc(String userFilepath) {
        try {
            //Default construction of an XML DOM along with the FileInputStream being set to the userFilepath
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            InputStream is = new FileInputStream(userFilepath);
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(is);
        } catch (SecurityException | ParserConfigurationException | IOException | SAXException |
                 IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
