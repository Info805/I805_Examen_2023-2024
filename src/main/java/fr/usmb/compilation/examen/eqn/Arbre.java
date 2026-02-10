package fr.usmb.compilation.examen.eqn;

public class Arbre {

    public enum NodeType {
        SUB, SUP, FOR, TO, BAR, SIZE, TEXT, SEQ
    }

    private NodeType type;
    private String value;
    private Arbre fg, fd;
    private int size = 12;

    public Arbre(NodeType type, String value, Arbre fg, Arbre fd) {
        this.type = type;
        this.value = value;
        this.fg = fg;
        this.fd = fd;
    }
    public Arbre(NodeType type, Arbre fg, Arbre fd) {
        this(type, null, fg, fd);
    }
    public Arbre(NodeType type, Arbre fg) {
        this(type, fg, null);
    }
    public Arbre(NodeType type, String value) {
        this(type, value, null, null);
    }
    public Arbre(NodeType type) {
        this(type, null, null, null);
    }
    public Arbre(NodeType type, String v, Arbre fg) {
        this(type, v, fg, null);
    }
    public NodeType getType() {
        return type;
    }
    public void setType(NodeType type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Arbre getFg() {
        return fg;
    }
    public void setFg(Arbre fg) {
        this.fg = fg;
    }
    public Arbre getFd() {
        return fd;
    }
    public void setFd(Arbre fd) {
        this.fd = fd;
    }
    public int getSize() {
        return size;
    }

    public int calcSize() {
        return this.calcSize(12);
    }

    public int calcSize(int currentSize) {
        switch (type) {
        case SUB :
        case SUP :
        case FOR :
            if (this.fg != null) currentSize = this.fg.calcSize(currentSize);
            // if (this.fd != null) this.fd.calcSize(currentSize * 2 / 3);
            // Arrondi a l'entier sup
            if (this.fd != null) this.fd.calcSize((currentSize * 2 + 2) / 3);
            this.size = currentSize;
            break;
        case SEQ :
            if (this.fg != null) this.fg.calcSize(currentSize);
            if (this.fd != null) this.fd.calcSize(currentSize);
            this.size = currentSize;
            break;
        case BAR :
        case TO :
            if (this.fg != null) currentSize = this.fg.calcSize(currentSize);
            if (this.fd != null) currentSize = this.fd.calcSize(currentSize);
            this.size = currentSize;
            break;
        case SIZE :
            currentSize = Integer.parseInt(this.value);
            if (this.fg != null) this.fg.calcSize(currentSize);
            this.size = currentSize;
            break;
        case TEXT :
            this.size = currentSize;
            break;
        }
        return this.size;
    }

    public String toString() {
        return this.toMathML();
    }

    // Arbre sous forme prefixée
    // public String toString() {
    //     return "(" + this.type + " " + this.value + " " + this.fg + " " + this.fd + "-" + this.size + "-" + ")";
    // }

    public String toMathML() {
        this.calcSize();
        StringBuilder res = new StringBuilder();
        res.append("<math>");
        this.toMathML(res);
        res.append("</math>");
        return res.toString();
    }

    public void toMathML(StringBuilder res) {
        switch (type) {
        case SUB :
            res.append("<msub>");
            this.fg.toMathML(res);
            this.fd.toMathML(res);
            res.append("</msub>");
            break;
        case SUP :
            res.append("<msup>");
            this.fg.toMathML(res);
            this.fd.toMathML(res);
            res.append("</msup>");
            break;
        case FOR :
            res.append("<munderover>");
            this.fg.toMathML(res);
            this.fd.toMathML(res);
            res.append("</munderover>");
            break;
        case BAR :
            res.append("<mover>");
            this.fg.toMathML(res);
            res.append("<mo>");
            res.append("&#x2500;");
            res.append("</mo>");
            res.append("</mover>");
            break;
        case TEXT :
            res.append("<mtext mathsize='").append(200*this.size/12).append("%'>");
            res.append(this.value);
            res.append("</mtext>");
            break;
        case SEQ :
            res.append("<mrow>");
            if (this.fg != null) this.fg.toMathML(res);
            res.append("<mspace mathsize='").append(200*this.size/12).append("%' width='0.11em'/>");
            if (this.fd != null) this.fd.toMathML(res);
            res.append("</mrow>");
            break;
        case SIZE :
            if (this.fg != null) this.fg.toMathML(res);
            break;
        case TO :
            if (this.fg != null) this.fg.toMathML(res);
            if (this.fd != null) this.fd.toMathML(res);
            break;
        }
    }
}
