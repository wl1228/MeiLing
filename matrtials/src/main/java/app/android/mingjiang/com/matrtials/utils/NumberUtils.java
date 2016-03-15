package app.android.mingjiang.com.matrtials.utils;

/**
 * 备注：
 * 作者：wangzs on 2016/2/23 14:52
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class NumberUtils {

    //判断是否为数字
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    //校验是否为空
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }else{
            return false;
        }
    }
}
