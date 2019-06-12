//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
//
//public class CheckEPrecision {
//
//	public static void main(String[] args) throws FileNotFoundException {
//		String e_real = "";
//		String e_res = "";
//		
//		File file = new File("result.txt");
//		Scanner scanner = new Scanner(file);
//		e_res = scanner.nextLine();
//		scanner.close();
//		
//		file = new File(args.length > 0 ? args[0] : "e_2mil_nasa.txt");
//		scanner = new Scanner(file);
//		e_real = scanner.nextLine();
//		scanner.close();
//		
//		e_real = e_real.substring(0, e_res.length());
//		
//		
//		char[] e_realArr = e_real.toCharArray();
//		char[] e_resArr = e_res.toCharArray();
//		
//		for (int i = 0; i < e_real.length(); i++) {
//			if (e_realArr[i] != e_resArr[i]) {
//				System.out.println(i - 2);
//				return;
//			}
//		}
//		System.out.println(e_real.length() - 2);
//	}
//
//}
