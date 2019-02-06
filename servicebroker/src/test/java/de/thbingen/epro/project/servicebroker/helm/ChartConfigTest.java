package de.thbingen.epro.project.servicebroker.helm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ChartConfigTest {
    private ChartConfig config;

    @Before
    public void setUp() throws Exception {
        Map<String, Object> confMap = new HashMap<>();

        Map<String, Object> a1Map = new HashMap<>();
        confMap.put("a", a1Map);

        Map<String, Object> a2Map = new HashMap<>();
        a1Map.put("a", a2Map);

        a2Map.put("a", "A3");
        a2Map.put("b", "B3");
        a2Map.put("c", "C3");

        config = new ChartConfig(confMap);
        System.out.println("=S=>" + confMap);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("=T=>" + config.getConfigMap());
    }

    @Test
    public void isSet() {
        assertTrue(config.isSet("a"));
        assertTrue(config.isSet("a.a"));
        assertTrue(config.isSet("a.a.a"));
        assertTrue(config.isSet("a.a.b"));
        assertTrue(config.isSet("a.a.c"));

        assertFalse(config.isSet("a.a.a.d"));
        assertFalse(config.isSet("a.b.a"));
        assertFalse(config.isSet("a.a.a.c"));
    }

    @Test
    public void set() {
        config.set("a.b.c.d", "test");

        Object abcd = config.get("a.b.c.d");
        assertEquals("test", abcd);

        Object aac = config.get("a.a.c");
        assertEquals("C3", aac);
    }

    @Test
    public void get() {
        assertNotNull(config.get("a"));
        assertNotNull(config.get("a.a"));

        assertEquals(config.get("a.a.a"), "A3");
        assertEquals(config.get("a.a.b"), "B3");
        assertEquals(config.get("a.a.c"), "C3");

        assertNull(config.get("b"));
        assertNull(config.get("a.a.d"));
        assertNull(config.get("a.a.a.a"));
        assertNull(config.get("a.a.a.d"));
        assertNull(config.get("a.b.c.d"));
    }

    @Test
    public void mergeFrom() {
        Map<String, Object> confMap = new HashMap<>();

        Map<String, Object> aMap = new HashMap<>();
        confMap.put("a", aMap);
        aMap.put("c", "C");

        Map<String, Object> aaMap = new HashMap<>();
        aMap.put("a", aaMap);

        aaMap.put("a", "A3");
        aaMap.put("b", "B3");
        aaMap.put("c", "C3");

        Map<String, Object> abMap = new HashMap<>();
        aMap.put("b", abMap);

        abMap.put("a", "A31");
        abMap.put("b", "B31");
        abMap.put("c", "C31");

        Map<String, Object> aabMap = new HashMap<>();
        aaMap.put("b", aabMap);

        aabMap.put("a", "A4");
        aabMap.put("b", "B4");
        aabMap.put("c", "C4");

        ChartConfig from = new ChartConfig(confMap);
        System.out.println("FROM");
        System.out.println(from.getConfigMap());

        config.mergeFrom(from);


        assertEquals("B4", config.get("a.a.b.b"));
        assertEquals("C", config.get("a.c"));
        assertNotEquals("B3", config.get("a.a.b"));
    }
}