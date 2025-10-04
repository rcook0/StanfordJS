package edu.stanford.cs.english;

import java.util.ArrayList;
import java.util.Iterator;

public class EnglishLexicon implements Iterable<String> {
   private ArrayList<String> words;

   public EnglishLexicon() {
      this(-1);
   }

   public EnglishLexicon(int length) {
      this.words = new ArrayList();
      this.addWordsMatchingLength(EnglishA.WORDS, length);
      this.addWordsMatchingLength(EnglishB.WORDS, length);
      this.addWordsMatchingLength(EnglishC1.WORDS, length);
      this.addWordsMatchingLength(EnglishC2.WORDS, length);
      this.addWordsMatchingLength(EnglishD.WORDS, length);
      this.addWordsMatchingLength(EnglishE.WORDS, length);
      this.addWordsMatchingLength(EnglishF.WORDS, length);
      this.addWordsMatchingLength(EnglishG.WORDS, length);
      this.addWordsMatchingLength(EnglishH.WORDS, length);
      this.addWordsMatchingLength(EnglishI.WORDS, length);
      this.addWordsMatchingLength(EnglishJ.WORDS, length);
      this.addWordsMatchingLength(EnglishK.WORDS, length);
      this.addWordsMatchingLength(EnglishL.WORDS, length);
      this.addWordsMatchingLength(EnglishM.WORDS, length);
      this.addWordsMatchingLength(EnglishN.WORDS, length);
      this.addWordsMatchingLength(EnglishO.WORDS, length);
      this.addWordsMatchingLength(EnglishP1.WORDS, length);
      this.addWordsMatchingLength(EnglishP2.WORDS, length);
      this.addWordsMatchingLength(EnglishQ.WORDS, length);
      this.addWordsMatchingLength(EnglishR.WORDS, length);
      this.addWordsMatchingLength(EnglishS1.WORDS, length);
      this.addWordsMatchingLength(EnglishS2.WORDS, length);
      this.addWordsMatchingLength(EnglishT.WORDS, length);
      this.addWordsMatchingLength(EnglishU.WORDS, length);
      this.addWordsMatchingLength(EnglishV.WORDS, length);
      this.addWordsMatchingLength(EnglishW.WORDS, length);
      this.addWordsMatchingLength(EnglishX.WORDS, length);
      this.addWordsMatchingLength(EnglishY.WORDS, length);
      this.addWordsMatchingLength(EnglishZ.WORDS, length);
   }

   public boolean contains(String word) {
      word = word.toLowerCase();
      int lh = 0;
      int rh = this.words.size() - 1;

      while(lh <= rh) {
         int mid = (lh + rh) / 2;
         int cmp = word.compareTo((String)this.words.get(mid));
         if (cmp == 0) {
            return true;
         }

         if (cmp < 0) {
            rh = mid - 1;
         } else {
            lh = mid + 1;
         }
      }

      return false;
   }

   public boolean containsPrefix(String prefix) {
      prefix = prefix.toLowerCase();
      int lh = 0;
      int rh = this.words.size() - 1;

      while(lh <= rh) {
         int mid = (lh + rh) / 2;
         String word = (String)this.words.get(mid);
         if (word.startsWith(prefix)) {
            return true;
         }

         int cmp = prefix.compareTo(word);
         if (cmp < 0) {
            rh = mid - 1;
         } else {
            lh = mid + 1;
         }
      }

      return false;
   }

   public int size() {
      return this.words.size();
   }

   public String get(int k) {
      return (String)this.words.get(k);
   }

   public Iterator<String> iterator() {
      return this.words.iterator();
   }

   private void addWordsMatchingLength(String[] array, int length) {
      int n = array.length;
      int i;
      if (length == -1) {
         for(i = 0; i < n; ++i) {
            this.words.add(array[i]);
         }
      } else {
         for(i = 0; i < n; ++i) {
            if (array[i].length() == length) {
               this.words.add(array[i]);
            }
         }
      }

   }
}
