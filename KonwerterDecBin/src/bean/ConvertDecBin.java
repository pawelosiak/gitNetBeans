/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;

/**
 *
 * @author pawel
 */
public class ConvertDecBin extends javax.swing.JButton implements Serializable {
	
	

	private String dec;
	private String bin;
	
	public ConvertDecBin() 
	{
		super();
	}
	
	public static char[] convertDecBin(String dec) 
	{
		String value = null;
		
		Object tekst = "Podano niewłaściwą wartość. Popraw wpis w polu DEC.";
		
		try {
		
		int Dec = Integer.parseInt(dec, 10);
		value = Integer.toBinaryString(Dec);
		}	catch(Exception e) {
			
			javax.swing.JOptionPane.showMessageDialog(null,  tekst,  "zły wpis", 0);
		}
		char[] charArray = value.toCharArray();
		
		System.out.println(charArray);
		return charArray;
	}

	public static int convertBinDec(String bin) 
	{
		
		int Dec = 0;
		Object tekst = "Podano niewłaściwą wartość. Popraw wpis w polu BIN.";
		try {
			
		 Dec = Integer.valueOf(bin, 2);
		
		}catch (Exception e) {
			
			javax.swing.JOptionPane.showMessageDialog(null,  tekst,  "zły wpis", 0);
		}
		return Dec;
	}
	
	public String getDec() {
		return dec;
	}

	
	public void setDec(String dec) {
		this.dec = dec;
	}

	
	public String getBin() {
		return bin;
	}

	
	public void setBin(String bin) {
		this.bin = bin;
	}

}
