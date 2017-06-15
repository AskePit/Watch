package com.pitm.watch;

import org.junit.Test;

import com.pitm.watch.pitm.PitmParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Aske on 30.04.2017.
 */

public class PitmTest {

    static String streamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    static void printMap(StringBuilder out, HashMap<String, Object> map, int depth) {
        out.append("{\r\n");
        ++depth;
        for(HashMap.Entry<String, Object> entry : map.entrySet()) {
            for(int i = 0; i<depth; ++i) out.append("    ");
            out.append(entry.getKey() + ' ');
            Object value = entry.getValue();
            if(value.getClass() == String.class) {
                out.append((String)value + ",\r\n");
            } else if(value.getClass() == HashMap.class) {
                printMap(out, (HashMap<String, Object>)value, depth);
            } else {
                printArray(out, (ArrayList<Object>)value, depth);
            }
        }
        for(int i = 0; i<depth-1; ++i) out.append("    ");
        out.append("},\r\n");
    }

    static void printArray(StringBuilder out, ArrayList<Object> array, int depth) {
        out.append("[\r\n");
        ++depth;
        for(Object value : array) {
            for(int i = 0; i<depth; ++i) out.append("    ");
            if(value.getClass() == String.class) {
                out.append((String)value + ",\r\n");
            } else if(value.getClass() == HashMap.class) {
                printMap(out, (HashMap<String, Object>)value, depth);
            } else {
                printArray(out, (ArrayList<Object>)value, depth);
            }
        }
        for(int i = 0; i<depth-1; ++i) out.append("    ");
        out.append("],\r\n");
    }

    @Test
    public void testPitmParser() throws Exception {
        InputStream is = getClass().getResourceAsStream("z_7086_1.pitm");
        String data = streamToString(is);
        HashMap<String, Object> map = PitmParser.parse(data);

        StringBuilder mapString = new StringBuilder();
        printMap(mapString, map, 0);
        assertEquals(mapString.toString(), data);
    }
}
