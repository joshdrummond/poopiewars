/*
 Joshua Drummond
 Poopie Wars! v1.11
 (yet another Asteroids clone)
 */

package com.joshdrummond.poopiewars;

import java.applet.*;
import java.awt.*;
import java.util.Vector;
import java.util.Random;

public class PoopieWars
  extends Applet
{
  private GameFrame frG;
  private Button btnNewGame;
  private Button btnHelp;
  private Button btnAbout;
  private Label lblTitle;
  private TextField tfWidth;
  private Label lblWidth;
  private TextField tfHeight;
  private Label lblHeight;
  private Label lblSound;
  private Label lblLives;
  private Label lblEnemies;
  private Label lblBullets;
  private Choice chLives;
  private Choice chEnemies;
  private Choice chBullets;
  private Panel panels[];
  private Panel panMain;
  private Checkbox [] checkboxes;
  private CheckboxGroup checkbox_group;
  public static Image[] pixEnemy;
  public static Image[] pixPlayer;
  public static Image[] pixBullet;
  public static AudioClip[] soundclip;
  public static int HIGHSCORE;
  private MediaTracker tracker;
  
  public void init()
  {
    System.out.println("-------------------------------------------------");
    System.out.println("Starting PoopieWars...");
    System.out.println("Version number: 1.11");
    System.out.println("Version date: 10/2/2000");
    System.out.println("(c)1998-2000 Josh Drummond");
    System.out.println("-------------------------------------------------");
    
    HIGHSCORE = 0;
    lblTitle = new Label("PoopieWars! Main Menu");
    lblTitle.setForeground(Color.green);
    lblTitle.setFont(new Font("Helvetica",Font.BOLD,36));
    lblWidth = new Label("Window Width (pixels):",Label.RIGHT);
    lblWidth.setForeground(Color.green);
    lblHeight = new Label("Window Height (pixels):",Label.RIGHT);
    lblHeight.setForeground(Color.green);
    lblSound = new Label("Sound:",Label.RIGHT);
    lblSound.setForeground(Color.green);
    lblLives = new Label("Lives:",Label.RIGHT);
    lblLives.setForeground(Color.green);
    lblEnemies = new Label("Poopies:",Label.RIGHT);
    lblEnemies.setForeground(Color.green);
    lblBullets = new Label("Bullets:",Label.RIGHT);
    lblBullets.setForeground(Color.green);
    btnNewGame = new Button("New Game");
    btnNewGame.setBackground(Color.green);
    btnAbout = new Button("  About  ");
    btnAbout.setBackground(Color.green);
    btnHelp = new Button("   Help    ");
    btnHelp.setBackground(Color.green);
    tfWidth = new TextField("600",5);
    tfWidth.setBackground(Color.green);
    tfHeight = new TextField("450",5);
    tfHeight.setBackground(Color.green);
    chLives = new Choice();
    chLives.setBackground(Color.green);
    chLives.setForeground(Color.black);
    for (int i=1; i < 6; i++)
      chLives.addItem(""+i);
    chLives.addItem("10");
    chLives.addItem("100");
    chLives.select("3");
    chBullets = new Choice();
    chBullets.setBackground(Color.green);
    chBullets.setForeground(Color.black);
    for (int i=0; i < 6; i++)
      chBullets.addItem(""+i);
    chBullets.addItem("10");
    chBullets.addItem("100");
    chBullets.select("10");
    chEnemies = new Choice();
    chEnemies.setBackground(Color.green);
    chEnemies.setForeground(Color.black);
    for (int i=0; i < 6; i++)
      chEnemies.addItem(""+i);
    chEnemies.addItem("10");
    chEnemies.addItem("100");
    chEnemies.select("4");
    checkbox_group = new CheckboxGroup();
    checkboxes = new Checkbox[2];
    checkboxes[0] = new Checkbox("On",checkbox_group,false);
    checkboxes[0].setForeground(Color.green);
    checkboxes[1] = new Checkbox("Off",checkbox_group,true);
    checkboxes[1].setForeground(Color.green);
    this.setBackground(Color.black);
    panels = new Panel[7];
    for (int i=0; i < 7; i++)
      panels[i] = new Panel();
    panMain = new Panel();
    panMain.setLayout(new GridLayout(7,1));
    panels[0].add(lblTitle);
    panels[1].add(lblWidth);
    panels[1].add(tfWidth);
    panels[2].add(lblHeight);
    panels[2].add(tfHeight);
    panels[3].add(lblSound);
    panels[3].add(checkboxes[0]);
    panels[3].add(checkboxes[1]);
    panels[4].add(lblLives);
    panels[4].add(chLives);
    panels[4].add(lblEnemies);
    panels[4].add(chEnemies);
    panels[4].add(lblBullets);
    panels[4].add(chBullets);
    panels[5].add(btnNewGame);
    panels[6].add(btnHelp);
    panels[6].add(btnAbout);
    for (int i = 0; i < 7; i++)
      panMain.add(panels[i]);
    this.setLayout(new BorderLayout());
    this.add("Center",panMain);
    pixEnemy = new Image[12];
    pixPlayer = new Image[8];
    pixBullet = new Image[1];
    soundclip = new AudioClip[11];
    for (int i=0; i < 11; i++)
      soundclip[i] = getAudioClip(getDocumentBase(), "sound"+i+".au");
    tracker = new MediaTracker(this);
    for (int i=0; i < 4; i++)
    {
      pixEnemy[i] = getImage(getDocumentBase(), "jpa"+i+".gif");
      tracker.addImage(pixEnemy[i], i);
    }
    for (int i=4; i < 8; i++)
    {
      pixEnemy[i] = getImage(getDocumentBase(), "jpb"+(i-4)+".gif");
      tracker.addImage(pixEnemy[i], i);
    }
    for (int i=8; i < 12; i++)
    {
      pixEnemy[i] = getImage(getDocumentBase(), "jpc"+(i-8)+".gif");
      tracker.addImage(pixEnemy[i], i);
    }
    for (int i=0; i < 8; i++)
    {
      pixPlayer[i] = getImage(getDocumentBase(), "sp"+i+".gif");
      tracker.addImage(pixPlayer[i], i+12);
    }
    pixBullet[0] = getImage(getDocumentBase(), "peace.gif");
    tracker.addImage(pixBullet[0], 20);
  }
  
  public boolean handleEvent(Event evt)
  {
    if (evt.id == Event.ACTION_EVENT)
    {
      if (evt.target == btnNewGame)
      {
        for (int i=0; i < 20; i++)
        {
          try {
            tracker.waitForID(i);
          }
          catch (InterruptedException e) { }
        }
        if (checkboxes[0].getState())
          PoopieWars.soundclip[1].play();
        frG = new GameFrame("Poopie Wars!", Integer.valueOf(chEnemies.getSelectedItem()).intValue(), checkboxes[0].getState(), Integer.valueOf(chLives.getSelectedItem()).intValue(), Integer.valueOf(chBullets.getSelectedItem()).intValue(), Integer.valueOf(tfWidth.getText()).intValue(), Integer.valueOf(tfHeight.getText()).intValue(), this);
        return true;
      }
      else if (evt.target == btnHelp)
      {
        if (checkboxes[0].getState())
          PoopieWars.soundclip[3].play();
        HelpFrame helpFrame = new HelpFrame();
        return true;
      }
      else if (evt.target == btnAbout)
      {
        if (checkboxes[0].getState())
          PoopieWars.soundclip[7].play();
        AboutFrame aboutFrame = new AboutFrame();
        return true;
      }
      else if (evt.target == checkboxes[0])
      {
        if (checkboxes[0].getState())
          PoopieWars.soundclip[8].play();
        return true;
      }
    }
    return false;
  }
}


class HelpFrame
  extends Frame
{
  private TextArea ta;
  private Button button;
  
  public HelpFrame()
  {
    super("Help Screen");
    ta = new TextArea(15,50);
    ta.setEditable(false);
    this.setBackground(Color.black);
    ta.setBackground(Color.black);
    ta.setForeground(Color.green);
    String s = "Object of the Game:\n\n" +
      "     To spread peace and love across the land by\n" +
      "eliminating the sad Poopies with peace and love.\n\n" +
      "     When hitting a Poopie, it will split into smaller\n" +
      "Poopies which are faster and more difficult to\n" +
      "hit.  After the Poopies are eliminated you will earn\n" +
      "points and advance to the next level.\n\n" +
      "     Your game will end when you are hit by a Poopie and\n" +
      "you have no more extra lives left.\n\n\n" +
      "Points:\n\n" +
      "  Large Poopie: 10 pts\n" +
      "  Medium Poopie: 50 pts\n" +
      "  Small Poopie: 100 pts\n" +
      "  New Level: 1000 times the level number completed pts\n\n\n" +
      "Controls:\n\n" +
      "  UP Key: Speeds up player\n" +
      "  DOWN Key: Slows down player\n" +
      "  LEFT Key: Rotates player counter-clockwise\n" +
      "  RIGHT Key: Rotates player clockwise\n" +
      "  SPACEBAR Key: Fire Weapon\n" +
      "  F1 key: Help Screen\n" +
      "  F2 Key: Sound Toggle\n" +
      "  F3 Key: Pause Toggle\n" +
      "  ESC Key: End Game (Also closing window with mouse)\n\n\n" +
      "Last but not least... GOOD LUCK!!! =)\n";
    ta.appendText(s);
    button = new Button("OKAY!");
    button.setBackground(Color.green);
    button.setForeground(Color.black);
    Panel p = new Panel();
    this.setLayout(new BorderLayout(10,10));
    this.add("Center",ta);
    p.add(button);
    this.add("South",p);
    this.pack();
    this.move(100,100);
    this.show();
  }
  
  public boolean action(Event e, Object arg)
  {
    if (e.target == button)
    {
      this.hide();
      this.dispose();
      return true;
    }
    else return false;
  }
}

class AboutFrame
  extends Frame
{
  private TextArea ta;
  private Button button;
  
  public AboutFrame()
  {
    super("About Screen");
    this.setBackground(Color.black);
    ta = new TextArea(15,50);
    ta.setEditable(false);
    ta.setBackground(Color.black);
    ta.setForeground(Color.green);
    String s = "Poopie Wars! v1.11\n" +
      "Copyright 1998 by Josh Drummond\n\n" +
      "     Thank you for playing this simple Java game of mine.\n"+
      "The basic gameplay idea comes from the classic arcade\n" +
      "game Asteroids, and the rest of this weird game was\n" +
      "inspired by equally weird people...\n" +
      "     Hope you liked it, comments/suggestions can be emailed\n" +
      "to jdrummon@uci.edu and look forward to the upcoming\n" +
      "adventure game to be released soon!\n\n"+
      "    Just like John Lennon, Paul McCartney, George Harrison\n" +
      "and Ringo Starr believe, \"All You Need Is Love.\"\n";
    ta.appendText(s);
    button = new Button("OKAY!");
    button.setBackground(Color.green);
    button.setForeground(Color.black);
    Panel p = new Panel();
    this.setLayout(new BorderLayout(10,10));
    this.add("Center",ta);
    p.add(button);
    this.add("South",p);
    this.pack();
    this.move(100,100);
    this.show();
  }
  
  public boolean action(Event e, Object arg)
  {
    if (e.target == button)
    {
      this.hide();
      this.dispose();
      return true;
    }
    else return false;
  }
}


class GameFrame
  extends Frame
  implements Runnable
{
  private Thread animator;
  public static Dimension dim;
  private Image offscreen;
  private Vector poopies;
  private Vector bullets;
  private PlayerObject player;
  private Applet app;
  private boolean PAUSE;
  private boolean SOUND;
  private boolean SHOW;
  private Random rand;
  private int level;
  private int points;
  private int pad;
  private int numEnemy;
  private int numBullets;
  
  public GameFrame(String title, int numEnemy, boolean sound, int lives, int numBullets, int fwidth, int fheight, Applet app)
  {
    super(title);
    this.app = app;
    this.numEnemy = numEnemy;
    this.numBullets = numBullets;
    level = 1;
    pad = 5;
    SHOW = false;
    points = 0;
    rand = new Random();
    PAUSE = false;
    SOUND = sound;
    this.resize(fwidth,fheight);
    this.show();
    dim = size();
    offscreen = this.createImage(fwidth,fheight);
    player = new PlayerObject((int)(fwidth/2),(int)(fheight/2),0,-1,0,lives,app);
    poopies = new Vector();
    for (int i=0; i < numEnemy; i++)
    {
      int dx = initDir();
      int dy = initDir();
      int x = initPos(fwidth);
      int y = initPos(fheight);
      if ((dy == dx) && (dx == 0))
        dx = 1;
      poopies.addElement(new PoopieObject(x,y,dx,dy,1,2,app));
      readjustPoop((PoopieObject)poopies.elementAt(i));
    }
    bullets = new Vector();
    (animator = new Thread(this)).start();
  }
  
  private int initDir()
  {
    int i = rand.nextInt();
    if (i < 0)
      i *= -1;
    i %= 3;
    if (i == 2)
      i = -1;
    return i;
  }
  
  private int initPos(int max)
  {
    int i = rand.nextInt();
    if (i < 0)
      i *= -1;
    i %= max;
    return i;
  }
  
  private void readjustPoop(PoopieObject poop)
  {
    if (poop.x + poop.width >= GameFrame.dim.width - 1)
      poop.x = GameFrame.dim.width - 1 - poop.width;
    if (poop.y + poop.height >= GameFrame.dim.height - 1)
      poop.y = GameFrame.dim.height - 1 - poop.height;
  }
  
  private void drawBackground(Graphics gr)
  {
    // *** draw the tile floor
    int n = (int)(GameFrame.dim.width / 6);
    for (int i=0; i < n; i++)
    {
      boolean c = ((i%2)==0);
      int y = 0;
      while (y < GameFrame.dim.height)
      {
        if (c)
          gr.setColor(new Color(255,200,200));
        else
          gr.setColor(new Color(255,0,0));
        gr.fillRect(n*i,y,n*(i+1),y+n);
        y += n;
        c = !c;
      }
    }
    // *** draw the stand
    gr.setColor(Color.gray);
    gr.fill3DRect(0,(int)(GameFrame.dim.height/4),(int)(GameFrame.dim.width/2),GameFrame.dim.height-2*((int)(GameFrame.dim.height/4)),true);
    
    // *** draw the seat
    gr.setColor(Color.lightGray);
    n = (int)(GameFrame.dim.width/10);
    int m = (int)(GameFrame.dim.height/10);
    gr.fillOval(n,m,GameFrame.dim.width-(2*n),GameFrame.dim.height-(2*m));
    // *** draw the inside
    gr.setColor(Color.gray);
    n = (int)(GameFrame.dim.width/5);
    m = (int)(GameFrame.dim.height/5);
    gr.fillOval(n,m,GameFrame.dim.width-(2*n),GameFrame.dim.height-(2*m));
    // *** draw the water
    gr.setColor(Color.cyan);
    n = (int)(GameFrame.dim.width/4);
    m = (int)(GameFrame.dim.height/4);
    gr.fillOval(n,m,GameFrame.dim.width-(2*n),GameFrame.dim.height-(2*m));
    // *** draw the hole
    gr.setColor(Color.darkGray);
    n = (int)(GameFrame.dim.width*9/20);
    m = (int)(GameFrame.dim.height*9/20);
    gr.fillOval(n,m,GameFrame.dim.height-(2*m),GameFrame.dim.width-(2*n));
  }
  
  public void drawObjects(Graphics gr)
  {
    for (int i=0; i < poopies.size(); i++)
    {
      gr.drawImage(((PoopieObject)poopies.elementAt(i)).getFrame(),
                     ((PoopieObject)poopies.elementAt(i)).getX(),
                     ((PoopieObject)poopies.elementAt(i)).getY(),
                   app);
    }
    for (int i=0; i < bullets.size(); i++)
    {
      gr.drawImage(((BulletObject)bullets.elementAt(i)).getFrame(),
                     ((BulletObject)bullets.elementAt(i)).getX(),
                     ((BulletObject)bullets.elementAt(i)).getY(),
                   app);
    }
    
    if (SHOW)
      gr.drawImage(player.getFrame(),
                   player.getX(),
                   player.getY(),
                   app);
  }
  
  public void drawStats(Graphics gr)
  {
    gr.setColor(Color.black);
    gr.setFont(new Font("Helvetica",Font.BOLD,15));
    gr.drawString("Level= "+level,5,35);
    gr.drawString("Points= "+points+"/"+PoopieWars.HIGHSCORE,5,48);
    gr.drawString("Lives= "+player.lives,5,60);
    gr.setFont(new Font("Helvetica",Font.BOLD,12));
    gr.drawString("Sound= "+SOUND,5,70);
    gr.drawString("Paused= "+PAUSE,5,80);
    gr.drawString("Speed= "+player.speed,5,90);
    gr.setFont(new Font("Courier",Font.BOLD,15));
    if (!SHOW)
      gr.drawString("Please wait until poopies clear space for you!",5,105);
  }
  
  public void paint(Graphics g)
  {
    drawBackground(g);
    drawObjects(g);
    drawStats(g);
  }
  
  public void update(Graphics g)
  {
    paint(offscreen.getGraphics());
    g.drawImage(offscreen,0,0, app);
  }
  
  public void checkBulletLife()
  {
    boolean cool = false;
    while (!cool)
    {
      cool = true;
      for (int i=0; i < bullets.size(); i++)
        if (((BulletObject)bullets.elementAt(i)).life <= 0)
        {
          bullets.removeElementAt(i);
          cool = false;
        }
    }
  }
  
  public void doBulletCollisions()
  {
    int xa1, xa2, xb1, xb2, ya1, ya2, yb1, yb2;
    
    for (int i=0; i < bullets.size(); i++)
    {
      xa1 = ((BulletObject)bullets.elementAt(i)).x;
      xa2 = ((BulletObject)bullets.elementAt(i)).x +
        ((BulletObject)bullets.elementAt(i)).width;
      ya1 = ((BulletObject)bullets.elementAt(i)).y;
      ya2 = ((BulletObject)bullets.elementAt(i)).y +
        ((BulletObject)bullets.elementAt(i)).height;
      for (int j=0; j < poopies.size(); j++)
      {
        
        xb1 = ((PoopieObject)poopies.elementAt(j)).x;
        xb2 = ((PoopieObject)poopies.elementAt(j)).x +
          ((PoopieObject)poopies.elementAt(j)).width;
        yb1 = ((PoopieObject)poopies.elementAt(j)).y;
        yb2 = ((PoopieObject)poopies.elementAt(j)).y +
          ((PoopieObject)poopies.elementAt(j)).height;
        boolean coll =
          ((xb1>=xa1)&&(xb1<=xa2)&&(yb1>=ya1)&&(yb1<=ya2)) ||
          ((xb2>=xa1)&&(xb2<=xa2)&&(yb1>=ya1)&&(yb1<=ya2)) ||
          ((xb2>=xa1)&&(xb2<=xa2)&&(yb2>=ya1)&&(yb2<=ya2)) ||
          ((xb1>=xa1)&&(xb1<=xa2)&&(yb2>=ya1)&&(yb2<=ya2));
        coll = (coll ||
                  ((xa1>=xb1)&&(xa1<=xb2)&&(ya1>=yb1)&&(ya1<=yb2)) ||
                  ((xa2>=xb1)&&(xa2<=xb2)&&(ya1>=yb1)&&(ya1<=yb2)) ||
                  ((xa2>=xb1)&&(xa2<=xb2)&&(ya2>=yb1)&&(ya2<=yb2)) ||
                  ((xa1>=xb1)&&(xa1<=xb2)&&(ya2>=yb1)&&(ya2<=yb2)));
        if (coll)
        {
          if (SOUND)
            PoopieWars.soundclip[4].play();
          int type = ((PoopieObject)poopies.elementAt(j)).type;
          switch (type)
          {
            case 0 : points += 100;
              break;
            case 1 : points += 50;
              break;
            case 2 : points += 10;
              break;
            default : break;
          }
          if (points > PoopieWars.HIGHSCORE)
            PoopieWars.HIGHSCORE = points;
          
          if (type != 0)
          {
            for (int k=0; k < 2; k++)
            {
              int dx = initDir();
              int dy = initDir();
              if ((dy == dx) && (dx == 0))
                dx = 1;
              poopies.addElement(new PoopieObject(xb1,yb1,dx,dy,1,type-1,app));
              readjustPoop((PoopieObject)poopies.elementAt(poopies.size() - 1));
            }
          }
          bullets.removeElementAt(i);
          poopies.removeElementAt(j);
          //      i--;
          //      j--;
          break;
        }
        
      }
    }
  }
  
  public boolean checkPlayerCollisions()
  {
    int xa1, xa2, ya1, ya2, xb1, xb2, yb1, yb2;
    
    xb1 = player.x + pad;
    xb2 = player.x + player.width - pad;
    yb1 = player.y + pad;
    yb2 = player.y + player.height - pad;
    
    for (int j=0; j < poopies.size(); j++)
    {
      xa1 = ((PoopieObject)poopies.elementAt(j)).x + pad;
      xa2 = ((PoopieObject)poopies.elementAt(j)).x +
        ((PoopieObject)poopies.elementAt(j)).width - pad;
      ya1 = ((PoopieObject)poopies.elementAt(j)).y + pad;
      ya2 = ((PoopieObject)poopies.elementAt(j)).y +
        ((PoopieObject)poopies.elementAt(j)).height - pad;
      boolean coll =
        ((xb1>=xa1)&&(xb1<=xa2)&&(yb1>=ya1)&&(yb1<=ya2)) ||
        ((xb2>=xa1)&&(xb2<=xa2)&&(yb1>=ya1)&&(yb1<=ya2)) ||
        ((xb2>=xa1)&&(xb2<=xa2)&&(yb2>=ya1)&&(yb2<=ya2)) ||
        ((xb1>=xa1)&&(xb1<=xa2)&&(yb2>=ya1)&&(yb2<=ya2));
      coll = (coll ||
                ((xa1>=xb1)&&(xa1<=xb2)&&(ya1>=yb1)&&(ya1<=yb2)) ||
                ((xa2>=xb1)&&(xa2<=xb2)&&(ya1>=yb1)&&(ya1<=yb2)) ||
                ((xa2>=xb1)&&(xa2<=xb2)&&(ya2>=yb1)&&(ya2<=yb2)) ||
                ((xa1>=xb1)&&(xa1<=xb2)&&(ya2>=yb1)&&(ya2<=yb2)));
      if (coll)
      {
        return true;
      }
    }
    return false;
  }
  
  public void nextLevel()
  {
    if (SOUND)
      PoopieWars.soundclip[2].play();
    points += (level*1000);
    if (points > PoopieWars.HIGHSCORE)
      PoopieWars.HIGHSCORE = points;
    level++;
    InfoDialog infoDialog = new InfoDialog(this,"Ready for Level #"+level+" ?", true, "You completed Level #"+(level-1)+"!!!",false);
    bullets.removeAllElements();
    for (int i=0; i < numEnemy; i++)
    {
      int dx = initDir();
      int dy = initDir();
      int x = initPos(GameFrame.dim.width);
      int y = initPos(GameFrame.dim.height);
      if ((dy == dx) && (dx == 0))
        dx = 1;
      poopies.addElement(new PoopieObject(x,y,dx,dy,1,2,app));
      readjustPoop((PoopieObject)poopies.elementAt(i));
    }
    player.x = (int)(GameFrame.dim.width/2);
    player.y = (int)(GameFrame.dim.height/2);
    player.speed = 0;
    SHOW = false;
  }
  
  public void doPlayerHit()
  {
    SHOW = false;
    //if (SOUND)
    player.lives--;
    player.x = (int)(GameFrame.dim.width/2);
    player.y = (int)(GameFrame.dim.height/2);
    player.speed = 0;
    if (player.lives <= 0)
    {
      if (SOUND)
        PoopieWars.soundclip[0].play();
      if (points == PoopieWars.HIGHSCORE)
      {
        InfoDialog infoDialog = new InfoDialog(this,"GREAT JOB! You have today's High Score = "+points, true, "            Sorry, GAME OVER!            ",true);
      }
      else
      {
        InfoDialog infoDialog = new InfoDialog(this,"Poop Happens... Your Score = "+points, true, "            Sorry, GAME OVER!            ",true);
      }
      if ((animator != null) && (animator.isAlive()))
        animator.stop();
      animator = null;
    }
    else
    {
      if (SOUND)
        PoopieWars.soundclip[5].play();
    }
  }
  
  public void run()
  {
    while (true)
    {
      if (!PAUSE) {
        
        if (poopies.size() <= 0)
          nextLevel();
        
        checkBulletLife();
        doBulletCollisions();
        boolean hit = checkPlayerCollisions();
        if (SHOW && hit)
          doPlayerHit();
        else if (!SHOW && !hit)
          SHOW = true;
        
        for (int i=0; i < poopies.size(); i++)
        {
          ((PoopieObject)poopies.elementAt(i)).incFrame();
          ((PoopieObject)poopies.elementAt(i)).incPos();
        }
        for (int i=0; i < bullets.size(); i++)
        {
          ((BulletObject)bullets.elementAt(i)).life--;
          ((BulletObject)bullets.elementAt(i)).incFrame();
          ((BulletObject)bullets.elementAt(i)).incPos();
        }
        
        if (SHOW)
          player.incPos();
        
        repaint();
        
        try {
          animator.sleep(100);
        } catch (Exception e) {}
      }
    }
  }
  
  public boolean handleEvent(Event evt)
  {
    if (evt.id == Event.WINDOW_ICONIFY)
    {
      PAUSE = true;
      return true;
    }
    else if (evt.id == Event.WINDOW_DEICONIFY)
    {
      PAUSE = false;
      return true;
    }
    else if (evt.id == Event.WINDOW_DESTROY)
    {
      if ((animator != null) && (animator.isAlive()))
        animator.stop();
      animator = null;
      this.dispose();
      if (SOUND)
        PoopieWars.soundclip[10].play();
      return true;
    }
    else if (evt.id == Event.KEY_PRESS)
    {
      if ((evt.key == ' ') && SHOW && !PAUSE)
      {
        if (bullets.size() < numBullets)
        {
          if (SOUND)
            PoopieWars.soundclip[6].play();
          bullets.addElement(new BulletObject(player.x+(int)(player.width/3),player.y+(int)(player.height/3),player.dx,player.dy,10,20,app));
        }
        return true;
      }
      else if (evt.key == 27)
      {
        if ((animator != null) && (animator.isAlive()))
          animator.stop();
        animator = null;
        this.dispose();
        if (SOUND)
          PoopieWars.soundclip[10].play();
        return true;
      }
      return false;
    }
    else if (evt.id == Event.KEY_ACTION)
    {
      if ((evt.key == Event.UP) && SHOW && !PAUSE)
      {
        player.speed ++; //= 2;
        if (player.speed > 5)
          player.speed = 5;
        //j     player.speed /= 2;
        return true;
      }
      else if ((evt.key == Event.DOWN) && SHOW && !PAUSE)
      {
        player.speed--; // -= 2;
        if (player.speed < 0)
          player.speed = 0;
        //    player.speed /= 2;
        return true;
      }
      else if ((evt.key == Event.RIGHT) && SHOW && !PAUSE)
      {
        if (player.dx == 0)
        {
          if (player.dy == 1)
            player.dx = -1;
          else
            player.dx = 1;
        }
        else if (player.dx == 1)
        {
          if (player.dy == 1)
            player.dx = 0;
          else
            player.dy++;
        }
        else  // player.dx == -1
        {
          if (player.dy == -1)
            player.dx = 0;
          else
            player.dy--;
        }
        player.decFrame();
        return true;
      }
      else if ((evt.key == Event.LEFT) && SHOW && !PAUSE)
      {
        if (player.dx == 0)
        {
          if (player.dy == 1)
            player.dx = 1;
          else
            player.dx = -1;
        }
        else if (player.dx == 1)
        {
          if (player.dy == -1)
            player.dx = 0;
          else
            player.dy--;
        }
        else  // player.dx == -1
        {
          if (player.dy == 1)
            player.dx = 0;
          else
            player.dy++;
        }
        player.incFrame();
        return true;
      }
      else if (evt.key == Event.F3)
      {
        PAUSE = !PAUSE;
        if (SOUND)
          PoopieWars.soundclip[9].play();
        repaint();
        return true;
      }
      else if (evt.key == Event.F2)
      {
        SOUND = !SOUND;
        repaint();
        if (SOUND)
          PoopieWars.soundclip[8].play();
        return true;
      }
      else if (evt.key == Event.F1)
      {
        PAUSE = true;
        repaint();
        if (SOUND)
          PoopieWars.soundclip[3].play();
        HelpFrame helpFrame = new HelpFrame();
        return true;
      }
    }
    return false;
  }
}

class PoopieObject
  extends SpriteObject
{
  private Applet app;
  public int type;
  
  public PoopieObject(int x, int y, int dx, int dy, int speed, int type, Applet a)
  {
    super(x,y,dx,dy,speed,a);
    Vector images = new Vector();
    app = a;
    this.type = type;
    if (type == 2)
    {
      for (int i=0; i < 4; i++)
        images.addElement(PoopieWars.pixEnemy[i]);
      this.speed = 1;
    }
    else if (type == 1)
    {
      for (int i=4; i < 8; i++)
        images.addElement(PoopieWars.pixEnemy[i]);
      this.speed = 5;
    }
    else
    {
      for (int i=8; i < 12; i++)
        images.addElement(PoopieWars.pixEnemy[i]);
      this.speed = 10;
    }
    super.setImages(images);
  }
  
  public void incPos()
  {
    if ((x + dx*speed < 0) || (x + width + dx*speed > GameFrame.dim.width)) dx = -dx;
    if ((y + dy*speed < 0) || (y + height + dy*speed > GameFrame.dim.height)) dy = -dy;
    x += dx*speed;
    y += dy*speed;
  }
}

class PlayerObject
  extends SpriteObject
{
  int lives;
  
  public PlayerObject(int x, int y, int dx, int dy, int speed, int lives, Applet a)
  {
    super(x,y,dx,dy,speed,a);
    this.lives = lives;
    Vector images = new Vector();
    for (int i=0; i < 8; i++)
      images.addElement(PoopieWars.pixPlayer[i]);
    super.setImages(images);
    for (int i=0; i < 4; i++)
      this.incFrame();
  }
}

class BulletObject
  extends SpriteObject
{
  int life;
  
  public BulletObject(int x, int y, int dx, int dy, int speed, int life, Applet a)
  {
    super(x,y,dx,dy,speed,a);
    Vector images = new Vector();
    this.life = life;
    images.addElement(PoopieWars.pixBullet[0]);
    super.setImages(images);
  }
}

class SpriteObject
  extends Object
{
  int x, y;
  int dx, dy;
  int speed;
  int width, height;
  private Vector images;
  private int currframe;
  private Applet app;
  
  public SpriteObject(int x, int y, int dx, int dy, int speed, Applet a)
  {
    height = width = 0;
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.speed = speed;
    app = a;
    currframe = 0;
    images = new Vector();
  }
  
  public int getX() { return x; }
  public int getFrameNum() { return currframe; }
  public int getY() { return y; }
  
  public void setImages(Vector images)
  {
    for (int i=0; i < images.size(); i++)
      this.images.addElement((Image)images.elementAt(i));
    if (images.size() > 0)
    {
      width = ((Image)images.elementAt(0)).getWidth(app);
      height = ((Image)images.elementAt(0)).getHeight(app);
    }
  }
  
  public void moveTo(int newX, int newY)
  {
    x = newX;
    y = newY;
  }
  
  public void changeDirection(int newDX, int newDY)
  {
    dx = newDX;
    dy = newDY;
  }
  
  public void incPos()
  {
    if (x + dx*speed + width <= 0)
      x = GameFrame.dim.width - 1;
    else
      x = (x + dx*speed) % GameFrame.dim.width;
    if (y + dy*speed + height <= 0)
      y = GameFrame.dim.height - 1;
    else
      y = (y + dy*speed) % GameFrame.dim.height;
  }
  
  public void incFrame()
  {
    currframe++;
    if (currframe >= images.size())
      currframe = 0;
    width = ((Image)images.elementAt(currframe)).getWidth(app);
    height = ((Image)images.elementAt(currframe)).getHeight(app);
  }
  
  public void decFrame()
  {
    currframe--;
    if (currframe < 0)
      currframe = images.size() - 1;
    width = ((Image)images.elementAt(currframe)).getWidth(app);
    height = ((Image)images.elementAt(currframe)).getHeight(app);
  }
  
  public Image getFrame()
  {
    return (Image)images.elementAt(currframe);
  }
}

class InfoDialog
  extends Dialog
{
  
  private Button button;
  private Label label;
  private Panel panel;
  private Dimension d,d2;
  private Frame parent;
  private boolean kill;
  
  public InfoDialog(Frame parent, String title, boolean modal, String message, boolean kill)
  {
    super(parent,title,modal);
    this.parent = parent;
    this.kill = kill;
    this.setLayout(new BorderLayout(15,15));
    button = new Button("Okie-Dokie!");
    button.setForeground(Color.green);
    button.setBackground(Color.black);
    button.setFont(new Font("TimesRoman",Font.ITALIC+Font.BOLD,14));
    label = new Label(message,Label.CENTER);
    label.setFont(new Font("Helvetica",Font.BOLD,20));
    panel = new Panel();
    panel.add(button);
    this.add("Center", label);
    this.add("South", panel);
    this.pack();
    d = parent.size();
    d2 = this.size();
    this.move(((int)d.width/2)-((int)d2.width/2),((int)d.height/2)-((int)d2.height/2));
    this.show();
  }
  
  public boolean action(Event e, Object arg)
  {
    if (e.target == button)
    {
      this.hide();
      this.dispose();
      if (kill)
      {
        parent.hide();
        parent.dispose();
      }
      return true;
    }
    else return false;
  }
}
