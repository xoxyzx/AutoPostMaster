import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class POST_HttpURLConnection {
    private static HttpURLConnection post_connection;
    //private BullShit bull; 
    public void posthandler(int picturenum, String text, long posttime) throws Exception
    {
        
        Pictures choosepicture = new Pictures();
        String pictureurl = choosepicture.getpicture(picturenum);
        String posttimestr = posttime + "";
        //URL
        String message = java.net.URLEncoder.encode(text,"utf-8");
        String Post_URL = "https://graph.facebook.com/v14.0/116632261037742/photos?message=";
        
        String field2 = "&url=";
        String field3 = "&scheduled_publish_time=";
        String field4 = "&published=false";
        String acc="&access_token=EAAKGbn2zuv4BAN4TEXftXjgwW9RFFufoZBnXy8eZBIBhMSWDZBQ9ZCGb2PjGalI3vkRIxLYCNYLewHZAUdLgtZBELH2vNYykZC8ZBRa8BaaK5PsKE2LSCdpLFrTU1nPAixPL6SxZA3IZCZBzLn8bUsqar32uREQlt8OZCYj8dGj2EKHve5OLDUwqroed";
        String result;
        if(posttime==0)
        {
             System.out.printf("posting nothing");
            result=Post_URL + message + field2 + pictureurl + acc;
        }
        else
        {
            result=Post_URL + message + field2 + pictureurl + field4 + field3 + posttimestr + acc;
        }
        
        //URL Parameters
        //String Post_URL_Parameters = "/";
        // Get Bytes of parameters
        //byte[] Post_Data = Post_URL_Parameters.getBytes(StandardCharsets.UTF_8);
        
        try {
            URL Demo_URL = new URL(result);
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
            JOptionPane.showMessageDialog(null, "上傳成功");

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