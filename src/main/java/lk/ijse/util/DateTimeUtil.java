package lk.ijse.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String timenow(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd                                                                                    HH:mm:ss");
        return sdf.format(new Date());
    }
    public static String datenow(){
        return null;
    }
}
