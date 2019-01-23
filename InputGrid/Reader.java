package InputGrid;
import java.io.*;

import GridAndUnit.Grid;
import Prep.Problem;

public class Reader {
    private Grid grid;
    private String pathname;

    public Reader(String pathname){
        this.pathname=pathname;
        grid=new Grid();
    }

    public Grid reader(){

        grid=new Grid();
        BufferedReader br;
        FileInputStream file;
        try {
            file = new FileInputStream(pathname);
            br=new BufferedReader(new InputStreamReader(file));

            String str=br.readLine();
            int num=0;
            int k=0;
            while(str!=null) {
                for(int i=0;i<str.length();i++) {
                    num=str.charAt(i)-'0';
                    if(num>=0 && num<=9){
                        grid.write(k,num);
                        k++;
                    }
                }
                str=br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException exc){
            System.out.println("There is some error with Text File: "+exc);
        }

        return grid;
    }

//    public void reader(){
//
//        grid=new Grid();
//    	BufferedReader br;
//    	FileInputStream file;
//		try {
//			file = new FileInputStream(pathname);
//			br=new BufferedReader(new InputStreamReader(file));
//
//            String str=br.readLine();
//            int num=0;
//            int k=0;
//            while(str!=null) {
//            	for(int i=0;i<str.length();i++) {
//            		num=str.charAt(i)-'0';
//                    if(num>=0 && num<=9){
//	                    grid.write(k,num);
////	                    System.out.print(" "+i+"->"+num);
//	                    k++;
//                    }
//            	}
//            	str=br.readLine();
////            	System.out.println("\n");
//            }
//            br.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}catch(IOException exc){
//            System.out.println("There is some error with Text File: "+exc);
//        }
//
////		return grid;
//    }

//    public static void main(String[] args){
////        Reader r=new Reader();
////        r.reader("Grid.txt");
////        System.out.println("Before presolving");
////        System.out.println(r.grid);
////        Problem pr=new Problem(r.grid);
////        System.out.println("After presolving");
////        System.out.println(r.grid);
//
//        Reader r1=new Reader("Grid1.txt");
//        r1.reader();
//        System.out.println("Before presolving");
//        System.out.println(r1.grid);
//        Problem pr1=new Problem(r1.grid);
//        Integer[] f=pr1.getEmptyCells();
//        System.out.println("After presolving");
//        System.out.println(r1.grid);
//    }
}