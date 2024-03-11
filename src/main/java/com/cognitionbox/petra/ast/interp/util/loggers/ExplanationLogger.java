package com.cognitionbox.petra.ast.interp.util.loggers;

import com.cognitionbox.petra.ast.terms.Obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import static com.cognitionbox.petra.ast.interp.util.Ops.intersect;

public final class ExplanationLogger {

    public <T> void preconditionisNotSubseteqDomain(String fullyQualifiedClassName, String ruleName, String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,m,caseId,"\n"+" precondition: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to domain:\n\n"+StatesLogging.toString(b));
    }

//    public <T> void preconditionisNotSubseteqDomain(String fullyQualifiedClassName, String ruleName, String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
//        logExplanation(fullyQualifiedClassName,ruleName,m,caseId,"\n"+m+" c"+caseId+" precondition: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to domain:\n\n"+StatesLogging.toString(b));
//    }

    public <T> void imageIsNotSubseteqPostcondition(String fullyQualifiedClassName, String ruleName, String m, int caseId, Set<List<String>> a, Set<List<String>> b, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,m,caseId,"\n"+m+" c"+caseId+" image: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to postcondition:\n\n"+StatesLogging.toString(b));
        //Set<String> result a.removeAll(b);
        //LOG.info("\n"+m+" c"+caseId+" image: \n\n"+StatesLogging.toString(a)+"\n\nis not subset or equal to postcondition:\n\n"+StatesLogging.toString(b));
    }

    public void logObjectPrivateStateSpace(String fullyQualifiedClassName, String ruleName, Set<List<String>> set, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,"",0,"\n[Omega^{"+A.getA()+"}]^{"+A.getA()+"} = "+(!set.isEmpty()?StatesLogging.toString(set):"\\emptyset"));
    }

    public void logPrivateStateSpace(String fullyQualifiedClassName, String ruleName, String label, Set<List<String>> set, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,"",0,"\n["+label+"]^{"+A.getA()+"} = "+(!set.isEmpty()?StatesLogging.toString(set):"\\emptyset"));
    }

    public void logCasesDomainOverlap(String fullyQualifiedClassName, String ruleName, String m, int i, Set<String> a, int j, Set<String> b, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,m,0,"\n"+m+"\n\n"+"[c_"+i+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(a)+"\n\n overlaps with \n\n"+"[c_"+j+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(b)+"\n\n on states \n\n"+StatesLogging.toString(intersect(a,b)));
    }

    public void logCasesDomainOverlap(String fullyQualifiedClassName, String ruleName, String m, int i, int j, Set<String> intersection, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,m,0,"\n"+m+"\n\n"+"[c_"+i+"]^{"+A.getA()+"} = \n"+"\n\n overlaps with \n\n"+"[c_"+j+"]^{"+A.getA()+"} = \n"+"\n\n on states \n\n"+StatesLogging.toString(intersection));
    }

    public void logPredicatesOverlap(String fullyQualifiedClassName, String ruleName, String p_i, Set<List<String>> a, String p_j, Set<List<String>> b, Obj A){
        logExplanation(fullyQualifiedClassName,ruleName,"",0,"\n["+p_i+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(a)+"\n\n overlaps with \n\n"+"["+p_j+"]^{"+A.getA()+"} = \n"+StatesLogging.toString(b)+"\n\n on states \n\n"+StatesLogging.toString(intersect(a,b)));
    }

    private void logExplanation(String fullyQualifiedClassName, String ruleName, String m, int id, String explanation){
        String rootPath = "./target/errors/";
        String packagePath = fullyQualifiedClassName.substring(0, fullyQualifiedClassName.lastIndexOf('.')).replace('.', File.separatorChar);
        String filePath = rootPath + File.separator + packagePath;
        String className = fullyQualifiedClassName.substring(fullyQualifiedClassName.lastIndexOf('.') + 1);
        String fileName = className+"_"+ruleName+"_"+m+"_"+id+"_bot.pex";
        String fullFilePath = filePath + File.separator + fileName;
        try {
            File directory = new File(filePath);
            //directory.delete();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File newFile = new File(fullFilePath);
            newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile);
            writer.write(explanation);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
