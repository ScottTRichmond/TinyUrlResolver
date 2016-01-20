import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TinyUrlResolver {
	

	public static void main(String[] args) {
		TinyUrlResolver TUR = new TinyUrlResolver();
		

	}
	
	public TinyUrlResolver() {
		
		ArrayList<String> urls = new ArrayList<String>();
		try {
			System.out.println("Please enter the Tinyurl you want resolved.");
			String startingURL = new Scanner(System.in).nextLine();
			
			Document document = Jsoup.connect(startingURL).get(); // Starting page. Hardcoded for now
			urls.add(document.location());
			
			while(document.baseUri().contains("tinyurl")) { //check to see if we've escaped tinyurl hell
				Element div = document.getElementById("contentcontainer"); // select the content element
				Elements links = div.select("a[href]"); // isolate the link
				String nextURL = links.first().attr("href").toString(); // get the value of the link
				System.out.println(nextURL + " " + urls.size());
				if(urls.contains(nextURL)) { //ensure we aren't in a loop
					System.out.println("Loop detected!");
					break;
				}
				urls.add(nextURL);
				document = Jsoup.connect(nextURL).get(); //go to next page
				
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter("output.txt");
			for(String url : urls) {
				writer.write(url + "\n");
			}
			writer.write("number of urls: " + urls.size());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
