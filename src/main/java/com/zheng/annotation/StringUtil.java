package com.zheng.annotation;

import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 文本左边补零
     *
     * @param maxLength 文本长度
     * @param str
     * @return
     */
    public static String leftCoverFormat(int maxLength, Integer str) {
        String value = "";
        try {
            // 得到一个NumberFormat的实例
            NumberFormat nf = NumberFormat.getInstance();
            // 设置是否使用分组
            nf.setGroupingUsed(false);
            // 设置最大整数位数
            nf.setMaximumIntegerDigits(maxLength);
            // 设置最小整数位数
            nf.setMinimumIntegerDigits(maxLength);
            value = nf.format(str);
        } catch (Exception ex) {
            log.error("fl.ec.product.common.utils.leftCoverFormat错误", ex);
        }
        return value;
    }

    /**
     * 判断对象是否为空，如果为空返回true，否则返回false
     *
     * @param object
     * @return
     */
    public static final boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof List && ((List) object).size() <=0){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空，如果为空返回true，否则返回false
     *
     * @param str
     * @return
     */
    public static final boolean isEmpty(final String str) {
        if (str == null || str.trim().length() < 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空，如果不为空返回true，否则返回false
     *
     * @param object
     * @return
     */
    public static final boolean isNotEmpty(final Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof List && ((List) object).size() <=0){
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为空，如果不为空返回true，否则返回false
     *
     * @param str
     * @return
     */
    public static final boolean isNotEmpty(final String str) {
        if (str == null || str.trim().length() < 1) {
            return false;
        }
        return true;
    }

    /**
     * 把null转换成""，如果不为null则转型
     *
     * @param o
     * @return
     */
    public static String nullToString(Object o) {
        String s = "";
        return o != null ? String.valueOf(o) : s;
    }


    /**
     * 功能说明:去掉字符串2端空格或空白。如果参数字符串为null，那么返回结果为空白字符串，即"";
     *
     * @param s 需要过滤的字符串
     * @return
     */
    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Function: splitString<BR>
     * Description: 此方法用于拼接sql    in <BR>
     *
     * @param str  需要传入的字符串  id,id,id,  或  id,id
     * @param type 需要截取的符号  比如：","
     * @return 'id','id'    如果没截取出来就会返回：''
     */
    public static String splitString(String str, String type) {
        String sql = " ";
        StringBuffer sbf = new StringBuffer();
        if (!StringUtil.isEmpty(str) && !StringUtil.isEmpty(type)) {
            String[] ids = str.split(type);
            for (int i = 0; i < ids.length; i++) {
                if (ids.length == (i + 1)) {
                    sbf.append("'").append(ids[i]).append("'");
                } else {
                    sbf.append("'").append(ids[i]).append("'").append(",");
                }
            }
            sbf.append(" ");
        } else {
            sbf.append("''");
        }
        return sbf.toString();

    }

    /**
     * 字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

    /**
     * 替换身份证号
     *
     * @param idCardNo
     * @return
     * @author
     */
    public static String replaceIdCardNo(String idCardNo) {
        // 校验原字符串与替换字符串是否为空，其一为空则返回原字符串
        if (!isNotEmpty(idCardNo)) {
            return idCardNo;
        }
        String tmp = idCardNo.substring(6, idCardNo.length() - 4);
        String rep = "";
        for (int i = 0; i < tmp.length(); i++) {
            rep += "*";
        }
        return idCardNo.replace(tmp, rep);
    }

    /**
     * 替换字符中的表情符号
     *
     * @param source
     * @param target
     * @return
     */
    public static String filterEmoji(String source, String target) {
        if (StringUtil.isEmpty(source)) {
            return source;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll(target);
            return source;
        }
        return source;
    }

    /**
     * 判断字符是否有表情符号
     *
     * @param source
     * @return
     */
    public static boolean isContainEmoji(String source) {
        if (StringUtil.isEmpty(source)) {
            return false;
        }
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = emoji.matcher(source);
        if (emojiMatcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 手机号加密显示
     *
     * @param mobile
     * @param replaceChar
     * @return
     */
    public static String setMobile(String mobile, String replaceChar) {
        if (mobile.length() < 11) {
            return mobile;
        }
        return mobile.substring(0, 3).concat(replaceChar).concat(replaceChar).concat(replaceChar).concat(replaceChar).concat(mobile.substring(7));
    }

    /**
     * 把数组转换成set
     *
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }
}
