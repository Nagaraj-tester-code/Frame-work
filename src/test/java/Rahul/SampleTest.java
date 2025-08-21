package Rahul;

import java.io.IOException;
import java.util.ArrayList;

public class SampleTest {
	
	public static void main(String[] args) throws IOException
	{
	
	DataDriven d=new DataDriven();
	ArrayList data=d.getData("testdata");	
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));
	
	DataDriven d1=new DataDriven();
	ArrayList data1=d.getData("testdata");	
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));
	
	DataDriven d3=new DataDriven();
	ArrayList data3=d.getData("testdata");	
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));
	
	DataDriven d2=new DataDriven();
	ArrayList data2=d.getData("testdata");	
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));
	
	

}
}