package Task1.Simulation.Field;

import Task1.Simulation.Animals.Animal;
import Task1.Simulation.Animals.Direction.Direction;
import Task1.Simulation.Coordinates.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;

public class Field {
    private int N;
    private int M;
    ArrayList<ArrayList<ArrayList<Animal>>> animalsField;

    private void InitAnimalsField(){
        animalsField = new ArrayList<ArrayList<ArrayList<Animal>>>();
        for(int i = 0; i < N; ++i){
            animalsField.add(new ArrayList<ArrayList<Animal>>());
            for(int j = 0; j < M; ++j)
                animalsField.get(i).add(new ArrayList<Animal>());
        }


    }
    private  void FillAnimalsField(ArrayList<Animal> iRabbits, ArrayList<Animal> iWolfs){
        for (Animal rabbit: iRabbits){
            Coordinates coordinates = rabbit.GetCoordinates();
            int x = coordinates.GetX();
            int y = coordinates.GetY();
            animalsField.get(x).get(y).add(rabbit);
        }
        for (Animal wolf: iWolfs){
            Coordinates coordinates = wolf.GetCoordinates();
            int x = coordinates.GetX();
            int y = coordinates.GetY();
            animalsField.get(x).get(y).add(wolf);
        }
    }
    private boolean CheckWolfRabbitOneCell(ArrayList<Animal> animalsCell)
    {
        boolean hasRabbit = false, hasWolf = false;
        for(Animal animal: animalsCell)
        {
            if(animal.GetType() == Animal.AnimalsType.rabbitType)
                hasRabbit = true;
            if(animal.GetType() == Animal.AnimalsType.wolfType)
                hasWolf = true;
        }
        return hasRabbit && hasWolf;
    }

    private int DeleteRabbits(ArrayList<Animal> animalsCell){
        int countRabbits = 0;
        for(int i = 0; i < animalsCell.size(); ++i)
        {
            Animal animal = animalsCell.get(i);
            if(animal.GetType() == Animal.AnimalsType.rabbitType)
            {
                animalsCell.remove(animal);
                ++countRabbits;
            }
        }
        return countRabbits;
    }

    private int FindOlderWolf(ArrayList<Animal> animalsCell){
        int index = 0;
        int maxAge = animalsCell.get(index).GetAge();
        for(int i = 0; i < animalsCell.size(); ++i)
        {
            int curAge = animalsCell.get(i).GetAge();
            if (curAge > maxAge)
                index = i;
        }
        return  index;
    }
    //----------------
    public Field(int iN, int iM, ArrayList<Animal> iRabbits, ArrayList<Animal> iWolfs) {
        N = iN;
        M = iM;
        InitAnimalsField();
        FillAnimalsField(iRabbits, iWolfs);
    }
    public void GoToNextLoop()
    {
        for(ArrayList<ArrayList<Animal>> animalsRow: animalsField){
            for(ArrayList<Animal> animalsCell : animalsRow) {
                for(Animal animal : animalsCell) {
                    animal.GoToNextLoop();
                }
            }
        }
    }
    public void MoveAnimals() {
        for(int  i = 0; i < animalsField.size(); ++i ){
            for(int k = 0; k < animalsField.get(i).size();++k) {
                for(int l = 0; l < animalsField.get(i).get(k).size();)
                {
                    Animal animal =  animalsField.get(i).get(k).get(l);
                    if(animal.IsMoving()) {
                        ++l;
                        continue;
                    }
                    int shift = 0;
                    Animal.AnimalsType type = animal.GetType();
                    if (type == Animal.AnimalsType.wolfType)
                        shift = 2;
                    else
                        shift = 1;
                    Direction direction = animal.GetDirection();
                    Coordinates coordinates = animal.GetCoordinates();
                    switch (direction){
                        case Up -> {
                            coordinates.ChangeCoordinates(0, shift );
                            if(!IsField(coordinates))
                                coordinates.SetY(0);
                        }
                        case Down -> {
                            coordinates.ChangeCoordinates(0,-shift);
                            if(!IsField(coordinates))
                                coordinates.SetY(GetMaxY());
                        }
                        case Left ->{
                            coordinates.ChangeCoordinates(-shift,0);
                            if(!IsField(coordinates))
                                coordinates.SetX(GetMaxX());
                        }
                        case Right ->{
                            coordinates.ChangeCoordinates(shift,0);
                            if(!IsField(coordinates))
                                coordinates.SetX(0);
                        }
                    }
                    animal.Move(coordinates);
                    int x = coordinates.GetX();
                    int y = coordinates.GetY();
                    animalsField.get(x).get(y).add(animal);
                    animalsField.get(i).get(k).remove(animal);
                }
            }
        }
    }

    public void EatAnimals(){
        for(ArrayList<ArrayList<Animal>> animalsRow: animalsField){
            for(ArrayList<Animal> animalsCell : animalsRow) {
                if(!CheckWolfRabbitOneCell(animalsCell))
                    continue;
                int countRabbits = DeleteRabbits(animalsCell);
                int index = 0;
                if(animalsCell.size() > 1)
                    index = FindOlderWolf(animalsCell);
                for (int i = 0; i < countRabbits;++i)
                    animalsCell.get(index).Eat();

            }
        }
    }

    public void ReproduceAnimals() {
        for(int k = 0; k < animalsField.size(); ++k){
            for(int l =0; l < animalsField.get(k).size(); ++l ) {
                for(int i =0; i < animalsField.get(k).get(l).size(); ++i) {
                    Animal animal = animalsField.get(k).get(l).get(i);
                    Animal child = animal.Reproduce();
                    if (child != null)
                        animalsField.get(k).get(l).add(child);
                }
            }
        }
    }

    public void DieAnimals() {
        for(int k = 0; k < animalsField.size(); ++k){
            for(int l =0; l < animalsField.get(k).size(); ++l ) {
                for(int i =0; i < animalsField.get(k).get(l).size(); ++i)
                {
                    Animal animal = animalsField.get(k).get(l).get(i);
                    if(animal.Die()){
                        animalsField.get(k).get(l).remove(animal);
                        --i;
                    }
                }
            }
        }
    }

    public boolean IsField(Coordinates iCoordinates) {
        final int x = iCoordinates.GetX();
        final int y = iCoordinates.GetY();
        if(x >= N || x < 0 || y >= M || y < 0)
            return false;
        return true;
    }

    public int GetMaxY(){
        return M - 1;
    }

    public int GetMaxX(){
        return N - 1;
    }
}
