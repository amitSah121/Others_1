package dragonquest;

import dragonquest.test.DragonQuestTest;

import javax.swing.*;    
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;  
import java.util.ArrayList;


public class DragonQuest extends JFrame{
    @SuppressWarnings("compatibility:-6154986216128234410")
    private static final long serialVersionUID = 7820391843117405114L;
    Boolean go_next = false;
    Combat combat;
    JPanel panel,groupBtn,setup,app;
    PlayerSetupUi player_setup;
    DragonSetupUi dragon_setup;
    JButton bt1,bt2,bt3;
    JScrollPane scroll ;
    CardLayout card;
    EndScreen end_screen;
    DragonQuestTest test;

    DragonQuest(){
        test = new DragonQuestTest();
        // player and dragon setup, in card 1
        panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        player_setup = new PlayerSetupUi();
        player_setup.test = test;
        player_setup.panel.setBackground(new Color(200,200,200));  
        panel.add(player_setup.panel);
        dragon_setup = new DragonSetupUi();
        dragon_setup.test = test;
        dragon_setup.panel.setBackground(new Color(223,223,223));  
        dragon_setup.addNewDragon();   
        dragon_setup.addNewDragon();   
        dragon_setup.addNewDragon();   
        dragon_setup.addNewDragon(); 
        panel.add(dragon_setup.panel);
        
        groupBtn = new JPanel();
        groupBtn.setLayout(new BoxLayout(groupBtn,BoxLayout.X_AXIS));
        bt1 = new JButton("Add Player");
        bt1.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                player_setup.addNewPlayer();
                player_setup.panel.revalidate();  
                player_setup.panel.repaint();
            }  
        });
        bt2 = new JButton("Add Random Dragon");
        bt2.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                dragon_setup.addNewDragon(); 
                dragon_setup.panel.revalidate();  
                dragon_setup.panel.repaint();
            }  
        }); 
        
        bt3 = new JButton("Combat"){
            @Override
            public Dimension getMaximumSize()
            {
                Dimension d = super.getMaximumSize();
                d.width = Integer.MAX_VALUE;
                return d;
            }
        };
        bt3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        bt3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setup = new JPanel();
        setup.setLayout(new BoxLayout(setup,BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Dragon Quest");
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        title.setFont(new Font("Serif", Font.PLAIN, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        setup.add(title);
        setup.add(panel);
        groupBtn.add(bt1);
        groupBtn.add(bt2);
        groupBtn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setup.add(groupBtn);
        setup.add(bt3);
        scroll = new JScrollPane(setup);
        
        // combat setup, card 2
        combat = new Combat();
        combat.test = test;
        
        
        // app design
        card = new CardLayout();
        end_screen = new EndScreen();
        end_screen.test = test;
        app = new JPanel();
        app.setLayout(card);
        app.add(scroll);
        app.add(combat.combat);
        app.add(end_screen.end);
        
        bt3.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){ 
                if(!go_next){
                    go_next = true;
                    
                }
                else{
                    app.remove(combat.combat);
                    app.remove(end_screen.end);
                    combat = new Combat();
                    combat.test = test;
                    app.add(combat.combat);
                    app.add(end_screen.end);
                }
                // Testing if combat on each renewal is nevel null
                test.not_null(combat);
                ArrayList<ArrayList<String>> dragon_list = new ArrayList<ArrayList<String>>();
                dragon_list = dragon_setup.getItems();
                ArrayList<ArrayList<String>> player_list = new ArrayList<ArrayList<String>>();
                player_list = player_setup.getItems();
                combat.set_lists(player_list,dragon_list);
                combat.set_events();
                combat.card = card;
                combat.app = app;
                combat.end_screen = end_screen;
                end_screen.app = app;
                end_screen.card = card;
                card.next(app);
            }  
        }); 
        add(app);
        setSize(800, 400);  
        setVisible(true);
        setTitle("DragonQuest");
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
    }
    
    
    public static void main(String[] a) {  
        new DragonQuest();
    }  
}  