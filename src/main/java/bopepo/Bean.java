/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bopepo;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author deivid
 */
@ManagedBean
@ViewScoped

public class Bean implements Serializable{
    
    public void gerarBoleto(){
      new MeuPrimeiroBoleto().exemplo();
    }
}
