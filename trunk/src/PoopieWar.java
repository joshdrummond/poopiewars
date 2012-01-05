/*
    Joshua Drummond
    Poopie Wars! v1.0
    (yet another Asteroids clone)
*/

import java.applet.*;
import java.awt.*;
import java.util.Vector;

public class PoopieWar
    extends Applet
{
    private GameFrame frG;
    private Button btnNewGame;
    private Button btnHelp;
    private Button btnAbout;
    private Label lblTitle;
    private TextField tfNumEnemy;
    private Label lblNumEnemy;
    private TextField tfWidth;
    private Label lblWidth;
    private TextField tfHeight;
    private Label lblHeight;
    private Label lblSound;
    private Label lblLives;
    private Choice chLives;
    private Panel chPanel;
    private Checkbox [] checkboxes;
    private CheckboxGroup checkbox_group;

    public void init()
    {
       lblTitle = new Label("PoopieWar! Main Menu");
       lblTitle.setForeground(Color.green);
       lblTitle.setFont(new Font("SansSerif",Font.BOLD,36));
       lblNumEnemy = new Label("Number of Poopies:",Label.RIGHT);
       lblWidth = new Label("Window Width (pixels):",Label.RIGHT);
       lblHeight = new Label("Window Height (pixels):",Label.RIGHT);
       lblSound = new Label("Sound:",Label.RIGHT);
       lblLives = new Label("Lives:",Label.RIGHT);
       btnNewGame = new Button("New Game");
       btnAbout = new Button("  About  ");
       btnHelp = new Button("   Help    ");
       tfNumEnemy = new TextField("3",5);
       tfWidth = new TextField("600",5);
       tfHeight = new TextField("450",5);
       tfNumEnemy.selectAll();
       chLives = new Choice();
       for (int i=1; i < 6; i++)
         chLives.addItem(""+i);
       chLives.addItem("10");
       chLives.addItem("25");
       chLives.select("3");
       checkbox_group = new CheckboxGroup();
       checkboxes = new Checkbox[2];
       checkboxes[0] = new Checkbox("On",checkbox_group,false);
       checkboxes[1] = new Checkbox("Off",checkbox_group,true);
       chPanel = new Panel();
       chPanel.add(checkboxes[0]);
       chPanel.add(checkboxes[1]);
       setLayout(new GridBagLayout());
       GridBagConstraints c = new GridBagConstraints();
       //setConstraints(c);
       c.fill = GridBagConstraints.BOTH;
       c.insets = new Insets(5,5,5,5);
       c.weightx = c.weighty = 1.0;
       c.gridheight = 1;
       c.gridwidth = 2;
       c.gridx = 1; c.gridy = 1;
       add(lblTitle,c);
       c.gridwidth = c.gridheight = 1;
       c.gridx = 1; c.gridy = 2;
       add(lblNumEnemy,c);
       c.gridx = 2; c.gridy = 2;
       add(tfNumEnemy,c);
       c.gridx = 1; c.gridy = 3;
       add(lblWidth,c);
       c.gridx = 2; c.gridy = 3;
       add(tfWidth,c);
       c.gridx = 1; c.gridy = 4;
       add(lblHeight,c);
       c.gridx = 2; c.gridy = 4;
       add(tfHeight,c);
       c.gridx = 1; c.gridy = 5;
       add(lblSound,c);
       c.gridx = 2; c.gridy = 5;
       add(chPanel,c);
       c.gridx = 1; c.gridy = 6;
       add(lblLives,c);
       c.gridx = 2; c.gridy = 6;
       add(chLives,c);
       c.gridwidth = 2;
       c.gridx = 1; c.gridy = 7;
       Panel p1 = new Panel();
       Panel p2 = new Panel();
       p1.add(btnNewGame);
       p2.add(btnHelp);
       p2.add(btnAbout);
       add(p1,c);
       c.gridx = 1; c.gridy = 8;
       add(p2,c);
    }

    public boolean handleEvent(Event evt)
    {
        if (evt.id == Event.ACTION_EVENT)
        {
            if (evt.target == btnNewGame)
            {
                frG = new GameFrame("Poopie Wars!", Integer.valueOf(tfNumEnemy.getText()).intValue(), checkboxes[0].getState(), Integer.valueOf(chLives.getSelectedItem()).intValue(), Integer.valueOf(tfWidth.getText()).intValue(), Integer.valueOf(tfHeight.getText()).intValue(), this);
   //             frG.resize(500,400);
    //            frG.show();
                return true;
            }
        }
        return false; 
    }

/*
    public void start()
    {
        (animator = new Thread(this)).start();
    }

    public void stop()
    {
        animator.stop();
        animator = null;
    }
*/
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

    public GameFrame(String title, int numEnemy, boolean sound, int lives, int fwidth, int fheight, Applet app)
    {
        super(title);
        this.app = app;
        PAUSE = false;
        SOUND = sound;
        this.resize(fwidth,fheight);
        this.show();
        dim = size();
        offscreen = this.createImage(fwidth,fheight); //dim.width, dim.height);
        player = new PlayerObject(100,100,0,1,0,lives,app);
        poopies = new Vector();
        for (int i=0; i < numEnemy; i++)
            poopies.addElement(new PoopieObject(i*2+100,i*3+100,i*2+1,i*2+1,1,0,app));
        bullets = new Vector();
        (animator = new Thread(this)).start();
    }

    void drawBackground(Graphics gr)
    {
        //*** draw sky
        gr.setColor(new Color(0x00ccff));
        gr.fillRect(0,0,640,200);
        //*** draw grass field
        gr.setColor(new Color(0x00ff33));
        gr.fillRect(0,200,639,299);
        //*** draw sun
        gr.setColor(new Color(0xffffcc));
        gr.fillOval(30,10,100,100);
        gr.setColor(new Color(0xffff66));
        gr.fillOval(42,22,75,75);
        gr.setColor(new Color(0xffff00));
        gr.fillOval(54,34,50,50);
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

        gr.drawImage(player.getFrame(),
                     player.getX(),
                     player.getY(),
                     app);

    }

    public void drawStats(Graphics gr)
    {
        gr.setColor(Color.red);
//        gr.drawString(((PoopieObject)poopies.elementAt(0)).getFrameNum()+"",10,10);
        gr.setFont(new Font("Serif",Font.BOLD,32));
        gr.drawString("Speed="+player.speed,10,50);
        gr.setFont(new Font("SansSerif",Font.ITALIC,15));
        gr.drawString("Lives="+player.lives,10,70);
        gr.drawString("Sound="+SOUND,10,100);
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

    public void run()
    {
        while (true)
        {
          if (!PAUSE) {
            for (int i=0; i < poopies.size(); i++)
            {
                ((PoopieObject)poopies.elementAt(i)).incFrame();
                ((PoopieObject)poopies.elementAt(i)).incPos();
            }

            player.incPos();

            //myImages.change();
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
/*            if (animator != null)
                 animator.suspend();
*/            return true;
        }
        else if (evt.id == Event.WINDOW_DEICONIFY)
        {
              PAUSE = false;
/*            if (animator != null) 
                animator.resume();
*/            return true;
        }
        else if (evt.id == Event.WINDOW_DESTROY)
        {
            if ((animator != null) && (animator.isAlive()))
                animator.stop();
            animator = null;
            this.dispose();
            return true;
        }
        else if (evt.id == Event.KEY_ACTION)
        {
            if (evt.key == Event.UP)
            {
                player.speed++;
                if (player.speed > 10)
                    player.speed = 10;
                return true;
            }
            else if (evt.key == Event.DOWN)
            {
                player.speed--;
                if (player.speed < 0)
                   player.speed = 0;
                return true;
            }
            else if (evt.key == Event.RIGHT)
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
            else if (evt.key == Event.LEFT)
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

    public PoopieObject(int x, int y, int dx, int dy, int speed, int type, Applet a)
    {
        super(x,y,dx,dy,speed,a);
        Vector images = new Vector();
        app = a;
        if (type == 0)
        {
            for (int i=0; i < 4; i++)
                images.addElement(a.getImage(a.getDocumentBase(), "jp"+i+".gif"));
        }
        else if (type == 1)
        {

        }
        else
        {

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
            images.addElement(a.getImage(a.getDocumentBase(), "sp"+i+".gif"));
        super.setImages(images);
    }
}

class BulletObject
    extends SpriteObject
{
    public BulletObject(int x, int y, int dx, int dy, int speed, Applet a)
    {
        super(x,y,dx,dy,speed,a);
        Vector images = new Vector();
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
//     if (currframe < images.size())
        return (Image)images.elementAt(currframe);
//       else
//        return null;
    }
}
