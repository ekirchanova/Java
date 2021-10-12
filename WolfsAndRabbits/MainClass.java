package Task1;

import Task1.Simulation.Animals.Direction.Direction;
import Task1.Simulation.Animals.Rabbit;
import Task1.Simulation.Animals.Wolf;
import Task1.Simulation.Animals.Animal;
import Task1.Simulation.Coordinates.Coordinates;
import Task1.Simulation.Field.Field;
import Task1.Simulation.Simulation;

import java.util.ArrayList;

public class MainClass {
    static public void main(String [] args){
        ArrayList<Animal> rabbits =new ArrayList<Animal>();
        rabbits.add(new Rabbit(new Coordinates(0,0), Direction.Right,0,100));
        //rabbits.add(new Rabbit(new Coordinates(1,1), Direction.Up,0,2));
        ArrayList<Animal> wolfs = new ArrayList<Animal>();
        wolfs.add(new Wolf(new Coordinates(0,2), Direction.Right,0,100));
        Simulation simulation = new Simulation(3,3,21,rabbits,wolfs);
        simulation.Simulate();
        Field field = simulation.GetField();
    }
}
