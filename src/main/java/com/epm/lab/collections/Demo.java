package com.epm.lab.collections;

import com.epm.lab.collections.dictionaries.Trie;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.nio.file.Files;

public class Demo {

    private enum Action {
        COUNT,
        PRINT
    }

    @Option(name = "-d", required = true, usage = "dictionary")
    private File dictionaryFile;

    @Option(name = "-w", required = true, usage = "word")
    private String[] words;

    @Argument(usage = "action", required = true, metaVar = "action")
    private Action action;

    private final Dictionary dictionary = new Trie();

    public void doMain(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        if (args.length == 0) {
            System.out.println("Simple dictionary. Usage: ");
            parser.printUsage(System.out);
            return;
        } else {
            parser.parseArgument(args);
        }
        // What's wrong here?
        Files.readAllLines(dictionaryFile.toPath()).forEach(dictionary::add);
        switch (action) {
            case COUNT:
                count();
                break;
            case PRINT:
                print();
                break;
            default:
                throw new AssertionError("No way!");
        }
    }

    private void print() {
        for (String word : words) {
            System.out.println(dictionary.startWith(word));
        }
    }

    private void count() {
        for (String word : words) {
            System.out.println(word + ": " + dictionary.countByPrefix(word));
        }
    }

    public static void main(String[] args) throws Exception {
        new Demo().doMain(args);
    }

}
