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

class SizeTest {

    @Nested
    @DisplayName("Test du calcul des tailles")
    class TestCalcSize {
        @Test
        void rawText() throws Exception {
            Reader in = new StringReader("x=2");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
        }

        @Test
        void rawInt() throws Exception {
            Reader in = new StringReader("2201");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(12, a.getFd().getSize());
        }

        @Test
        void sub() throws Exception {
            Reader in = new StringReader("a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
        }

        @Test
        void sup() throws Exception {
            Reader in = new StringReader("a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
        }

        @Test
        void bar() throws Exception {
            Reader in = new StringReader("bar a");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
        }

        @Test
        void size() throws Exception {
            Reader in = new StringReader("size 24 a");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(24, a.getSize());
            assertEquals(24, a.getFg().getSize());
        }

        @Test
        void forTo() throws Exception {
            Reader in = new StringReader("∫ for 0 to ∞");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(8, a.getFd().getFd().getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(6, a.getFd().getFd().getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(6, a.getFd().getFd().getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(6, a.getFd().getFd().getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(6, a.getFd().getFd().getSize());
        }

        @Test
        @DisplayName("bar sub")
        void barSub() throws Exception {
            Reader in = new StringReader("bar a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(12, a.getFg().getFg().getSize());
            assertEquals(8, a.getFd().getSize());
        }

        @Test
        @DisplayName("bar sup")
        void barSup() throws Exception {
            Reader in = new StringReader("bar a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(12, a.getFg().getFg().getSize());
            assertEquals(8, a.getFd().getSize());
        }

        @Test
        @DisplayName("sub bar")
        void subBar() throws Exception {
            Reader in = new StringReader("a sub bar b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
        }

        @Test
        @DisplayName("sup bar")
        void supBar() throws Exception {
            Reader in = new StringReader("a sup bar b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
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
            a.calcSize();
            assertEquals(8, a.getSize());
            assertEquals(8, a.getFg().getSize());
            assertEquals(8, a.getFg().getFg().getSize());
            assertEquals(6, a.getFd().getSize());
        }

        @Test
        @DisplayName("size sub")
        void sizeSub() throws Exception {
            Reader in = new StringReader("size 8 a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(8, a.getSize());
            assertEquals(8, a.getFg().getSize());
            assertEquals(8, a.getFg().getFg().getSize());
            assertEquals(6, a.getFd().getSize());
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
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(10, a.getFd().getSize());
            assertEquals(10, a.getFd().getFg().getSize());
        }

        @Test
        @DisplayName("sub size")
        void subSize() throws Exception {
            Reader in = new StringReader("a sub size 10 b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(10, a.getFd().getSize());
            assertEquals(10, a.getFd().getFg().getSize());
        }

        @Test
        @DisplayName("for to sub")
        void forToSub() throws Exception {
            Reader in = new StringReader("∫ for 0 to a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(8, a.getFd().getFd().getSize());
            assertEquals(8, a.getFd().getFd().getFg().getSize());
            assertEquals(6, a.getFd().getFd().getFd().getSize());
        }

        @Test
        @DisplayName("for sub to")
        void forSubTo() throws Exception {
            Reader in = new StringReader("∫ for a sub b to 5");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(8, a.getFd().getFd().getSize());
            assertEquals(8, a.getFd().getFg().getFg().getSize());
            assertEquals(6, a.getFd().getFg().getFd().getSize());
        }

        @Test
        @DisplayName("sub for to")
        void subForTo() throws Exception {
            Reader in = new StringReader("∫ sub b for 0 to 10");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            a.calcSize();
            assertEquals(12, a.getSize());
            assertEquals(12, a.getFg().getSize());
            assertEquals(8, a.getFd().getSize());
            assertEquals(8, a.getFd().getFg().getSize());
            assertEquals(8, a.getFd().getFd().getSize());
            assertEquals(12, a.getFg().getFg().getSize());
            assertEquals(8, a.getFg().getFd().getSize());
        }
    }
}
