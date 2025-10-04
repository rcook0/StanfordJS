package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class JSFile {
   public static final String DEFAULT_CGI_SERVER = "https://web.stanford.edu/class/cs54n/cgi-bin";
   private static String JSFILE = "jsfile";
   private static String server = null;
   private String path;

   public JSFile(String path) {
      this.path = path;
   }

   public String getPath() {
      return this.path;
   }

   public String getName() {
      return getTail(this.path);
   }

   public String getProtocol() {
      return this.path.substring(0, this.path.indexOf(":") + 1);
   }

   public String getPathname() {
      return this.path.substring(this.path.indexOf(":") + 1);
   }

   public void read(ActionListener listener) {
      String protocol = this.getProtocol();
      if (protocol.equals("http:")) {
         (new Thread(new URLReader(this.path, listener))).start();
      } else {
         String request;
         if (protocol.equals("cgi:")) {
            request = "/readFile.pl?path=" + this.encode(this.getPathname());
            sendHttpRequest(request, listener, this);
         } else {
            request = null;

            Object e;
            try {
               BufferedReader rd = new BufferedReader(new FileReader(this.path));
               String result = "";

               while(true) {
                  String line = rd.readLine();
                  if (line == null) {
                     rd.close();
                     e = JSEvent.createActionEvent(this, result);
                     break;
                  }

                  result = result + line + "\n";
               }
            } catch (IOException var7) {
               e = new JSErrorEvent(this, var7.getMessage());
            }

            if (listener != null && e != null) {
               JSEvent.dispatch(listener, (ActionEvent)e);
            }
         }
      }

   }

   public void write(String text, ActionListener listener) {
      String protocol = this.getProtocol();
      if (protocol.equals("http:")) {
         throw new RuntimeException("Illegal network write");
      } else {
         String request;
         if (protocol.equals("cgi:")) {
            request = "/writeFile.pl?path=" + this.encode(this.getPathname()) + "&text=" + this.encode(text);
            sendHttpRequest(request, listener, this);
         } else {
            request = null;

            Object e;
            try {
               BufferedWriter wr = new BufferedWriter(new FileWriter(this.path));
               wr.write(text, 0, text.length());
               wr.close();
               e = JSEvent.createActionEvent(this, "");
            } catch (IOException var6) {
               e = new JSErrorEvent(this, var6.getMessage());
            }

            if (listener != null && e != null) {
               JSEvent.dispatch(listener, (ActionEvent)e);
            }
         }

      }
   }

   public void readDirectory(ActionListener listener) {
      String protocol = this.getProtocol();
      if (protocol.equals("http:")) {
         throw new RuntimeException("Not yet implemented");
      } else {
         if (protocol.equals("cgi:")) {
            String request = "/readDirectory.pl?path=" + this.encode(this.getPathname());
            sendHttpRequest(request, listener, this);
         } else {
            String[] names = (new File(this.path)).list();
            String text = "";

            for(int i = 0; i < names.length; ++i) {
               text = text + names[i] + "\n";
            }

            ActionEvent e = JSEvent.createActionEvent(this, text);
            if (listener != null) {
               JSEvent.dispatch(listener, e);
            }
         }

      }
   }

   public void readDirectoryTree(ActionListener listener) {
      String protocol = this.getProtocol();
      if (protocol.equals("http:")) {
         throw new RuntimeException("Not yet implemented");
      } else if (protocol.equals("cgi:")) {
         String request = "/readTree.pl?path=" + this.encode(this.getPathname());
         sendHttpRequest(request, listener, this);
      } else {
         throw new RuntimeException("Not yet implemented");
      }
   }

   public void delete(ActionListener listener) {
      String protocol = this.getProtocol();
      if (protocol.equals("cgi:")) {
         String request = "/deleteFile.pl?path=" + this.encode(this.getPathname());
         sendHttpRequest(request, listener, this);
      } else {
         throw new RuntimeException("Not yet implemented");
      }
   }

   public static boolean isLocalFileSystem() {
      return true;
   }

   public static void login(ActionListener listener) {
      String uid = System.getenv("USER");
      ActionEvent e = JSEvent.createActionEvent(JSFILE, uid);
      if (listener != null) {
         JSEvent.dispatch(listener, e);
      }

   }

   public static void sendHttpRequest(String request, ActionListener listener, Object source) {
      if (server == null) {
         throw new RuntimeException("No CGI server defined");
      } else {
         Object e = null;

         try {
            String url = server + "/" + request;
            InputStream in = (new URL(url)).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String text = "";
            String line = rd.readLine();
            if (line == null) {
               e = new JSErrorEvent(source, "Error: No response from server");
            } else if (line.startsWith("Error:")) {
               e = new JSErrorEvent(source, line);
            } else {
               if (!line.equals("OK")) {
                  text = line + "\n";
               }

               while(true) {
                  line = rd.readLine();
                  if (line == null) {
                     e = JSEvent.createActionEvent(source, text);
                     break;
                  }

                  text = text + line + "\n";
               }
            }

            rd.close();
         } catch (IOException var9) {
            e = new JSErrorEvent(source, var9.getMessage());
         }

         if (e != null && listener != null) {
            listener.actionPerformed((ActionEvent)e);
         }

      }
   }

   public static void setCGIServer(String url) {
      server = url;
   }

   public static String getCGIServer() {
      return server;
   }

   public static String getHead(String path) {
      int slashIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
            slashIndex = i;
         }
      }

      if (slashIndex == -1) {
         return "";
      } else if (slashIndex == 0) {
         return path.substring(0, 1);
      } else {
         return path.substring(0, slashIndex);
      }
   }

   public static String getTail(String path) {
      int slashIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
            slashIndex = i;
         }
      }

      if (slashIndex == -1) {
         return path;
      } else {
         return path.substring(slashIndex + 1);
      }
   }

   public static String getRoot(String path) {
      int dotIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         switch(path.charAt(i)) {
         case '.':
            dotIndex = i;
            break;
         case '/':
         case '\\':
            dotIndex = -1;
         }
      }

      if (dotIndex == -1) {
         return path;
      } else {
         return path.substring(0, dotIndex);
      }
   }

   public static String getExtension(String path) {
      int dotIndex = -1;

      for(int i = 0; i < path.length(); ++i) {
         switch(path.charAt(i)) {
         case '.':
            dotIndex = i;
            break;
         case '/':
         case '\\':
            dotIndex = -1;
         }
      }

      if (dotIndex == -1) {
         return "";
      } else {
         return path.substring(dotIndex);
      }
   }

   public static String[] parseHTMLDirectory(String html) {
      ArrayList<String> files = new ArrayList();
      int start = html.indexOf("Parent Directory");
      if (start == -1) {
         return null;
      } else {
         while(true) {
            start = html.indexOf("<td><a href=\"", start);
            int finish;
            if (start == -1) {
               finish = files.size();
               String[] array = new String[finish];

               for(int i = 0; i < finish; ++i) {
                  array[i] = (String)files.get(i);
               }

               return array;
            }

            start += "<td><a href=\"".length();
            finish = html.indexOf("\"", start);
            if (finish == -1) {
               return null;
            }

            files.add(html.substring(start, finish));
         }
      }
   }

   private String encode(String str) {
      try {
         return URLEncoder.encode(str, "UTF-8");
      } catch (IOException var3) {
         return str;
      }
   }
}
