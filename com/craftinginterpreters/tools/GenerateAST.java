package com.craftinginterpreters.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


// Script to generate the Expr class Expr.java in ../trick/
public class GenerateAST {

    // Main method to execture the script along with the directory for Expr.java
    public static void main(String[] args) throws IOException {

        if(args.length != 1) {
            System.err.println("Usage: generate ast <output directory");
            System.exit(64);
        }
        String outputDir = args[0];


        // List of all types of possible expressions
        List<String> types = Arrays.asList(
            "Binary : Expr left, Token operator, Expr right",
            "Grouping : Expr expression",
            "Literal : Object value",
            "Unary : Token operator, Expr right"
        );
        defineAST(outputDir, "Expr", types);
    }


    /**
    * Produces the imported packages and class definition and the Visitor pattern definition
     * @param outputDir - the output directory for the existing script (Location of Expr.java)
     * @param  baseName - the name of the class (Expr)
     * @param  types - List of all possible expression types along with its expression
     **/
    private static void defineAST(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.craftinginterpreters.trick;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + "{");
        defineVisitor(writer, baseName, types);

        for(String type:types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }
        writer.println();
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");
        writer.println("}");
        writer.close();
    }

    /**
     * Writes the individual class definitions with variables, constructor and the visitor method 
     * @param writer - Writer object which is writing to the file
     * @param baseName - Name of the abstract class (Expr.java)
     * @param className - Name of the individual class for each expression type
     * @param fieldList - List of the expressions components and its type definition
     */

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {

        writer.println("    static class " + className + " extends " + baseName + " {");
        writer.println("        " + className + "(" + fieldList +") {");
        String[] fields = fieldList.split(", ");
        for(String field : fields) {
            String name = field.split(" ")[1];
            writer.println("            this." + name+ " = " + name + ";");
        }
        writer.println("        }");

        // Visitor pattern
        writer.println();
        writer.println("        @Override");
        writer.println("        <R> R accept(Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");


        writer.println();
        for(String field : fields) {
            writer.println("        final " + field + ";");
        }
        writer.println("    }");
    }

    /**
     * Defindes the visitor pattern interface 
     * @param writer - Writer object which is writing to the file
     * @param baseName - Name of the abstract class (Expr.java)
     * @param types - List of all possible expression types along with its expression
     */
    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println("    interface Visitor<R> {");

        for(String type:types) {
            String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");   
        }
        writer.println("    }");
    }
}