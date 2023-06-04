package com.craftinginterpreters.trick;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.FlavorException;

import static com.craftinginterpreters.trick.TokenType.*;

/*Class where we scan through code and tokenize the source */
class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>(); 
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private static final Map<String,TokenType> keywords;

    static{
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

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

    /*The identifier of all the words to determine what we send to the tokenizer
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
            case '!':
                addToken(match('=') ? BANG_EQUAL: BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/':
                if(match('/')){
                    while(peek() != '\n' && !isAtEnd()) advance();
                }
                else{
                    addToken(SLASH);
                }
                break;
            //below skips over chars we dont care about: i.e whitespace
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            //begin more complex tokens such as strings
            case '"': string(); break;

            default:
                /* |************| This can be problematic as a blob of 
                 * random text will return error for each char of that blob
                 * improve this by reporting blobs of unknown text as one error
                */
                if(isDigit(c)){
                    number();
                }
                else if(isAlpha(c)){
                    identifier();
                }
                else{
                    trick.error(line, "Unexpected character.");
                }
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

    /*Checks for more complex operations such as != and <= treating
     *  them as a single token, telling us if we have matched such 
     * a case or are still within the simple cases 
     * @param: char to check - char
     * @return: if we have matched a more complex case - boolean
    */
    private boolean match(char expected){
        if(isAtEnd()) return false;
        if(source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    /*Lookahead method similar to match but here we will use this to continuelly check
     * for the next character till we reach EOF or EOL, doesnt consume chars
     * @param:none
     * @return: identifying char of end of commentting system - char
     */
    private char peek(){
        if(isAtEnd()) return '\0';
        return source.charAt(current);
    }

    //STRING LITERALS

    /*handles the tokenization parameters upon encountering a string
     * @param:none
     * @return:none
     */
    private void string(){
        //this means we can have multi line strings, may want a different implementation
        // |************|
        while(peek() != '"' && !isAtEnd()){
            if(peek() == '\n') line++;
            advance();
        }

        if(isAtEnd()){
            trick.error(line, "Unterminated string.");
            return;
        }

        //advance past the closing "
        advance();
        //|*************| \n is not supported for strings - may want to implement this
        //grab the string as object and send it as our object in the token class
        String value = source.substring(start + 1, current-1);
        addToken(STRING, value);
    }

    // NUMBER LITERALS

    /*Checks if the char at hand is a digit value
     * @param: the present char in the source file - char
     * @return: is it a digit or not - boolean
     */
    private boolean isDigit(char c){
        return c >='0' && c <= '9';
    }

    /*Analyzes the whole number and any . parts to take the whole lexeme and send it for tokenization
     * @param: none
     * @return: none
     */
    private void number(){
        while(isDigit(peek())) advance();

        //fraction
        if(peek() == '.' && isDigit(peekNext())){
            advance();

            while(isDigit(peek())) advance();
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    /*An further peek method, this is the most we will peek at a time - 2 chars ahead - and this seocndary function makes that clear
     * @param: none
     * @return: what is the char 2 steps ahead - char
     */
    private char peekNext(){
        if(current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    //RESEVED WORDS AND IDENTIFIERS

    private void identifier(){
        while(isAlphaNumeric(peek())) advance();
        //Reserved Words Catcher
        String text = source.substring(start, current);
        TokenType type = keywords.get(text);
        if(type == null) type = IDENTIFIER;
        addToken(type);
    }

    private boolean isAlpha(char c){
        return(c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNumeric(char c){
        return isAlpha(c) || isDigit(c);
    }

}
