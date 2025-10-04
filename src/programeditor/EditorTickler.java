package edu.stanford.cs.programeditor;

class EditorTickler implements Runnable {
   private ProgramEditor editor;

   public EditorTickler(ProgramEditor editor) {
      this.editor = editor;
   }

   public void run() {
      try {
         Thread.sleep(2000L);
      } catch (InterruptedException var3) {
      }

      int n = this.editor.getComponentCount();

      for(int i = 0; i < n; ++i) {
         this.editor.getComponent(i).repaint();
      }

      this.editor.repaint();
   }
}
