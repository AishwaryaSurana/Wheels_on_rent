/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vaibhav
 */
public class  State{
    String state_name;

    public State() {
    }

    public State(String city_name, String state_name) {
        
        this.state_name = state_name;
    }

  

    public String getState_name() {
        return state_name;
    }

    
    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
    
    
}
