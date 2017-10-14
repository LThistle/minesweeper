import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Minesweeper
{
   public static void main(String[] args)
   {
      int size = Integer.parseInt(JOptionPane.showInputDialog("What size board do you want?"));
      
      int mines = Integer.parseInt(JOptionPane.showInputDialog("How many mines do you want?"));
      
      JFrame frame = new JFrame("Minesweeper");
      frame.setSize(50*size,50*size);
      frame.setLocation(200,100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new SweeperPanel(size,mines));
      frame.setVisible(true);
      frame.setResizable(false);
   
   }
}
class SweeperPanel extends JPanel
{
   public SweeperPanel(int size, int mine)
   {
      setLayout(new GridLayout(size,size,0,0));
      SweeperMap myMap = new SweeperMap(mine,size);
      for(int i=0; i<size; i++)
      {
         for(int j=0; j<size; j++)
         {
            char myChar = myMap.getCharAt(i,j);           
            addButton(this,myChar);
         }
      }
   } 
   private void addButton(JPanel panel, char ch)
   {
      JButton myButton = new JButton();
      myButton.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().
                            getResource("CoverTile.png")).getImage().
                            getScaledInstance(50,50, Image.SCALE_SMOOTH)));
      add(myButton);
      myButton.addActionListener(new Listener(ch, myButton));       
   }
   
   private class Listener implements ActionListener
   {
      private char myCh;
      private JButton myParent;
      
      public Listener(char ch, JButton parent)
      {
         myCh = ch;
         myParent = parent;
      }
      public void actionPerformed(ActionEvent e)
      {
         myParent.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().
                            getResource(myCh + "Tile.png")).getImage().
                            getScaledInstance(50,50, Image.SCALE_SMOOTH)));  
      }
   }
}

class SweeperMap
{
   private int numMines;
   private int boardSize;
   private int startXPos;
   private int startYPos;
   private char[][] myMap;

   public SweeperMap(int mines, int size)
   {
      numMines = mines;
      boardSize = size;
      myMap = createMap(boardSize,numMines);
      myMap = fillMap(myMap);     
   }

   public char getCharAt(int x, int y)
   {
      return myMap[x][y];
   }

   private static char[][] createMap(int size, int mines)
   {
      char[][] map = new char[size][size];
      int count = mines;
      while(count>0)
      {
         int xPos = (int)(Math.random()*size);
         int yPos = (int)(Math.random()*size);
      
         if(map[xPos][yPos]!='#')
         {
            map[xPos][yPos]='#';
            count--;
         }
      }
      return map;
   }

   private static char[][] fillMap(char[][] map)
   {
      int size = map.length;
      for(int x=0; x<size; x++)
      {
         for(int y=0; y<size; y++)
         {
            if(map[x][y]!='#')
            {
               int count = 0;
            
            //top row
               if(x-1>=0 && y+1<size && map[x-1][y+1]=='#')
                  count++;
               if(y+1<size && map[x][y+1]=='#')
                  count++;
               if(x+1<size && y+1<size && map[x+1][y+1]=='#')
                  count++;
            //middle row   
               if(x-1>=0 && map[x-1][y]=='#')
                  count++;              
               if(x+1<size && map[x+1][y]=='#')
                  count++;
            //bottom row
               if(x-1>=0 && y-1>=0 && map[x-1][y-1]=='#')
                  count++;
               if(y-1>=0 && map[x][y-1]=='#')
                  count++;
               if(x+1<size && y-1>=0 && map[x+1][y-1]=='#')
                  count++;
            
               map[x][y]=Character.forDigit(count,10); 
            }
         }
      }
      return map;
   }
}