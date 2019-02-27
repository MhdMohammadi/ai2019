package client.model.Strategy;

import client.model.*;
import client.model.Map;

import javax.print.attribute.standard.Destination;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import static client.model.HeroName.BLASTER;

public class FullGolem {
    private World world;
    private int[][][][] dis;
    private Map map;
    private int rows, columns;
    private int xDestination, yDestination;

    public FullGolem(World world){

        this.world = world;
        map = world.getMap();

        this.rows = map.getRowNum();
        this.columns = map.getColumnNum();


        dis = new int[rows][columns][rows][columns];
        setDis();

    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void pick(){
        world.pickHero(HeroName.values()[1]);
    }

    public void moveHero(Hero hero){
        int xHero = hero.getCurrentCell().getRow();
        int yHero = hero.getCurrentCell().getColumn();
        int xDes = xDestination, yDes = yDestination;
        int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};
        for(int i = 0; i < 4; i ++){
            int newX = xHero + dx[i], newY = yHero + dy[i];
            if(newX < 0 || newX >= rows || newY < 0 || newY >= columns)
                continue;
            if(world.getMap().getCell(newX, newY).isWall())
                continue;
            System.out.println(newX + " " + newY + " " + xDes + " " + yDes);
            System.out.println(dis[newX][newY][xDes][yDes] + " " + dis[xHero][yHero][xDes][yDes]);

            if(dis[newX][newY][xDes][yDes] < dis[xHero][yHero][xDes][yDes]){
                world.moveHero(hero, Direction.values()[i]);
                System.out.println("HOOOOOOOOOOOOOOOOOOOOOOOOOO");
                break;
            }
        }
    }

    public void doMove(){
        findCOM();

        Hero[] heroes = world.getMyHeroes();
        for (Hero hero : heroes)
        {
            moveHero(hero);
        }

    }

    public void findCOM(){
        int xCOM = 0, yCOM = 0, cnt = 0;
        for(int i = 0; i < rows; i ++){
            for(int j = 0; j < columns; j ++){
                if(map.getCell(i, j).isInObjectiveZone()){
                    xCOM += i;
                    yCOM += j;
                    cnt ++;
                }
            }
        }
        xDestination = xCOM / cnt;
        yDestination = yCOM / cnt;
    }

    public void setDis() {
        int INF = 100 * 100 * 100;
        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

        for(int i = 0; i < this.rows; i ++)
            for(int j = 0; j < this.columns; j ++){

                for(int x = 0; x < this.rows; x ++)
                    for(int y = 0; y < this.columns; y ++)
                        dis[i][j][x][y] = INF;

                    dis[i][j][i][j] = 0;

                Queue<Integer> queue = new LinkedList<>();
                queue.add(i * (rows + columns) + j);


                while(queue.size() > 0){
                    int x = queue.peek() / (rows + columns);
                    int y = queue.peek() % (rows + columns);
                    queue.remove();
                    for(int dir = 0; dir < 4; dir ++) {
                        int newX = x + dx[dir], newY = y + dy[dir];
                        if(newX < 0 || newY < 0 || newX >= rows || newY >= columns)
                            continue;
                        if(dis[i][j][newX][newY] == INF){
                            dis[i][j][newX][newY] =  dis[i][j][x][y] + 1;
                            if(!map.getCell(newX, newY).isWall())
                                queue.add(newX * (rows + columns) + newY);
                        }
                    }

                }

            }
        for(int i = 0; i < 10; i ++)
            for(int j = 0; j < 10; j ++)
                for(int k = 0; k < 10; k ++)
                    for(int l = 0; l < 10; l ++)
                        System.out.println(i + " " + j + " " + k + " " + l + " " + dis[i][j][k][l]);
    }

}


