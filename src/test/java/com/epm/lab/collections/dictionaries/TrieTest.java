package com.epm.lab.collections.dictionaries;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrieTest {

    @Test
    void incrementSize() {
        //GIVEN
        Trie sut = new Trie();
        //WHEN
        sut.add("hello");
        //THEN
        assertEquals(1, sut.size());
        //WHEN
        sut.add("world");
        //THEN
        assertEquals(2, sut.size());
    }

    @Test
    void countByPrefix() {
        //GIVEN
        Trie sut = new Trie();
        //WHEN
        sut.add("hello");
        //THEN
        assertEquals(1, sut.countByPrefix("hell"));
    }

    @Test
    void countByPrefixSubstringsMatter() {
        //GIVEN
        Trie sut = new Trie();
        //WHEN
        sut.add("hello");
        sut.add("hell");
        //THEN
        assertEquals(2, sut.countByPrefix("hell"));
    }

    @Test
    void findByPrefix() {
        //GIVEN
        Trie sut = new Trie();
        //WHEN
        sut.add("seven");
        sut.add("hello");
        sut.add("hell");
        sut.add("test");
        //WHEN
        List<String> all = sut.startWith("hell");
        //THEN
        assertEquals(2, all.size());
        assertEquals(all.get(0), "hell");
        assertEquals(all.get(1), "hello");
    }

    @Test
    void findByPrefixInverted() {
        //GIVEN
        Trie sut = new Trie();
        //WHEN
        sut.add("hell");
        sut.add("hello");
        //WHEN
        List<String> all = sut.startWith("hell");
        //THEN
        assertEquals(2, all.size());
        assertEquals(all.get(0), "hell");
        assertEquals(all.get(1), "hello");
    }

}