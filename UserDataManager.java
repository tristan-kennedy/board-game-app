
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

public class UserDataManager {

    private final String userFilepath;

    private User currentUser;

    public UserDataManager(String userFilepath) {
        this.userFilepath = userFilepath;
        currentUser = new User("Guest", "");
    }

    public void initializeFile() {
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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public Boolean login(String userName, String password) {
        //Call to private class which initializes an XML Doc
        Document doc = initializeXMLDoc(userFilepath);

        NodeList userList = doc.getElementsByTagName("user");
        for(int i = 0; i<userList.getLength(); i++){
            Node user = userList.item(i);
            String testName = user.getAttributes().getNamedItem("userName").getTextContent();
            if(userName.equals(testName)){
                String testPassword = user.getAttributes().getNamedItem("password").getTextContent();
                if(password.equals(testPassword)){
                    currentUser = new User(userName, password);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }

    public void logout() {
        currentUser = new User("Guest", "");
    }

    /* To be implemented once reviews/collections are implemented
    public ArrayList<Review> importReviews(){

    }

    private ArrayList<GameCollection> importCollections(){

    }
     */

    public Boolean createAccount(String userName, String password) {
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

    /* To be implemented once reviews/collections are implemented
    public void saveData(){

    }
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

    private static Document initializeXMLDoc(String userFilepath) {
        try {
            //Default construction of an XML DOM along with the FileInputStream being set to the userFilepath
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            InputStream is = new FileInputStream(userFilepath);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            return doc;
        } catch (SecurityException | ParserConfigurationException | IOException | SAXException |
                 IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
