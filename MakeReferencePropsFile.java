import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MakeReferencePropsFile {
	
	public static void main(String[] args) throws IOException{
		ArrayList<File> files = new ArrayList<File>();
		listAllFiles("E:\\code\\aspnet\\aspnetcore\\src", files);
		
		ArrayList<File> extFiles = new ArrayList<File>();
		listAllFiles("E:\\code\\aspnet\\extensions\\src", extFiles);
		
		HashMap<String, HashSet<String>> refs = new HashMap<String, HashSet<String>>();
		for (File f: files){
			Scanner fileScan = new Scanner(f);
			String k = fileScan.nextLine();
			HashSet<String> v = new HashSet<String>();
			while (fileScan.hasNextLine()){
				String line = fileScan.nextLine();
				if (!line.isEmpty()){
					String[] parts = line.split(";");
					for (String ding: parts){
						v.add(ding);
					}
				}
			}
			refs.put(k, v);
		}
		
		HashMap<String, HashSet<String>> extRefs = new HashMap<String, HashSet<String>>();
		for (File f: extFiles){
			Scanner fileScan = new Scanner(f);
			String k2 = fileScan.nextLine();
			HashSet<String> v2 = new HashSet<String>();
			while (fileScan.hasNextLine()){
				String line = fileScan.nextLine();
				if (!line.isEmpty()){
					String[] parts = line.split(";");
					for (String ding: parts){
						v2.add(ding);
					}
				}
			}
			extRefs.put(k2, v2);
		}
		
		for (String s1: refs.keySet()){
			HashSet<String> temp = new HashSet<String>();
			for (String s2: refs.get(s1)){
				if (extRefs.containsKey(s2)){
					temp.addAll(extRefs.get(s2));
				}
			}
			refs.get(s1).addAll(temp);
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"E:\\code\\scratch\\IndirectReferences.props"), true));
		bw.write("<Project>");
		bw.newLine();
		for (String s: refs.keySet()){
			HashSet<String> v = refs.get(s);
			bw.write("\t<ItemGroup Condition=\"@(Reference->AnyHaveMetadataValue(\'Identity\', \'" + s + "\'))\">");
			bw.newLine();
			for (String ref: v){
				bw.write("\t\t<IndirectReference Include=\"" + ref + "\" KeepDuplicates=\"false\" />");
				bw.newLine();
			}
			bw.write("\t</ItemGroup>");
			bw.newLine();
		}
		for (String s: extRefs.keySet()){
			HashSet<String> v = extRefs.get(s);
			bw.write("\t<ItemGroup Condition=\"@(Reference->AnyHaveMetadataValue(\'Identity\', \'" + s + "\'))\">");
			bw.newLine();
			for (String ref: v){
				bw.write("\t\t<IndirectReference Include=\"" + ref + "\" KeepDuplicates=\"false\" />");
				bw.newLine();
			}
			bw.write("\t</ItemGroup>");
			bw.newLine();
		}
		bw.write("</Project>");
		bw.newLine();
		bw.flush();
		bw.close();
	}

	public static void listAllFiles(String path, ArrayList<File> files){
		File root = new File(path);
        File[] list = root.listFiles();
        
        if (list != null) {  // In case of access error, list is null
            for (File f : list) {
                if (f.isDirectory()) {
                    listAllFiles(f.getAbsolutePath(), files);
                } else {
                	if (f.getName().equals("ImportMe.props")){
                		files.add(f);
                	}
                }
            }
        }
	}
	
}
