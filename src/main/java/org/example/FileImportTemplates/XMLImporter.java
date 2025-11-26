package org.example.FileImportTemplates;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.*;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

/**
 * Concrete {@link FileImporter} implementation for importing questions from an
 * XML file.<br>
 * Each child element of the root represents one {@link JeopardyQuestion}
 * containing exactly four options (A–D). The importer converts each XML
 * element into a question instance.
 *
 * <p><strong>Error Handling:</strong> Any exception during parsing results in a
 * {@link RuntimeException} with a descriptive message.</p>
 */
public class XMLImporter extends FileImporter
{
    /**
     * Parses an XML file and converts each child element of the root into a
     * {@link JeopardyQuestion}.
     * <p>
     * This method loads the XML document, iterates through each element, and
     * extracts the following fields:
     * </p>
     * <ul>
     *     <li><strong>Category</strong></li>
     *     <li><strong>Value</strong></li>
     *     <li><strong>Prompt</strong> (stored as <em>QuestionText</em> in XML)</li>
     *     <li><strong>Options A–D</strong> (inside the <em>Options</em> element)</li>
     *     <li><strong>Correct Answer</strong></li>
     * </ul>
     *
     * @param file the XML file to pars; must not be {@code null}.
     * @return a list of {@link JeopardyQuestion} objects representing each
     *         question element in the XML document.
     *
     * @throws RuntimeException if the file cannot be read or if the XML format
     *         is invalid.
     */
    @Override
    protected List<JeopardyQuestion> parse(File file)
    {
        List<JeopardyQuestion> questions = new ArrayList<>();
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList nodelist = root.getChildNodes();

            for (int i = 0; i < nodelist.getLength(); i++)
            {
                Node node = nodelist.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;

                    String category = getTagText(element, "Category");
                    int value = Integer.parseInt(getTagText(element, "Value"));
                    String prompt = getTagText(element, "QuestionText");

                    List<Option> optionList = new ArrayList<>();

                    Element options = (Element) element.getElementsByTagName("Options").item(0);
                    String optA = getTagText(options, "OptionA");
                    String optB = getTagText(options, "OptionB");
                    String optC = getTagText(options, "OptionC");
                    String optD = getTagText(options, "OptionD");

                    optionList.add(new Option("A", optA));
                    optionList.add(new Option("B", optB));
                    optionList.add(new Option("C", optC));
                    optionList.add(new Option("D", optD));

                    String answer = getTagText(element, "CorrectAnswer");

                    JeopardyQuestion q = new JeopardyQuestion(answer, prompt, category, value, optionList);

                    questions.add(q);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error parsing XML: " + e.getMessage());
        }

        return questions;
    }

    /**
     * Helper method to retrieve the text content of a child element by tag name.
     *
     * @param parent the parent XML element
     * @param tagName the name of the child tag to retrieve
     * @return the text content of the tag, or an empty string if not found
     */
    private static String getTagText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}

