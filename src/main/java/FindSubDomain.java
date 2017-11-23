import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 子域名嗅探器，使用时将domain修改为想要嗅探
 * 的域名调用相应的方法即可开始嗅探，嗅探到之
 * 后将会以标准输出的形式输出子域名默认页面的
 * 标题和完整域名
 *
 * @author AnonymousHu
 */
public class FindSubDomain {
    //根域名
    private static String domain = "jlu.edu.cn";

    public static String getDomain() {
        return domain;
    }

    public static void setDomain(String domain) {
        FindSubDomain.domain = domain;
    }


    /**
     * 判断子域名，可用则返回子域名默认页面标题，否则返回404
     *
     * @param prefix 子域名前缀
     * @return 子域名默认页面标题或404
     */
    public static String isPrefixAvailable(String prefix) {
        try {
            Document doc = Jsoup.connect("http://" + prefix + "." + domain).get();
            return doc.title();
        } catch (IOException e) {
            return "404";
        }
    }

    /**
     * 查找长度为一个字符的子域名
     *
     * @param prf 前缀，可留空
     */
    public static void FindSubIn1(String prf) {
        for (char a = 'a'; a <= 'z'; a++) {
            String prefix = prf + a;
            String title = isPrefixAvailable(prefix);
            if (title != "404") {
                System.out.println(prefix + "." + domain + " : " + title);
            }
        }
    }

    /**
     * 查找长度为二个字符的子域名
     *
     * @param prf 前缀，可留空
     */
    public static void FindSubIn2(String prf) {
        for (char a = 'a'; a <= 'z'; a++) {
            FindSubIn1("" + prf + a);
        }
    }

    /**
     * 查找长度为三个字符的子域名
     *
     * @param prf 前缀，可留空
     */
    public static void FindSubIn3(String prf) {
        for (char a = 'a'; a <= 'z'; a++) {
            FindSubIn2(prf + a);
        }
    }

    /**
     * 查找长度为四个字符的子域名
     *
     * @param prf 前缀，可留空
     */
    public static void FindSubIn4(String prf) {
        for (char x = 'a'; x <= 'z'; x++) {
            new FindSubIn3Thread(prf + x).start();
        }
    }


    /**
     * 查找长度为一个字符的子域名
     * 调用两层查找3位子域名线程
     *
     * @param prf 前缀，可留空
     */
    public static void FindSubIn5(String prf) {
        for (char x = 't'; x <= 'z'; x++) {
            FindSubIn4(prf + "" + x);
        }
    }

    /**
     * （Facade模式应用）查找四位字符内的所有子域名
     */
    public static void FindSubIn1234() {
        FindSubIn1("");
        FindSubIn2("");
        FindSubIn3("");
        FindSubIn4("");
    }

    /**
     * 内部线程，用于执行嗅探
     */
    private static class FindSubIn3Thread extends Thread {
        String prf;

        FindSubIn3Thread(String prf) {
            this.prf = prf;
        }

        @Override
        public void run() {
            super.run();
            FindSubIn3(prf);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        FindSubIn1234();
    }
}
