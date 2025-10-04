package edu.stanford.cs.tokenscanner;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

public class TokenScanner {
   public static final int EOF = -1;
   public static final int SEPARATOR = 0;
   public static final int WORD = 1;
   public static final int NUMBER = 2;
   public static final int STRING = 3;
   public static final int OPERATOR = 4;
   private static final int INITIAL_STATE = 0;
   private static final int BEFORE_DECIMAL_POINT = 1;
   private static final int AFTER_DECIMAL_POINT = 2;
   private static final int STARTING_EXPONENT = 3;
   private static final int FOUND_EXPONENT_SIGN = 4;
   private static final int SCANNING_EXPONENT = 5;
   private static final int SCANNING_HEX = 6;
   private static final int FINAL_STATE = 7;
   private Reader input;
   private String wordChars;
   private String savedChars;
   private Stack<String> savedTokens;
   private ArrayList<String> operators;
   private int cpos;
   private boolean ignoreWhitespaceFlag;
   private boolean ignoreCommentsFlag;
   private boolean scanNumbersFlag;
   private boolean scanStringsFlag;

   public TokenScanner() {
      this.ignoreWhitespaceFlag = false;
      this.ignoreCommentsFlag = false;
      this.scanNumbersFlag = false;
      this.scanStringsFlag = false;
      this.cpos = 0;
      this.input = null;
      this.wordChars = "";
      this.savedTokens = new Stack();
      this.operators = new ArrayList();
   }

   public TokenScanner(String str) {
      this();
      this.setInput(str);
   }

   public TokenScanner(Reader rd) {
      this();
      this.setInput(rd);
   }

   public void setInput(String str) {
      this.setInput((Reader)(new StringReader(str)));
   }

   public void setInput(Reader rd) {
      this.savedChars = "";
      this.savedTokens.clear();
      this.cpos = 0;
      this.input = rd;
   }

   public boolean hasMoreTokens() {
      String token = this.nextToken();
      this.saveToken(token);
      return !token.isEmpty();
   }

   public String nextToken() {
      return !this.savedTokens.isEmpty() ? (String)this.savedTokens.pop() : this.scanToken();
   }

   public void saveToken(String token) {
      this.savedTokens.push(token);
   }

   public int getPosition() {
      return this.savedTokens.isEmpty() ? this.cpos : this.cpos - ((String)this.savedTokens.peek()).length();
   }

   public void ignoreWhitespace() {
      this.ignoreWhitespaceFlag = true;
   }

   public void ignoreComments() {
      this.ignoreCommentsFlag = true;
   }

   public void scanNumbers() {
      this.scanNumbersFlag = true;
   }

   public void scanStrings() {
      this.scanStringsFlag = true;
   }

   public void addWordCharacters(String str) {
      for(int i = 0; i < str.length(); ++i) {
         char ch = str.charAt(i);
         if (this.wordChars.indexOf(ch) == -1) {
            this.wordChars = this.wordChars + ch;
         }
      }

   }

   public boolean isWordCharacter(int ch) {
      if (ch == -1) {
         return false;
      } else {
         return Character.isLetterOrDigit(ch) || this.wordChars.indexOf((char)ch) >= 0;
      }
   }

   public void addOperator(String op) {
      this.operators.add(op);
   }

   public void verifyToken(String expected) {
      String token = this.nextToken();
      if (!token.equals(expected)) {
         String msg = "";
         if (token.equals("")) {
            msg = "Missing " + expected;
         } else {
            msg = "Found " + token + " when expecting " + expected;
         }

         throw new RuntimeException(msg);
      }
   }

   public int getTokenType(String token) {
      if (token != null && token.length() != 0) {
         char ch = token.charAt(0);
         if (Character.isWhitespace(ch)) {
            return 0;
         } else if (Character.isDigit(ch)) {
            return 2;
         } else if (ch != '"' && ch != '\'') {
            return this.isWordCharacter(ch) ? 1 : 4;
         } else {
            return 3;
         }
      } else {
         return -1;
      }
   }

   public int getChar() {
      try {
         ++this.cpos;
         if (!this.savedChars.isEmpty()) {
            int ch = this.savedChars.charAt(0);
            this.savedChars = this.savedChars.substring(1);
            return ch;
         } else {
            int ch = this.input.read();
            if (ch == 13) {
               ch = this.input.read();
               if (ch != 10) {
                  this.savedChars = (char)ch + this.savedChars;
                  ch = 10;
               }
            }

            return ch;
         }
      } catch (IOException var2) {
         throw new RuntimeException(var2.toString());
      }
   }

   public void ungetChar(int ch) {
      --this.cpos;
      if (this.input instanceof PushbackReader) {
         try {
            ((PushbackReader)this.input).unread(ch);
            return;
         } catch (IOException var3) {
         }
      }

      if (ch >= 0) {
         this.savedChars = (char)ch + this.savedChars;
      }

   }

   public String getStringValue(String token) {
      String str = "";
      int start = 0;
      int finish = token.length();
      if (finish > 1 && (token.charAt(0) == '"' || token.charAt(0) == '\'')) {
         start = 1;
         --finish;
      }

      for(int i = start; i < finish; ++i) {
         char ch = token.charAt(i);
         if (ch == '\\') {
            ++i;
            ch = token.charAt(i);
            if (!Character.isDigit(ch) && ch != 'x') {
               switch(ch) {
               case '"':
                  ch = '"';
                  break;
               case '\'':
                  ch = '\'';
                  break;
               case '\\':
                  ch = '\\';
                  break;
               case 'a':
                  ch = 7;
                  break;
               case 'b':
                  ch = '\b';
                  break;
               case 'f':
                  ch = '\f';
                  break;
               case 'n':
                  ch = '\n';
                  break;
               case 'r':
                  ch = '\r';
                  break;
               case 't':
                  ch = '\t';
                  break;
               case 'v':
                  ch = 11;
               }
            } else {
               int base = 8;
               int maxDigits = 3;
               if (ch == 'x') {
                  base = 16;
                  maxDigits = 2;
                  ++i;
               }

               int result = 0;
               int digit = false;

               for(int limit = Math.min(finish, i + maxDigits); i < limit; ++i) {
                  ch = token.charAt(i);
                  int digit;
                  if (Character.isDigit(ch)) {
                     digit = ch - 48;
                  } else if (base == 16 && ch >= 'A' && ch <= 'F') {
                     digit = ch - 65 + 10;
                  } else {
                     if (base != 16 || ch < 'a' || ch > 'f') {
                        break;
                     }

                     digit = ch - 97 + 10;
                  }

                  result = base * result + digit;
               }

               ch = (char)result;
               --i;
            }
         }

         str = str + ch;
      }

      return str;
   }

   private String scanToken() {
      int ch = this.scanChar();
      if (ch == -1) {
         return "";
      } else if (this.scanNumbersFlag && Character.isDigit(ch)) {
         this.ungetChar(ch);
         return this.scanNumber();
      } else if (this.isWordCharacter(ch)) {
         this.ungetChar(ch);
         return this.scanWord();
      } else if (this.scanStringsFlag && (ch == 34 || ch == 39)) {
         this.ungetChar(ch);
         return this.scanString();
      } else {
         String op;
         for(op = "" + (char)ch; this.isOperatorPrefix(op); op = op + (char)ch) {
            ch = this.getChar();
            if (ch == -1) {
               break;
            }
         }

         while(op.length() > 1 && !this.isOperator(op)) {
            this.ungetChar(op.charAt(op.length() - 1));
            op = op.substring(0, op.length() - 1);
         }

         return op;
      }
   }

   private int scanChar() {
      boolean slashStarComment = false;
      boolean slashSlashComment = false;

      int ch;
      while(true) {
         int peek;
         label56:
         do {
            while(true) {
               while(true) {
                  ch = this.getChar();
                  if (!slashStarComment) {
                     if (!slashSlashComment) {
                        continue label56;
                     }

                     if (ch == 10 || ch == -1) {
                        slashSlashComment = false;
                        if (ch == 10 && this.isOperator("\n")) {
                           this.ungetChar(ch);
                        }
                     }
                  } else {
                     if (ch == -1) {
                        throw new RuntimeException("Unclosed comment");
                     }

                     peek = this.getChar();
                     if (ch == 42 && peek == 47) {
                        slashStarComment = false;
                     } else {
                        this.ungetChar(peek);
                     }
                  }
               }
            }
         } while(this.ignoreWhitespaceFlag && Character.isWhitespace(ch) && !this.isOperator(Character.toString((char)ch)));

         if (!this.ignoreCommentsFlag || ch != 47) {
            break;
         }

         peek = this.getChar();
         if (peek == 42) {
            slashStarComment = true;
         } else {
            if (peek != 47) {
               this.ungetChar(peek);
               break;
            }

            slashSlashComment = true;
         }
      }

      return ch;
   }

   private String scanWord() {
      String str = "";

      int ch;
      for(ch = this.getChar(); this.isWordCharacter(ch); ch = this.getChar()) {
         str = str + (char)ch;
      }

      this.ungetChar(ch);
      return str;
   }

   private String scanNumber() {
      String str = "";
      int state = 0;
      int ec = 69;

      while(state != 7) {
         int ch = this.getChar();
         switch(state) {
         case 0:
            if (ch == 48) {
               int peek = this.getChar();
               if (peek == 120 || peek == 88) {
                  str = "0x";
                  state = 6;
                  continue;
               }

               this.ungetChar(peek);
            }

            state = 1;
            break;
         case 1:
            if (ch == 46) {
               state = 2;
            } else if (ch != 69 && ch != 101) {
               if (!Character.isDigit(ch)) {
                  state = 7;
               }
            } else {
               state = 3;
            }
            break;
         case 2:
            if (ch != 69 && ch != 101) {
               if (!Character.isDigit(ch)) {
                  state = 7;
               }
            } else {
               ec = ch;
               state = 3;
            }
            break;
         case 3:
            if (ch != 43 && ch != 45) {
               if (Character.isDigit(ch)) {
                  state = 5;
               } else {
                  state = 7;
               }
            } else {
               state = 4;
            }
            break;
         case 4:
            if (Character.isDigit(ch)) {
               state = 5;
            } else {
               this.ungetChar(ch);
               ch = ec;
               state = 7;
            }
            break;
         case 5:
            if (!Character.isDigit(ch)) {
               state = 7;
            }
            break;
         case 6:
            if (!Character.isDigit(ch) && "ABCDEFabcdef".indexOf(ch) == -1) {
               state = 7;
            }
         }

         if (state == 7) {
            this.ungetChar(ch);
            break;
         }

         str = str + (char)ch;
      }

      return str;
   }

   private String scanString() {
      char delim = (char)this.getChar();
      String str = "" + delim;
      int prev = 0;

      while(true) {
         int ch = this.getChar();
         if (ch == -1 || ch == delim && prev != 92) {
            return str + delim;
         }

         str = str + (char)ch;
         prev = ch;
      }
   }

   private boolean isOperator(String op) {
      for(int i = 0; i < this.operators.size(); ++i) {
         if (((String)this.operators.get(i)).equals(op)) {
            return true;
         }
      }

      return false;
   }

   private boolean isOperatorPrefix(String op) {
      for(int i = 0; i < this.operators.size(); ++i) {
         if (((String)this.operators.get(i)).startsWith(op)) {
            return true;
         }
      }

      return false;
   }
}
