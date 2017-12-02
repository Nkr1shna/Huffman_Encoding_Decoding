// KRISHNA CHAITANYA NELLOORE cs610 5389 prp

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class henc5389 {

private static String FILENAME = "";

static String[] code_table = new String[256];

static ArrayList<Integer> listForBinary = new ArrayList<Integer>();

public static void main(String[] args) {

								FILENAME = args[0];

								/** Building frequency map **/

								Integer[] freq_map = new Integer[256];

								int line_count=0;

								try (FileInputStream br = new FileInputStream(new File(FILENAME))) {

																								int line;

																								while ((line = br.read()) != -1) {
																																line_count++;
																																listForBinary.add(line);
																																if(freq_map[line]==null)
																																								freq_map[line]= 1;
																																else
																																								freq_map[line] = freq_map[line]+1;
																								}

																} catch (IOException e) {
																								e.printStackTrace();
																}
								BinaryNode5389 root=Huffman5389(freq_map,line_count);
								buildCodes5389(root,"");
								writeCodesToFile5389(code_table);
								writeToBinaryFile5389(args[0],listForBinary,code_table);
}

private static BinaryNode5389 Huffman5389(Integer[] freq_map, int line_count) {

								BinaryHeap5389 binHeap = new BinaryHeap5389(line_count);
								for(int m=0; m<freq_map.length; m++)
								{
																if(freq_map[m]!=null)
																{
																								int val = m;
																								int freq = freq_map[val];
																								BinaryNode5389 dn = new BinaryNode5389(val,freq);
																								binHeap.insert(dn);
																}

								}

								while(binHeap.getHeapSize()>1) {

																BinaryNode5389 min1 = binHeap.deleteMin();
																BinaryNode5389 min2 = binHeap.deleteMin();
																int val = -1;
																int freq = min1.getFreq() + min2.getFreq();
																BinaryNode5389 dn_new = new BinaryNode5389(val,freq,min1,min2);
																binHeap.insert(dn_new);
								}

								BinaryNode5389 root = binHeap.heap[1];
								return(root);
}

private static void buildCodes5389(BinaryNode5389 n, String path) {
								if (n.getLeft() != null)
																buildCodes5389(n.getLeft(), path+"0");
								if (n.getRight() != null)
																buildCodes5389(n.getRight(), path+"1");
								if (n.getLeft() == null && n.getRight() == null) {
																code_table[n.getVal()]=path;
								}

}

private static void writeCodesToFile5389(String[] code_table) {

								String filename = "code_table.txt";

								try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename,false))) {
																								boolean firstLine = true;
																								for(int i=0; i<code_table.length; i++)
																								{
																																if(code_table[i]!=null)
																																{
																																								String content ="";
																																								if(firstLine) {
																																																content = Integer.toString(i) + " " + code_table[i];
																																																firstLine = false;
																																								}
																																								else
																																																content = "\n" + Integer.toString(i) + " " + code_table[i];
																																								bw.write(content);
																																}

																								}
																								bw.close();

																} catch (IOException e) {
																								e.printStackTrace();
																}

}

private static void writeToBinaryFile5389(String filename,ArrayList<Integer> listForBinary, String[] code_table) {
	String delfilename = filename;							
	filename+=".huf";
								File fl = new File(delfilename);
								try (FileOutputStream fos  = new FileOutputStream(new File(filename),false)) {

																								String code_buffer ="";
																								for(int i=0; i<listForBinary.size(); i++)
																								{
																																String code = code_table[listForBinary.get(i)];
																																String current_code = code_buffer+code;

																																int len = current_code.length();
																																int mul = len-len%8;

																																if(len<8)
																																								code_buffer = current_code;

																																else{
																																								int start_index=0;
																																								int end_index =8;
																																								while(end_index <= mul) {
																																																byte binary = (byte)Integer.parseInt(current_code.substring(start_index, end_index),2);
																																																start_index=end_index;
																																																end_index+=8;
																																																fos.write(binary);
																																								}

																																								code_buffer = current_code.substring(mul, len);
																																}
																								}

																								fos.close();

																} catch (IOException e) {
																								e.printStackTrace();
																}
								fl.delete();

}
}


class BinaryHeap5389 {

private int heap_size;
BinaryNode5389[] heap;
private static final int d = 2;

public BinaryHeap5389(int capacity){
								heap_size=0;
								heap = new BinaryNode5389[capacity];
}

/** Function to check if heap is empty **/

public boolean isEmpty( )
{
								return heap_size == 0;
}

/** Check if heap is full **/

public boolean isFull( )
{
								return heap_size == heap.length;
}

/** Return indices of parent and children **/

private int parent(int i)
{
								return i/d;
}

private int left(int i)
{
								return d*i;
}

private int right(int i)
{
								return d*i+1;
}

/** Insert new node **/

public void insert(BinaryNode5389 node)
{

								if (isFull( ))
																throw new NoSuchElementException("Heap is full");
								heap_size++;
								int i = heap_size;
								while(i > 1 && heap[parent(i)].getFreq() > node.getFreq()) {
																heap[i] = heap[parent(i)];
																i = parent(i);
								}
								heap[i] = node;
}

public BinaryNode5389 findMin( )
{
								if (isEmpty())
																throw new NoSuchElementException("Heap is empty");
								return heap[3];
}

/** Delete min **/

public BinaryNode5389 deleteMin()
{
								if (isEmpty())
																throw new NoSuchElementException("Heap is empty");
								BinaryNode5389 min_node = heap[1];
								heap[1] = heap[heap_size];
								heap_size--;
								heapify(1);
								return min_node;
}

/** Percolate Down **/

private void heapify(int i)
{
								int l = left(i);
								int r = right(i);
								int smallest;
								if(r <= heap_size) {
																if(heap[l].getFreq() < heap[r].getFreq())
																								smallest = l;
																else
																								smallest = r;
																if(heap[i].getFreq() > heap[smallest].getFreq()) {
																								swap(i, smallest);
																								heapify(smallest);
																}
								}
}


private void swap(int i, int l) {
								BinaryNode5389 tmp = heap[i];
								heap[i] = heap[l];
								heap[l] = tmp;
}


public int getHeapSize(){
								return heap_size;
}


}

class BinaryNode5389 {

private int val=-1;
private int freq=-1;
private BinaryNode5389 left=null;
private BinaryNode5389 right=null;

public int getVal() {
								return val;
}
public void setVal(int val) {
								this.val = val;
}
public int getFreq() {
								return freq;
}
public void setFreq(int freq) {
								this.freq = freq;
}
public BinaryNode5389 getLeft() {
								return left;
}
public void setLeft(BinaryNode5389 left) {
								this.left = left;
}
public BinaryNode5389 getRight() {
								return right;
}
public void setRight(BinaryNode5389 right) {
								this.right = right;
}
public BinaryNode5389(int val, int freq){
								this.val = val;
								this.freq = freq;
}

public BinaryNode5389(int val, int freq, BinaryNode5389 left, BinaryNode5389 right){
								this.val = val;
								this.freq = freq;
								this.left = left;
								this.right = right;
}

}
