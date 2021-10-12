package Task1.Simulation;

import Task1.Simulation.Field.Field;
import Task1.Simulation.Animals.Animal;

import java.util.ArrayList;

public class Simulation {
    private Field field;
    private int durationSimulation;


    //-------------
    public Simulation(int iN, int iM, int iT, ArrayList<Animal> iRabbits, ArrayList<Animal> iWolfs) {
        field = new Field(iN, iM, iRabbits, iWolfs);
        durationSimulation = iT;

    }

    public void Simulate() {
        for(int i = 0; i<= durationSimulation;++i)
        {
            field.MoveAnimals();
            field.EatAnimals();
            field.ReproduceAnimals();
            field.DieAnimals();
            field.GoToNextLoop();
        }
    }

    public Field GetField(){
        return field;
    }
}
