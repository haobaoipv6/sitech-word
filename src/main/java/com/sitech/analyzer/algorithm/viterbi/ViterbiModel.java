package com.sitech.analyzer.algorithm.viterbi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * viterbi模型
 * @author hb
 */
public class ViterbiModel {
	private static Map<Character, char[]> prevStatus;
	private static Map<Character, Double> start;
	private static Map<Character, Map<Character, Double>> trans;
	private static final String PROB_EMIT = "/prob_emit.txt";
    private static char[] states = new char[] {'B', 'M', 'E', 'S'};
    private static Map<Character, Map<Character, Double>> emit;
	
	
	private void loadModel() {
        long s = System.currentTimeMillis();
        prevStatus = new HashMap<Character, char[]>();
        prevStatus.put('B', new char[] {'E', 'S'});
        prevStatus.put('M', new char[] {'M', 'B'});
        prevStatus.put('S', new char[] {'S', 'E'});
        prevStatus.put('E', new char[] {'B', 'M'});

        start = new HashMap<Character, Double>();
        start.put('B', -0.26268660809250016);
        start.put('E', -3.14e+100);
        start.put('M', -3.14e+100);
        start.put('S', -1.4652633398537678);

        trans = new HashMap<Character, Map<Character, Double>>();
        Map<Character, Double> transB = new HashMap<Character, Double>();
        transB.put('E', -0.510825623765990);
        transB.put('M', -0.916290731874155);
        trans.put('B', transB);
        Map<Character, Double> transE = new HashMap<Character, Double>();
        transE.put('B', -0.5897149736854513);
        transE.put('S', -0.8085250474669937);
        trans.put('E', transE);
        Map<Character, Double> transM = new HashMap<Character, Double>();
        transM.put('E', -0.33344856811948514);
        transM.put('M', -1.2603623820268226);
        trans.put('M', transM);
        Map<Character, Double> transS = new HashMap<Character, Double>();
        transS.put('B', -0.7211965654669841);
        transS.put('S', -0.6658631448798212);
        trans.put('S', transS);

        InputStream is = this.getClass().getResourceAsStream(PROB_EMIT);
        try {
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            emit = new HashMap<Character, Map<Character, Double>>();
            Map<Character, Double> values = null;
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("\t");
                if (tokens.length == 1) {
                    values = new HashMap<Character, Double>();
                    emit.put(tokens[0].charAt(0), values);
                } else {
                    values.put(tokens[0].charAt(0), Double.valueOf(tokens[1]));
                }
            }
        } catch (IOException e) {
            System.err.println(String.format("%s: load model failure!", PROB_EMIT));
        } finally {
            try {
                if (null != is) is.close();
            } catch (IOException e) {
                System.err.println(String.format("%s: close failure!", PROB_EMIT));
            }
        }
        System.out.println(String.format("model load finished, time elapsed %d ms.",
                System.currentTimeMillis() - s));
    }
}
