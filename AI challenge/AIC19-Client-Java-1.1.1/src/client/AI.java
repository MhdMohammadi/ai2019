package client;

import client.model.*;
import client.model.Strategy.FullGolem;

import java.sql.SQLOutput;
import java.util.Random;

public class AI
{

    private FullGolem strategy;
    private Random random = new Random();

    public void preProcess(World world)
    {
        strategy = new FullGolem(world);
        System.out.println("pre process started");
    }


    public void pickTurn(World world)
    {
        strategy.setWorld(world);
        System.out.println("pick started");
        strategy.pick();
        System.out.println("PICK ENDED");
    }

    public void moveTurn(World world)
    {
        System.out.println("move started");

        strategy.setWorld(world);
        strategy.doMove();
    }

    public void actionTurn(World world) {
        System.out.println("action started");

        Hero[] heroes = world.getMyHeroes();
        Map map = world.getMap();
        for (Hero hero : heroes)
        {
            System.out.println(hero.getName());
            int row = random.nextInt(map.getRowNum());
            int column = random.nextInt(map.getColumnNum());

            world.castAbility(hero, hero.getAbilities()[random.nextInt(3)], row, column);
        }
    }

}
