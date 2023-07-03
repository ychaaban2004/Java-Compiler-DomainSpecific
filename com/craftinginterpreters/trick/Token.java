package com.craftinginterpreters.trick;
/*Token instantiation abstracted in a class */
class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
<<<<<<< HEAD
    /*Construct to instantiate token objectts with passed values for instance field instantiation
     * @param: token type - trick's enum object, lexeme name - string, line number - int
=======

    /**
     * Construct to instantiate token objectts with passed values for instance field instantiation
     * @param type - trick's enum object 
     * @param name - string,
     * @param line - int
>>>>>>> 4ac6a27d214592a0a3a20bd8331dd5ee3784920c
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