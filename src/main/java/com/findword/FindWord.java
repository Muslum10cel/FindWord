/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.findword;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author SirOncel
 */
public class FindWord {

    private static final String URL = "https://randomwordgenerator.com/json/words.json";
    private static String WORD = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static StringBuilder dashed = null;

    public static void main(String[] args) throws IOException {

        boolean lost = true;
        char play;
        System.out.print("Do You Want To Play? (y/n) : ");
        play = scanner.nextLine().charAt(0);
        if (play == 'q') {
            System.out.println("Good Bye...");
            System.exit(0);
        }

        JSONArray jSONArray = readJsonFromUrl(URL).getJSONArray("data");

        int len = jSONArray.length();
        Random random = new Random();
        int wordIndex = random.nextInt(len);

        System.out.println("Let\'s Find The Word...");
        WORD = jSONArray.getJSONObject(wordIndex).getString("word").toUpperCase();

        char[] chars = new char[WORD.length()];
        Arrays.fill(chars, '_');
        dashed = new StringBuilder(new String(chars));

        String suggest = "";
        System.out.println("Word is consist of " + WORD.length() + " Letter");
        System.out.println("The Word Is : " + dashed.toString());

        int attempt = WORD.length();

        while (attempt != 0) {
            System.out.print("Guess The Letter : ");
            suggest = scanner.nextLine().toUpperCase();

            if (!suggest.matches("[a-zA-Z]")) {
                System.out.println("Please Enter A Letter");
                continue;
            }

            --attempt;
            if (WORD.contains(suggest)) {
                System.out.println("Congrats! Word Contains " + suggest + " And I Will Show The Places Of It...");
                System.out.println("After Replacement : " + replaceSuggest(suggest));
                if (!dashed.toString().contains("_")) {
                    System.out.println("Congratulations! You Have Found The Word And It Is : " + WORD);
                    lost = false;
                    break;
                }
            } else if (attempt != 0) {
                System.out.println("Keep Trying! You Still " + attempt + " Chances");
            }

        }

        if (lost) {
            System.out.println("You Lost! The Word Was " + WORD);
        }

    }

    private static String replaceSuggest(String suggest) {
        for (int i = 0; i < WORD.length(); i++) {
            if (String.valueOf(WORD.charAt(i)).equals(suggest)) {
                dashed.replace(i, i + 1, suggest);
            }
        }
        return dashed.toString();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }
}
