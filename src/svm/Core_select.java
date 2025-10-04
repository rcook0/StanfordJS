package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_select extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.select", "**");
      Value ix = svm.pop();
      Value obj = receiver == null ? svm.pop() : receiver;
      int type = obj.getType();
      String cname;
      String name;
      if (type == 83) {
         cname = obj.getStringValue();
         if (ix.getType() == 83) {
            name = ix.getStringValue();
            if (name.equals("length")) {
               svm.pushInteger(cname.length());
            } else {
               SVMMethodClosure mc = new SVMMethodClosure(obj, "String", name);
               svm.push(Value.createObject(mc, "MethodClosure"));
            }
         } else {
            int index = ix.getIntegerValue();
            int n = cname.length();
            if (index < 0) {
               index += n;
            }

            if (index >= 0 && index < n) {
               svm.pushString(cname.substring(index, index + 1));
            } else {
               throw new RuntimeException("String index out of bounds");
            }
         }
      } else {
         if (type == 79) {
            cname = obj.getClassName();
            if (cname.equals("Array")) {
               SVMArray array = (SVMArray)obj.getValue();
               if (ix.getType() == 83) {
                  String name = ix.getStringValue();
                  if (name.equals("length")) {
                     svm.pushInteger(array.size());
                     return;
                  }

                  SVMMethodClosure mc = new SVMMethodClosure(obj, "Array", name);
                  svm.push(Value.createObject(mc, "MethodClosure"));
               } else {
                  svm.push((Value)array.get(ix.getIntegerValue()));
               }
            } else {
               Value prop;
               if (!cname.equals("Object") && !cname.equals("Map")) {
                  name = ix.getStringValue();
                  prop = obj.getProperty(name);
                  if (prop != null) {
                     svm.push(prop);
                  } else {
                     if (cname.equals("Class")) {
                        cname = (String)obj.getValue();
                        obj = null;
                     }

                     SVMClass c = SVMClass.forName(cname);
                     if (c == null) {
                        throw new RuntimeException("Undefined class " + cname);
                     }

                     SVMMethod m = null;

                     try {
                        m = c.getMethod(name);
                     } catch (Exception var12) {
                        svm.push(Value.UNDEFINED);
                        return;
                     }

                     if (m == null) {
                        throw new RuntimeException("Undefined method " + name);
                     }

                     if (m.isConstant()) {
                        m.execute(svm, (Value)null);
                        return;
                     }

                     SVMMethodClosure mc = new SVMMethodClosure(obj, cname, name);
                     svm.push(Value.createObject(mc, "MethodClosure"));
                  }
               } else {
                  SVMObject map = (SVMObject)obj.getValue();
                  prop = (Value)map.get(ix.getStringValue());
                  svm.push(prop == null ? Value.UNDEFINED : prop);
               }
            }
         } else {
            if (type != 73 && type != 68) {
               throw new RuntimeException("Illegal selection");
            }

            if (ix.getType() == 83) {
               cname = ix.getStringValue();
               SVMMethodClosure mc = new SVMMethodClosure(obj, "Number", cname);
               svm.push(Value.createObject(mc, "MethodClosure"));
               return;
            }
         }

      }
   }
}
