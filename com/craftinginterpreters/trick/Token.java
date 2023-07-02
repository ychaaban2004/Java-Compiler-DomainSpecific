package com.craftinginterpreters.trick;
/*Token instantiation abstracted in a class */
class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    /**
     * Construct to instantiate token objectts with passed values for instance field instantiation
     * @param type - trick's enum object 
     * @param name - string,
     * @param line - int
     * @return: toke - object
    */
    Token(TokenType type, String lexeme, Object literal, int line){
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    /**
     * Produces word represenation of the token for us to see the interpreter tokenizing source code
     * @param none
     * @return token as a message - string
     */
    public String toString(){
        return type + " " + lexeme + " " + literal;
    }
}