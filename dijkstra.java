import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class dijkstra {
	
	String startNode;
	String node_1,node_2;
	int cost;
	ConcurrentHashMap<String, Integer> paths = new ConcurrentHashMap<String, Integer>();
	
	public dijkstra(String location) {
		try {
			File myFile = new File(location);
		    Scanner myReader = new Scanner(myFile);
		    
		    startNode = myReader.nextLine();
		    control_start_node(startNode);
		    
		    
		    while(myReader.hasNextLine()) {
		    	String data = myReader.nextLine();
		    	String[] a = data.split(" ");
		    	node_1 = a[0];
				node_2 = a[1];
				cost = Integer.parseInt(a[2]);
				nodeControl(a);
				costControl(cost);
				
				if(node_1.equals(startNode)) {
					paths.put(node_2,cost);
				}
				else if(node_2.equals(startNode)) {
					paths.put(node_1, cost);
				}
				else {
					for(String my_nodes: paths.keySet()) {
						if(node_1.equals(my_nodes)) {
							for(String my_nodes2: paths.keySet()) {
								if(node_2.equals(my_nodes2) ) {
									cost+=paths.get(my_nodes);	
									if(paths.get(my_nodes2)>cost) {
										paths.put(node_2,cost);
									}
								}
								else {
									cost+=paths.get(node_1);
										if (!paths.containsKey(node_2))
											paths.put(node_2,cost);
										for(String my_nodes3: paths.keySet()) {
											if(paths.get(my_nodes3)>cost) {
												paths.put(node_2,cost);	
											}
										}
									}
							}
						}
						else if(node_2.equals(my_nodes) && !paths.containsKey(node_1)) {
							
							String temp;
							temp=node_1;
							node_1=node_2;
							node_2=temp;
							
							
							
							for(String my_nodes2: paths.keySet()) {
								if(node_1.equals(my_nodes2) ) {
									cost+=paths.get(node_1);	
									if(paths.get(my_nodes2)>cost) {
										paths.put(node_2,cost);
									}
								}
								else {
										cost+=paths.get(node_1);
										if (!paths.containsKey(node_2))
											paths.put(node_2,cost);
										for(String my_nodes3: paths.keySet()) {
											if(paths.get(my_nodes3)>cost) {
												paths.put(node_2,cost);
											}		
										}
								}
							}
						}
					}
				}
		    }
		    sortAndShow();
		    myReader.close();
		}
		catch (NoSuchElementException e) {
			System.out.println("Dosyanin icerigi bos.\n"+e);
		}
		catch (FileNotFoundException e) {
			System.out.println("Islem yapmak istediginiz dosya bulunamadi.\n"+e);
		}
		catch (IOException e) {
			System.out.println("Dosya isleminde bir hata olustu. Lütfen kontrol ediniz\n"+e);
		}
		catch (NumberFormatException e) {
			System.out.println("Lütfen maliyet degerlerine tamsayi giriniz\n"+e);
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Start node diger nodelara uymuyor. Text dosyanizi kontrol edin");
		}
		catch (Exception e) {//kontrol edilmeyen diger hatalar icin
			System.out.println(e);
		}
	}
	public static <Integer, String> Set<String> getKeysByValue(Map<String, Integer> map, Integer value) {
	    Set<String> keys = new HashSet<String>();
	    for (Entry<String, Integer> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
	public void sortAndShow() {
		int v=0;
		int valuesArray[]=new int [paths.size()];
		for(String i : paths.keySet()) {
			valuesArray[v]=paths.get(i);
			v++;
		}
		int n = valuesArray.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (valuesArray[j] > valuesArray[j+1]){
                    int temp = valuesArray[j];
                    valuesArray[j] = valuesArray[j+1];
                    valuesArray[j+1] = temp;
                }
        String t=startNode;
        System.out.print("\t "+t);
        int count=0;
        for(int i=0;i<valuesArray.length;i++) {
        	String arr[]=getKeysByValue(paths, valuesArray[i]).toString().split("");
        	if(arr.length==3)
        		t+=arr[1];
        	else {
        		for(int b=1;b<arr.length;b+=3) {
	        		if(arr[b].equals(t.substring(t.length()-1))) {
	        			continue;
	        		}
        			t+=arr[b];
        			break;
        		}
        	}
        	System.out.print("\nStep"+count+"\t "+t+" "+valuesArray[i]);
        	count++;
        }
        if(count<1) {
        	System.out.print(" = Start node\nStart node diger nodelara uymuyor.");
        }
	}
	public void control_start_node(String start_node) {
		if(start_node.length()>1) {
			System.out.println("Start node hatali. Lütfen dosyanizin icerigini kontrol edin\nProgram durduruluyor.");
			System.exit(0);
		}
	}
	public void nodeControl(String [] array) {
		for(int node=0;node<array.length-1;node++) {
			if(array[node].length()!=1) {
				System.out.println("Bazi nodelar bos ya da birden fazla karakterden olusturulmus.\nLütfen kontrol edin.");
				System.exit(0);
			}
		}
	}
	public void costControl(int cost) {
		if(cost<0) {
			System.out.println("Maliyet negatif olamaz.");
			System.exit(0);
		}
	}
}