package edu.stanford.cs.java2js;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

class JSBugCanvas extends JComponent {
   public static final int WIDTH = 44;
   public static final int HEIGHT = 44;
   private static final String BUG_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAsCAYAAAAehFoBAAADGUlEQVR42u2ZPU7DQBCFLSRKEJR0iAPQUNJyBSRKCkSHRMcJkOhoEBIVBVS5AlfIFbgCV1j0YT3lZbxO1ontCMFKKyfrn/0882b2x9U0peo31eof+C8APzykdHeX0mSS0mDA5+cpUels1WcAWVV1vb5O6ehoIOCzs5SoANPZ/n53cO4X6NdX+ik8ZxBgOgKQ8vlZu5M2IEruPzmZWVbPQQ78H9TCXj4+apcCUworC1M4DgaMRXGf3AosBdcC1OZaSYjKy3H/y0t9n9oGCzq3ktzq0LFzrvF7vOgchugdGItgqdPTWeeylAIIXWNll4fDUhVoHLm2RA4rAUsGz8/zAKqSh6zGS+ie3HWSCdf1CqxA4zcPp0PPozl5IAs/x38NEFiWZygt9j7SeVDQofQWoZAB0NRo2ah1SaFLDu8sh9zvnDRyFferKL0tS4Nra5hO/ahAXASKBfEK13nO7SKFlYEVHByxDpaWBNqAFWAOW5oVesnDx8dNKIHngD0/qw1rjwa8tzcf+QIF4vGxmRmQgwJsHdi1Jj/eOdYD7PKymco4720HBxsGJvhkve3tvH6Vb6OENgK8rPqop8wwapag7u6WwWoK6mmPNg/ArtCdYdXR1tY83OFhE5hgQw4+rdRLeFuXwWMlWHWA5TREO4BPzJm5eTvXx7YumaMzbNusynUKlKRAu6c0rE6J84ySyXsxsFYRi6xwfz8/wcGKEYpnxAHEay/APHyZxhyMa7WgzGmakpuW9gJM58uWLYLFeq+vzYEjahoPtFn46mqNJRIgJTsxvJDr7+ammUE0S4vZIaZAzpFtFklv4YysdAXtXqDz9/eZFXkZzdaAavOAtD+d1t5qM1Yv+2L+cNe7a7VNBi4ZeYJj24DS+8Ze1PzbWw20s1M+Mmr5lJPGOFuk1cwLT0813MVF/d8zjDZXJJ9cwI8G7J0LLLe8cuDcjG5wWLQbg9i3s3I5mRfSwmBUYEBjp9pYcdjb22a6axusBgWOQQOsSyOmRO0mMcxv/JNBLq9GqZTsEW/020b0gBasv+IrUu+bgf+fvf4K8Ddxu3rpl8K1pQAAAABJRU5ErkJggg==";
   private JSImage bugImage;

   public JSBugCanvas() {
      this.setPreferredSize(new Dimension(44, 44));
      this.bugImage = new JSImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAsCAYAAAAehFoBAAADGUlEQVR42u2ZPU7DQBCFLSRKEJR0iAPQUNJyBSRKCkSHRMcJkOhoEBIVBVS5AlfIFbgCV1j0YT3lZbxO1ontCMFKKyfrn/0882b2x9U0peo31eof+C8APzykdHeX0mSS0mDA5+cpUels1WcAWVV1vb5O6ehoIOCzs5SoANPZ/n53cO4X6NdX+ik8ZxBgOgKQ8vlZu5M2IEruPzmZWVbPQQ78H9TCXj4+apcCUworC1M4DgaMRXGf3AosBdcC1OZaSYjKy3H/y0t9n9oGCzq3ktzq0LFzrvF7vOgchugdGItgqdPTWeeylAIIXWNll4fDUhVoHLm2RA4rAUsGz8/zAKqSh6zGS+ie3HWSCdf1CqxA4zcPp0PPozl5IAs/x38NEFiWZygt9j7SeVDQofQWoZAB0NRo2ah1SaFLDu8sh9zvnDRyFferKL0tS4Nra5hO/ahAXASKBfEK13nO7SKFlYEVHByxDpaWBNqAFWAOW5oVesnDx8dNKIHngD0/qw1rjwa8tzcf+QIF4vGxmRmQgwJsHdi1Jj/eOdYD7PKymco4720HBxsGJvhkve3tvH6Vb6OENgK8rPqop8wwapag7u6WwWoK6mmPNg/ArtCdYdXR1tY83OFhE5hgQw4+rdRLeFuXwWMlWHWA5TREO4BPzJm5eTvXx7YumaMzbNusynUKlKRAu6c0rE6J84ySyXsxsFYRi6xwfz8/wcGKEYpnxAHEay/APHyZxhyMa7WgzGmakpuW9gJM58uWLYLFeq+vzYEjahoPtFn46mqNJRIgJTsxvJDr7+ammUE0S4vZIaZAzpFtFklv4YysdAXtXqDz9/eZFXkZzdaAavOAtD+d1t5qM1Yv+2L+cNe7a7VNBi4ZeYJj24DS+8Ze1PzbWw20s1M+Mmr5lJPGOFuk1cwLT0813MVF/d8zjDZXJJ9cwI8G7J0LLLe8cuDcjG5wWLQbg9i3s3I5mRfSwmBUYEBjp9pYcdjb22a6axusBgWOQQOsSyOmRO0mMcxv/JNBLq9GqZTsEW/020b0gBasv+IrUu+bgf+fvf4K8Ddxu3rpl8K1pQAAAABJRU5ErkJggg==");
   }

   public void paintComponent(Graphics g) {
      g.setColor(JSErrorDialog.ERROR_BACKGROUND);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      int x = (this.getWidth() - 44) / 2;
      int y = (this.getHeight() - 44) / 2;
      g.drawImage(this.bugImage, x, y, this);
   }
}
