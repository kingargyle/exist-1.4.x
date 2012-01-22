// $ANTLR 2.7.7 (2006-11-01): "XQuery.g" -> "XQueryParser.java"$

	package org.exist.xquery.parser;

	import antlr.debug.misc.*;
	import java.io.StringReader;
	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.Iterator;
	import java.util.Stack;
	import org.exist.storage.BrokerPool;
	import org.exist.storage.DBBroker;
	import org.exist.storage.analysis.Tokenizer;
	import org.exist.EXistException;
	import org.exist.dom.DocumentSet;
	import org.exist.dom.DocumentImpl;
	import org.exist.dom.QName;
	import org.exist.security.PermissionDeniedException;
	import org.exist.security.User;
	import org.exist.xquery.*;
	import org.exist.xquery.value.*;
	import org.exist.xquery.functions.*;

public interface XQueryTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int QNAME = 4;
	int PREDICATE = 5;
	int FLWOR = 6;
	int PARENTHESIZED = 7;
	int ABSOLUTE_SLASH = 8;
	int ABSOLUTE_DSLASH = 9;
	int WILDCARD = 10;
	int PREFIX_WILDCARD = 11;
	int FUNCTION = 12;
	int UNARY_MINUS = 13;
	int UNARY_PLUS = 14;
	int XPOINTER = 15;
	int XPOINTER_ID = 16;
	int VARIABLE_REF = 17;
	int VARIABLE_BINDING = 18;
	int ELEMENT = 19;
	int ATTRIBUTE = 20;
	int ATTRIBUTE_CONTENT = 21;
	int TEXT = 22;
	int VERSION_DECL = 23;
	int NAMESPACE_DECL = 24;
	int DEF_NAMESPACE_DECL = 25;
	int DEF_COLLATION_DECL = 26;
	int DEF_FUNCTION_NS_DECL = 27;
	int GLOBAL_VAR = 28;
	int FUNCTION_DECL = 29;
	int PROLOG = 30;
	int OPTION = 31;
	int ATOMIC_TYPE = 32;
	int MODULE = 33;
	int ORDER_BY = 34;
	int GROUP_BY = 35;
	int POSITIONAL_VAR = 36;
	int MODULE_DECL = 37;
	int MODULE_IMPORT = 38;
	int SCHEMA_IMPORT = 39;
	int ATTRIBUTE_TEST = 40;
	int COMP_ELEM_CONSTRUCTOR = 41;
	int COMP_ATTR_CONSTRUCTOR = 42;
	int COMP_TEXT_CONSTRUCTOR = 43;
	int COMP_COMMENT_CONSTRUCTOR = 44;
	int COMP_PI_CONSTRUCTOR = 45;
	int COMP_NS_CONSTRUCTOR = 46;
	int COMP_DOC_CONSTRUCTOR = 47;
	int PRAGMA = 48;
	int GTEQ = 49;
	int SEQUENCE = 50;
	int LITERAL_xpointer = 51;
	int LPAREN = 52;
	int RPAREN = 53;
	int NCNAME = 54;
	int LITERAL_xquery = 55;
	int LITERAL_version = 56;
	int SEMICOLON = 57;
	int LITERAL_module = 58;
	int LITERAL_namespace = 59;
	int EQ = 60;
	int STRING_LITERAL = 61;
	int LITERAL_declare = 62;
	int LITERAL_default = 63;
	// "boundary-space" = 64
	int LITERAL_ordering = 65;
	int LITERAL_construction = 66;
	// "base-uri" = 67
	// "copy-namespaces" = 68
	int LITERAL_option = 69;
	int LITERAL_function = 70;
	int LITERAL_variable = 71;
	int LITERAL_import = 72;
	int LITERAL_encoding = 73;
	int LITERAL_collation = 74;
	int LITERAL_element = 75;
	int LITERAL_order = 76;
	int LITERAL_empty = 77;
	int LITERAL_greatest = 78;
	int LITERAL_least = 79;
	int LITERAL_preserve = 80;
	int LITERAL_strip = 81;
	int LITERAL_ordered = 82;
	int LITERAL_unordered = 83;
	int COMMA = 84;
	// "no-preserve" = 85
	int LITERAL_inherit = 86;
	// "no-inherit" = 87
	int DOLLAR = 88;
	int LCURLY = 89;
	int RCURLY = 90;
	int COLON = 91;
	int LITERAL_external = 92;
	int LITERAL_schema = 93;
	int LITERAL_as = 94;
	int LITERAL_at = 95;
	// "empty-sequence" = 96
	int QUESTION = 97;
	int STAR = 98;
	int PLUS = 99;
	int LITERAL_item = 100;
	int LITERAL_for = 101;
	int LITERAL_let = 102;
	int LITERAL_some = 103;
	int LITERAL_every = 104;
	int LITERAL_if = 105;
	int LITERAL_typeswitch = 106;
	int LITERAL_update = 107;
	int LITERAL_replace = 108;
	int LITERAL_value = 109;
	int LITERAL_insert = 110;
	int LITERAL_delete = 111;
	int LITERAL_rename = 112;
	int LITERAL_with = 113;
	int LITERAL_into = 114;
	int LITERAL_preceding = 115;
	int LITERAL_following = 116;
	int LITERAL_where = 117;
	int LITERAL_return = 118;
	int LITERAL_in = 119;
	int LITERAL_by = 120;
	int LITERAL_stable = 121;
	int LITERAL_ascending = 122;
	int LITERAL_descending = 123;
	int LITERAL_group = 124;
	int LITERAL_satisfies = 125;
	int LITERAL_case = 126;
	int LITERAL_then = 127;
	int LITERAL_else = 128;
	int LITERAL_or = 129;
	int LITERAL_and = 130;
	int LITERAL_instance = 131;
	int LITERAL_of = 132;
	int LITERAL_treat = 133;
	int LITERAL_castable = 134;
	int LITERAL_cast = 135;
	int BEFORE = 136;
	int AFTER = 137;
	int LITERAL_eq = 138;
	int LITERAL_ne = 139;
	int LITERAL_lt = 140;
	int LITERAL_le = 141;
	int LITERAL_gt = 142;
	int LITERAL_ge = 143;
	int GT = 144;
	int NEQ = 145;
	int LT = 146;
	int LTEQ = 147;
	int LITERAL_is = 148;
	int LITERAL_isnot = 149;
	int ANDEQ = 150;
	int OREQ = 151;
	int LITERAL_to = 152;
	int MINUS = 153;
	int LITERAL_div = 154;
	int LITERAL_idiv = 155;
	int LITERAL_mod = 156;
	int PRAGMA_START = 157;
	int PRAGMA_END = 158;
	int LITERAL_union = 159;
	int UNION = 160;
	int LITERAL_intersect = 161;
	int LITERAL_except = 162;
	int SLASH = 163;
	int DSLASH = 164;
	int LITERAL_text = 165;
	int LITERAL_node = 166;
	int LITERAL_attribute = 167;
	int LITERAL_comment = 168;
	// "processing-instruction" = 169
	// "document-node" = 170
	int LITERAL_document = 171;
	int SELF = 172;
	int XML_COMMENT = 173;
	int XML_PI = 174;
	int LPPAREN = 175;
	int RPPAREN = 176;
	int AT = 177;
	int PARENT = 178;
	int LITERAL_child = 179;
	int LITERAL_self = 180;
	int LITERAL_descendant = 181;
	// "descendant-or-self" = 182
	// "following-sibling" = 183
	int LITERAL_parent = 184;
	int LITERAL_ancestor = 185;
	// "ancestor-or-self" = 186
	// "preceding-sibling" = 187
	int DOUBLE_LITERAL = 188;
	int DECIMAL_LITERAL = 189;
	int INTEGER_LITERAL = 190;
	// "schema-element" = 191
	int END_TAG_START = 192;
	int QUOT = 193;
	int APOS = 194;
	int QUOT_ATTRIBUTE_CONTENT = 195;
	int ESCAPE_QUOT = 196;
	int APOS_ATTRIBUTE_CONTENT = 197;
	int ESCAPE_APOS = 198;
	int ELEMENT_CONTENT = 199;
	int XML_COMMENT_END = 200;
	int XML_PI_END = 201;
	int XML_CDATA = 202;
	int LITERAL_collection = 203;
	int LITERAL_validate = 204;
	int XML_PI_START = 205;
	int XML_CDATA_START = 206;
	int XML_CDATA_END = 207;
	int LETTER = 208;
	int DIGITS = 209;
	int HEX_DIGITS = 210;
	int NMSTART = 211;
	int NMCHAR = 212;
	int WS = 213;
	int EXPR_COMMENT = 214;
	int PREDEFINED_ENTITY_REF = 215;
	int CHAR_REF = 216;
	int S = 217;
	int NEXT_TOKEN = 218;
	int CHAR = 219;
	int BASECHAR = 220;
	int IDEOGRAPHIC = 221;
	int COMBINING_CHAR = 222;
	int DIGIT = 223;
	int EXTENDER = 224;
}
