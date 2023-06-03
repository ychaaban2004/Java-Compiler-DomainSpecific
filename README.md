# Java-Compiler-DomainSpecific

This is a Java compiler, called **Tricks**, built to understand how compilers are built and with added functionality we deemed would improve the language.

Programming Language Developed: Trick - a domain specific version of Java

## Credits

Please note this language is built upon following the following source very closesly and the credit for the basics functionality and design of the compiler goes to this source:

[Crafting Interpreters](https://craftinginterpreters.com/)

HEAD NOTES:
        - Any mention of "|*********|" indicates an area of improvement or advancement  needed for development
        - Any mention of "|&&&&&&&&&| indicates uncertainties or additions needed for basic functionality

IDEAS FOR IMPLEMENTATION:
        - To quote Crafting Interpreters "Ideally, we would have an actual abstraction, some kind of “ErrorReporter” interface that gets passed to the scanner and parser so that we can swap out different reporting strategies." - may be an opporutnity for AI to play a role


## Planning


Chapter - 5 by 7th June (Ratiq)
Chapter - 4 by 7th June (Youssef)



## Progress




## Documentation

NUMBER LITERALS:
        when performing method calls on numbers, negation will not take precendence over the method
                i.e: print -123.abs(); --> -123