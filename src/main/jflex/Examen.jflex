/* --------------------------Section de Code Utilisateur---------------------*/
package fr.usmb.compilation.examen.eqn;
import java_cup.runtime.Symbol;

%%

/* -----------------Section des Declarations et Options----------------------*/
// nom de la class a generer
%class Lexer
%public
%unicode


// utilisation avec CUP
// nom de la classe generee par CUP qui contient les symboles terminaux
%cupsym sym
// generation analyser lexical pour CUP
%cup

// code a ajouter dans la classe produite
%{

%}

/* definitions regulieres */
int     =   [0-9]+
text    =   [^{}\s]+
espace  =   \s

%%
/* ------------------------Section des Regles Lexicales----------------------*/

/* regles */
{espace}+       { /* pas d'action */ }
"sup"           { return new Symbol(sym.SUP); }
"sub"           { return new Symbol(sym.SUB); }
"for"           { return new Symbol(sym.FOR); }
"to"            { return new Symbol(sym.TO); }
"bar"           { return new Symbol(sym.BAR); }
"size"          { return new Symbol(sym.SIZE); }
"{"             { return new Symbol(sym.GAUCHE); }
"}"             { return new Symbol(sym.DROITE); }
{int}           { return new Symbol(sym.INT, yytext()); }
{text}          { return new Symbol(sym.TEXT, yytext()); }
/* .               { return new Symbol(sym.error); } */
