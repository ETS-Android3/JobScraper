package com.job.scrape.Warehouse;

import com.job.scrape.Models.Company;
import com.job.scrape.Models.Offer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Transformer {

    public static List<Offer> getRekruteOfferList(List<Document> documentList) {

        List<Offer> offerList = new ArrayList<>();

        for (Document document: documentList) {
            Elements scriptElements = document.select("ul.job-list.job-list2 > li");

            int sizeEle = scriptElements.size();
            for (int j = 0; j < sizeEle; j++) {
                try{

                    Elements division = scriptElements.get(j).select("div.section");
                    String title = division.select("h2 > a").text().trim();
                    String link1 = division.select("h2 > a").first().attr("href");
                    String link = "https://www.rekrute.com"+link1;
                    Elements infos = division.select("div.holder");
                    String requirements = infos.get(0).select("span").text();
                    String infoCompany = infos.get(0).select("span").get(1).text();
                    String description = infos.get(0).select("span").get(2).text();
                    //ajouter ville
                    int villeIndex = title.indexOf(" | ");
                    String ville = title.substring(villeIndex+3);
                    //ajouter contrat
                    Elements info = infos.get(0).select("ul");
                    String contrat = info.select("li").get(4).text().substring(26);
                    //ajouter experience
                    String experience = info.select("li").get(2).text().substring(21);

                    String image = scriptElements.get(j).select("div > div > a > img").attr("src");
                    String companyName = scriptElements.get(j).select("div > div > a > img").attr("alt");


                    //get requirements ready
                    String[] technologies ={"react","angular","vuejs","html","css","javascript","python","sql","java","node","typescript","c#","bash","shell","c++"
                            ,"php","flutter","go","kotlin","rust","ruby","dart","assembly","swift","matlab","mysql","postgresql","sqlite","mongodb","redis","firebase","oracle",
                            "aws","docker","heroku","kubernetes","linux","flask","django","asp.net","spring","laravel","tensorflow","react native","keras"};

                    ArrayList<Integer> requirementes = new ArrayList<Integer>();

                    for (int f = 0; f < technologies.length; f++) {
                        if(description.toLowerCase().contains(technologies[f]) || requirements.toLowerCase().contains(technologies[f])){
                            requirementes.add(1);
                        }
                        else{
                            requirementes.add(0);
                        }
                    }

                    String date = infos.get(0).select("em").text();
                    String additionalInfo = infos.get(0).select("ul").text();

                    Offer offre = new Offer(title, null, contrat, date, ville, link, experience, requirementes, new Company(companyName, "https://www.rekrute.com"+image));
                    offerList.add(offre);

                }catch(Exception e){
                    throw e;
                }
            }
        }

        return offerList;
    }

    public static String list2Json(List<Offer> offerList) {
        String json = "[";
        json += "{" +
                String.format("\"title\":\"%s\",", offerList.get(0).getTitle())+
                String.format("\"salary\":\"%s\",", offerList.get(0).getSalary())+
                String.format("\"pubDate\":\"%s\",", offerList.get(0).getPubDate())+
                String.format("\"postType\":\"%s\",", offerList.get(0).getPostType())+
                String.format("\"location\":\"%s\",", offerList.get(0).getLocation())+
                String.format("\"experience\":\"%s\",", offerList.get(0).getExperience())+
                String.format("\"link\":\"%s\",", offerList.get(0).getLink())+
                String.format("\"company\":%s", "{"+
                        String.format("\"name\":\"%s\",", offerList.get(0).getCompany().getName())+
                        String.format("\"image\":\"%s\"", offerList.get(0).getCompany().getImage())+
                        "}")+
                "}";
        if (offerList.size() > 1)
            for (int i = 1; i < offerList.size(); i++) {
                String offerJson = ",{" +
                        String.format("\"title\":\"%s\",", offerList.get(i).getTitle())+
                        String.format("\"salary\":\"%s\",", offerList.get(i).getSalary())+
                        String.format("\"pubDate\":\"%s\",", offerList.get(i).getPubDate())+
                        String.format("\"postType\":\"%s\",", offerList.get(i).getPostType())+
                        String.format("\"location\":\"%s\",", offerList.get(i).getLocation())+
                        String.format("\"experience\":\"%s\",", offerList.get(i).getExperience())+
                        String.format("\"link\":\"%s\",", offerList.get(i).getLink())+
                        String.format("\"company\":%s", "{"+
                                String.format("\"name\":\"%s\",", offerList.get(i).getCompany().getName())+
                                String.format("\"image\":\"%s\"", offerList.get(i).getCompany().getImage())+
                                "}")+
                        "}";
                json += offerJson;
            }

        return json + "]";
    }
}
