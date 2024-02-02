package dragonquest;

import dragonquest.test.DragonQuestTest;

import javax.swing.*;    
import javax.swing.event.*;
import java.awt.*;  
import java.awt.event.*;
import java.util.ArrayList;
import java.io.Serializable;

public class PlayerSetupUi implements Serializable
{
    @SuppressWarnings("compatibility:680147837059426755")
    private static final long serialVersionUID = -1576003291879956642L;
    GridBagLayout grid;
    GridBagConstraints gbc;
    JPanel panel;
    JLabel name,max_health,power,defence,agility, item;
    JTextField enter_name,enter_max_health,enter_power,enter_defence,enter_agility;
    JComboBox entry_item;
    DragonQuestTest test;
    int current_row = 0, total_items = 0;
    
    PlayerSetupUi(){
       this.grid = new GridBagLayout();
       this.gbc = new GridBagConstraints();
       this.panel = new JPanel();
       this.panel.setLayout(grid);
       this.gbc.fill = GridBagConstraints.HORIZONTAL; 
       this.gbc.anchor = GridBagConstraints.FIRST_LINE_END;//GridBagConstraints.NORTH;
       // this.gbc.weighty = 2;
       
       addNewPlayerSetup();
       
       
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
    
    void setupLabelCombo(JLabel l, String l_name,JComboBox t, String[] t_name, int x, int y){
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
       t = new JComboBox<String>(t_name);
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
    
    void addNewPlayer(){
        int index = -1;
        for(Component c : this.panel.getComponents()){
            index++;
        }
        Component c = this.panel.getComponent(index);
        this.panel.remove(c);
        this.current_row--;
        addSpacer(0);
        addNewPlayerSetup();
    }
    
    void addNewPlayerSetup(){
       this.total_items++;
       String[] items = new String[]{"Iron Armor","Iron Sword","Wood Sword","Wood Armor","Iron Shield","Wood Shield","Health Potion 10","Health Potion 20","Health Potion 30"};
       String[] armors = new String[]{"Iron Armor","Wood Armor"};
       String[] swords = new String[]{"Iron Sword","Wood Sword"};
       String[] shield = new String[]{"Iron Shield","Wood Shield"};
       String[] potions = new String[]{"Health Potion 10","Health Potion 20","Health Potion 30"};
       setupLabelTextField(this.name, "Name", this.enter_name, "Enter Name"+Integer.toString(current_row), 0 , this.current_row++);
       setupLabelTextField(this.max_health, "Max Health (20 + ", this.enter_max_health, "0", 0 , this.current_row++);
       setupLabelTextField(this.power, "Power", this.enter_power, "10", 0 , this.current_row++);
       setupLabelTextField(this.defence, "Defence", this.enter_defence, "10", 0 , this.current_row++);
       setupLabelTextField(this.agility, "Agility", this.enter_agility, "10", 0 , this.current_row++);
       setupLabelCombo(this.item,"Item 1",this.entry_item, armors,0, this.current_row++);
       setupLabelCombo(this.item,"Item 2",this.entry_item, swords,0, this.current_row++);
       setupLabelCombo(this.item,"Item 3",this.entry_item, shield,0, this.current_row++);
       setupLabelCombo(this.item,"Item 4",this.entry_item, potions,0, this.current_row++);
       addSpacer(1);
    }
    
    ArrayList<ArrayList<String>> getItems(){
        String[] items = new String[]{"Iron Armor","Iron Sword","Wood Sword","Wood Armor","Iron Shield","Wood Shield","Health Potion 10","Health Potion 20","Health Potion 30"};
        ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
        for(int i=0 ; i<this.total_items ; i++){
            ArrayList<String> temp = new ArrayList<String>();
            int index = i*19;
            for(int j=0 ; j<5 ; j++){
                if(j>1){
                    int num = Integer.parseInt(((JTextField)(this.panel.getComponent(index+1))).getText());
                    // testing if enetred value is always within the range
                    test.greater_than(num,50);
                }
                temp.add(((JTextField)(this.panel.getComponent(index+1))).getText());
                index+=2;
            }
            
            for(int j=0 ; j<4 ; j++){
                JComboBox cb = ((JComboBox)(this.panel.getComponent(index+1)));
                test.string_lies_in_array(items,(String)cb.getItemAt(cb.getSelectedIndex()));
                temp.add((String)cb.getItemAt(cb.getSelectedIndex()));
                index+=2;
            }
            s.add(temp);
        }
        return s;
    }
    
}

