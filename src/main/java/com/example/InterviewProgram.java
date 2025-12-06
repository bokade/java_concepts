package com.example;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InterviewProgram {
    public static void main(String[] args) {
        List<Integer> list = List.of(2, 4, 6, 7,3,2,5,7,4);

        Map<Integer, Integer> map = new LinkedHashMap();
        for(int i = 0; i<list.size(); i++){
            map.put(list.get(i), map.getOrDefault(list.get(i),0)+1);
        }


        for(Map.Entry<Integer,Integer> m: map.entrySet()){
            if(m.getValue()>1){
                System.out.println(m.getKey()+" : "+m.getValue());
            }
        }


        Map<Integer, Long> mapp = list.stream().collect(Collectors.groupingBy(e -> e, LinkedHashMap::new, Collectors.counting()));

        mapp.entrySet().stream().filter(e -> e.getValue() > 1).forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));

    }
}
