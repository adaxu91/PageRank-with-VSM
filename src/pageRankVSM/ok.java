package pageRankVSM;

import java.util.ArrayList;




public class ok {


	public static void main(String args[]){
		ArrayList<String> al = new ArrayList<String> ();
		String a ="0 3 10 11 13 16 18 22 25 26 28 29 33 39 53 134 181 204 213 227 229 230 242 248 273 307 315 344 354 422 547 548 583 942 987 1074 1112 1572 1820 2476 2478 2480 2496 2510 2511 2531 2532 2607 2628 2653 2654 3868 3920 3930 4170 4273 4281 4320 4361 4390 4414 4443 4557 4561 4562 4655 5371 5594 5697 8747 9877";
	    String s1 []= a.split(" ");
	    for(int i=0;i<s1.length;i++){
	    	al.add(s1[i]);
	    }
	    
	    //PageRand+VSM
	    String b ="548 4273 4827 315 572 2481 25 2511 313 2476 0 4390 18 9408 2477 2496 5352 2470 4281 324";
	    
	    //VSM
	    //String b ="4827 4273 315 2481 572 313 0 2511 548 2476 25 4390 18 9408 2496 2477 5352 2470 4281 324";
	    String bb = "1 1 0 0 0 0 1 1 0 1 1 1 1 0 0 1 0 0 0 0";
	    String s2[] = b.split(" ");
	    String s3[] = bb.split(" ");
	    int ii=0;
	    for(int i=0;i<s2.length;i++){
	    if(al.contains(s2[i])&&s3[i].equals("1")){//contains the s2[i] and the judgement is also equal to 1
	    	ii++;
	    }
	    }
	    double aa = (double)ii/20.00;
	    System.out.println(aa);
	}
}
