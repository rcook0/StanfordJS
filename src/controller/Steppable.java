package edu.stanford.cs.controller;

public interface Steppable {
   void step();

   boolean isCallable();

   int getStackDepth();
}
