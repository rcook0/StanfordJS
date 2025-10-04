package edu.stanford.cs.java2js;

import java.awt.Color;
import java.util.HashMap;

public class JSColor {
   private static HashMap<String, Color> jsColors = null;

   public static Color decode(String name) {
      if (name.startsWith("#")) {
         return decodeHexColor(name.substring(1));
      } else if (name.startsWith("0x")) {
         return decodeHexColor(name.substring(2));
      } else {
         if (jsColors == null) {
            initJSColors();
         }

         Color color = (Color)jsColors.get(name.toLowerCase());
         if (color == null) {
            throw new RuntimeException("No color named " + name);
         } else {
            return color;
         }
      }
   }

   public static String encode(Color color) {
      int argb = color.getRGB();
      int aa = argb >> 24 & 255;
      int digits = 8;
      if (aa == 255) {
         argb &= 16777215;
         digits = 6;
      }

      String str;
      for(str = Integer.toHexString(argb).toUpperCase(); str.length() < digits; str = "0" + str) {
      }

      return "#" + str;
   }

   private static Color decodeHexColor(String str) {
      return new Color(Integer.parseInt(str, 16), str.length() > 6);
   }

   private static void initJSColors() {
      jsColors = new HashMap();
      jsColors.put("aliceblue", new Color(15792383));
      jsColors.put("antiquewhite", new Color(16444375));
      jsColors.put("aqua", new Color(65535));
      jsColors.put("aquamarine", new Color(8388564));
      jsColors.put("azure", new Color(15794175));
      jsColors.put("beige", new Color(16119260));
      jsColors.put("bisque", new Color(16770244));
      jsColors.put("black", new Color(0));
      jsColors.put("blanchedalmond", new Color(16772045));
      jsColors.put("blue", new Color(255));
      jsColors.put("blueviolet", new Color(9055202));
      jsColors.put("brown", new Color(10824234));
      jsColors.put("burlywood", new Color(14596231));
      jsColors.put("cadetblue", new Color(6266528));
      jsColors.put("chartreuse", new Color(8388352));
      jsColors.put("chocolate", new Color(13789470));
      jsColors.put("coral", new Color(16744272));
      jsColors.put("cornflowerblue", new Color(6591981));
      jsColors.put("cornsilk", new Color(16775388));
      jsColors.put("crimson", new Color(14423100));
      jsColors.put("cyan", new Color(65535));
      jsColors.put("darkblue", new Color(139));
      jsColors.put("darkcyan", new Color(35723));
      jsColors.put("darkgoldenrod", new Color(12092939));
      jsColors.put("darkgray", new Color(11119017));
      jsColors.put("darkgrey", new Color(11119017));
      jsColors.put("darkgreen", new Color(25600));
      jsColors.put("darkkhaki", new Color(12433259));
      jsColors.put("darkmagenta", new Color(9109643));
      jsColors.put("darkolivegreen", new Color(5597999));
      jsColors.put("darkorange", new Color(16747520));
      jsColors.put("darkorchid", new Color(10040012));
      jsColors.put("darkred", new Color(9109504));
      jsColors.put("darksalmon", new Color(15308410));
      jsColors.put("darkseagreen", new Color(9419919));
      jsColors.put("darkslateblue", new Color(4734347));
      jsColors.put("darkslategray", new Color(3100495));
      jsColors.put("darkslategrey", new Color(3100495));
      jsColors.put("darkturquoise", new Color(52945));
      jsColors.put("darkviolet", new Color(9699539));
      jsColors.put("deeppink", new Color(16716947));
      jsColors.put("deepskyblue", new Color(49151));
      jsColors.put("dimgray", new Color(6908265));
      jsColors.put("dimgrey", new Color(6908265));
      jsColors.put("dodgerblue", new Color(2003199));
      jsColors.put("firebrick", new Color(11674146));
      jsColors.put("floralwhite", new Color(16775920));
      jsColors.put("forestgreen", new Color(2263842));
      jsColors.put("fuchsia", new Color(16711935));
      jsColors.put("gainsboro", new Color(14474460));
      jsColors.put("ghostwhite", new Color(16316671));
      jsColors.put("gold", new Color(16766720));
      jsColors.put("goldenrod", new Color(14329120));
      jsColors.put("gray", new Color(8421504));
      jsColors.put("grey", new Color(8421504));
      jsColors.put("green", new Color(32768));
      jsColors.put("greenyellow", new Color(11403055));
      jsColors.put("honeydew", new Color(15794160));
      jsColors.put("hotpink", new Color(16738740));
      jsColors.put("indianred", new Color(13458524));
      jsColors.put("indigo", new Color(4915330));
      jsColors.put("ivory", new Color(16777200));
      jsColors.put("khaki", new Color(15787660));
      jsColors.put("lavender", new Color(15132410));
      jsColors.put("lavenderblush", new Color(16773365));
      jsColors.put("lawngreen", new Color(8190976));
      jsColors.put("lemonchiffon", new Color(16775885));
      jsColors.put("lightblue", new Color(11393254));
      jsColors.put("lightcoral", new Color(15761536));
      jsColors.put("lightcyan", new Color(14745599));
      jsColors.put("lightgoldenrodyellow", new Color(16448210));
      jsColors.put("lightgray", new Color(13882323));
      jsColors.put("lightgrey", new Color(13882323));
      jsColors.put("lightgreen", new Color(9498256));
      jsColors.put("lightpink", new Color(16758465));
      jsColors.put("lightsalmon", new Color(16752762));
      jsColors.put("lightseagreen", new Color(2142890));
      jsColors.put("lightskyblue", new Color(8900346));
      jsColors.put("lightslategray", new Color(7833753));
      jsColors.put("lightslategrey", new Color(7833753));
      jsColors.put("lightsteelblue", new Color(11584734));
      jsColors.put("lightyellow", new Color(16777184));
      jsColors.put("lime", new Color(65280));
      jsColors.put("limegreen", new Color(3329330));
      jsColors.put("linen", new Color(16445670));
      jsColors.put("magenta", new Color(16711935));
      jsColors.put("maroon", new Color(8388608));
      jsColors.put("mediumaquamarine", new Color(6737322));
      jsColors.put("mediumblue", new Color(205));
      jsColors.put("mediumorchid", new Color(12211667));
      jsColors.put("mediumpurple", new Color(9662683));
      jsColors.put("mediumseagreen", new Color(3978097));
      jsColors.put("mediumslateblue", new Color(8087790));
      jsColors.put("mediumspringgreen", new Color(64154));
      jsColors.put("mediumturquoise", new Color(4772300));
      jsColors.put("mediumvioletred", new Color(13047173));
      jsColors.put("midnightblue", new Color(1644912));
      jsColors.put("mintcream", new Color(16121850));
      jsColors.put("mistyrose", new Color(16770273));
      jsColors.put("moccasin", new Color(16770229));
      jsColors.put("navajowhite", new Color(16768685));
      jsColors.put("navy", new Color(128));
      jsColors.put("oldlace", new Color(16643558));
      jsColors.put("olive", new Color(8421376));
      jsColors.put("olivedrab", new Color(7048739));
      jsColors.put("orange", new Color(16753920));
      jsColors.put("orangered", new Color(16729344));
      jsColors.put("orchid", new Color(14315734));
      jsColors.put("palegoldenrod", new Color(15657130));
      jsColors.put("palegreen", new Color(10025880));
      jsColors.put("paleturquoise", new Color(11529966));
      jsColors.put("palevioletred", new Color(14381203));
      jsColors.put("papayawhip", new Color(16773077));
      jsColors.put("peachpuff", new Color(16767673));
      jsColors.put("peru", new Color(13468991));
      jsColors.put("pink", new Color(16761035));
      jsColors.put("plum", new Color(14524637));
      jsColors.put("powderblue", new Color(11591910));
      jsColors.put("purple", new Color(8388736));
      jsColors.put("rebeccapurple", new Color(6697881));
      jsColors.put("red", new Color(16711680));
      jsColors.put("rosybrown", new Color(12357519));
      jsColors.put("royalblue", new Color(4286945));
      jsColors.put("saddlebrown", new Color(9127187));
      jsColors.put("salmon", new Color(16416882));
      jsColors.put("sandybrown", new Color(16032864));
      jsColors.put("seagreen", new Color(3050327));
      jsColors.put("seashell", new Color(16774638));
      jsColors.put("sienna", new Color(10506797));
      jsColors.put("silver", new Color(12632256));
      jsColors.put("skyblue", new Color(8900331));
      jsColors.put("slateblue", new Color(6970061));
      jsColors.put("slategray", new Color(7372944));
      jsColors.put("slategrey", new Color(7372944));
      jsColors.put("snow", new Color(16775930));
      jsColors.put("springgreen", new Color(65407));
      jsColors.put("steelblue", new Color(4620980));
      jsColors.put("tan", new Color(13808780));
      jsColors.put("teal", new Color(32896));
      jsColors.put("thistle", new Color(14204888));
      jsColors.put("tomato", new Color(16737095));
      jsColors.put("turquoise", new Color(4251856));
      jsColors.put("violet", new Color(15631086));
      jsColors.put("wheat", new Color(16113331));
      jsColors.put("white", new Color(16777215));
      jsColors.put("whitesmoke", new Color(16119285));
      jsColors.put("yellow", new Color(16776960));
      jsColors.put("yellowgreen", new Color(10145074));
   }
}
