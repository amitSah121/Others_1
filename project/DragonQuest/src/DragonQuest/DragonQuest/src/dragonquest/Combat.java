package dragonquest;

import dragonquest.test.DragonQuestTest;

import java.awt.CardLayout;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.HashMap;

import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import java.io.Serializable;

class Combat implements Serializable{
    @SuppressWarnings("compatibility:8326340002620353695")
    private static final long serialVersionUID = 6144079775145415769L;
    ArrayList<ArrayList<String>> dragon_list , player_list;
    HashMap<String,Boolean> dragon_dead, player_dead;
    JPanel combat,panel,output, panel1, panel2, attack_tab,heal_tab,take_tab,equip_tab;
    JTabbedPane tabs;
    JComboBox attack_option_list, heal_option_list, take_option_list, drop_option_list, equip_option_list;
    JButton attack_dragon, take_heal, take_item, drop_item, equip_item;
    JScrollPane scroll;
    JList<String> p_list;  
    JLabel health_bar;
    String[] items = new String[]{"Iron Armor","Iron Sword","Wood Sword","Wood Armor","Iron Shield","Wood Shield","Health Potion 10","Health Potion 20","Health Potion 30"};
    String[] heal_potions = new String[]{"Health Potion 10","Health Potion 20","Health Potion 30"};
    int[] health_potion = new int[]{5,10,15};
    String[] equip_options = new String[]{"Iron Armor","Iron Sword","Wood Sword","Wood Armor","Iron Shield","Wood Shield"};
    int[] health_armor = new int[]{5,5,2,2,5,2};
    String[] attack_weapon = new String[]{"Iron Sword","Wood Sword"};
    int[] attack_weapon_damage = new int[]{2,1};
    String[] defense_armor = new String[]{"Iron Armor","Wood Armor","Iron Shield","Wood Shield"};
    String[] dragon_attacks = new String[]{"Claw","Burn","Strike"};
    ArrayList<String> surrounding_items = new ArrayList<String>();
    int player_turn = 0,constant_damage = 1, dragon_turn = 0;
    ArrayList<String> player_name;
    HashMap<String,Boolean> players_alive;
    JLabel current_player;
    transient Player player_class;
    DefaultListModel<String> model;
    JList<String> model_list;
    CardLayout card;
    JPanel app;
    EndScreen end_screen;
    DragonQuestTest test;
    
    Combat() {
        this.combat = new JPanel();
        this.combat.setLayout(new BoxLayout(this.combat,BoxLayout.Y_AXIS));
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel,BoxLayout.X_AXIS));
        this.panel1 = new JPanel();
        this.panel1.setLayout(new BoxLayout(this.panel1,BoxLayout.Y_AXIS));
        this.health_bar = new JLabel("Health");    
        this.panel1.setBackground(new Color(250,250,250));
        this.panel2 = new JPanel();
        this.panel2.setLayout(new BoxLayout(this.panel2,BoxLayout.Y_AXIS));
        this.tabs = new JTabbedPane(){
            @Override
            public Dimension getMaximumSize()
            {
                Dimension d = super.getMaximumSize();
                d.width = Integer.MAX_VALUE;
                return d;
            }
        };
        this.attack_tab = new JPanel();
        this.heal_tab = new JPanel();
        this.take_tab = new JPanel();
        this.equip_tab = new JPanel();
        this.tabs.add("Attack",this.attack_tab);
        this.tabs.add("Heal",this.heal_tab);
        this.tabs.add("Take/Drop",this.take_tab);
        this.tabs.add("Equip",this.equip_tab);
        this.panel2.add(this.tabs);
        this.panel.add(this.panel1);
        this.panel.add(this.panel2);
        this.output = new JPanel();
        this.output.setBackground(new Color(223,223,223));
        this.model = new DefaultListModel<>();
        this.model_list = new JList<>( this.model );
        this.output.add(this.model_list);
        this.scroll = new JScrollPane(this.output){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 150);
            }
        };
        this.combat.add(this.panel);
        this.combat.add(this.scroll);
        
        
    }
    
    void set_events(){
        
        this.take_heal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String h = (String)heal_option_list.getItemAt(heal_option_list.getSelectedIndex());
                if(player_class.get_player_heals_status(player_turn, h)){
                    int temp_h = player_class.get_player_heals_value(player_turn,h);
                    player_class.set_player_heals_status(player_turn,h,false);
                    int new_health = temp_h + player_class.get_player_health(player_turn);
                    if(Integer.parseInt(player_class.player.get(player_turn).get(1)) >= new_health){
                        player_class.set_player_health(player_turn,new_health);
                    }
                    player_list.get(player_turn).set(player_class.get_item_index(player_turn,h),"used");
                    String name = Integer.toString(player_turn)+" : "+player_list.get(player_turn).get(0);
                    int current_player_health = player_class.get_player_health(player_turn);
                    boolean b1 = update_player_turn();
                    boolean b2 = dragon_turn();
                    update_tabs();
                    model.addElement(name+" took healing potion with health "+Integer.toString(temp_h)+" and Increased health to "+Integer.toString(current_player_health));
                    if(b1 || b2){
                        if(b1) end_screen.won = "Dragon";
                        else if(b2) end_screen.won = "Player";
                        end_screen.update();
                        card.next(app);
                    }
                }
            }
        });
        
        this.equip_item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String h = (String)equip_option_list.getItemAt(equip_option_list.getSelectedIndex());
                if(!player_class.get_player_armor_status(player_turn, h)){
                    player_class.set_player_armor_status(player_turn,h,true);
                    player_list.get(player_turn).set(player_class.get_item_index(player_turn,h),"used");
                    String name = Integer.toString(player_turn)+" : "+player_list.get(player_turn).get(0);
                    boolean b1 = update_player_turn();
                    boolean b2 = dragon_turn();
                    update_tabs();
                    model.addElement(name+" equipped "+h);
                    if(b1 || b2){
                        if(b1) end_screen.won = "Dragon";
                        else if(b2) end_screen.won = "Player";
                        end_screen.update();
                        card.next(app);
                    }
                }
            }
        });
        
        this.drop_item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String h = (String)drop_option_list.getItemAt(drop_option_list.getSelectedIndex());
                int p = player_class.get_item_index(player_turn,h);
                if(p != -1){
                    surrounding_items.add(h);
                    player_list.get(player_turn).set(player_class.get_item_index(player_turn,h),"used");
                    String name = Integer.toString(player_turn)+" : "+player_list.get(player_turn).get(0);
                    boolean b1 = update_player_turn();
                    boolean b2 = dragon_turn();
                    update_tabs();
                    model.addElement(name + " dropped item "+h);
                    if(b1 || b2){
                        if(b1) end_screen.won = "Dragon";
                        else if(b2) end_screen.won = "Player";
                        end_screen.update();
                        card.next(app);
                    }
                }
            }
        });
        
        this.take_item.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String h = (String)take_option_list.getItemAt(take_option_list.getSelectedIndex());
                int p = player_class.get_item_index(player_turn,"used");
                if(p != -1){
                    for(int i=0 ; i<surrounding_items.size() ; i++){
                        if(surrounding_items.get(i).equals(h)){
                            surrounding_items.remove(i);
                            break;
                        }
                    }
                    player_list.get(player_turn).set(p,h);
                    for(int i=0 ; i<heal_potions.length ; i++){
                        if(heal_potions[i].equals(h)){
                            player_class.set_player_heals_status(player_turn,h,true);
                        }
                    }
                    for(int i=0 ; i<equip_options.length ; i++){
                        if(equip_options[i].equals(h)){
                            player_class.set_player_armor_status(player_turn,h,false);
                        }
                    }
                    String name = Integer.toString(player_turn)+" : "+player_list.get(player_turn).get(0);
                    boolean b1 = update_player_turn();  
                    boolean b2 = dragon_turn();
                    update_tabs();
                    model.addElement(name+" took "+h+" from the surrounding.");
                    if(b1 || b2){
                        if(b1) end_screen.won = "Dragon";
                        else if(b2) end_screen.won = "Player";
                        end_screen.update();
                        card.next(app);
                    }
                }
            }
        });
        
        this.attack_dragon.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String h = (String)attack_option_list.getItemAt(attack_option_list.getSelectedIndex());
                if(!dragon_dead.get(h)){
                    int damage = constant_damage;
                    if(player_class.weapon_equipped(player_turn) != null){
                        int damage1 = player_class.is_item_weapon(player_turn,player_class.weapon_equipped(player_turn));
                        if(damage1 != -1) damage += damage1;
                    }
                    update_dragon_life(h,damage);
                    String name = Integer.toString(player_turn)+" : "+player_list.get(player_turn).get(0);
                    boolean b2 = dragon_turn();
                    boolean b1 = update_player_turn();
                    update_tabs();
                    model.addElement(name+" attacked dragon with "+h+" with a damage of "+Integer.toString(damage));
                    if(b1 || b2){
                        if(b1) end_screen.won = "Dragon";
                        else if(b2) end_screen.won = "Player";
                        end_screen.update();
                        card.next(app);
                    }
                }
            }
        });
        
    }
    
    boolean update_player_turn(){
        while(true){
            player_turn = player_turn+1 < player_list.size() ? player_turn+1 : 0;
            int p = 0;
            for(Map.Entry<String,Boolean> m1 : this.player_dead.entrySet()){
                if(!m1.getValue()){
                    p++;
                }
            }
            if(p==0) return true;
            if(!this.player_dead.get(this.player_list.get(player_turn).get(0))){
                break;
            }
        }
        
        return false;
        
    }
    
    
    
    void update_dragon_life(String h,int damage){
        for(ArrayList<String> as1 : this.dragon_list){
            if(as1.get(0).equals(h)){
                as1.set(1,Integer.toString(Integer.parseInt(as1.get(1))-damage));
                if(Integer.parseInt(as1.get(1)) <= 0){
                    this.dragon_dead.put(h,true);
                    model.addElement(h+" died.");
                }
            }
        }
    }
    
    boolean dragon_turn(){
        int p = 0;
        for(Map.Entry<String,Boolean> m1 : this.dragon_dead.entrySet()){
            if(!m1.getValue()){
                p++;
            }
        }
        if(p==0) return true;
        int damage = (int)(Math.random()*2+1);
        int maxRange = this.player_list.size() - 1, minRange = 0;
        int player = 0;
        while(true){
            player = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
            if(this.player_dead.get(this.player_list.get(player).get(0))){
                continue;
            }
            break;
        }
        maxRange = this.dragon_list.size() - 1;
        minRange = 0;
        int dragon = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
        maxRange = this.dragon_attacks.length - 1;
        minRange = 0;
        int dragon_attack = (int) (Math.random() * (maxRange - minRange + 1)) + minRange;;
        int new_health = this.player_class.set_player_health(player,this.player_class.get_player_health(player)-damage);
        if(this.player_class.get_player_health(player) <= 0){
            this.player_dead.put(this.player_list.get(player).get(0),true);
            this.model.addElement(this.dragon_list.get(dragon).get(0)+" killed "+this.player_list.get(player).get(0)+" with "+this.dragon_attacks[dragon_attack]);
        }else{
            this.model.addElement(this.dragon_list.get(dragon).get(0)+" attacked "+this.player_list.get(player).get(0)+" with "+this.dragon_attacks[dragon_attack]+" and player health got to "+Integer.toString(new_health));
        }
        
        return false;
    }
    
    void set_lists(ArrayList<ArrayList<String>> player_list ,ArrayList<ArrayList<String>> dragon_list){
        this.dragon_list = dragon_list;
        this.player_list = player_list;
        this.player_name = new ArrayList<>();
        this.players_alive = new HashMap<>();
        this.dragon_dead = new HashMap<>();
        this.player_dead = new HashMap<>();
        for(ArrayList<String> as1 : this.dragon_list){
            this.dragon_dead.put(as1.get(0),false);
        }
        for(ArrayList<String> as1 : this.player_list){
            this.player_dead.put(as1.get(0),false);
        }
//        this.p_list = new JList<String>(get_players());
        this.current_player = new JLabel(Integer.toString(player_turn) + " : " +player_list.get(player_turn).get(0),SwingConstants.CENTER){
            @Override
            public Dimension getMaximumSize()
            {
                Dimension d = super.getMaximumSize();
                d.width = Integer.MAX_VALUE;
                return d;
            }
        };
        current_player.setAlignmentX(Component.CENTER_ALIGNMENT);
        current_player.setFont(new Font("Serif", Font.PLAIN, 16));
        this.panel1.add(current_player);
        this.health_bar.setText("Health: "+Integer.toString(Player.health_starting));
        this.panel1.add(this.health_bar);
//        this.panel1.add(this.p_list); 
        this.player_class = new Player(this.player_list);
        set_tabs();
    }
    
    
    
    DefaultListModel<String> get_players(){
        DefaultListModel<String> temp = new DefaultListModel<String>();
        for(ArrayList<String> as1 : this.player_list){
            temp.addElement(as1.get(0));
            this.player_name.add(as1.get(0));
            this.players_alive.put(as1.get(0),true);
        }
        return temp;
    }
    
    void set_tabs(){
        String[] temp = new String[this.dragon_list.size()];
        for(int i=0 ; i < this.dragon_list.size() ; i++){
            temp[i] = this.dragon_list.get(i).get(0);
        }
        this.attack_option_list = new JComboBox(temp);
        this.attack_dragon = new JButton("Attack");
        this.attack_tab.add(this.attack_option_list);
        this.attack_tab.add(this.attack_dragon);
        
        temp = get_player_items("heal");
        this.heal_option_list = new JComboBox<String>(temp);
        this.take_heal= new JButton("Heal");
        this.heal_tab.add(this.heal_option_list);
        this.heal_tab.add(this.take_heal);
        
        temp = (String[])surrounding_items.toArray(new String[surrounding_items.size()]);
        this.take_option_list = new JComboBox<String>(temp);        
        temp = get_player_items("all");
        this.drop_option_list = new JComboBox<String>(temp);
        this.take_item= new JButton("Take");
        this.drop_item= new JButton("Drop");
        this.take_tab.add(this.take_option_list);
        this.take_tab.add(this.take_item);
        this.take_tab.add(this.drop_option_list);
        this.take_tab.add(this.drop_item);
        
        temp = get_player_items("equip");
        this.equip_option_list = new JComboBox<String>(temp);
        this.equip_item= new JButton("Equip");
        this.equip_tab.add(this.equip_option_list);
        this.equip_tab.add(this.equip_item);
        
    }
    
    void update_tabs(){
        this.health_bar.setText("Health: "+Integer.toString(this.player_class.get_player_health(this.player_turn)));
        current_player.setText(Integer.toString(player_turn) + " : " +player_list.get(player_turn).get(0));
        ArrayList<String> temp1 = new ArrayList<>();
        for(int i=0 ; i < this.dragon_list.size() ; i++){
            if(this.dragon_dead.get(this.dragon_list.get(i).get(0))) continue;
            temp1.add(this.dragon_list.get(i).get(0));
        }
        this.attack_option_list.removeAllItems();
        for(int i=0 ; i<temp1.size() ; i++){
            this.attack_option_list.addItem(temp1.get(i));
        }
        
        String[] temp = get_player_items("heal");
        this.heal_option_list.removeAllItems();
        for(int i=0 ; i<temp.length ; i++){
            this.heal_option_list.addItem(temp[i]);
        }
        
        temp = (String[])surrounding_items.toArray(new String[surrounding_items.size()]);
        this.take_option_list.removeAllItems();
        for(int i=0 ; i<temp.length ; i++){
            this.take_option_list.addItem(temp[i]);
        }        
        temp = get_player_items("all");
        this.drop_option_list.removeAllItems();
        for(int i=0 ; i<temp.length ; i++){
            this.drop_option_list.addItem(temp[i]);
        }
        
        temp = get_player_items("equip");
        this.equip_option_list.removeAllItems();
        for(int i=0 ; i<temp.length ; i++){
            this.equip_option_list.addItem(temp[i]);
        }
            
        this.panel.revalidate();  
        this.panel.repaint();
    }
    
    String[] get_player_items(String item_name){
        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<String> as1 = player_list.get(player_turn);
                if(item_name.equals("heal")){
                    for(int i=0 ; i<heal_potions.length ; i++){
                        for(int m=0 ; m<4 ; m++){
                            if(heal_potions[i].equals(as1.get(m+5))) temp.add(heal_potions[i]);
                        }
                    }
                }else if(item_name.equals("equip")){
                    for(int i=0 ; i<equip_options.length ; i++){
                        for(int m=0 ; m<4 ; m++){
                            if(equip_options[i].equals(as1.get(m+5))) temp.add(equip_options[i]);
                        }
                    }
                }else{
                    for(int i=0 ; i<items.length ; i++){
                        for(int m=0 ; m<4 ; m++){
                            if(items[i].equals(as1.get(m+5))) temp.add(items[i]);
                        }
                    }
                
        }
        
        return (String[])temp.toArray(new String[temp.size()]);
    }
    
    class Player{
        ArrayList<ArrayList<String>> player;
        ArrayList<HashMap<String,Integer>> armor;
        ArrayList<HashMap<String,Boolean>> equipped;
        ArrayList<HashMap<String,Integer>> heals;
        ArrayList<HashMap<String,Boolean>> heal_used;
        HashMap<String,Integer> player_life;
        final static int health_starting = 20;
        
        Player(ArrayList<ArrayList<String>> player){
            this.player = player;
            this.armor = new ArrayList<>();
            this.equipped = new ArrayList<>();
            this.heals = new ArrayList<>();
            this.heal_used = new ArrayList<>();
            this.player_life = new HashMap<>();
            
            for(int j=0 ; j<this.player.size() ; j++){
                String temp = this.player.get(j).get(0);
                this.player_life.put(temp,Player.health_starting);
                HashMap<String,Integer> temp_a = new HashMap<String,Integer>();
                HashMap<String,Boolean> temp_e = new HashMap<String,Boolean>();
                HashMap<String,Integer> temp_h = new HashMap<String,Integer>();
                HashMap<String,Boolean> temp_u = new HashMap<String,Boolean>();
                for(ArrayList<String> as1 : this.player){
                    if(as1.get(0).equals(temp)){
                        if("equip".equals("equip")){
                            for(int i=0 ; i<equip_options.length ; i++){
                                for(int m=0 ; m<4 ; m++){
                                    if(equip_options[i].equals(as1.get(m+5))){
                                        for(int k=0 ; k<equip_options.length ; k++){
                                            if(as1.get(m+5).equals(equip_options[k])){
                                                temp_a.put(equip_options[k],health_armor[k]);
                                                temp_e.put(equip_options[k],false);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            for(int i=0 ; i<heal_potions.length ; i++){
                                for(int m=0 ; m<4 ; m++){
                                    if(heal_potions[i].equals(as1.get(m+5))){
                                        for(int k=0 ; k<heal_potions.length ; k++){
                                            if(as1.get(m+5).equals(heal_potions[k])){
                                                temp_h.put(heal_potions[k],health_potion[k]);
                                                temp_u.put(heal_potions[k],true);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            this.armor.add(temp_a);
                            this.equipped.add(temp_e);
                            this.heals.add(temp_h);
                            this.heal_used.add(temp_u);
                        }
                    }
                }
            }
            
        }
        
        
        boolean get_player_armor_status(int player_id,String armor){
            Boolean b = this.equipped.get(player_id).get(armor);
            return b;
        }
        
        boolean get_player_heals_status(int player_id,String heal){
            Boolean b = this.heal_used.get(player_id).get(heal);
            return b;
        }
        
        void set_player_heals_status(int player_id,String heal, boolean b){
            // testing if heal actually lies in heal_potions
            if(heal != null)test.string_lies_in_array(heal_potions,heal);
            this.heal_used.get(player_id).put(heal,b);
        }
        
        void set_player_armor_status(int player_id,String armor, boolean b){
            // testing if item actually lies in items
            if(armor != null)test.string_lies_in_array(items,armor);
            this.equipped.get(player_id).put(armor,b);
        }
        
        
        
        Integer set_player_health(int player_id, int health){
            boolean b1 = false;
            int b1i = -1;
            int final_health = health;
            for(int i=0 ; i<defense_armor.length ; i++){
                if(this.equipped.get(player_id).get(defense_armor) != null && this.equipped.get(player_id).get(defense_armor[i])){
                    b1 = true;
                    b1i = i;
                    break;
                }
            }
            if(b1){
                switch(defense_armor[b1i]){
                    case "Iron Armor":
                    this.player_life.put(this.player.get(player_id).get(0), health+2);
                    case "Iron Shield":
                    this.player_life.put(this.player.get(player_id).get(0), health+4);
                    break;
                    case "Wood Shield":
                    this.player_life.put(this.player.get(player_id).get(0), health+3);
                    break;
                }
                switch(defense_armor[b1i]){
                    case "Wood Armor":
                    this.player_life.put(this.player.get(player_id).get(0), health+1);
                    case "Iron Shield":
                    this.player_life.put(this.player.get(player_id).get(0), health+3);
                    break;
                    case "Wood Shield":
                    this.player_life.put(this.player.get(player_id).get(0), health+2);
                    break;
                }
                return this.player_life.get(this.player.get(player_id).get(0));
            }
            this.player_life.put(this.player.get(player_id).get(0), health);
            return this.player_life.get(this.player.get(player_id).get(0));
        }
        
        Integer get_player_armor_value(int player_id,String armor){
            // testing if armor actually lies in items
            if(armor != null)test.string_lies_in_array(items,armor);
            Integer b = this.armor.get(player_id).get(armor);
            return b;
        }
        
        Integer get_player_heals_value(int player_id,String armor){
            // testing if armor actually lies in health_potions
            if(armor != null)test.string_lies_in_array(heal_potions,armor);
            Integer b = this.heals.get(player_id).get(armor);
            return b;
        }
        
        Integer get_player_health(int player_id){
            return this.player_life.get(this.player.get(player_id).get(0));
        }
        
        Integer get_item_index(int player_id, String item){
            for(int i=0 ; i<4 ; i++){
                if(this.player.get(player_id).get(i+5).equals(item)){
                    return i+5;
                }
            }
            return -1;
        }
        
        Integer is_item_weapon(int player_id,String item){
            // testing if item actually lies in items
            if(item != null) test.string_lies_in_array(items,item);
            if(this.equipped.get(player_id).get(item)){
                int p = this.get_item_index(player_id,item);
                if(item.equals("Iron Sword")) return 2;
                else if(item.equals("Wood Sword")) return 1;
            }
            return -1;
        }
        
        String weapon_equipped(int player_id){
            for(Map.Entry<String,Boolean> m1 : this.equipped.get(player_id).entrySet()){
                if(m1.getValue()){
                    if(m1.getKey().equals("Iron Sword")) return "Iron Sword";
                    else if(m1.getKey().equals("Wood Sword")) return "Wood Sword";
                }
            }
            return null;
        }
        
    }
    
    
    
}
