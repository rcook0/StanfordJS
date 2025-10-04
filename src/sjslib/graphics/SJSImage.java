package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.java2js.JSImage;

class SJSImage extends JSImage {
   public SJSImage(String cwd, String url) {
      super(staticURL(cwd, url));
   }

   private static String staticURL(String cwd, String url) {
      return !url.startsWith("/") && url.indexOf(":") == -1 ? cwd + "/" + url : url;
   }
}
