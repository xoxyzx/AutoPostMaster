import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class POST_HttpURLConnectionIG {
    private static HttpURLConnection post_connection;
    //private BullShit bull; 
    public void posthandler(int picturenum, String text) throws Exception
    {
      
        PicturesIG choosepicture = new PicturesIG();
        String pictureurl = choosepicture.getpicture(picturenum);
        String pictureurlencode = java.net.URLEncoder.encode(pictureurl,"utf-8");
        String textencode = java.net.URLEncoder.encode(text,"utf-8");
        //URL
        String Postfirst1 = "https://graph.facebook.com/v14.0/17841452024837206/media?image_url=";
        String ca = "&caption=";
        String postsecond1 = "https://graph.facebook.com/v14.0/17841452024837206/media_publish?creation_id=";
        
        String acc="&access_token=EAAKGbn2zuv4BADJXkiQRdgqwlLzAsK35c2gv4uCPT6mit88VgJX1rn6WMRjI1RkLZC4HoDZAJ4w5Upe3tyk0dkjOtgiffWTsqPWo5rzljfX6ZCt1TgWmqapMjecvINl08E0IfxjeKh3eXz6EVEfiUuHOaRIZCIe2SYMoT0wxXEYIBzudxejU";
        String result1= Postfirst1 + pictureurlencode + ca + textencode + acc;
        String postfinal = "";
        String returnid = "";
        String trimreturnid = "";
        //URL Parameters
        //String Post_URL_Parameters = "/";
        // Get Bytes of parameters
        //byte[] Post_Data = Post_URL_Parameters.getBytes(StandardCharsets.UTF_8);
        // System.out.printf("posting hello");
        try {
            URL Demo_URL = new URL(result1);
            post_connection = (HttpURLConnection) Demo_URL.openConnection();
            post_connection.setDoOutput(true);
            //Set the method
            post_connection.setRequestMethod("POST");
            //The request property
            post_connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //write the bytes or the data to the URL connection.
            /*try (DataOutputStream Data_Output_Stream = new DataOutputStream(post_connection.getOutputStream())) {
                Data_Output_Stream.write(Post_Data);
            }*/

            //Get the content
            StringBuilder Post_Webpage_Content;
            try (BufferedReader webpage = new BufferedReader(
                    new InputStreamReader(post_connection.getInputStream()))) {
                String Webpage_line;
                Post_Webpage_Content = new StringBuilder();
                while ((Webpage_line = webpage.readLine()) != null) {
                    Post_Webpage_Content.append(Webpage_line);
                    Post_Webpage_Content.append(System.lineSeparator());
                }
            }
            System.out.println(Post_Webpage_Content.toString());
            
            returnid = Post_Webpage_Content.toString();
        }
        catch(Exception ex)
        {
            System.out.printf("bad");
            System.out.println(ex);
        } 
        finally {
            post_connection.disconnect();
        }


        System.out.println("returned=" +  returnid);
        for(int i=0; i<returnid.length(); i++)
        {
            if(returnid.charAt(i)<='9'&& returnid.charAt(i)>='0')
            {
                trimreturnid += returnid.charAt(i);
            }
        }
        System.out.println("trimreturned=" + trimreturnid);
        postfinal = postsecond1 + trimreturnid + acc;
        System.out.println(postfinal);

        try {
            URL Demo_URL = new URL(postfinal);
            post_connection = (HttpURLConnection) Demo_URL.openConnection();
            post_connection.setDoOutput(true);
            //Set the method
            post_connection.setRequestMethod("POST");
            //The request property
            post_connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //write the bytes or the data to the URL connection.
            /*try (DataOutputStream Data_Output_Stream = new DataOutputStream(post_connection.getOutputStream())) {
                Data_Output_Stream.write(Post_Data);
            }*/

            //Get the content
            StringBuilder Post_Webpage_Content;
            try (BufferedReader webpage = new BufferedReader(
                    new InputStreamReader(post_connection.getInputStream()))) {
                String Webpage_line;
                Post_Webpage_Content = new StringBuilder();
                while ((Webpage_line = webpage.readLine()) != null) {
                    Post_Webpage_Content.append(Webpage_line);
                    Post_Webpage_Content.append(System.lineSeparator());
                }
            }
            System.out.println(Post_Webpage_Content.toString());
            

        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, "上傳錯誤 排程只能在10分鐘後到75天前");
            System.out.println(ex);
        } 
        finally {
            post_connection.disconnect();
        }
    }
       
    
}