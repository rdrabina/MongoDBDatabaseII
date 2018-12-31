package pl.edu.agh.bd.mongo;

public class Main {
    public static void main(String[] args) {
        Homework homework = new Homework();

        if (args.length != 0) {
            switch (args[0]) {
                case "simpleQuery":
                    homework.simpleQuery();
                    break;
                case "aggregateQuery":
                    homework.aggregateQuery();
                    break;
                case "mapReduceQuery":
                    System.out.println("Map reduce could take a while.");
                    homework.mapReduceQuery();
                    break;
                default:
                    System.out.println("Invalid parameter.");
            }
        } else {
            homework.simpleQuery();
            homework.aggregateQuery();

            System.out.println("Map reduce could take a while.");
            homework.mapReduceQuery();
        }
    }
}
