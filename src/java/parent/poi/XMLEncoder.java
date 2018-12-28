package parent.poi;

/**
 * @Description:
 * @Create: 13-8-23 下午2:22   huxx
 * @Update:
 * @To change this template use File | Settings | File Templates.
 */
public class XMLEncoder {
    private static final String[] xmlCode = new String[256];

    static {
        // Special characters
        xmlCode['\''] = "&apos;";
        xmlCode['\"'] = "&quot;"; // double quote
        xmlCode['&'] = "&amp;"; // ampersand
        xmlCode['<'] = "&lt;"; // lower than
        xmlCode['>'] = "&gt;"; // greater than
    }

    /**
     * <p>
     * Encode the given text into xml.
     * </p>
     *
     * @param string the text to encode
     * @return the encoded string
     */
    public static String encode(String string) {
        if (string == null) return "";
        int n = string.length();
        char character;
        String xmlchar;
        StringBuffer buffer = new StringBuffer();
        // loop over all the characters of the String.
        for (int i = 0; i < n; i++) {
            character = string.charAt(i);
            // the xmlcode of these characters are added to a StringBuffer one by one
            try {
                xmlchar = xmlCode[character];
                if (xmlchar == null) {
                    buffer.append(character);
                } else {
                    buffer.append(xmlCode[character]);
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                buffer.append(character);
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        String test = "\'\"4&<2>1";
        System.out.println(encode(test));
    }
}
