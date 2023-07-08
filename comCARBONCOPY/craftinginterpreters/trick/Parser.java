package comCARBONCOPY.craftinginterpreters.trick.craftinginterpreters.trick;

import java.util.List;

import static
comCARBONCOPY.craftinginterpreters.trick.TokenType.*;

class Parser {
    private final List<Token> tokens;
    private int current = 0;
    
    Parser(List<Token> tokens){
        this tokens = tokens;
    }

    private Expr expression(){
        return equality();
    }

    
}
