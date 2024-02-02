package dragonquest;

import dragonquest.test.DragonQuestTest;

import javax.swing.*;    
import javax.swing.event.*;
import java.awt.*;  
import java.awt.event.*;
import java.util.ArrayList;

import java.io.Serializable;

public class DragonSetupUi implements Serializable
{
    @SuppressWarnings("compatibility:-2836217667449032901")
    private static final long serialVersionUID = 6221141047397587359L;
    GridBagLayout grid;
    GridBagConstraints gbc;
    JPanel panel;
    JLabel name,health, power, defence, agility;
    JTextField enter_name,enter_max_health,enter_power,enter_defence,enter_agility;
    int current_row = 0, total_items = 0;
    DragonQuestTest test;
    
    DragonSetupUi(){
       this.grid = new GridBagLayout();
       this.gbc = new GridBagConstraints();
       this.panel = new JPanel();
       this.panel.setLayout(grid);
       this.gbc.fill = GridBagConstraints.HORIZONTAL;  
       
       addNewDragonSetup();
       
       
    }
    
    void setupLabelTextField(JLabel l, String l_name,JTextField t, String t_name, int x, int y){  
       this.gbc.gridx = x;
       this.gbc.gridy = y;
       this.gbc.ipadx = 20;
       this.gbc.ipady = 10;
       l = new JLabel(l_name);
       this.panel.add(l, this.gbc); 
       this.gbc.gridx = x+1;
       this.gbc.gridy = y;
       this.gbc.ipadx = 20;
       this.gbc.ipady = 10;
       t = new JTextField(t_name);
       this.panel.add(t,this.gbc);
    }
    
    void addSpacer(int wt){
        
        this.gbc.gridx = 0;
        this.gbc.gridy = this.current_row++;
        this.gbc.ipady = 20;
        this.gbc.weighty = wt;
        this.panel.add(new JLabel(" "),this.gbc);
        this.gbc.weighty = 0;
    }
    
    void addNewDragon(){
        int index = -1;
        for(Component c : this.panel.getComponents()){
            index++;
        }
        Component c = this.panel.getComponent(index);
        this.panel.remove(c);
        this.current_row--;
        addSpacer(0);
        
        addNewDragonSetup();
    }
    
    void addNewDragonSetup(){
        this.total_items++;
       setupLabelTextField(this.name, "Name", this.enter_name, "Dragon"+Integer.toString(this.current_row), 0 , this.current_row++);
       setupLabelTextField(this.health, "Health ", this.enter_max_health, Integer.toString((int)(Math.random()*30+20)), 0 , this.current_row++);
       setupLabelTextField(this.power, "Power", this.enter_power, Integer.toString((int)(Math.random()*30+20)), 0 , this.current_row++);
       setupLabelTextField(this.defence, "Defence", this.enter_defence, Integer.toString((int)(Math.random()*30+20)), 0 , this.current_row++);
       setupLabelTextField(this.agility, "Agility", this.enter_agility, Integer.toString((int)(Math.random()*30+20)), 0 , this.current_row++);
       addSpacer(1);
    }
    
    ArrayList<ArrayList<String>> getItems(){
        ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
        for(int i=0 ; i<this.total_items ; i++){
            ArrayList<String> temp = new ArrayList<String>();
            int index = i*11;
            for(int j=0 ; j<5 ; j++){
                if(j>1){
                    int num = Integer.parseInt(((JTextField)(this.panel.getComponent(index+1))).getText());
                    test.greater_than(num,50);
                }
                temp.add(((JTextField)(this.panel.getComponent(index+1))).getText());
                index+=2;
            }
            s.add(temp);
        }
        return s;
    }
    
}
