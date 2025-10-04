package edu.stanford.cs.console;

class CharacterQueue {
   private String buffer = "";
   private boolean isWaiting;

   public CharacterQueue() {
   }

   public synchronized void enqueue(char ch) {
      this.buffer = this.buffer + ch;
      this.notifyAll();
   }

   public synchronized void enqueue(String str) {
      this.buffer = this.buffer + str;
      this.notifyAll();
   }

   public synchronized char dequeue() {
      while(this.buffer.length() == 0) {
         try {
            this.isWaiting = true;
            this.wait();
            this.isWaiting = false;
         } catch (InterruptedException var2) {
         }
      }

      char ch = this.buffer.charAt(0);
      this.buffer = this.buffer.substring(1);
      return ch;
   }

   public synchronized void clear() {
      this.buffer = "";
      this.notifyAll();
   }

   public boolean isWaiting() {
      return this.isWaiting;
   }
}
