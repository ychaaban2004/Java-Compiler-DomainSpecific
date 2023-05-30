package com.craftinginterpreters.trick;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.net.ssl.SNIHostName;

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
    IDENTIFIER, STRING, NUMBER,

    //Keyword tokens (reserved names)
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}

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

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes,Charset.defaultCharset()));
        
        if(hadError) System.exit(65);
    }

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

    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        //for now we will be printing the tokens we make |**************************|
         for(Token token : tokens){
            System.out.println(token);
         }
    }

    static void error(int line, String message){
        report(line, "", message);
    }
    //|************************| Include more advanced error reporting, rather than just the line specify where excatly in the line with a string message
    private static void report(int line, String where, String message){ 
        System.err.println("[line " + line + "] Error" + where + ":" + message);
        hadError = true;
    }
}