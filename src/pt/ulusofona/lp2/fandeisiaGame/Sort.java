package pt.ulusofona.lp2.fandeisiaGame;
import java.util.*;
import java.lang.*;
import java.io.*;

public class Sort implements Comparator<Creature> {

        public int compare(Creature a, Creature b){
            return a.id - b.id;
        }
}

