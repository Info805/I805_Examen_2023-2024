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

class BaseTest {

    @Nested
    @DisplayName("Arbre abstrait")
    class TestParser {
        @Test
        void rawText() throws Exception {
            Reader in = new StringReader("x=2");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.TEXT, a.getType());
            assertEquals("x=2", a.getValue());
        }

        @Test
        void rawInt() throws Exception {
            Reader in = new StringReader("2201");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.TEXT, a.getType());
            assertEquals("2201", a.getValue());
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
            assertEquals(Arbre.NodeType.SEQ, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("x=2", a.getFg().getValue());
            assertEquals("2201", a.getFd().getValue());
        }

        @Test
        @DisplayName("sequence of EQN expresssion are rigth assoc")
        void seqComposed() throws Exception {
            Reader in = new StringReader("x=2 2201 y=1");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SEQ, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.SEQ, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("x=2", a.getFg().getValue());
            assertEquals("2201", a.getFd().getFg().getValue());
            assertEquals("y=1", a.getFd().getFd().getValue());
        }

        @Test
        void sub() throws Exception {
            Reader in = new StringReader("a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUB, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        void sup() throws Exception {
            Reader in = new StringReader("a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUP, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        void bar() throws Exception {
            Reader in = new StringReader("bar a");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.BAR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertNull(a.getFd());
            assertEquals("a", a.getFg().getValue());
        }

        @Test
        void size() throws Exception {
            Reader in = new StringReader("size 24 a");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SIZE, a.getType());
            assertEquals("24", a.getValue());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertNull(a.getFd());
            assertEquals("a", a.getFg().getValue());
        }

        @Test
        void forTo() throws Exception {
            Reader in = new StringReader("∫ for 0 to ∞");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("∫", a.getFg().getValue());
            assertEquals("0", a.getFd().getFg().getValue());
            assertEquals("∞", a.getFd().getFd().getValue());
        }

        @Test
        void group() throws Exception {
            Reader in = new StringReader("{x=2}");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.TEXT, a.getType());
            assertEquals("x=2", a.getValue());
        }
        @Test
        @DisplayName("sup is right assoc")
        void supRightAssoc() throws Exception {
            Reader in = new StringReader("a sup b sup c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUP, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.SUP, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getFg().getValue());
            assertEquals("c", a.getFd().getFd().getValue());
        }

        @Test
        @DisplayName("sub is right assoc")
        void subRightAssoc() throws Exception {
            Reader in = new StringReader("a sub b sub c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUB, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.SUB, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getFg().getValue());
            assertEquals("c", a.getFd().getFd().getValue());
        }

        @Test
        @DisplayName("sub priority is not higher than sup")
        void subSup() throws Exception {
            Reader in = new StringReader("a sub b sup c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUB, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.SUP, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getFg().getValue());
            assertEquals("c", a.getFd().getFd().getValue());
        }

        @Test
        @DisplayName("sup priority is not higher than sub")
        void supSub() throws Exception {
            Reader in = new StringReader("a sup b sub c");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUP, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals(Arbre.NodeType.SUB, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("a", a.getFg().getValue());
            assertEquals("b", a.getFd().getFg().getValue());
            assertEquals("c", a.getFd().getFd().getValue());
        }

        @Test
        @DisplayName("bar priority is higher than sub")
        void barSub() throws Exception {
            Reader in = new StringReader("bar a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUB, a.getType());
            assertEquals(Arbre.NodeType.BAR, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        @DisplayName("bar priority is higher than sup")
        void barSup() throws Exception {
            Reader in = new StringReader("bar a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUP, a.getType());
            assertEquals(Arbre.NodeType.BAR, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        @DisplayName("size priority is higher than sup")
        void sizeSup() throws Exception {
            Reader in = new StringReader("size 8 a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUP, a.getType());
            assertEquals(Arbre.NodeType.SIZE, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        @DisplayName("size priority is higher than sub")
        void sizeSub() throws Exception {
            Reader in = new StringReader("size 8 a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SUB, a.getType());
            assertEquals(Arbre.NodeType.SIZE, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals("a", a.getFg().getFg().getValue());
            assertEquals("b", a.getFd().getValue());
        }

        @Test
        @DisplayName("for to operator is non assoc")
        void forToAssoc() throws Exception {
            Reader in = new StringReader("∫ for 0 to Σ for 1 to 2");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            assertThrows(Exception.class, () -> p.parse());
        }

        @Test
        @DisplayName("sub priority is higher than for to - 1")
        void forToSub() throws Exception {
            Reader in = new StringReader("∫ for 0 to a sub b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals("∫", a.getFg().getValue());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            Arbre a2 = a.getFd().getFd();
            assertEquals(Arbre.NodeType.SUB, a2.getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFd().getType());
            assertEquals("a", a2.getFg().getValue());
            assertEquals("b", a2.getFd().getValue());
        }

        @Test
        @DisplayName("sub priority is higher than for to - 2")
        void subForTo() throws Exception {
            Reader in = new StringReader("∫ sub b for 0 to 10");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            assertEquals("10", a.getFd().getFd().getValue());
            Arbre a2 = a.getFg();
            assertEquals(Arbre.NodeType.SUB, a2.getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFd().getType());
            assertEquals("∫", a2.getFg().getValue());
            assertEquals("b", a2.getFd().getValue());
        }

        @Test
        @DisplayName("sup priority is higher than for to - 1")
        void forToSup() throws Exception {
            Reader in = new StringReader("∫ for 0 to a sup b");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals("∫", a.getFg().getValue());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            Arbre a2 = a.getFd().getFd();
            assertEquals(Arbre.NodeType.SUP, a2.getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFd().getType());
            assertEquals("a", a2.getFg().getValue());
            assertEquals("b", a2.getFd().getValue());
        }

        @Test
        @DisplayName("sup priority is higher than for to - 2")
        void supForTo() throws Exception {
            Reader in = new StringReader("∫ sup b for 0 to 10");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            assertEquals("10", a.getFd().getFd().getValue());
            Arbre a2 = a.getFg();
            assertEquals(Arbre.NodeType.SUP, a2.getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a2.getFd().getType());
            assertEquals("∫", a2.getFg().getValue());
            assertEquals("b", a2.getFd().getValue());
        }

        @Test
        @DisplayName("seq priority is lower than for to - 1")
        void forToSeq() throws Exception {
            Reader in = new StringReader("∫ for 0 to 10 i");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre b = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SEQ, b.getType());
            assertEquals(Arbre.NodeType.TEXT, b.getFd().getType());
            assertEquals("i", b.getFd().getValue());
            Arbre a = b.getFg();
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals("∫", a.getFg().getValue());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            assertEquals("10", a.getFd().getFd().getValue());
        }

        @Test
        @DisplayName("seq priority is lower than for to - 2")
        void seqForTo() throws Exception {
            Reader in = new StringReader("i ∫ for 0 to 10");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre b = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SEQ, b.getType());
            assertEquals(Arbre.NodeType.TEXT, b.getFg().getType());
            assertEquals("i", b.getFg().getValue());
            Arbre a = b.getFd();
            assertEquals(Arbre.NodeType.FOR, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getType());
            assertEquals("∫", a.getFg().getValue());
            assertEquals(Arbre.NodeType.TO, a.getFd().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getFd().getType());
            assertEquals("0", a.getFd().getFg().getValue());
            assertEquals("10", a.getFd().getFd().getValue());
        }


        @Test
        @DisplayName("braces can be used to group eqn expressions")
        void brace1() throws Exception {
            Reader in = new StringReader("{x=2 2201} y=1");

            Lexer lexer = new Lexer(in);
            @SuppressWarnings("deprecation")
            parser p = new parser(lexer);
            Symbol res = p.parse();
            Arbre a = (Arbre)res.value;
            assertEquals(Arbre.NodeType.SEQ, a.getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFd().getType());
            assertEquals(Arbre.NodeType.SEQ, a.getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFg().getType());
            assertEquals(Arbre.NodeType.TEXT, a.getFg().getFd().getType());
            assertEquals("x=2", a.getFg().getFg().getValue());
            assertEquals("2201", a.getFg().getFd().getValue());
            assertEquals("y=1", a.getFd().getValue());
        }
    }
}
