package com.craftinginterpreters.trick;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.trick.TokenType.*;

/*Class where we scan through code and tokenize the source */
class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>(); 
    private int start = 0;
    private int current = 0;
    private int line = 1;

    /*Construct will instantiate the source code with the provided source file for each scanner object
     * @param: source file - string
     * @return: scanner object - object
     */
    Scanner(String source){
        this.source = source;
    }

    /*Produceds a list of token classes rather than token objects, as we only want to instantiate
     *  the objects when we need them in the moment thus saving memory in a current run time 
     * @param: none
     * @return: list of token classes - list<class>
    */
    List<Token> scanTokens(){
        while(!isAtEnd()){
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
    
    /*Helper method to determine if we have reached the end of the reading file
     * @param: none
     * @return: have we finished reading? - boolean
     */
    private boolean isAtEnd(){
        return current >= source.length(); 
    }

    /*scans to produce tokens assuming each token will be one char long and moving at a 1 char pace
     * @param: none
     * @return: none
     */
    private void scanToken(){
        char c = advance();
        switch(c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            
            default:
                /* |************| This can be problematic as a blob of 
                 * random text will return error for each char of that blob
                 * improve this by reporting blobs of unknown text as one error
                */
                trick.error(line, "Unexpected character.");
                break;
        }
    }

    /*Gives us the right char to tokenize moving along the code
     * @param: none
     * @return: the char to tokenize - char
     */
    private char advance(){
        return source.charAt(current++);
    }
    
    /*Polymorphed method taking the simple commands of the swithc statement in method "scanToken" and understanding how to fill
     * in the other parameters of tokenization
     * @param: the enum object of what kind of token we have - enum object
     * @return: none
     */
    private void addToken(TokenType type){
        addToken(type,null);
    }
    
    /*Continuation of the polymorphed method setting the token object to be filled with the correct enum object type
     * setting the Object to be null as we are not working with literals wit these single char tokens
     * filling in the line number and what the actual string of the token is
     * @param: what token type it is - enum object, object type - object superclass
     * @return: none
     */
    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type,text,literal,line));
    }
}
