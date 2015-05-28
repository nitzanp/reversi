package reversi;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Methods {
	
	public static void placeAtCenter(java.awt.Window obj, int width, int height){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
     
        int X = (dim.width - width)/2;
        int Y = (dim.height - height)/2;
        obj.setLocation(X, Y);
	}
	
}
