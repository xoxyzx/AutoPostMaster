import org.json.*;
import org.json.JSONObject;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BullShit {
    private static final int  max_int=1000;
    private String [] famous=new String[max_int]; 
    private String [] before=new String[max_int];
    private String [] after=new String[max_int];
    private String [] bull=new String[max_int];
    private final int max_length=500;
    public BullShit(String topic,int words) {
           Path path = Paths.get("data.json"); // read json data

        try {
            String content = Files.readString(path);
            //System.out.println(content);

            JSONObject obj = new JSONObject(content);
            JSONArray arr = obj.getJSONArray("famous");
            JSONArray arr1 = obj.getJSONArray("before");
            JSONArray arr2 = obj.getJSONArray("after");
            JSONArray arr3 = obj.getJSONArray("bullshit");
            for(int i=0;i<arr.length();i++){//convert jason array to string
            famous[i]=arr.getString(i);

             }
              shuffle(famous,arr.length());// shuffle string famous
             for(int i=0;i<arr1.length();i++){
                before[i]=arr1.getString(i);
                }
                shuffle(before,arr1.length());
            for(int i=0;i<arr2.length();i++){
                 after[i]=arr2.getString(i);
                }
                shuffle(after,arr2.length());
            for(int i=0;i<arr3.length();i++){
                        bull[i]=arr3.getString(i);
                 }
                 shuffle(bull,arr3.length());
       }catch (IOException e){
            e.printStackTrace();
        }

       String article=Generate(topic,words);
       //System.out.println(article);
    }
    public void shuffle (String [] str,int len){
        Random r= new Random();
     
       for(int first=0;first<len;first++){
		
			int second = r.nextInt(len);
			String temp = str[first];
			str[first] = str[second];
			str[second] = temp;
       }
      
    }

    public boolean CanEnd(String str){// check  last char 
      //char [] runstring=new char [1000]; 
        //str=runstring;
      if(str.substring(str.length()-1)=="。")
        return true;
       else if(str.substring(str.length()-1)=="?")
       return true;
       else 
          return false;
    }

    public String Generate(String topic,int words){
      Random r=new Random();
      int f=0,a=0,be=0,bul=0;
      int len=0;
      String article=null;
      while(len<=words){
          int x=r.nextInt(100);
          if(x<5&&CanEnd(article)){
              article+="\n  ";
               
            }
         else if(x<30){
            String temp=famous[f].replace("a",before[be] ).replace("b",after[a]);
            a++;be++;f++;
            
            len+=temp.length();
           article+= temp;
           
        }

          else {
              String temp=bull[bul].replace("x", topic);
              bul++;
               len+=temp.length();
              article+=temp;
            }
        
        }
        article=article.substring(4);// delete null
       //System.out.println(article.substring(article.length()-1));
        return article;
    }


}