import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConversion {
    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.TAIWAN); //Specify your locale

    public long timeConversion(String time) {
        long unixTime = 0;
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); //Specify your timezone
        try {
            unixTime = dateFormat.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return unixTime;
    }
}