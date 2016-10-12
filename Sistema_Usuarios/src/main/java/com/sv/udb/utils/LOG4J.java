/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.utils;

import org.apache.log4j.*;

/**
 *
 * @author aleso
 */
public class LOG4J {
    
    private static Logger log = Logger.getLogger(LOG4J.class);
    public LOG4J() {
        try{
            PropertyConfigurator.configure(this.getClass().getClassLoader().getResource("/log4j.properties").getPath());;
            
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
        
    }
    //El mas básico, equivalente a un System.out.println
    public void trace(String mens){
        try{
            log.trace(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
        
    }
    //Muy util para procesos, para valores, variables
    public void debug(String mens){
        try{
            log.debug(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
        
    }
    //Información que puede ser importante
    public void info(String mens){
        try{
            log.info(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
    }
    //Fallos no críticos
    public void warn(String mens){
        try{
            log.warn(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
    }
    //Errores importantes, que no obligan a cerrar la aplicación
    public void error(String mens){
        try{
            log.error(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
    }
    //Errores importantes que obligan a cerrar la aplicación
    public void fatal(String mens){
        try{
            log.fatal(mens);
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
        }
    }
    
}
