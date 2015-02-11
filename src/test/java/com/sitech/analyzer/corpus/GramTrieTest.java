package com.sitech.analyzer.corpus;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sitech.analyzer.corpus.GramTrie;

/**
 *
 * @author hb
 */
public class GramTrieTest {
    private final GramTrie trie = new GramTrie();
    @Before
    public void setUp() {
        trie.put("杨尚川", 100);
        trie.put("杨尚喜", 99);
        trie.put("杨尚丽", 98);
        trie.put("中华人民共和国", 1);
    }
    @After
    public void tearDown() {
        trie.clear();
    }
    @Test
    public void testClear() {
        assertEquals(100, trie.get("杨尚川"), 0);
        assertEquals(1, trie.get("中华人民共和国"), 0);
        trie.clear();
        assertEquals(0, trie.get("杨尚川"), 0);
        assertEquals(0, trie.get("中华人民共和国"), 0);
    }
    @Test
    public void testGet() {
        assertEquals(100, trie.get("杨尚川"), 0);
        assertEquals(99, trie.get("杨尚喜"), 0);
        assertEquals(98, trie.get("杨尚丽"), 0);
        assertEquals(1, trie.get("中华人民共和国"), 0);
        assertEquals(0, trie.get("杨"), 0);
        assertEquals(0, trie.get("杨尚"), 0);
    }
}