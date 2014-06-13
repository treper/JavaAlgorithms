package ataosky.example.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
/*
* this code convert md5Id_tagId_list of such format to google open source all pair similarity's input format little edian
 * http://code.google.com/p/google-all-pairs-similarity-search
 * */
public class Md5IdTagIdListToBinary {
	
	public static void main(String[] args) throws IOException
	{
		String infile = args[0];
		String ofile = args[1];
		System.out.println(infile);
		System.out.println(ofile);
		FileInputStream fi = new FileInputStream(new File(infile));
		FileOutputStream fo = new FileOutputStream(new File(ofile));
		InputStreamReader ir = new InputStreamReader(fi);
		BufferedReader br = new BufferedReader(ir);
		String s;
		ByteBuffer bb =  ByteBuffer.allocate(4);
		while((s=br.readLine())!=null)
		{
			String[] ss = s.split("\t");
			Integer md5Id = Integer.parseInt(ss[0]);
			byte[] bytes = bb.putInt(md5Id).array();
			ArrayUtils.reverse(bytes);//little edian
			fo.write(bytes);
			bb.clear();
			Integer len = ss.length-1;
			bytes = bb.putInt(len).array();
			ArrayUtils.reverse(bytes);
			fo.write(bytes);
			bb.clear();
			for(int i=1;i<=len;i++)
			{
				Integer tagId = Integer.parseInt(ss[i]);
				bytes = bb.putInt(tagId).array();
				ArrayUtils.reverse(bytes);
				fo.write(bytes);
				bb.clear();
			}
			
		}
		fo.close();
		br.close();
		ir.close();
		fi.close();
	}

}
