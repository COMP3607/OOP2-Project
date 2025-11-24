package org.example.FileImportTemplates;

import org.example.Question.JeopardyQuestion;

import java.util.ArrayList;
import java.util.List;

public abstract class FileImporter {
    public boolean openFile(){
        return true;
    }

    public boolean closeFile(){
        return true;
    }

    public List<JeopardyQuestion> Questions(){
        return  new ArrayList<JeopardyQuestion>();
    }
}
