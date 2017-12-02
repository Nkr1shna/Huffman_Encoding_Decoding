# Huffman_Encoding_Decoding

Compilation and execution instructions
1. Git clone or download files henc5389.java and hdec5389.java to a folder.
2. Place the file to be encoded in the root of the folder where henc5389.java and hdec5389.java are located.
3. Open terminal/ssh into the root folder.
4. Type javac henc5389.java and press enter to compile.
5. Type java henc5389 <filename> and press enter.
6. You can see the endoced file in the same root folder with name<filename>.huf
7. Type javac hdec5389.java and press enter to compile.
8. Type java hdec5389 <filename>.huf and press enter.
9. The Original file will be over written with the same name i.e. <filename>

Warnings:
1. Do not run the henc5389 on 2 different files before decoding. The huffman codes are written to a file code_table.txt. If you run the henc first time on one file and then again on another file, the file code_table.txt will be over written and the old huffman codes will be lost.
