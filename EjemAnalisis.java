/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejemanalisis;

/**
 *
 * @author DELL
 */
public class EjemAnalisis {

    public static void main(String[] args) {
        Suma(1000000000);
    }
    
    public static long Suma(long n){
         long Inicio=System.currentTimeMillis();
         long Resultado = 0;
         for (long j = 1; j < 10; j++) 
         for(long i=1; i<n; i++)
         Resultado += 1;
         long Final= System.currentTimeMillis();
         long Total = Final - Inicio;
         System.out.println("Tiempo: "+Total+" ns ");
return Resultado;
          }

}