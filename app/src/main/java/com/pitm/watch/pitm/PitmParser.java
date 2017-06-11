package com.pitm.watch.pitm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Aske on 30.04.2017.
 */

public class PitmParser {
    public static Map<String, Object> parse(String data) throws ParseException {
        return new PitmParserInternal().parse(data);
    }

    static class ParseException extends Exception {
        private String m_message;

        ParseException(String message) {
            m_message = message;
        }

        String message() {
            return m_message;
        }
    }

    static class InputEndException extends Exception {}

    static class PitmParserInternal {
        private String m_in;
        private HashMap<String, Object> m_out; {
            m_out = new HashMap<String, Object>();
        }

        private int m_pos = 0;
        private StringBuilder m_key;
        private StringBuilder m_literal;
        private HashMap<String, Object> m_object;
        private ArrayList<Object> m_array;
        private ContainerType m_container;

        enum ContainerType {
            Object,
            Array,
        }
        private Stack<String> m_keyStack; {
            m_keyStack = new Stack<String>();
        }
        private Stack<ContainerType> m_containerStack; {
            m_containerStack = new Stack<ContainerType>();
        }
        private Stack<Object> m_parentStack; {
            m_parentStack = new Stack<Object>();
        }

        private char curr() throws InputEndException {
            if(m_pos >= m_in.length()) {
                throw new InputEndException();
            }
            return m_in.charAt(m_pos);
        }

        private void shift() {
            ++m_pos;
        }

        private char shift_post() throws InputEndException {
            ++m_pos;
            return curr();
        }

        private char shift_pre() throws InputEndException {
            char c = curr();
            ++m_pos;
            return c;
        }

        private void eat(char c) throws ParseException, InputEndException {
            eatSpace();
            if(curr() == c) {
                shift();
            } else {
                throw new ParseException("Expected " + c + ", got " + curr());
            }
            eatSpace();
        }

        private void eatSpace() {
            try {
                if (!Character.isWhitespace(curr())) {
                    return;
                }
                while (Character.isWhitespace(shift_post()));
            } catch (InputEndException e) {
                return;
            }
        }

        private String removeComments(String str) {
            return str;
        }

        Map<String, Object> parse(String data) throws ParseException {
            m_in = removeComments(data.trim());
            parseDocument();
            return m_out;
        }

        private void parseDocument() throws ParseException {
            m_object = m_out;
            m_container = ContainerType.Object;
            parseObject();
        }

        private void parseObject() throws ParseException {
            try {
                eat('{');
                parseFields();
                eat('}');
            } catch (InputEndException e) {
                throw new ParseException("Could not parse object");
            }

            if(m_parentStack.isEmpty()) {
                return;
            }

            if(m_containerStack.pop() == ContainerType.Object) {
                HashMap<String, Object> p = (HashMap<String, Object>)m_parentStack.pop();
                m_object = p;
                m_container = ContainerType.Object;
            } else {
                ArrayList<Object> p = (ArrayList<Object>)m_parentStack.pop();
                m_array = p;
                m_container = ContainerType.Array;
            }
        }

        private void parseFields() throws ParseException {
            try {
                eatSpace();
                char c = curr();

                while (c != '}') {
                    if (Character.isJavaIdentifierStart(c)) {
                        parseField();
                    } else {
                        throw new ParseException("Expected key, got " + c);
                    }
                    eatSpace();
                    c = curr();
                }
            } catch (InputEndException e) {
                return;
            }
        }

        private void parseField() throws ParseException, InputEndException {
            parseKey();
            parseValue();
            parseComma();
        }

        private void parseComma() throws ParseException, InputEndException {
            if(curr() == ',') {
                shift();
            }
            eatSpace();
        }

        private void parseKey() throws ParseException {
            try {
                m_key = new StringBuilder();
                m_key.append(shift_pre());

                while (Character.isJavaIdentifierPart(curr())) {
                    m_key.append(shift_pre());
                }
                m_keyStack.push(m_key.toString());
                eatSpace();
            } catch (InputEndException e) {
                return;
            }
        }

        private void parseValue() throws ParseException {
            char c;
            try {
                c = curr();
            } catch (InputEndException e) {
                return;
            }

            if(c != '{' && c != '[') {
                parseLiteral();
                return;
            }

            Object newContainer;
            ContainerType newType = (c == '[') ? ContainerType.Array : ContainerType.Object;
            if(newType == ContainerType.Array) {
                newContainer = new ArrayList<Object>();
            } else {
                newContainer = new HashMap<String, Object>();
            }

            if(m_container == ContainerType.Object) {
                m_object.put(m_keyStack.pop(), newContainer);
                m_parentStack.push(m_object);
            } else {
                m_array.add(newContainer);
                m_parentStack.push(m_array);
            }
            m_containerStack.push(m_container);
            m_container = newType;

            if(newType == ContainerType.Array) {
                m_array = (ArrayList<Object>)newContainer;
                parseArray();
            } else {
                m_object = (HashMap<String, Object>)newContainer;
                parseObject();
            }
        }

        private void parseLiteral() {
            try {
                m_literal = new StringBuilder();
                m_literal.append(shift_pre());

                while (!Character.isWhitespace(curr()) && curr() != ',' && curr() != ']' && curr() != '}') {
                    m_literal.append(shift_pre());
                }
                eatSpace();
            } catch (InputEndException e) {
                return;
            }

            String literal = m_literal.toString();

            if(m_container == ContainerType.Array) {
                m_array.add(literal);
            } else {
                m_object.put(m_keyStack.pop(), literal);
            }
        }

        private void parseArray() throws ParseException {
            try {
                eat('[');
                parseItems();
                eat(']');
            } catch (InputEndException e) {
                throw new ParseException("Could not parse array");
            }

            if(m_parentStack.isEmpty()) {
                return;
            }

            if(m_containerStack.pop() == ContainerType.Object) {
                HashMap<String, Object> p = (HashMap<String, Object>)m_parentStack.pop();
                m_object = p;
                m_container = ContainerType.Object;
            } else {
                ArrayList<Object> p = (ArrayList<Object>)m_parentStack.pop();
                m_array = p;
                m_container = ContainerType.Array;
            }
        }

        private void parseItems() throws ParseException {
            try {
                eatSpace();
                char c = curr();

                while (c != ']') {
                    if (!Character.isWhitespace(c) && c != '}') {
                        parseItem();
                    } else {
                        throw new ParseException("Expected key, got " + c);
                    }
                    eatSpace();
                    c = curr();
                }
            } catch (InputEndException e) {
                return;
            }
        }

        private void parseItem() throws ParseException, InputEndException {
            parseValue();
            parseComma();
        }
    }
}
