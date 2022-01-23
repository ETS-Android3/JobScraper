package com.job.scrape.Utils;

import java.util.ArrayList;

public class Recommender {
    public static ArrayList<String> recommande(ArrayList<ArrayList<ArrayList<String>>> liste_rules , ArrayList<String> skills ) {
        ArrayList<String> recommandation = new ArrayList<String>();
        int max=0;
        int max_item_recommanded=0;



        for(int i=0;i<liste_rules.get(0).size();i++) {
            int max_items_found=0;
            int recommanded_for_this=0;
            for(int j=0;j<skills.size();j++) {

                if(liste_rules.get(0).get(i).contains(skills.get(j))) {
                    max_items_found++;
                }

            }
            recommanded_for_this=liste_rules.get(2).get(i).size();
            if(max_items_found>max) {
                recommandation=liste_rules.get(2).get(i);
                max=max_items_found;

            }
            else if(max_items_found==max) {
                if(recommanded_for_this>max_item_recommanded) {
                    recommandation=liste_rules.get(2).get(i);
                }
            }
        }

        return recommandation;
    }
}
