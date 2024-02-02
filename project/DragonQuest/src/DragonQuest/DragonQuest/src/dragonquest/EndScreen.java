package dragonquest;

import dragonquest.test.DragonQuestTest;

import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.Serializable;

public class EndScreen implements Serializable{
    @SuppressWarnings("compatibility:1909996678711700601")
    private static final long serialVersionUID = 9200685762741184709L;
    JPanel end;
    JLabel won_text;
    JButton home;
    String won="";
    JPanel app;
    CardLayout card;
    DragonQuestTest test;
    
    EndScreen() {
        this.won_text = new JLabel("Won"){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 150);
            }
        };
        this.home = new JButton("Home"){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(70, 30);
            }
        };
        this.end = new JPanel();
//        this.end.setLayout(new BoxLayout(this.end,BoxLayout.Y_AXIS));
//        this.end.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.end.add(this.won_text);
        this.end.add(this.home);
        
        this.home.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                   card.previous(app);
                   card.previous(app);
            }
        });
    }
    
    void update(){
        this.won_text.setText(won+" Won.");   
    }
}
