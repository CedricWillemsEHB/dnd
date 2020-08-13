package View;

import java.io.IOException;

public interface StringListener {
	public void makeMap() throws IOException;
	public void clickNorth() throws IOException;
	public void clickSouth() throws IOException;
	public void clickEast() throws IOException;
	public void clickWest() throws IOException;
	
}
