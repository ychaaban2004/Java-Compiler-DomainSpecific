# Expected Functionality


## Data Types

- Boolean - Logic implementation such as **True** and **False** 
``` java
result = true;
result = false;
```

- Numbers - Integers, doubles and floats

```java
result = 1234;
result = 1.234;
```

- String - Characters and string words and statements

```java
result = 'a';
result = "word";
result = "This is a statement";
```

- Null - A symbol to specify **null** value or and empty string

## Expression

- Arithmetic (only numbers) 
	- Basic mathematical expressions. These are **binary operands** as they take only 2 arguments and are in a *infix* notation (there exists *prefix* and *postfix*);
		```java
		a+b; //Addition
		a-b; //Subtraction
		a*b; //Multiplication
		a/b; //Division
		```
	- Negation - Negation can also take only one operand.
		``` java
		-a; //Negation
		```
	- Addition of Strings - The addition symbol can also be used to  concatenate two strings or string and character or two character together into a string.

		```java
		result = "apple" + "orange";
		// result is appleorange;
		```

- Comparison and Equality
	- Basic comparison variables.
		```java
		a < b //Less than
		a > b //Grater than
		a <= b //Less than or equal to
		a >= b //Greater than or equal to
		```

	- Equality - Symbol can also be used to compare the value between 2 variables as well can return error when different types of variables are compared.

		```java 
		1 == 1; //Equal to
		"cat" != "dog"; //Not equal to 
		5 = "5"; //Type error
		```

- Logical Operators
	- Not operator

		```java
		!true; //false
		!false; //true
		```

	- And operator - Returns the first operand if it is false, or the right operand otherwise.
		```java
		true and false; //false
		true and true; //true
		```
	- Or operator - Returns the left operand if it is true and the right operand otherwise.
		```java
		true or false; //true
		false or false; //false
		```

## Precedence and Grouping

Using brackets to manually change the precedence of certain operations.

- Possible addition
	- Bitwise operations
	- Shifting
	- Modulo
	- Conditional operators


## Statements

Expressions produce a *value* whereas statements produces an *effect* (modifying some state, reading input or producing output).

```java
{
	System.out.println("First statement");
	System.out.println("Second statement");
}
```


## Variables

Variables are declared using the **datatype** followed by the variable name, often followed by the value of the variable. If not stated, it defaults to **NULL**.

```java
int num = 10;
int num2; //Null value variable
```


## Control flow

Control flow means the ability to skip some code or execute some lines of code multiple times.

- If statements
	```java
	if (condition)
		print "yes";
	else 
		print "no";
```

- While loop - executes the code while the condition statement is true.
	```java
	int a = 0;
	while (a<10)
		print "yes";
	a++;
```

- For loop 
	```java
	for(variable initialisation; condition; increment or decrement)
		print "yes";
```


- Possible addition
	- Do while statements
	- For-in and Foreach loop (Requires dynamic dispatch)


## Functions

Functions can be defined by declaring the *return type* along with the name and the inputs to the function. (**Parameter** is the variable that holds the value of the argument (a in this situation), whereas **Argument** is the actual value passed into the function done during the function call).

```java
void print(int a) {
	System.out.println(a);
}
```

If execution reaches the end of the block without hitting a *return*, it implicitly returns **NULL**.


