package com.craftinginterpreters.trick;

import com.craftinginterpreters.trick.Scanner;
import java.io.BufferedReader;
import java.io.File; //DO NOT DELETE: this will be used when reading a file rather than line by line
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/*List of defined token names */
enum TokenType{
    //Single char tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    //one/two char tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    //Literals.
    IDENTIFIER, STRING, NUMBER,CHAR,

    //Keyword tokens (reserved names)
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}
/*Main Class
 * Runs source code files, and line by line inputs, holds the main method to run
 */
public class trick{
    static boolean hadError = false;

    public static void main(String[] args) throws IOException{
        if(args.length > 1){
            System.out.println("Usage: trick [script]");
            System.exit(64);
        } else if (args.length == 1){
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /**
     * Runs a source code file to interpret
     * @param path - path of string, i.e the code itself
     * @return: none
     */

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes,Charset.defaultCharset()));
        
        if(hadError) System.exit(65);
    }
    
    /**
     * Runs code interpretation line by line using stdin
     * @param none
     * @return none
     */
    private static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print("> ");
            String line = reader.readLine();
            if(line == null) break;
            run(line); //|&&&&&&| hadError exit needed here?
            hadError = false;
        }

    }

    /**
     * What happens once we pass some code and want to "run the interpeter" - FOR NOW JUST OUTPUTTING TOKENS
     * @param source - source code
     * @return none
     */
    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        //for now we will be printing the tokens we make |**************************|
         for(Token token : tokens){
            System.out.println(token);
         }
    }

    /**
     * Basic error handling method and its helper, tells your there is an error and where - stdout
     * @param line - Line number of error location
     * @param message - Error message
     * @return none
     */
    static void error(int line, String message){
        report(line, "", message);
    }
    
    /*|************************| Include more advanced error reporting, rather than just the line 
    specify where excatly in the line with a string message*/
    private static void report(int line, String where, String message){ 
        System.err.println("[line " + line + "] Error" + where + ":" + message);
        hadError = true;
    }
}