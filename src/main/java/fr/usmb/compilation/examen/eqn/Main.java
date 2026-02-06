package fr.usmb.compilation.examen.eqn;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import java_cup.runtime.Symbol;

public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lexer;
        if (args.length > 0)
            lexer = new Lexer(new FileReader(args[0]));
        else {
            // Reader in0 = new StringReader("{ ∫ for 0 to ∞} { {size 24 x} sup 3 }");
            Reader in0 = new StringReader("bar {{Σ for i=1 to ∞} i sup k sub i}");

            lexer = new Lexer(in0);
            //lexer = new Lexer(new InputStreamReader(System.in));
        }
        @SuppressWarnings("deprecation")
        parser p = new parser(lexer);
        Symbol res = p.parse();
        Arbre a = (Arbre)res.value;
        System.out.println(a);
    }
}
