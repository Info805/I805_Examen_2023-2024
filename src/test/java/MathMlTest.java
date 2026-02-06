import static org.junit.jupiter.api.Assertions.*;

import java.io.Reader;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import fr.usmb.compilation.examen.eqn.parser;
import fr.usmb.compilation.examen.eqn.Arbre;
import fr.usmb.compilation.examen.eqn.Lexer;
import java_cup.runtime.Symbol;

class MathMlTest {

    @Nested
    @DisplayName("Test generation MathML")
    class TestGenMathMl {
        @Test
        void rawText() throws Exception {
            Reader in = new StringReader("x=2");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><mtext mathsize='200%'>x=2</mtext></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        void rawInt() throws Exception {
            Reader in = new StringReader("2201");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><mtext mathsize='200%'>2201</mtext></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("basic sequence of EQN expresssion")
        void seqBasic() throws Exception {
            Reader in = new StringReader("x=2 2201 ");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><mrow><mtext mathsize='200%'>x=2</mtext><mspace mathsize='200%' width='0.11em'/><mtext mathsize='200%'>2201</mtext></mrow></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        void sub() throws Exception {
            Reader in = new StringReader("a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msub><mtext mathsize='200%'>a</mtext><mtext mathsize='133%'>b</mtext></msub></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        void sup() throws Exception {
            Reader in = new StringReader("a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msup><mtext mathsize='200%'>a</mtext><mtext mathsize='133%'>b</mtext></msup></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        void size() throws Exception {
            Reader in = new StringReader("size 24 a");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><mtext mathsize='400%'>a</mtext></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        void forTo() throws Exception {
            Reader in = new StringReader("∫ for 0 to ∞");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><munderover><mtext mathsize='200%'>∫</mtext><mtext mathsize='133%'>0</mtext><mtext mathsize='133%'>∞</mtext></munderover></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("sup sup")
        void supRightAssoc() throws Exception {
            Reader in = new StringReader("a sup b sup c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msup><mtext mathsize='200%'>a</mtext><msup><mtext mathsize='133%'>b</mtext><mtext mathsize='100%'>c</mtext></msup></msup></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("sub sub")
        void subRightAssoc() throws Exception {
            Reader in = new StringReader("a sub b sub c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msub><mtext mathsize='200%'>a</mtext><msub><mtext mathsize='133%'>b</mtext><mtext mathsize='100%'>c</mtext></msub></msub></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("sub sup")
        void subSup() throws Exception {
            Reader in = new StringReader("a sub b sup c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msub><mtext mathsize='200%'>a</mtext><msup><mtext mathsize='133%'>b</mtext><mtext mathsize='100%'>c</mtext></msup></msub></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("sup sub")
        void supSub() throws Exception {
            Reader in = new StringReader("a sup b sub c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msup><mtext mathsize='200%'>a</mtext><msub><mtext mathsize='133%'>b</mtext><mtext mathsize='100%'>c</mtext></msub></msup></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("size sup")
        void sizeSup() throws Exception {
            Reader in = new StringReader("size 8 a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msup><mtext mathsize='133%'>a</mtext><mtext mathsize='100%'>b</mtext></msup></math>";
            assertEquals(expected, a.toMathML());
        }

        @Test
        @DisplayName("sup size")
        void supSize() throws Exception {
            Reader in = new StringReader("a sup size 10 b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            String expected = "<math><msup><mtext mathsize='200%'>a</mtext><mtext mathsize='166%'>b</mtext></msup></math>";
            assertEquals(expected, a.toMathML());
        }
    }
}
