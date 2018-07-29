package com.epm.lab.collections.dictionaries;

import com.epm.lab.collections.Dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Trie implements Dictionary {

    private static class Node {
        Node[] children = new Node[26];
        boolean word;
        int count;

        Node getOrCreateChild(char c) {
            int index = c - 'a';
            if (children[index] == null) {
                children[index] = new Node();
            }
            return children[index];
        }

        Node get(char c) {
            return children[c - 'a'];
        }

    }

    private final Node root = new Node();

    @Override
    public void add(String word) {
        requireNonEmptyLowercaseString(word, "word");
        // Left for simplicity. Can be replaced with a similar traversal
        List<Node> visited = new ArrayList<>();
        visited.add(root);
        Node currNode = root;
        for (int i = 0; i < word.length(); i++) {
            currNode = currNode.getOrCreateChild(word.charAt(i));
            visited.add(currNode);
        }
        if (!currNode.word) {
            currNode.word = true;
            visited.forEach(node -> node.count++);
        }
    }

    @Override
    public List<String> startWith(String prefix) {
        requireNonEmptyLowercaseString(prefix, "prefix");
        Node node = getNodeByPrefix(prefix);
        if (node == null) {
            return Collections.emptyList();
        }
        List<String> output = new ArrayList<>();
        dfs(node, output, new StringBuilder(prefix));
        return output;
    }

    private static void dfs(Node root, List<String> output, StringBuilder current) {
        if (root.word) {
            output.add(current.toString());
        }
        for (int i = 0; i < root.children.length; i++) {
            Node child = root.children[i];
            if (child != null) {
                //choose
                current.append((char) ('a' + i));
                //explore
                dfs(child, output, current);
                //revert
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    @Override
    public int countByPrefix(String prefix) {
        requireNonEmptyLowercaseString(prefix, "prefix");
        Node currNode = getNodeByPrefix(prefix);
        return currNode == null ? 0 : currNode.count;
    }

    private Node getNodeByPrefix(String prefix) {
        Node currNode = root;
        for (int i = 0; i < prefix.length(); i++) {
            currNode = currNode.get(prefix.charAt(i));
            if (currNode == null) {
                return null;
            }
        }
        return currNode;
    }

    @Override
    public int size() {
        return root.count;
    }

    private static void requireNonEmptyLowercaseString(String str, String argName) {
        Objects.requireNonNull(str, argName);
        if (argName.isEmpty()) {
            throw new IllegalArgumentException(argName + ": empty strings are not allowed");
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLowerCase(c)) {
                throw new IllegalArgumentException("Illegal character '" + c + "'");
            }
        }
    }

}
