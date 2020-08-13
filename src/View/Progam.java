package View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dnd.*;

public class Progam {
	public static void main(String[] args) {
		Explorer exp = new Explorer();
		exp.makeMap();
		try {
			exp.letsExplore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
