package edu.stanford.cs.sjs;

import edu.stanford.cs.jsmenu.JSMenu;

class FileMenu extends JSMenu {
   public FileMenu(SJS app) {
      super("File");
      this.add(new NewFileItem(app));
      this.add(new OpenFileItem(app));
      this.add(new CloseFileItem(app));
      this.add(new SaveFileItem(app));
      this.addSeparator();
      this.add(new ExportFileItem(app));
   }
}
