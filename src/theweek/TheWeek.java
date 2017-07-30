/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theweek;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author YousifK
 */
public class TheWeek {

    public static void main(String[] args) throws FileNotFoundException,
            ParseException, IOException {

        String line;
        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> data2 = new ArrayList<>();
        ArrayList<String> data3 = new ArrayList<>();
        ArrayList<String> data4 = new ArrayList<>();
        ArrayList<Map<String, Integer>> data6 = new ArrayList<>();
        Map<String, Integer> MapData6 = new IdentityHashMap<>();
        ArrayList<String> data7 = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        BufferedReader br = new BufferedReader(new FileReader("D:\\data.csv"));

        while ((line = br.readLine()) != null) {
            String[] ItemColumn = line.split(",");
            if (!ItemColumn[5].equals("NO ANSWER")) {
                data.add(ItemColumn[0].substring(0, ItemColumn[0].length() - 3));
            }
            if (Integer.parseInt(ItemColumn[4]) > 0) {
                c.setTime(inputFormat.parse(ItemColumn[0]));
                for (int i = 0; i < Integer.parseInt(ItemColumn[3]) - Integer.parseInt(ItemColumn[4]); i++) {
                    data2.add(Long.toString(c.getTimeInMillis() + (Integer.parseInt(ItemColumn[3]) - Integer.parseInt(ItemColumn[4])) * 1000 + i * 1000));
                }
            }

            data3.add(ItemColumn[1] + "+" + ItemColumn[2]);

            if (ItemColumn[5].equals("ANSWERED")) {
                data4.add(ItemColumn[2]);
            }

            MapData6.put(ItemColumn[1], Integer.parseInt(ItemColumn[4]));

            data7.add(ItemColumn[1]);

        }
        br.close();

        System.out.println("1. peak minute of incoming phonecalls. :  " + MaxArrayList(data));
        String Requirement2 = MaxArrayList(data2);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        System.out.println("2. peak time of simultaneous phonecalls. :  "
                + Collections.frequency(data2, Requirement2) + " Peack Second : " + sf.format(Long.parseLong(Requirement2)));

        String Requirement3 = MaxArrayList(data3);
        System.out.println("3. Biggest relationship between a client and an employee. :  "
                + Requirement3.substring(Requirement3.lastIndexOf("+") + 1) + "  C : " + Requirement3.substring(0, Requirement3.length() - 3));

        System.out.println("4. most productive employee :  " + MaxArrayList(data4));
        System.out.println("5. least productive employee :  " + MinArrayList(data4));

        data6.add(MapData6);
        Map<String, Integer> NewMapData6 = MaxMapTwoValue(data6);
        String reLongestTime = Collections.max(NewMapData6.entrySet(), Map.Entry.comparingByValue()).getValue().toString();
        String reLongestTimeUUID = Collections.max(NewMapData6.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("6. client with longest talk time. " + reLongestTimeUUID + "  Time : " + reLongestTime
                + " Hours : " + Double.parseDouble(reLongestTime) / 3600);

        System.out.println("7. client with most frequent calls.  " + MaxArrayList(data7));

    }

    static String MaxArrayList(ArrayList<String> al) {
        return al.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Entry::getValue))
                .get()
                .getKey();

    }

    static String MinArrayList(ArrayList<String> al) {
        return al.stream()
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet()
                .stream()
                .min(Comparator.comparing(Entry::getValue))
                .get()
                .getKey();
    }

    static Map<String, Integer> MaxMapTwoValue(ArrayList<Map<String, Integer>> maps) {
        Map<String, Integer> result = new HashMap<>();
        maps.forEach((map) -> {
            map.entrySet().forEach((entry) -> {
                int newValue = entry.getValue();
                Integer existingValue = result.get(entry.getKey());
                if (existingValue != null) {
                    newValue = newValue + existingValue;
                }
                result.put(entry.getKey(), newValue);
            });
        });
        return result;
    }
}
