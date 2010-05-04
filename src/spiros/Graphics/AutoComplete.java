package spiros.Graphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class AutoComplete extends JComboBox //implements JComboBox.KeySelectionManager
{
    /**
     * 
     */
    private static final long serialVersionUID = -7027547990331212187L;

    private String searchFor;

    private long lap;

    public class CBDocument extends PlainDocument
    {
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
        {
            System.err.println("insertString: "+offset+" "+str);
            if (str == null)
                return;
            
            super.insertString(offset, str, a);
            
            if (!isPopupVisible() && str.length() != 0){
                fireActionEvent();
            }
                
        }
    }

    public AutoComplete(Object[] items)
    {
        super(items);
        lap = new java.util.Date().getTime();
        
        System.err.println("---------------------Autoooo: "+items[0]);
        
        
//        setKeySelectionManager(this);
        JTextField tf;
        if (getEditor() != null)
        {
            tf = (JTextField) getEditor().getEditorComponent();
            if (tf != null)
            {
                
                tf.setDocument(new CBDocument());
                
                addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        
                        
                        System.err.println("actionPerformed: "+evt);
                        
                        JTextField tf = (JTextField) getEditor().getEditorComponent();
                        String text = tf.getText();
                        ComboBoxModel aModel = getModel();
                        String current;
                        for (int i = 0; i < aModel.getSize(); i++)
                        {
                            current = aModel.getElementAt(i).toString();
                            if (current.toLowerCase().startsWith(text.toLowerCase()))
                            {
                                tf.setText(current);
                                tf.setSelectionStart(text.length());
                                tf.setSelectionEnd(current.length());
                                break;
                            }
                        }
                    }
                });
            }
        }
    }

    public int selectionForKey(char aKey, ComboBoxModel aModel)
    {
        long now = new java.util.Date().getTime();
        if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE && searchFor.length() > 0)
        {
            searchFor = searchFor.substring(0, searchFor.length() - 1);
        }
        else
        {
            // System.out.println(lap);
            // Kam nie hier vorbei.
            if (lap + 1000 < now)
                searchFor = "" + aKey;
            else
                searchFor = searchFor + aKey;
        }
        lap = now;
        String current;
        for (int i = 0; i < aModel.getSize(); i++)
        {
            current = aModel.getElementAt(i).toString().toLowerCase();
            
            System.err.println("current: "+current);
            System.err.println("searchFor: "+searchFor);
            
            if (current.toLowerCase().startsWith(searchFor.toLowerCase()))
                return i;
        }
        return -1;
    }

    public void fireActionEvent()
    {
        super.fireActionEvent();
        System.err.println("fireActionEvent!!!!");
    }

    public static void main(String arg[])
    {
        JFrame f = new JFrame("AutoCompleteComboBox");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(200, 300);
        Container cp = f.getContentPane();
        cp.setLayout(null);
        String[] names = { "Beate", "Claudia", "Fjodor", "Fred", "Friedrich", "Fritz", "Frodo", "Hermann", "Willi" };
        JComboBox cBox = new AutoComplete(names);
        
        cBox.setBounds(50, 50, 100, 21);
        cBox.setEditable(true);
        cp.add(cBox);
        f.setVisible(true);
    }
}
