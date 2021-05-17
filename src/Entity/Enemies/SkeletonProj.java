package Entity.Enemies;
import Entity.Animation;
import Entity.Player;
import TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;

public class SkeletonProj  extends Projectile{

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    private Player myPlayer;
    private int damage;
    private int health;
    public SkeletonProj(TileMap tm, boolean right,Player p) {

        super(tm);
        moveSpeed=2.25;
        facingRight=right;
        if(right)
        {
            dx=moveSpeed;

        }
        else
        {
            dx=-moveSpeed;
        }
        width=92;
        height=102;
        cwidth=14;
        cheight=15;
        health=1;
        damage=2;
        myPlayer=p;
        try
        {
            BufferedImage spritesheet= ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/SkeletonProj.png"));
            sprites=new BufferedImage[4];
            hitSprites=new BufferedImage[4];
            for(int i=0;i<sprites.length;i++)
            {
                sprites[i]=spritesheet.getSubimage(i*width,0,width,height);
            }
            for(int i=0;i<hitSprites.length;i++)
            {
                hitSprites[i]=spritesheet.getSubimage(i*width,height,width,height);
            }
            animation=new Animation();
            animation.setFrames(sprites);
            animation.setDelay(200);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public int getDamage()
    {
        return damage;
    }
    public void hit(int damage) {
        health-=damage;
        if(health<0){health=0;}
        if(health==0)setHit();
    }
    public void setHit() {
        if(hit)return;
        hit=true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx=0;
    }
    public boolean shouldRemove(){return remove;}
    private void attackThings() {
        if(dx==0 && !hit)
        {
            setHit();
        }
        if(hit &&animation.hasPlayedOnce())
        {
            remove=true;

        }
        if(x<0)
        {
            x=0;
            setHit();
        }
        if(abs(x-myPlayer.getx())>200)
        {
            setHit();
        }
    }
    public void update() {
        checkTileMapCollision();
        setPosition(xtemp,ytemp);
        attackThings();
        animation.update();
    }
    public void draw(Graphics2D g) {
        setMapPosition();
        if (facingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int) (x + xmap - width / 2),
                    (int) (y + ymap - height / 2-17),
                    width
                    ,height,
                    null
            );
        }
        else
        {

            g.drawImage(
                    animation.getImage(),
                    (int) (x + xmap - width / 2 + width),
                    (int) (y + ymap - height / 2 -17 ),
                    -width,
                    height,
                    null
            );

        }
    }
}
