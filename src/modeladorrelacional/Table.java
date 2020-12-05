/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeladorrelacional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author rsoto
 */
public class Table {
    
    public static final char DK_CHAR = '-';
    public static final char PK_CHAR = '*';
    public static final char FK_CHAR = '+';
    public static final char SINGLE_CHAR = '@';

    public static final int DK = '-';
    public static final int PK = '+';
    public static final int FK = '*';
    public static final int SINGLE = '@';
    
    private String name;
    private String primaryKey;
    private HashSet keys;
    
    private ArrayList<String> attributes;
    
    public Table(){
        name = "";
        primaryKey = null;
        keys = new HashSet<String>();
        attributes = new ArrayList<String>();
    }
    
    public Table(String name){
        name = name;
        primaryKey = null;
        keys = new HashSet<String>();
        attributes = new ArrayList<String>();
    }
    
    public boolean setPK(String pk){
        return keys.add(pk);
    }
    
    public boolean isPK(String a){
        return keys.contains(a);
    }
    
    public boolean add(String a){
        boolean result = false;
        if (a != null) {
            if (!attributes.contains(a)) {
                result = attributes.add(a);
            }
        }
        return result;
    }
    
    public boolean remove(String a){
        return attributes.remove(a);
    }
    
    public static String attribName(String a){
        return a.substring(1);
    }
    
    public static int attribType(String a){
        return a.charAt(0);
    }
    
    @Override
    public String toString(){
        
        StringBuilder sb = new StringBuilder(name);
        
        sb.append("(");
        
        Iterator it = attributes.iterator();
        int n = attributes.size();
        
        for (int i = 0; i < n; i++) {
            
            String a = (String) it.next();
            sb.append(a);
            
            if (isPK(a)) {
                sb.append("*");
            }
            
            if (i < (n - 1)) {
                sb.append(",");
            }
        }
        sb.append(")");
        
        return sb.toString();
    }
    
}
