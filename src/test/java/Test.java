import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class Test {
 
    private static final String SERIAL_NUMBER = "XXXX"; // 流水号格式
    private static Test primaryGenerater = null;
 
    private Test() {
    }
 
    /**
     * 取得PrimaryGenerater的单例实现
     *
     * @return
     */
    public static Test getInstance() {
        if (primaryGenerater == null) {
            synchronized (Test.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new Test();
                }
            }
        }
        return primaryGenerater;
    }
 
    /**
     * 生成下一个编号
     */
    public synchronized String generaterNextNumber(String sno) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        if (sno == null) {
            id = formatter.format(date) + "0001";
        } else {
            int count = SERIAL_NUMBER.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            DecimalFormat df = new DecimalFormat("0000");
            id = formatter.format(date)
                    + df.format(1 + Integer.parseInt(sno.substring(8, 12)));
        }
        return id;
    }
    
    public static void main(String[] args) {
		
    	System.out.println(
    	new Test().generaterNextNumber(""));
	}
}