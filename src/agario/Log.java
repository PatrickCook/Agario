/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agario;

/**
 *
 * @author patrickcook
 */
public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * Log output
     * @param str 
     */
    public static void l(String str){
        System.out.println(ANSI_GREEN + str + ANSI_RESET);
    }
    /**
     * Error output
     * @param str 
     */
    public static void e(String err){
        System.out.println(ANSI_RED + "ERR: " + err + ANSI_RESET);
    }
    /**
     * Debug
     * @param str 
     */
    public static void d(String debug){
        System.out.println(ANSI_RED + "DEBUG: " + debug + ANSI_RESET);
    }
    /**
     * Print object
     * @param str 
     */
    public static void o(Object obj){
        System.out.println(obj);
    }
}
