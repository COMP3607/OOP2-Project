package org.example.FileImportTemplates;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.*;

import org.example.Question.JeopardyQuestion;
import org.example.Question.Option;

public class XMLImporter extends FileImporter
{
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

    // Helper to read text inside a tag
    private static String getTagText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return "";
    }
}
