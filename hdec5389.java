// KRISHNA CHAITANYA NELLOORE cs610 5389 prp


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class hdec5389 {
	
	public static void main(String[] args){
		
		String encoded_file = args[0];
		String code_table_file = "code_table.txt";
		
		DecodeTree5389 decode_tree = new DecodeTree5389();
		
		generateDecodeTree5389(code_table_file,decode_tree);
		generateDecoder5389(encoded_file,decode_tree);
		
	}

	private static void generateDecodeTree5389(String code_table_file,DecodeTree5389 decode_tree) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(code_table_file))) {

			String line;
			
			while ((line = br.readLine()) != null) {
				String[] pair = line.split(" ");
				int value = Integer.parseInt(pair[0]);
				String huffman_code = pair[1];
				DecodeTreeNode5389 temp = decode_tree.root;
				for(int i=0;i<huffman_code.length()-1;i++){
					
					DecodeTreeNode5389 latest_node = new DecodeTreeNode5389();
					if(huffman_code.charAt(i)=='0' && temp.left==null){
						 temp.left = latest_node;
						 temp=latest_node;
					}
					else if(huffman_code.charAt(i)=='0' && temp.left!=null)
						temp=temp.left;
					else if(huffman_code.charAt(i)=='1' && temp.right==null){
						temp.right=latest_node;
						temp=temp.right;
					}
					else if(huffman_code.charAt(i)=='1' && temp.right!=null)
						temp=temp.right;
					
				}
				
				DecodeTreeNode5389 leaf = new DecodeTreeNode5389(value);
				if(huffman_code.charAt(huffman_code.length()-1)=='0')
					temp.left=leaf;
				else
					temp.right=leaf;
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void generateDecoder5389(String encoded_file, DecodeTree5389 decode_tree) {
		
		String[] encoded_filenames = encoded_file.split("\\.");
		if(!encoded_filenames[encoded_filenames.length-1].equals("huf"))
		{
			throw new NoSuchElementException("Illegal file");
		}
		String decode_file = "";
		for(int f=0;f<encoded_filenames.length-1;f++)
		{
			if(decode_file.equals(""))
			{
				decode_file+=encoded_filenames[f];
			}
			else {decode_file+="."+encoded_filenames[f];}
		}
		
		ArrayList<Integer> decoded_values = new ArrayList<Integer>();
		
		try(InputStream inputStream = new FileInputStream(encoded_file)){
			
			DecodeTreeNode5389 tmp = decode_tree.root;
			
			int byteRead;
			
			while ((byteRead = inputStream.read()) != -1) {
                
				String binary_str = Integer.toBinaryString(byteRead);
				if(binary_str.length()!=8){
					int extra_len = 8-binary_str.length();
					String padding_bits ="";
					while(extra_len>0){
						padding_bits+="0";
						extra_len--;
					}
					binary_str=padding_bits+binary_str;
				}
				for(int i=0;i<binary_str.length();i++){
					if(binary_str.charAt(i)=='0')
						tmp=tmp.left;
					else
						tmp=tmp.right;
					if(tmp.isLeaf){
						decoded_values.add(tmp.value);
						tmp=decode_tree.root;
					}
				}
            }
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		writeToFile5389(decoded_values,decode_file);
		
	}

	private static void writeToFile5389(ArrayList<Integer> decoded_values, String decode_file) {
		boolean isFirstLine = true;		
		try (FileOutputStream fos = new FileOutputStream(new File(decode_file))) {
			char content;
			for(int val : decoded_values){
				if(isFirstLine){
					content = (char) val;
					isFirstLine = false;
				}
				else
					content= (char) val;
				fos.write((byte)content);
			}
			
		fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File fl = new File(decode_file+".huf");
		fl.delete();
		
	}

}

class DecodeTreeNode5389 {	
	int value;
	DecodeTreeNode5389 left;
	DecodeTreeNode5389 right;
	boolean isLeaf;
	
	public DecodeTreeNode5389(){
		int value=-1;
		this.left=null;
		this.right=null;
		this.isLeaf=false;		
	}
	
	public DecodeTreeNode5389(int value){
		this.value=value;
		this.left=null;
		this.right=null;
		this.isLeaf=true;
	}
	
}

class DecodeTree5389 {
	
	DecodeTreeNode5389 root;
	
	public DecodeTree5389(){
		this.root = new DecodeTreeNode5389();
	}
}
