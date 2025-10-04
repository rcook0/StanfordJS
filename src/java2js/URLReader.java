package edu.stanford.cs.java2js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

class URLReader implements Runnable {
   private String name;
   private ActionListener listener;

   public URLReader(String name, ActionListener listener) {
      this.name = name;
      this.listener = listener;
   }

   public void run() {
      Object e = null;

      try {
         if (this.name.startsWith("http:/") && !this.name.startsWith("http://")) {
            this.name = this.name.substring(0, 6) + "/" + this.name.substring(6);
         }

         URL url = new URL(this.name);
         InputStream is = url.openStream();
         BufferedReader rd = new BufferedReader(new InputStreamReader(is));
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

      JSEvent.dispatch(this.listener, (ActionEvent)e);
   }
}
