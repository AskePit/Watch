package com.pitm.watch;

import org.junit.Test;

import com.pitm.watch.pitm.PitmParser;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Aske on 30.04.2017.
 */

public class PitmTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<String, Object> map = PitmParser.parse("{\n" +
                "  id z_7086_1\n" +
                "  bitmap z_7086_1/z_7086_1.jpg\n" +
                "  \n" +
                "  axises [\n" +
                "    { id main,   pos { x 600 y 621 } }\n" +
                "    { id left,   pos { x 482 y 595 } }\n" +
                "    { id right,  pos { x 710 y 597 } }\n" +
                "    { id bottom, pos { x 596 y 739 } }\n" +
                "  ]\n" +
                "  \n" +
                "  scales [\n" +
                "    {\n" +
                "      id hour\n" +
                "      val    { min 0 max 12 }\n" +
                "      degree { min 0 max 360 }\n" +
                "    }\n" +
                "    {\n" +
                "      id minsec\n" +
                "      val    { min 0 max 60 }\n" +
                "      degree { min 0 max 360 }\n" +
                "    }\n" +
                "    {\n" +
                "      id deci\n" +
                "      val    { min 0 max 10 }\n" +
                "      degree { min 0 max 360 }\n" +
                "    }\n" +
                "  ]\n" +
                "  \n" +
                "  hands [\n" +
                "    { \n" +
                "      id hour\n" +
                "      view {\n" +
                "        axis main\n" +
                "        bitmap z_7086_1/hour.png\n" +
                "        joint 0\n" +
                "        zorder 2\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          scale hour\n" +
                "          role hour\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "    { \n" +
                "      id minute\n" +
                "      view {\n" +
                "        axis main\n" +
                "        bitmap z_7086_1/minute.png\n" +
                "        joint 0\n" +
                "        zorder 3\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          scale minsec\n" +
                "          role minute\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "    {\n" +
                "      id second\n" +
                "      view {\n" +
                "        axis right\n" +
                "        bitmap z_7086_1/mini.png\n" +
                "        joint 0\n" +
                "        zorder 1\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          scale minsec\n" +
                "          role second\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "    {\n" +
                "      id chrono_second\n" +
                "      view {\n" +
                "        axis main\n" +
                "        bitmap z_7086_1/second.png\n" +
                "        joint 0\n" +
                "        zorder 4\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          scale minsec\n" +
                "          role chrono_second\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "    {\n" +
                "      id chrono_minute\n" +
                "      view {\n" +
                "        axis left\n" +
                "        bitmap z_7086_1/mini.png\n" +
                "        joint 0\n" +
                "        zorder 1\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          scale minsec\n" +
                "          role chrono_minute\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "    {\n" +
                "      id chrono_hour\n" +
                "      view {\n" +
                "        axis right\n" +
                "        bitmap z_7086_1/mini.png\n" +
                "        joint 0\n" +
                "        zorder 1\n" +
                "      }\n" +
                "      roles [\n" +
                "        {\n" +
                "          states [before30min]\n" +
                "          scale deci\n" +
                "          role chrono_decisecond\n" +
                "        }\n" +
                "        {\n" +
                "          states [after30min]\n" +
                "          scale deci\n" +
                "          role chrono_hour\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "  \n" +
                "  triggers [\n" +
                "    {\n" +
                "      id A\n" +
                "\t  rect { x 710 y 650 width 10 height 10 }\n" +
                "      states []\n" +
                "    }\n" +
                "    {\n" +
                "      id B\n" +
                "\t  rect { x 710 y 650 width 10 height 10 }\n" +
                "      states []\n" +
                "    }\n" +
                "  ]\n" +
                "  \n" +
                "  chrono {\n" +
                "    hands [chrono_second, chrono_minute, chrono_hour]\n" +
                "    buttons [A, B]\n" +
                "    states [init stopped running freezed]\n" +
                "    automat [\n" +
                "      { from init, action A, to running }\n" +
                "      { from running, action A, to stopped }\n" +
                "      { from running, action B, to freezed }\n" +
                "      { from freezed, action A, to stopped }\n" +
                "      { from freezed, action B, to running }\n" +
                "      { from stopped, action A, to running }\n" +
                "      { from stopped, action B, to init }\n" +
                "    ]\n" +
                "  }\n" +
                "}");
        System.out.print(map);
    }
}
