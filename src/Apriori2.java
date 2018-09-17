import java.util.*;
import java.io.*;

public class Apriori2 {
	
	static boolean isSubList(List l1,List l2) {
		boolean flag=true;
		for(int i=0;i<l2.size();i++) {
			if(!l1.contains(l2.get(i))) {
				flag=false;
			}
		}
		return flag;
	}
	static List<String> returnList(List<String> l1,List<String> l2) {
		List<String> jl=new ArrayList<String>();
		if(l1.size()==1) {
			jl.add(l1.get(0));
			jl.add(l2.get(0));
			return jl;
		}
		int i=0;
		for(i=0;i<l1.size()-1;i++) {
			if(l1.get(i)!=l2.get(i)) {
				break;
			}
		}
		if(i==l1.size()-1)
		{
			jl.addAll(l1);
			jl.add(l2.get(l2.size()-1));
		    return jl;
		}
		return null;
	}
	public static void main(String[] args) throws Exception {
		
		int min_sup=2;
		Scanner sc=new Scanner(System.in);
		List<List<String>> trans=new ArrayList<>();
		LinkedHashMap< List<String>,Integer> C1=new LinkedHashMap<>();
		List<List<String>> L1=new ArrayList<>();
		System.out.println("Enter file name with csv extension:");
		String fname=sc.nextLine();
		File file=new File(fname);
		Scanner fp=new Scanner(file);
		if(fp.hasNextLine()) {
			String s[]=fp.nextLine().split(",");
			for(String ite:s) {
				List<String> l=new ArrayList<>();
				l.add(ite);
				C1.put(l, 0);
			}
		}
		while(fp.hasNextLine()) {
			String s[]=fp.nextLine().split(",");
			List<String> l=new ArrayList<>();
			for(String ite:s) {
				l.add(ite);
			}
			trans.add(l);
		}

		for(int i=0;i<trans.size();i++) {
			int j=i+1;
			System.out.println("T"+j+":"+trans.get(i));
		}		
		
		System.out.print("Enter minimum support count:");
		min_sup=sc.nextInt();
		int h=1;
		for(List<String>  ll: C1.keySet()) {
			int count=0;
			for(int t=0;t<trans.size();t++) {
				if(isSubList(trans.get(t),ll)) {
					count++;
				}
			}
			C1.put(ll,count);
		}
		if(!C1.isEmpty())
		System.out.println("Condidate ites C"+h);
		for(List<String>  ll: C1.keySet()) {
			System.out.println(ll+" : "+C1.get(ll).intValue());
			if(C1.get(ll).intValue()>=min_sup) {
				L1.add(ll);
			}
		}
		if(!L1.isEmpty())
			System.out.println("List of frequent  itemset L"+(h++));
		for(int i=0;i<L1.size();i++) {
			System.out.println(">"+L1.get(i));
		}
		
		while(!C1.isEmpty()||!L1.isEmpty()) {
			C1.clear();
			
			for(int i=0;i<L1.size();i++) {
				for(int j=i+1;j<L1.size();j++) {
					List<String> l=returnList(L1.get(i), L1.get(j));
					if(l!=null)
						C1.put(l,0);
				}
			}
			L1.clear();
			for(List<String> ls:C1.keySet()) {
				int count=0;
				for(int y=0;y<trans.size();y++) {
					if(isSubList(trans.get(y),ls)) {
						count++;
					}
				}
				C1.put(ls,count);
			}
			if(!C1.isEmpty())
			System.out.println("Condidate items C"+h);
			for(List<String>  ll: C1.keySet()) {
				System.out.println(ll+" : "+C1.get(ll).intValue());
				if(C1.get(ll).intValue()>=min_sup) {
					L1.add(ll);
				}
			}
			if(!L1.isEmpty())
			System.out.println("List of frequent  itemset L"+(h++));
			for(int i=0;i<L1.size();i++) {
				System.out.println(">"+L1.get(i));
			}
		}
	}
}
