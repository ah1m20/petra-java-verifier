package energymanagement;

import java.time.LocalDateTime;

public class CsvLogger {
    public static void log(String... values){
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now());
        sb.append(",");
        sb.append(values[0]);
        for (int i=1;i<values.length;i++){
            sb.append(","+values[i]);
        }
        System.out.println(sb);
    }
}
